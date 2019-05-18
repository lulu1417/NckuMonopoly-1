package ingame;

import java.util.ArrayList;

public class Game {
	public static final int Width = 1600;
	public static final int Height = 900;
	public static ArrayList<String> signals = new ArrayList<String>();
	public static ArrayList<GraphicItem> graphicItems = new ArrayList<GraphicItem>();
	public static ArrayList<Player> players = new ArrayList<Player>();
	public static GameState gamestate = GameState.INIT;
	public static int playerCount = 4;
	public static final Cell[] cells = {
		new Cell(372,160,CellType.NORMAL),
		new Cell(372,246,CellType.NORMAL),
		new Cell(372,332,CellType.NORMAL),
		new Cell(372,418,CellType.NORMAL),
		new Cell(372,504,CellType.NORMAL),
		new Cell(372,590,CellType.NORMAL),
		new Cell(372,676,CellType.NORMAL),
		new Cell(372,762,CellType.NORMAL),
		new Cell(458,762,CellType.NORMAL),
		new Cell(544,762,CellType.NORMAL),
		new Cell(630,762,CellType.NORMAL),
		new Cell(716,762,CellType.NORMAL),
		new Cell(802,762,CellType.NORMAL),
		new Cell(888,762,CellType.NORMAL),
		new Cell(974,762,CellType.NORMAL),
		new Cell(1060,762,CellType.NORMAL),
		new Cell(1146,762,CellType.NORMAL),
		new Cell(1232,762,CellType.NORMAL),
		new Cell(1318,762,CellType.NORMAL),
		new Cell(1404,762,CellType.NORMAL),
		new Cell(1490,762,CellType.NORMAL),
		new Cell(1490,676,CellType.NORMAL),
		new Cell(1490,590,CellType.NORMAL),
		new Cell(1490,504,CellType.NORMAL),
		new Cell(1490,418,CellType.NORMAL),
		new Cell(1490,332,CellType.NORMAL),
		new Cell(1490,246,CellType.NORMAL),
		new Cell(1490,160,CellType.NORMAL),
		new Cell(1404,160,CellType.NORMAL),
		new Cell(1318,160,CellType.NORMAL),
		new Cell(1232,160,CellType.NORMAL),
		new Cell(1146,160,CellType.NORMAL),
		new Cell(1060,160,CellType.NORMAL),
		new Cell(974,160,CellType.NORMAL),
		new Cell(888,160,CellType.NORMAL),
		new Cell(802,160,CellType.NORMAL),
		new Cell(716,160,CellType.NORMAL),
		new Cell(630,160,CellType.NORMAL),
		new Cell(544,160,CellType.NORMAL),
		new Cell(458,160,CellType.NORMAL)
	};
}
