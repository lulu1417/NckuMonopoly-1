package ingame;

import java.awt.Graphics;
import java.awt.Point;

public class Player extends GraphicItem {
	//ctor
	public Player(int w, int h, String img_path, int lesson, int club, int love, int money, int currentCell, int id) {
		super((int) Game.cell[currentCell].getX(), (int) Game.cell[currentCell].getY(), w, h, img_path);
		this.lesson = lesson;
		this.club = club;
		this.love = love;
		this.money = money;
		this.currentCell = currentCell;
		this.id = id;
	}
	//method
	protected int drawY(double sc) {
		return (int) (rect.getY() * sc - drawH(sc) * 0.9);
	}
	public void moveToNext() {
		if(++this.currentCell>=Game.cell.length) this.currentCell=0;
		this.setPosition(Game.cell[this.currentCell]);
	}
	//var
	private int currentCell, id;
	private int lesson, club, love, money;
}
