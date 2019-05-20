import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import ingame.CellType;
import ingame.Game;
import ingame.GameState;
import ingame.GraphicImgItem;
import ingame.GraphicItem;
import ingame.GraphicTextItem;
import ingame.Player;

public class NckuMonopoly {

	//main
	public static void main(String[] args) {
		NckuMonopoly game = new NckuMonopoly();
	}

	//ctor
	public NckuMonopoly() {
		mainW = new MainWindow();
		this.setGameState(GameState.START);
		
		//thread
		while(true) {

			//timer
			for(int i=0; i<Game.graphicItems.size(); ++i) Game.graphicItems.get(i).timerRun();
			
			//receive signals
			for(String signal: Game.signals) {
				System.out.println("Got signal: "+signal);
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
					if(signal.startsWith("Button clicked: Select")) {
						mainW.getPlayingPanel().deleteNormalSelections();
						Random rng = new Random();
						int add = rng.nextInt(4)+2;
						if(signal.endsWith("lesson")) {
							currentPlayer.addLesson(add);
						} else if(signal.endsWith("club")) {
							currentPlayer.addClub(add);
						} else {
							currentPlayer.addLove(add);
						}
						this.tickStart(21);
					}
					break;
				default:
				}
			}
			Game.signals.clear();
			
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
							this.rollingNum = newNum;
							//die img
							String dieImg = "/die" + newNum + ".png";
							GraphicItem die = new GraphicImgItem((Game.Width-250)/2+250, Game.Height/2, 100, 100, dieImg, Game.graphicItems);
							die.setLifeTime(i==times-1 ? 50 : standardTickAdd);
							if(i == times-1) { //last time
								//die number hint
								String hintString = "擲出點數：" + newNum;
								GraphicItem hint = new GraphicTextItem((Game.Width-250)/2+250-80, Game.Height/2-50, 30, hintString, Game.graphicItems);
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
						this.currentPlayer.moveToNext();
						if(--this.rollingNum<=0) this.setGameState(GameState.EVENT);
						this.tickStart();
					}
				}
				break;
			case EVENT:
				if(ticking) {
					if(++tick==20) {
						tickPause();
						switch(currentPlayer.getCurrentCell().getCellType()) {
							case NORMAL: //normal event
								mainW.getPlayingPanel().createNormalSelections();
								break;
							default:
						}
					} else if(tick>=50) {
						int id = this.currentPlayer.getID();
						if(++id>=Game.playerCount) id=0;
						this.currentPlayer = Game.players.get(id);
						this.setGameState(GameState.ROLLING);
						this.tickPause();
						mainW.getPlayingPanel().createRollingButton();
					}
				}
				break;
			default:
				break;
			}
			
			//dead
			Iterator<GraphicItem> it = Game.graphicItems.iterator();
			while (it.hasNext()) {
			    GraphicItem graphicItem = it.next();
			    if (graphicItem.isDead()) it.remove();
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
		//bg
		GraphicImgItem bg = new GraphicImgItem(Game.Width/2, Game.Height/2, Game.Width, Game.Height, "/bg.png", Game.graphicItems);
		//players
		for(int i=0; i<Game.playerCount; ++i) {
			String playerImg = "/player" + (i+1) + ".png";
			Player player = new Player(77, 120, playerImg, 0, 0, 0, 1000, 0, i, Game.graphicItems);
			player.createScoreBoard(20, 20+i*190, "/scoreboard"+ (i+1) +".png", Game.graphicItems);
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
		//roll button
		mainW.getPlayingPanel().createRollingButton();
		//tick
		this.tickPause();
		//roll
		this.rollingNum = 0;
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
		//debug
		System.out.println("Gamestate changed: "
						+ Game.gamestate.toString()
						+ " to " +
						gamestate.toString());
		//main window
		mainW.changePanel(gamestate);
		Game.gamestate = gamestate;
	}
	//var
	private MainWindow mainW;
	private long tick;
	private boolean ticking;
	private Player currentPlayer;
	private int rollingNum;
}
