import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.Comparator;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import ingame.Game;
import ingame.GraphicImgItem;
import ingame.GraphicItem;
import ingame.Player;

public class PlayingPanel extends MainPanel{
	//ctor
	public PlayingPanel(Dimension dim) {
		super(dim);
		this.normalSelections = new ClickButton[3];
	}
	//method
	public void paint(Graphics g) {
		Collections.sort(Game.graphicItems, new Comparator<GraphicItem>() {
			public int compare(GraphicItem a, GraphicItem b) 
			{
				if(a.getZ() > b.getZ()) return 1;
				if(a.getZ() == b.getZ()) return 0;
				return -1;
			}
		});
		//paint
		double sc = (double)this.getWidth() / Game.Width;
		for(GraphicItem graphicItem: Game.graphicItems) graphicItem.draw(g,sc);
		Graphics2D g2 = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1.0);
		g2.setComposite(ac);
		super.paintComponents(g);
	}
	public void createRollingButton() {
		if(this.rollingButton != null) return;
		int w = 250, h = 100;
		double sc = (double)this.getWidth() / Game.Width;
		this.rollingButton = new ClickButton((Game.Width-250)/2+250, Game.Height/2, w, h, sc, "擲骰子", "Roll");
		this.add(rollingButton);
	}
	public void deleteRollingButton() {
		if(this.rollingButton == null) return;
		this.remove(rollingButton);
		this.rollingButton = null;
	}
	public void createNormalSelections() {
		int w = 500, h = 100;
		double sc = (double)this.getWidth() / Game.Width;
		for(int i=0; i<3; ++i) {
			if(this.normalSelections[i] != null) break;
			String text, signal;
			switch (i) {
			case 0:
				text = "寫點作業";
				signal = "Select lesson";
				break;
			case 1:
				text = "社團活動";
				signal = "Select club";
				break;
			default:
				text = "跟心上人聊天";
				signal = "Select love";
				break;
			}
			this.normalSelections[i] = new ClickButton((Game.Width-250)/2+250, Game.Height/2+(i-1)*(h+50), w, h, sc, text, signal);
			this.add(normalSelections[i]);
		}
	}
	public void deleteNormalSelections() {
		for(int i=0; i<3; ++i) {
			if(normalSelections[i] == null) break;
			this.remove(normalSelections[i]);
			normalSelections[i] = null;
		}
	}
	//var
	private ClickButton rollingButton;
	private ClickButton[] normalSelections;
}
