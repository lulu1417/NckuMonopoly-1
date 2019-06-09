package ingame;

import java.awt.Point;
import java.util.ArrayList;

public class Player extends GraphicImgItem {
	//ctor
	public Player(int w, int h, String img_path, int lesson, int club, int love, int money, int currentCell, int id, ArrayList<GraphicItem> itemList) {
		super((int) Game.cells[currentCell].getX(), (int) Game.cells[currentCell].getY(), w, h, img_path, itemList);
		this.lesson = lesson;
		this.club = club;
		this.love = love;
		this.money = money;
		this.id = id;
		this.scoreboard = null;
		this.currentCell = currentCell;
		this.selectedHint = null;
		this.freezeCount = 0;
		this.eliminated = false;
	}
	//method
	@Override
	protected int drawY(double sc) {
		return (int) (rect.getY() * sc - drawH(sc) * 0.9);
	}
	public void moveTo(int cell) {
		int valid_cell = cell % Game.cells.length;
		int oldCell = this.currentCell;
		this.currentCell = valid_cell;
		repositionPlayers(oldCell);
		repositionPlayers(valid_cell);
	}
	public static void repositionPlayers(int cell) {
		//players in the same cell
		ArrayList<Player> playersInCell = new ArrayList<Player>();
		for(Player player: Game.players) if(player.currentCell == cell) playersInCell.add(player);
		//position
		if(playersInCell.size() == 1) { //one player
			Player player = playersInCell.get(0);
			player.setPosition(Game.cells[cell]);
			//arrow
			if(player.selectedHint != null) {
				Point arrow_pos = new Point(
						(int) (player.getX()),
						(int) (player.getY()-150));
				player.selectedHint.setPosition(arrow_pos);
			}
		} else { // two or more
			double angle = -Math.PI/8;
			for(Player player: playersInCell) {
				Point aim = Game.cells[cell];
				int bias_w = playersInCell.size()*13;
				Point biased = new Point(
						(int) (bias_w*Math.cos(angle) + aim.getX()),
						(int) (30*Math.sin(angle) + aim.getY()));
				player.setPosition(biased);
				//arrow
				if(player.selectedHint != null) {
					Point arrow_pos = new Point(
							(int) (biased.getX()),
							(int) (biased.getY()-150));
					player.selectedHint.setPosition(arrow_pos);
				}
				angle += 2*Math.PI/playersInCell.size();
			}
		}
	}
	public void moveToNext() {
		int nextCell = this.currentCell+1;
		this.moveTo(nextCell);
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
	public int checkFreeze() {
		if(this.freezeCount==0) return 0;
		return this.freezeCount--;
	}
	//get-set
	public void setEliminated(boolean eliminated) {
		this.eliminated = eliminated;
		this.setOpacity(0.0);
		this.scoreboard.setEliminated(eliminated);
	}
	public void setFreezeCount(int freezeCount) {
		this.freezeCount = freezeCount;
	}
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
	public boolean getEliminated() {
		return this.eliminated;
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
	public int getCurrentCellID() {
		return this.currentCell;
	}
	public int getID() {
		return this.id;
	}
	public void select() {
		this.setOpacity(1.0);
		this.scoreboard.select();
		if(this.selectedHint != null) {
			this.selectedHint.kill();
			this.selectedHint = null;
		}
		this.selectedHint = new GraphicImgItem((int) this.getX(), (int) this.getY()-150, 50, 50, "/arrow.png", Game.graphicItems);
		this.selectedHint.setZ(10000);
		this.selectedHint.setFloating(true, 15);
	}
	public void unselect() {
		if(this.eliminated) this.setOpacity(0.0);
		else this.setOpacity(0.5);
		this.scoreboard.unselect();
		if(this.selectedHint != null) {
			this.selectedHint.kill();
			this.selectedHint = null;
		}
	}
	//player with more y should be paint more later
	@Override
	public double getZ() {
		return this.getY();
	}
	//var
	private GraphicImgItem selectedHint;
	private int currentCell, id;
	private int lesson, club, love, money;
	private PlayerScoreboard scoreboard;
	private int freezeCount;
	private boolean eliminated;
}
