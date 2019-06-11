import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import ingame.Game;
import ingame.GraphicImgItem;
import ingame.GraphicItem;

public class StartPanel extends MainPanel{
	//ctor
	public StartPanel(Dimension dim) {
		super(dim);
		double sc = (double)this.getWidth() / Game.Width;
		//start button
		{
			int w = 800, h = 240;
			int x=Game.Width/2, y=Game.Height/2-160;
			int period = 180, max_angle = 12;
			this.titleTimer = 0;
			this.title = new GraphicImgItem(x, y, w, h, "/title.png", null) {
				@Override
				public void timerRun() {
					super.timerRun();
					if(++titleTimer>=period) titleTimer=0;
				}
				@Override
				public void draw(Graphics g, double sc) {
					double angle;
					if(titleTimer>=period/4*3) angle = (titleTimer-period);
					else if(titleTimer<=period/4) angle = titleTimer;
					else angle = (period/2 - titleTimer);
					angle *= Math.PI/180/period*4*max_angle;
					Graphics2D g2 = (Graphics2D)g;
					g2.rotate(angle,x*sc,y*sc);
					super.draw(g, sc);
					g2.rotate(-angle,x*sc,y*sc);
				}
			};
		}
		//start button
		{
			int w = 250, h = 80;
			ClickButton startButton = new ClickButton(Game.Width/2, Game.Height/2+100, w, h, sc, "開始遊戲", "Start");
			this.add(startButton);
		}
		//end button
		{
			int w = 250, h = 80;
			ClickButton endButton = new ClickButton(Game.Width/2, Game.Height/2+220, w, h, sc, "退出遊戲", "Exit");
			this.add(endButton);
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
		title.draw(g,sc);
		super.paintComponents(g);
	}
	public void timerRun() {
		title.timerRun();
	}
	//var
	private GraphicItem bg, title;
	private int titleTimer;
}
