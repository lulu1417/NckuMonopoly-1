package ingame;
import java.awt.Point;
import java.util.ArrayList;

public class Game {
	public static final int Width = 800;
	public static final int Height = 450;
	public static ArrayList<String> signals = new ArrayList<String>();
	public static ArrayList<GraphicItem> graphicItems = new ArrayList<GraphicItem>();
	public static GameState gamestate = GameState.START;
	public static int playerCount = 4;
	public static final Point[] cell = {
		new Point(120,80),
		new Point(120,123),
		new Point(120,166),
		new Point(120,209),
		new Point(120,252),
		new Point(120,295),
		new Point(120,338),
		new Point(120,381),
		new Point(163,381),
		new Point(206,381),
		new Point(249,381),
		new Point(292,381),
		new Point(335,381),
		new Point(378,381),
		new Point(421,381),
		new Point(464,381),
		new Point(507,381),
		new Point(550,381),
		new Point(593,381),
		new Point(636,381),
		new Point(679,381),
		new Point(679,338),
		new Point(679,295),
		new Point(679,252),
		new Point(679,209),
		new Point(679,166),
		new Point(679,123),
		new Point(679,80),
		new Point(636,80),
		new Point(593,80),
		new Point(550,80),
		new Point(507,80),
		new Point(464,80),
		new Point(421,80),
		new Point(378,80),
		new Point(335,80),
		new Point(292,80),
		new Point(249,80),
		new Point(206,80),
		new Point(163,80)
	};
}
