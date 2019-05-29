import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import ingame.Game;
import ingame.GraphicItem;

public class FatePanel extends MainPanel{
	//ctor
	public FatePanel(Dimension dim, int type) {
		super(dim);
		this.type = type;
		this.graphicItems = new ArrayList<GraphicItem>();
	}
	public void paint(Graphics g) {
		double sc = (double)this.getWidth() / Game.Width;
		for(GraphicItem graphicItem: this.graphicItems) graphicItem.draw(g,sc);
		Graphics2D g2 = (Graphics2D)g;
		AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 1.0);
		g2.setComposite(ac);
		super.paintComponents(g);
	}
	public void doTick() {
		//timer
		for(int i=0; i<this.graphicItems.size(); ++i) this.graphicItems.get(i).timerRun();
		//dead
		Iterator<GraphicItem> it = this.graphicItems.iterator();
		while (it.hasNext()) {
		    GraphicItem graphicItem = it.next();
		    if(graphicItem.isDead()) it.remove();
		}
	};
	protected void gameEnd(int point) {
		Game.signals.add("Fate ended: "+ this.type + " " + point);
	}
	protected Point mouse() {
		double sc = (double)this.getWidth() / Game.Width;
		Point mouse_pos_temp = MouseInfo.getPointerInfo().getLocation();
		Point mouse_pos = new Point(
				(int) ((mouse_pos_temp.getX()-this.getLocationOnScreen().getX())/sc),
				(int) ((mouse_pos_temp.getY()-this.getLocationOnScreen().getY())/sc)
				);
		return mouse_pos;
	}
	protected int type;
	protected ArrayList<GraphicItem> graphicItems;
}