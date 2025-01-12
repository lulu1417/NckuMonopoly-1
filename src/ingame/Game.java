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
		new Cell("中正堂",370,220,CellType.START),
		new Cell("成功湖",370,310,CellType.SELECT).setScore(-10).setMessage("被校狗追"),
		new Cell("光一光二宿舍",370,400,CellType.SELECT).setScore(20).setMessage("在宿舍偷養貓沒被發現"),
		new Cell("成功廳",370,490,CellType.FATE).setScore(2).setMessage("猜兔子"),
		new Cell("光復操場",370,590,CellType.SELECT).setScore(-5).setMessage("晨練耍廢"),
		new Cell("第一活動中心",370,690,CellType.SELECT).setScore(30).setMessage("當上社團幹部").setSelectPolicy(PlayerScoreType.CLUB),
		new Cell("「飛撲」雕塑",465,690,CellType.SELECT).setScore(-20).setMessage("穿越飛撲，微積分被當").setSelectPolicy(PlayerScoreType.LESSON),
		new Cell("總圖書館",560,690,CellType.FATE).setScore(1).setMessage("知識王"),
		new Cell("資訊系館",655,690,CellType.SELECT).setScore(30).setMessage("認真上課"),
		new Cell("　D24",750,690,CellType.CHANCE),
		new Cell("九乘九",845,690,CellType.SELECT).setScore(-10).setMessage("機車違停被拖吊"),
		new Cell("育樂街",940,690,CellType.SELECT).setScore(-10).setMessage("吃到蟑螂"),
		new Cell("勝九舍",1035,690,CellType.SELECT).setScore(-10).setMessage("和室友同時吹頭髮，全宿舍跳電"),
		new Cell("勝後",1130,690,CellType.FATE).setScore(3).setMessage("接愛心"),
		new Cell("圖書部",1225,690,CellType.SHOP).setMessage("交易時間"),
		new Cell("麥當勞",1225,590,CellType.SELECT).setScore(10).setMessage("五秒內吃完一個大麥克"),
		new Cell("航太系館",1225,495,CellType.CHANCE),
		new Cell("機械系館",1225,400,CellType.NOTHING),
		new Cell("自強操場",1225,310,CellType.SELECT).setScore(-10).setMessage("晨練睡過頭"),
		new Cell("敬一舍",1225,220,CellType.SELECT).setScore(-5).setMessage("帶女友回宿舍過夜被抓到"),
		new Cell("新K館",1135,220,CellType.CHANCE),
		new Cell("成大醫院",1045,220,CellType.HOSPITAL).setMessage("住院一週，暫停兩回合"),
		new Cell("成杏廳",950,220,CellType.CHANCE),
		new Cell("醫院附設餐廳",850,220,CellType.SELECT).setScore(15).setMessage("學生證優惠"),
		new Cell("社科院",760,220,CellType.SELECT).setScore(30).setMessage("認真上課"),
		new Cell("歷史系館",665,220,CellType.SELECT).setScore(15).setMessage("加簽到喪葬史"),
		new Cell("榕園",575,220,CellType.CHANCE),
		new Cell("販賣部",475,220,CellType.SHOP)
	};
}
