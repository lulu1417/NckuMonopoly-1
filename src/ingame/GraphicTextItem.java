package ingame;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.URL;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class GraphicTextItem implements GraphicItem{
	//ctor
	public GraphicTextItem(int x, int y, int fontSize, String text, ArrayList<GraphicItem> itemList) {
		this.pos = new Point(x, y);
		this.text = text;
		this.fontSize = fontSize;
		itemList.add(this);
	}
	
	//method
	@Override
	public void draw(Graphics g, double sc) {
		g.setFont(new Font("Microsoft Jhenghei", Font.BOLD, fontSize)); 
		g.drawString(text, this.drawX(sc), this.drawY(sc));
	}
	protected int drawH(double sc) {
		return (int) (fontSize*sc);
	}
	protected int drawX(double sc) {
		return (int) (pos.getX() * sc);
	}
	protected int drawY(double sc) {
		return (int) (pos.getY() * sc - drawH(sc) * 0.5);
	}
	
	//get-set
	public void setPosition(Point pos) {
		this.pos.setLocation(pos);
	}
	public void setText(String text) {
		this.text = text;
	}
	//var
	protected Point pos;
	protected int fontSize;
	protected String text;
}
