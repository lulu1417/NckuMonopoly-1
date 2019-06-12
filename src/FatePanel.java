import java.awt.AlphaComposite;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import ingame.Game;
import ingame.GraphicItem;
import sound.Music;

public class FatePanel extends MainPanel{
	//ctor
	public FatePanel(Dimension dim, int type) {
		super(dim);
		this.type = type;
		this.graphicItems = new ArrayList<GraphicItem>();
		this.leftPressed = false;
		this.middlePressed = false;
		this.rightPressed = false;
		this.music = new Music("/Enter_the_Party.wav");
		FatePanel self = this;
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					self.leftPressed = true;
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					self.middlePressed = true;
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					self.rightPressed = true;
				}
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					self.leftPressed = false;
				} else if (e.getButton() == MouseEvent.BUTTON2) {
					self.middlePressed = false;
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					self.rightPressed = false;
				}
			}
		});
		
	}
	public void paint(Graphics g) {
		double sc = (double)this.getWidth() / Game.Width;
		//sort
		Collections.sort(this.graphicItems, new Comparator<GraphicItem>() {
			public int compare(GraphicItem a, GraphicItem b) 
			{
				if(a.getZ() > b.getZ()) return 1;
				if(a.getZ() == b.getZ()) return 0;
				return -1;
			}
		});
		//draw
		for(GraphicItem graphicItem: this.graphicItems) graphicItem.draw(g,sc);
		//draw component
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
	}
	protected void gameEnd(int point) {
		this.music.check();
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
	protected boolean getLeftPressed() {
		return this.leftPressed;
	}
	protected boolean getMiddlePressed() {
		return this.middlePressed;
	}
	protected boolean getRightPressed() {
		return this.rightPressed;
	}
	protected int type;
	protected ArrayList<GraphicItem> graphicItems;
	private boolean leftPressed, middlePressed, rightPressed;
	private Music music;
}