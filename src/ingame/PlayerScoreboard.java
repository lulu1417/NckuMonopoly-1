package ingame;

import java.awt.Graphics;
import java.util.ArrayList;

public class PlayerScoreboard extends GraphicImgItem{
	//ctor
	public PlayerScoreboard(int x, int y, String img_path, int lesson, int club, int love, int money, ArrayList<GraphicItem> itemList) {
		super(x,y,280,180,img_path, itemList);
		int fontSize = 46;
		this.lessonText = new GraphicTextItem(x+20, y+45+fontSize/2, fontSize, "課業：", itemList);
		this.clubText = new GraphicTextItem(x+20, y+45+37+fontSize/2, fontSize, "社團：", itemList);
		this.loveText = new GraphicTextItem(x+20, y+45+37*2+fontSize/2, fontSize, "愛情：", itemList);
		this.moneyText = new GraphicTextItem(x+20, y+45+37*3+fontSize/2, fontSize, "金錢：", itemList);
		this.lessonNum = new GraphicTextItem(x+140, y+45+fontSize/2, fontSize, Integer.toString(lesson), itemList);
		this.clubNum = new GraphicTextItem(x+140, y+45+37+fontSize/2, fontSize, Integer.toString(club), itemList);
		this.loveNum = new GraphicTextItem(x+140, y+45+37*2+fontSize/2, fontSize, Integer.toString(love), itemList);
		this.moneyNum = new GraphicTextItem(x+140, y+45+37*3+fontSize/2, fontSize, Integer.toString(money), itemList);
	}
	//method
	@Override
	public void draw(Graphics g, double sc) {
		super.draw(g, sc);
		this.lessonText.draw(g, sc);
		this.clubText.draw(g, sc);
		this.loveText.draw(g, sc);
		this.moneyText.draw(g, sc);
		this.lessonNum.draw(g, sc);
		this.clubNum.draw(g, sc);
		this.loveNum.draw(g, sc);
		this.moneyNum.draw(g, sc);
	}
	@Override
	protected int drawX(double sc) {
		return (int) (rect.getX() * sc);
	}
	protected int drawY(double sc) {
		return (int) (rect.getY() * sc);
	}
	//get-set
	public void setLesson(int lesson) {
		this.lessonNum.setText(Integer.toString(lesson));
	}
	public void setClub(int club) {
		this.clubNum.setText(Integer.toString(club));
	}
	public void setLove(int love) {
		this.loveNum.setText(Integer.toString(love));
	}
	public void setMoney(int money) {
		this.moneyNum.setText(Integer.toString(money));
	}
	//var
	private GraphicTextItem lessonText, clubText, loveText, moneyText;
	private GraphicTextItem lessonNum, clubNum, loveNum, moneyNum;
}
