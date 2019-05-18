package ingame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.URL;

import javax.imageio.ImageIO;

public class GraphicImgItem implements GraphicItem{
	//ctor
	public GraphicImgItem(int x, int y, int w, int h, String img_path) {
		try {
			URL url = this.getClass().getResource(img_path);
	        this.img = ImageIO.read(url);
	    }
	    catch (Exception ex) {
	        System.out.println("This image file doesn't exist: "+img_path);
	    }
		this.rect = new Rectangle(x, y, w, h);
	}
	
	//method
	@Override
	public void draw(Graphics g, double sc) {
        g.drawImage(this.img, this.drawX(sc), this.drawY(sc), this.drawW(sc), this.drawH(sc), null);
	}
	protected int drawW(double sc) {
		return (int) (rect.getWidth()*sc);
	}
	protected int drawH(double sc) {
		return (int) (rect.getHeight()*sc);
	}
	protected int drawX(double sc) {
		return (int) (rect.getX() * sc - drawW(sc) * 0.5);
	}
	protected int drawY(double sc) {
		return (int) (rect.getY() * sc - drawH(sc) * 0.5);
	}
	
	//get-set
	public void setPosition(Point pos) {
		this.rect.setLocation(pos);
	}
	
	//var
	protected Rectangle rect;
	protected Image img;
}
