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
		new Cell("中正堂",372,160,CellType.START),
		new Cell("成功湖",372,246,CellType.SELECT).setScore(-10).setMessage("被校狗追"),
		new Cell("光一光二宿舍",372,332,CellType.SELECT).setScore(20).setMessage("在宿舍偷養貓沒被發現"),
		new Cell("成功廳",372,418,CellType.FATE).setMessage("猜兔子"),
		new Cell("光復操場",372,504,CellType.SELECT).setScore(-5).setMessage("晨練耍廢"),
		new Cell("第一活動中心",372,590,CellType.SELECT).setScore(30).setMessage("當上社團幹部").setSelectPolicy(PlayerScoreType.CLUB),
		new Cell("「飛撲」雕塑",372,676,CellType.SELECT).setScore(-20).setMessage("穿越飛撲，微積分被當").setSelectPolicy(PlayerScoreType.LESSON),
		new Cell("總圖書館",372,762,CellType.FATE).setMessage("知識王"),
		new Cell("資訊系館",458,762,CellType.SELECT).setScore(30).setMessage("認真上課"),
		new Cell("　D24",544,762,CellType.CHANCE),
		new Cell("九乘九",630,762,CellType.SELECT).setScore(-10).setMessage("機車違停被拖吊"),
		new Cell("育樂街",716,762,CellType.SELECT).setScore(-10).setMessage("吃到蟑螂"),
		new Cell("勝九舍",802,762,CellType.SELECT).setScore(-10).setMessage("和室友同時吹頭髮，全宿舍跳電"),
		new Cell("勝後",888,762,CellType.FATE).setMessage("接愛心"),
		new Cell("圖書部",974,762,CellType.SHOP),
		new Cell("M",1060,762,CellType.SELECT).setScore(10).setMessage("五秒內吃完一個大麥克"),
		new Cell("航太系館",1146,762,CellType.CHANCE),
		new Cell("機械系館",1232,762,CellType.NOTHING),
		new Cell("自強操場",1318,762,CellType.SELECT).setScore(-10).setMessage("晨練睡過頭"),
		new Cell("敬一舍",1404,762,CellType.SELECT).setScore(-5).setMessage("帶女友回宿舍過夜被抓到"),
		new Cell("新K館",1490,762,CellType.CHANCE),
		new Cell("成大醫院",1490,676,CellType.HOSPITAL),
		new Cell("成杏廳",1490,590,CellType.CHANCE),
		new Cell("醫院附設餐廳",1490,504,CellType.SELECT).setScore(15).setMessage("學生證優惠"),
		new Cell("社科院",1490,418,CellType.SELECT).setScore(30).setMessage("認真上課"),
		new Cell("歷史系館",1490,332,CellType.SELECT).setScore(15).setMessage("加簽到喪葬史"),
		new Cell("榕園",1490,246,CellType.CHANCE),
		new Cell("販賣部",1490,160,CellType.SHOP)
	};
}
