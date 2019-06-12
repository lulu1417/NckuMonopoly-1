import java.awt.Point;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

import ingame.Cell;
import ingame.Game;
import ingame.GameState;
import ingame.GraphicImgItem;
import ingame.GraphicItem;
import ingame.GraphicTextItem;
import ingame.Player;
import sound.Music;
import sound.Sound;

public class NckuMonopoly {

	//main
	public static void main(String[] args) {
		NckuMonopoly game = new NckuMonopoly();
	}

	//ctor
	public NckuMonopoly() {
		mainW = new MainWindow();
		this.setGameState(GameState.START);
		this.steppedScore = 0;
		this.nextMoveNoEvent = false;
		this.music = new Music("/bgm.wav");
		this.music.check();
		this.musicPlayingBeforeFate = true;
		this.soundOff = false;

		//thread
		while(true) {

			//timer
			if(Game.gamestate == GameState.START) mainW.getStartPanel().timerRun();
			for(int i=0; i<Game.graphicItems.size(); ++i) Game.graphicItems.get(i).timerRun();
			
			//focus
			switch (Game.gamestate) {
			case START:
				mainW.getStartPanel().requestFocus();
				break;
			case FATE:
				mainW.getFatePanel().requestFocus();
				break;
			default:
				mainW.getPlayingPanel().requestFocus();
				break;
			}
			
			//receive signals
			for(String signal: Game.signals) {
				System.out.println("Got signal: "+signal);
				if(signal.equals("Button clicked: Exit")) mainW.dispatchEvent(new WindowEvent(mainW, WindowEvent.WINDOW_CLOSING));
				else if(signal.equals("Button clicked: Back")) {
					pauseBgMusic();
					this.setGameState(GameState.START);
				}
				if(Game.gamestate!=GameState.START && Game.gamestate!=GameState.FATE) {
					if(signal.equals("Button clicked: Music")) this.changeBgMusicState();
					else if (signal.equals("Button clicked: Sound")) this.changeSoundState();
				}
				switch (Game.gamestate) {
				case START:
					if(signal.equals("Button clicked: Start")) this.start();
					break;
				case ROLLING:
					if(signal.equals("Button clicked: Roll")) {
						mainW.getPlayingPanel().deleteRollingButton();
						this.tickStart();
					}
					break;
				case EVENT:
					if(signal.startsWith("Button clicked: Select score:")) {
						mainW.getPlayingPanel().deleteSelections();
						Random rng = new Random();
						if(signal.endsWith("lesson")) {
							currentPlayer.addLesson(this.steppedScore);
						} else if(signal.endsWith("club")) {
							currentPlayer.addClub(this.steppedScore);
						} else {
							currentPlayer.addLove(this.steppedScore);
						}
					if(!soundOff) {
						if(this.steppedScore>0) Sound.playSound("/addPoints.wav");
						else Sound.playSound("/deduct.wav");
						}
						this.tickStart(21);
					} else if(signal.startsWith("Button clicked: Select die point:")) {
						mainW.getPlayingPanel().deleteDieSelections();
						String diePointStr = signal.substring("Button clicked: Select die point: ".length());
						int fateDiePoint = Integer.valueOf(diePointStr);
						this.rollingNum = fateDiePoint;
						this.tickStart(111);
					}
					break;
				case FATE:
					try {
						Scanner scanner = new Scanner(signal);
						String fateIdentifier = scanner.next() + " " + scanner.next();
						if(fateIdentifier.equals("Fate ended:")) {
							int fateType = scanner.nextInt();
							String fateTypeStr;
							int fatePoint = scanner.nextInt();
							if(!soundOff) {
								if(fatePoint>0) Sound.playSound("/addPoints.wav");
								else Sound.playSound("/deduct.wav");
							}
							switch (fateType) {
							case 1:
								currentPlayer.addLesson(fatePoint);
								fateTypeStr = "課業";
								break;
							case 2:
								currentPlayer.addClub(fatePoint);
								fateTypeStr = "社團";
								break;
							default:
								currentPlayer.addLove(fatePoint);
								fateTypeStr = "愛情";
								break;
							}
							String str = "小遊戲結束，獲得" + fateTypeStr + "分數" + fatePoint + "分";
							this.setGameState(GameState.EVENT);
							mainW.getPlayingPanel().showEventName(str, Game.Width/2, Game.Height/2-100, 60);
							this.tickStart(21);
						}
						scanner.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
					break;
				default:
				}
			}
			Game.signals.clear();

			//mouse
			if(Game.gamestate != GameState.START && Game.gamestate != GameState.END && Game.gamestate != GameState.FATE) {
				Point mouse_pos = mainW.getPlayingPanel().getMouseLocation();
				for(Cell cell_pos: Game.cells) {
					if((Math.abs(mouse_pos.getX() - cell_pos.getX()) < 50) && (Math.abs(mouse_pos.getY() - cell_pos.getY()) < 50)) {
						mainW.getPlayingPanel().showCellName(cell_pos, cell_pos.getName());
						break;
					}
				}
			}
			
			//tick execution
			switch (Game.gamestate) {
			case ROLLING:
				if(ticking) {
					++tick;
					final int times = 26;
					int standardTick = 0;
					int standardTickAdd = 1;
					for(int i=0; i<times; ++i) {
						standardTick += standardTickAdd;
						if(i<times/2) standardTickAdd = 1;
						else standardTickAdd = i-times/2;
						if(tick == standardTick) {
							//random choose new die number
							Random rng = new Random();
							int newNum;
							if(this.rollingNum==0) newNum = rng.nextInt(6)+1;
							else {
								newNum = rng.nextInt(5)+1;
								if(newNum>=this.rollingNum) ++newNum;
							}
							int cheatNumber = mainW.getPlayingPanel().getCheatNumber();
							this.rollingNum = newNum = cheatNumber==0 ? newNum : cheatNumber; //debug
							//die img
							String dieImg = "/die" + newNum + ".png";
							GraphicItem die = new GraphicImgItem(Game.Width/2, Game.Height/2, 100, 100, dieImg, Game.graphicItems);
							if(!soundOff) Sound.playSound("/rolling.wav");
							die.setLifeTime(i==times-1 ? 50 : standardTickAdd);
							if(i == times-1) { //last time
								//die number hint
								String hintString = "擲出點數：" + newNum;
								GraphicItem hint = new GraphicTextItem(Game.Width/2, Game.Height/2-80, 30, hintString, Game.graphicItems);
								hint.setLifeTime(50);
								//change state
								this.setGameState(GameState.MOVING);
								this.tickStart(-30);
							}
						} else if(tick < standardTick) break;
					}
				}
				break;
			case MOVING:
				if(ticking) {
					if(++tick>=20) {
						if(!soundOff) Sound.playSound("/move.wav");
						if(this.rollingNum>0) this.currentPlayer.moveToNext();
						if(--this.rollingNum<=0) this.setGameState(GameState.EVENT);
						this.tickStart();
					}
				}
				break;
			case EVENT:
				if(this.nextMoveNoEvent) { //event won't be triggered in this move
					this.nextMoveNoEvent = false;
					tickStart(21);
				}
				if(ticking) {
					if(++tick==20) {
						tickPause();
						Cell steppedCell = currentPlayer.getCurrentCell();
						switch(steppedCell.getCellType()) {
							case NOTHING: //no event
								mainW.getPlayingPanel().showEventName("這裡什麼都沒有...", Game.Width/2, Game.Height/2-100, 90);
								if(!soundOff) Sound.playSound("/nothing.wav");
								tickStart(21);
								break;
							case SELECT: //select event
								String selections[];
								switch(steppedCell.getSelectPolicy()) {
									case THREE: {
										String selections_temp[] = {"課業", "社團", "愛情"};
										selections = selections_temp;
										} break;
									case LESSON: {
										String selections_temp[] = {"課業","",""}; 
										selections = selections_temp;
										} break;
									case CLUB: {
										String selections_temp[] = {"","社團",""}; 
										selections = selections_temp;
										} break;
									default: {
										String selections_temp[] = {"","","愛情"}; 
										selections = selections_temp;
										} break;
								}
								int score = steppedCell.getScore();
								this.steppedScore = score;
								if(!soundOff) {
									if(score>0) Sound.playSound("/goodluck.wav");
									else Sound.playSound("/badluck.wav");
								}
								for(int i=0;i<selections.length;++i)
									if(!selections[i].equals(""))
										selections[i] = selections[i] + (score>0?"+":"") + score;
								mainW.getPlayingPanel().createSelections(steppedCell.getMessage(),selections[0],selections[1],selections[2]);
								break;
							case START: //start event
								mainW.getPlayingPanel().showEventName("回到起點，獲得一百元", Game.Width/2, Game.Height/2-100, 90);
								if(!soundOff) {
									Sound.playSound("/goodluck.wav");
									Sound.playSound("/addMoney.wav");
								}
								currentPlayer.addMoney(100);
								tickStart(21);
								break;
							case CHANCE: //chance
								int chanceCount = 7;
								Random rng = new Random();
								int chanceNum = rng.nextInt(chanceCount);
								switch (chanceNum) {
								case 0: {
									mainW.getPlayingPanel().showEventName("Java翹課，課業減半", Game.Width/2, Game.Height/2-100, 90);
									currentPlayer.addLesson(-currentPlayer.getLesson()/2);
									if(!soundOff) {
										Sound.playSound("/badluck.wav");
										Sound.playSound("/deduct.wav");
									}
									tickStart(21);
								} break;
								case 1: {
									mainW.getPlayingPanel().showEventName("對中發票，獲得200元", Game.Width/2, Game.Height/2-100, 90);
									currentPlayer.addMoney(200);
									if(!soundOff) {
										Sound.playSound("/goodluck.wav");
										Sound.playSound("/addMoney.wav");
									}
									tickStart(21);
								} break;
								case 2: {
									mainW.getPlayingPanel().showEventName("撿到50元", Game.Width/2, Game.Height/2-100, 90);
									currentPlayer.addMoney(50);
									if(!soundOff) {
										Sound.playSound("/goodluck.wav");
										Sound.playSound("/addMoney.wav");
									}
									tickStart(21);
								} break;
								case 3: {
									String chanceSelections[] = {"課業","社團","愛情"};
									int chanceScore = -10;
									this.steppedScore = chanceScore;
									for(int i=0;i<chanceSelections.length;++i)
										if(!chanceSelections[i].equals(""))
											chanceSelections[i] = chanceSelections[i] + chanceScore;
									mainW.getPlayingPanel().createSelections("衣服穿反",chanceSelections[0],chanceSelections[1],chanceSelections[2]);
									if(!soundOff) {
										Sound.playSound("/badluck.wav");
										Sound.playSound("/deduct.wav");
									}
								} break;
								case 4: {
									mainW.getPlayingPanel().showEventName("考上研究所，學業+50", Game.Width/2, Game.Height/2-100, 90);
									currentPlayer.addLesson(50);
									if(!soundOff) {
										Sound.playSound("/goodluck.wav");
										Sound.playSound("/addPoints.wav");
									}
									tickStart(21);
								} break;
								case 5: {
									mainW.getPlayingPanel().createDieSelections("搭乘台南Uber，自由選擇步數前進");
									if(!soundOff) Sound.playSound("/goodluck.wav");
								} break;
								case 6: {
									mainW.getPlayingPanel().showEventName("沒注意行人號誌，發生車禍", Game.Width/2, Game.Height/2-100, 90);
									if(!soundOff) Sound.playSound("/badluck.wav");
									tickStart(131);
								} break;
								default:
									break;
								}
								break;
							case FATE: //fate event
								mainW.getPlayingPanel().showEventName("命運：" + steppedCell.getMessage(), Game.Width/2, Game.Height/2-100, 60);
								tickStart(51);
								break;
							case HOSPITAL: //hospital event
								mainW.getPlayingPanel().showEventName(steppedCell.getMessage(), Game.Width/2, Game.Height/2-100, 60);
								currentPlayer.setFreezeCount(2);
								tickStart(21);
								break;
							default: //shop event
								if(!soundOff) {
									Sound.playSound("/goodluck.wav");
									Sound.playSound("/addMoney.wav");
								}
								String ShopSelections[] = {"課業+","社團+","愛情+"};
								int ExchangeScore = currentPlayer.getMoney();
								this.steppedScore = ExchangeScore;
								System.out.println("");
								for(int i=0;i<ShopSelections.length;++i)
									if(!ShopSelections[i].equals("+"))
										ShopSelections[i] = ShopSelections[i] + ExchangeScore;
								mainW.getPlayingPanel().createSelections("商店：可將金錢換為任一學分分數", ShopSelections[0],ShopSelections[1],ShopSelections[2]);
								currentPlayer.setMoney(0);
								break;
						}
					} else if(tick==50) { //normal end phase
						//lose check
						if(currentPlayer.getLesson()<=0) {
							if(!soundOff) Sound.playSound("/badluck.wav");
							mainW.getPlayingPanel().showEventName("課業歸零，二一出局", Game.Width/2, Game.Height/2-100, 90);
							currentPlayer.setEliminated(true);
						}
						//win check
						int threshlod = 300;
						if(currentPlayer.getLesson()>=threshlod //player's score is bigger than threshold, this player win
							|| currentPlayer.getLove()>=threshlod
							|| currentPlayer.getClub()>=threshlod) {
							this.setGameState(GameState.END);
							if(!soundOff) Sound.playSound("/win.wav");
							tickStart();
						} else if(Player.getEliminatedPlayerCount() == Game.playerCount-1) { //only one player isn't eliminated, this player win
							for(Player player: Game.players) {
								if(!player.getEliminated()) {
									this.currentPlayer = player;
									break;
								}
							}
							this.setGameState(GameState.END);
							if(!soundOff) Sound.playSound("/win.wav");
							tickStart();
						}
						//next player's rolling state
						if(Game.gamestate != GameState.END) {
							//choose next player
							int id = this.currentPlayer.getID();
							for(int i=0; i<Game.playerCount; ++i) {
								if(++id>=Game.playerCount) id=0;
								this.currentPlayer = Game.players.get(id);
								if(!currentPlayer.getEliminated()) break; //not select eliminated player
								if(i==Game.playerCount-1) { //all player got eliminated, last player win
									setGameState(GameState.END);
									if(!soundOff) Sound.playSound("/win.wav");
									tickStart();
								}
							}
							//select next player and unselect others
							for(Player player: Game.players) {
								if(player == currentPlayer) player.select();
								else player.unselect();
							}
							int freezeCount = currentPlayer.checkFreeze();
							if(freezeCount>0) { //next player is being frozen
								this.nextMoveNoEvent = true;
								String showStr = "此玩家被暫停，剩餘" + (freezeCount-1) + "回合";
								if(!soundOff) Sound.playSound("/badluck.wav");
								mainW.getPlayingPanel().showEventName(showStr, Game.Width/2, Game.Height/2-100, 90);
							} else { //next player is normal
								this.setGameState(GameState.ROLLING);
								this.tickPause();
								mainW.getPlayingPanel().createRollingButton();
							}
						}
					} else if(tick==110) { //fate
						Cell steppedCell = currentPlayer.getCurrentCell();
						this.setGameState(GameState.FATE, steppedCell.getScore());
					} else if(tick==112) { //chance: choose die
						//die img
						String dieImg = "/die" + this.rollingNum + ".png";
						GraphicItem die = new GraphicImgItem(Game.Width/2, Game.Height/2, 100, 100, dieImg, Game.graphicItems);
						die.setLifeTime(50);
						//die number hint
						String hintString = "選擇點數：" + this.rollingNum;
						GraphicItem hint = new GraphicTextItem(Game.Width/2, Game.Height/2-80, 30, hintString, Game.graphicItems);
						hint.setLifeTime(50);
						//change state
						this.nextMoveNoEvent = true;
						this.setGameState(GameState.MOVING);
						this.tickStart(-30);
					} else if(tick==160) { //chance: hospitalized
						currentPlayer.moveTo(21);
						this.rollingNum = 0;
						this.setGameState(GameState.MOVING);
						this.tickStart(-30);
					}
				}
				break;
			case FATE:
				FatePanel fatePanel = mainW.getFatePanel();
				fatePanel.doTick();
				fatePanel.repaint();
				break;
			case END:
				if(ticking) {
					if(++tick>=30) {
						tickPause();
						mainW.getPlayingPanel().createEndList(currentPlayer.getID());
					}
				}
			default:
				break;
			}

			//dead
			Iterator<GraphicItem> it = Game.graphicItems.iterator();
			while (it.hasNext()) {
			    GraphicItem graphicItem = it.next();
			    if(graphicItem.isDead()) it.remove();
			}
			
			//repaint
			mainW.repaint();
			
			//time out
			try {
				Thread.sleep(33); //30 fps
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//method
	public void start() {
		this.setGameState(GameState.ROLLING); //page
		//re-initialize items
		this.steppedScore = 0;
		this.nextMoveNoEvent = false;
		this.music = new Music("/bgm.wav");
		this.music.check();
		this.musicPlayingBeforeFate = true;
		this.soundOff = false;
		Game.graphicItems = new ArrayList<GraphicItem>();
		Game.players = new ArrayList<Player>();
		//bg
		GraphicImgItem bg = new GraphicImgItem(Game.Width/2, Game.Height/2, Game.Width, Game.Height, "/bg.png", Game.graphicItems);
		//players
		Player.resetEliminatedPlayerCount();
		for(int i=0; i<Game.playerCount; ++i) {
			String playerImg = "/player" + (i+1) + ".png";
			Player player = new Player(77, 120, playerImg, 100, 100, 100, 50, 0, i, Game.graphicItems);
			player.createScoreBoard(i<2?20:1300, i%2==0?30:690, "/scoreboard"+ (i+1) +".png", Game.graphicItems);
			if(i==0) this.currentPlayer = player;
			Game.players.add(player);
			if(i==Game.playerCount-1) {
				try {
					player.moveTo(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		for(Player player: Game.players) {
			if(player == currentPlayer) player.select();
			else player.unselect();
		}
		//roll button
		mainW.getPlayingPanel().createRollingButton();
		//tick
		this.tickPause();
		//roll
		this.rollingNum = 0;
		//music
		playBgMusic();
	}
	public void changeSoundState() {
		soundOff = !soundOff;
		mainW.getPlayingPanel().changeSoundButton(!soundOff);
	}
	public void changeBgMusicState() {
		music.check();
		boolean isPlaying = music.getPlaying();
		mainW.getPlayingPanel().changeMusicButton(isPlaying);
	}
	public void pauseBgMusic() {
		if(music.getPlaying()) this.changeBgMusicState();
	}
	public void playBgMusic() {
		if(!music.getPlaying()) this.changeBgMusicState();
	}
	public void tickStart() {
		this.tickStart(0);
	}
	public void tickStart(int init_tick) {
		this.ticking = true;
		this.tick = init_tick;
	}
	public void tickPause() {
		this.ticking = false;
	}
	public void tickContinue() {
		this.ticking = true;
	}
	
	//get-set
	public void setGameState(GameState gamestate) {
		this.setGameState(gamestate, 0);
	}
	public void setGameState(GameState gamestate, int fateType) {
		//debug
		System.out.println("Gamestate changed: "
						+ Game.gamestate.toString()
						+ " to " +
						gamestate.toString());
		//music
		boolean restorePlaying = false;
		if(Game.gamestate != GameState.FATE && gamestate == GameState.FATE) {
			this.musicPlayingBeforeFate = this.music.getPlaying();
			this.pauseBgMusic();
		} else if(Game.gamestate == GameState.FATE && gamestate != GameState.FATE) {
			restorePlaying = true;
		}
		//main window
		try {
			mainW.changePanel(gamestate, fateType);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//music
		if(restorePlaying) {
			mainW.getPlayingPanel().changeMusicButton(false);
			if(this.musicPlayingBeforeFate) this.playBgMusic();
			else this.pauseBgMusic();
		}
		Game.gamestate = gamestate;
	}
	//var
	private MainWindow mainW;
	private long tick;
	private boolean ticking, nextMoveNoEvent;
	private Player currentPlayer;
	private int rollingNum, steppedScore;
	private Music music;
	private boolean musicPlayingBeforeFate;
	private boolean soundOff;
}
