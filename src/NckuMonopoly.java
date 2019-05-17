import java.awt.Point;
import java.util.Random;

import ingame.Game;
import ingame.GameState;
import ingame.GraphicItem;
import ingame.Player;

public class NckuMonopoly {

	//main
	public static void main(String[] args) {
		NckuMonopoly game = new NckuMonopoly();
	}

	//ctor
	public NckuMonopoly() {
		mainW = new MainWindow();
		Game.gamestate = GameState.INIT;
		this.setGameState(GameState.START);
		//thread
		while(true) {
			//receive signals
			for(String signal: Game.signals) {
				System.out.println("Got signal: "+signal);
				switch (Game.gamestate) {
				case START:
					if(signal.equals("Start")) this.start();
					break;
				case ROLLING:
					if(signal.equals("Roll")) {
						mainW.getPlayingPanel().deleteRollingButton();
						this.tickStart();
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
					final int times = 30;
					int standardTick = 0;
					for(int i=0; i<times; ++i) {
						if(i<times/2) standardTick += 1;
						else if(i==times-1) standardTick += 50;
						else standardTick += i-times/2;
						if(tick == standardTick) {
							if(i == times-1) {
								mainW.getPlayingPanel().deleteDie();
								this.setGameState(GameState.MOVING);
								this.tickStart(-20);
							} else {
								Random rng = new Random();
								int newNum;
								if(this.rollingNum==0) newNum = rng.nextInt(6)+1;
								else {
									newNum = rng.nextInt(5)+1;
									if(newNum>=this.rollingNum) ++newNum;
								}
								this.rollingNum = newNum;
								mainW.getPlayingPanel().createDie(newNum);
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
				} else {
					
				}
				break;
			case EVENT:
				if(ticking) {
					
				}
				break;
			default:
				break;
			}
			//repaint
			mainW.repaint();
			//time out
			try {
				Thread.sleep(20);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//method
	public void start() {
		this.setGameState(GameState.ROLLING); //page
		//bg
		GraphicItem bg = new GraphicItem(Game.Width/2, Game.Height/2, Game.Width, Game.Height, "/bg.png");
		Game.graphicItems.add(bg);
		//players
		for(int i=0; i<Game.playerCount; ++i) {
			Player player = new Player(100, 100, "/player.png", 0, 0, 0, 0, 0, i);
			if(i==0) this.currentPlayer = player;
			Game.graphicItems.add(player);
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
