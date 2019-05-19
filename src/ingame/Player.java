package ingame;

import java.awt.Graphics;
import java.awt.Panel;
import java.awt.Point;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Player extends GraphicImgItem {
	//ctor
	public Player(int w, int h, String img_path, int lesson, int club, int love, int money, int currentCell, int id, ArrayList<GraphicItem> itemList) {
		super((int) Game.cells[currentCell].getX(), (int) Game.cells[currentCell].getY(), w, h, img_path, itemList);
		this.lesson = lesson;
		this.club = club;
		this.love = love;
		this.money = money;
		this.currentCell = currentCell;
		this.id = id;
		this.scoreboard = null;
	}
	//method
	@Override
	protected int drawY(double sc) {
		return (int) (rect.getY() * sc - drawH(sc) * 0.9);
	}
	public void moveTo(int cell) throws Exception {
		if(cell >= Game.cells.length) throw new Exception("This cell is not exist!");
		this.currentCell = cell;
		this.setPosition(Game.cells[this.currentCell]);
	}
	public void moveToNext() {
		int nextCell = this.currentCell+1;
		try {
			if(nextCell >= Game.cells.length) this.moveTo(0);
			else this.moveTo(nextCell);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void createScoreBoard(int x, int y, String img_path, ArrayList<GraphicItem> itemList) {
		this.scoreboard = new PlayerScoreboard(x, y, img_path, this.lesson, this.club, this.love, this.money, itemList);
	}
	public void addLesson(int add) {
		this.lesson += add;
		this.scoreboard.setLesson(this.lesson);
		this.scoreboard.showLessonAddition(add);
	}
	public void addClub(int add) {
		this.club += add;
		this.scoreboard.setClub(this.club);
		this.scoreboard.showClubAddition(add);
	}
	public void addLove(int add) {
		this.love += add;
		this.scoreboard.setLove(this.love);
		this.scoreboard.showLoveAddition(add);
	}
	public void addMoney(int add) {
		this.money += add;
		this.scoreboard.setMoney(this.money);
		this.scoreboard.showMoneyAddition(add);
	}
	//get-set
	public void setLesson(int lesson) {
		this.lesson = lesson;
		this.scoreboard.setLesson(lesson);
	}
	public void setClub(int club) {
		this.club = club;
		this.scoreboard.setClub(club);
	}
	public void setLove(int love) {
		this.love = love;
		this.scoreboard.setLove(love);
	}
	public void setMoney(int money) {
		this.money = money;
		this.scoreboard.setMoney(money);
	}
	public int getLesson() {
		return this.lesson;
	}
	public int getClub() {
		return this.club;
	}
	public int getLove() {
		return this.love;
	}
	public int getMoney() {
		return this.money;
	}
	public Cell getCurrentCell() {
		return Game.cells[this.currentCell];
	}
	public int getID() {
		return this.id;
	}
	//var
	private int currentCell, id;
	private int lesson, club, love, money;
	private PlayerScoreboard scoreboard;
}
