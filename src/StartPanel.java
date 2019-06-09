import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JPanel;

import ingame.Game;
import ingame.GraphicImgItem;
import ingame.GraphicItem;

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
		//background
		{
			this.bg = new GraphicImgItem(Game.Width/2, Game.Height/2, Game.Width, Game.Height, "/start.png", null);
		}
	}
	//method
	public void paint(Graphics g) {
		double sc = (double)this.getWidth() / Game.Width;
		bg.draw(g,sc);
		super.paintComponents(g);
	}
	//var
	private GraphicItem bg;
}
