import java.awt.Dimension;

import javax.swing.JPanel;

import ingame.Game;

public class StartPanel extends MainPanel{
	//ctor
	public StartPanel(Dimension dim) {
		super(dim);
		//start button
		{
			int w = 250, h = 100;
			double sc = (double)this.getWidth() / Game.Width;
			ClickButton startButton = new ClickButton(Game.Width/2, Game.Height/2, w, h, sc, "開始遊戲","Start");
			this.add(startButton);
		}
	}
	//var
}
