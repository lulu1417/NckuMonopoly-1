import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import ingame.Game;
import ingame.GameState;

public class MainWindow extends JFrame {
	//ctor
	public MainWindow() {
		//mainwindow
		super();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); //size
		this.setBounds(0, 0, (int) dim.getWidth(), (int) dim.getHeight());
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		this.setUndecorated(true);
		//show
		this.setVisible(true);
	}
	//method
	public void changePanel(GameState newGamestate, int fateType) throws Exception {
		Dimension dim = this.getSize();
		//panel
		if(newGamestate == GameState.START) {
			if(Game.gamestate != GameState.START) {
				if(this.playingPanel != null) this.remove(this.playingPanel);
				this.playingPanel = null;
				if(this.fatePanel != null) this.remove(this.fatePanel);
				this.fatePanel = null;
				this.startPanel = new StartPanel(dim);
				this.add(this.startPanel);
			}
		} else if(newGamestate == GameState.FATE) {
			if(Game.gamestate != GameState.FATE) {
				if(this.playingPanel != null) this.remove(this.playingPanel);
				this.playingPanel = null;
				if(this.startPanel != null) this.remove(this.startPanel);
				this.startPanel = null;
				switch(fateType) {
				case 1:
					this.fatePanel = new FatePanel1(dim);
					break;
				case 2:
					this.fatePanel = new FatePanel2(dim);
					break;
				case 3:
					this.fatePanel = new FatePanel3(dim);
					break;
				default:
					throw new Exception("Fate type must be provided:\n"
							+ "1: find rabbit\n"
							+ "2: knowledge championship\n"
							+ "3: get hearts\n");
				}
				this.add(this.fatePanel);
			}
		} else {
			if(Game.gamestate == GameState.START || Game.gamestate == GameState.INIT || Game.gamestate == GameState.FATE) {
				if(this.startPanel != null) this.remove(this.startPanel);
				this.startPanel = null;
				if(this.fatePanel != null) this.remove(this.fatePanel);
				this.fatePanel = null;
				this.playingPanel = new PlayingPanel(dim);
				this.add(this.playingPanel);
			}
		}
	}
	//get-set
	public StartPanel getStartPanel() {
		return startPanel;
	}
	public PlayingPanel getPlayingPanel() {
		return playingPanel;
	}
	public FatePanel getFatePanel() {
		return fatePanel;
	}
	//var
	private StartPanel startPanel;
	private PlayingPanel playingPanel;
	private FatePanel fatePanel;
}
