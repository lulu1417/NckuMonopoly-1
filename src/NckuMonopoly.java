import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import ingame.CellType;
import ingame.Game;
import ingame.GameState;
import ingame.GraphicImgItem;
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
						if(signal.endsWith("lesson")) {
							currentPlayer.setLesson(currentPlayer.getLesson()+1);
						} else if(signal.endsWith("club")) {
							currentPlayer.setClub(currentPlayer.getClub()+1);
						} else {
							currentPlayer.setLove(currentPlayer.getLove()+1);
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
					final int times = 24;
					int standardTick = 0;
					for(int i=0; i<times; ++i) {
						if(i<times/2) standardTick += 1;
						else standardTick += i-times/2;
						if(tick == standardTick) {
							Random rng = new Random();
							int newNum;
							if(this.rollingNum==0) newNum = rng.nextInt(6)+1;
							else {
								newNum = rng.nextInt(5)+1;
								if(newNum>=this.rollingNum) ++newNum;
							}
							this.rollingNum = newNum;
							mainW.getPlayingPanel().createDie(newNum);
							if(i == times-1) {
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
						mainW.getPlayingPanel().deleteDie();
						switch(currentPlayer.getCurrentCell().getCellType()) {
							case NORMAL:
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
			Player player = new Player(120, 120, "/player.png", 0, 0, 0, 5000, 0, i, Game.graphicItems);
			player.createScoreBoard(20, 20+i*190, "/scoreboard"+ (i+1) +".png", Game.graphicItems);
			if(i==0) this.currentPlayer = player;
			Game.players.add(player);
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
