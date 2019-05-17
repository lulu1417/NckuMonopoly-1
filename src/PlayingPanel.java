import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ingame.Game;
import ingame.GraphicItem;

public class PlayingPanel extends MainPanel{
	//ctor
	public PlayingPanel(Dimension dim) {
		super(dim);
	}
	//method
	public void paint(Graphics g) {
		double sc = (double)this.getWidth() / Game.Width;
		for(GraphicItem graphicItem: Game.graphicItems) graphicItem.draw(g,sc);
		super.paintComponents(g);
	}
	public void createRollingButton() {
		if(this.rollingButton != null) return;
		int w = 250, h = 100;
		this.rollingButton = new ClickButton((this.getWidth()-w)/2, (this.getHeight()-h)/2, w, h, "Roll");
		this.add(rollingButton);
	}
	public void deleteRollingButton() {
		if(this.rollingButton == null) return;
		this.remove(rollingButton);
		this.die = null;
	}
	public void createDie(int rollingNum) {
		this.deleteDie();
		String dieImg = "/die" + rollingNum + ".png";
		this.die = new GraphicItem(Game.Width/2, Game.Height/2, 100, 100, dieImg);
		Game.graphicItems.add(die);
	}
	public void deleteDie() {
		if(this.die == null) return;
		Game.graphicItems.remove(die);
		this.die = null;
	}
	//var
	private ClickButton rollingButton;
	private GraphicItem die;
}
