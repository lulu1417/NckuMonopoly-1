package ingame;

import java.awt.Color;
import java.util.ArrayList;

public class EndList extends GraphicImgItem {
	public EndList(int championID, ArrayList<GraphicItem> itemList) {
		//ctor
		super(Game.Width/2, Game.Height/2, 400, 500, "/endlist.png", itemList);
		this.colorTimer = 0;
		this.colorPeriod = 30;
		//win image
		this.winImage = new GraphicImgItem(Game.Width/2, Game.Height/2-75, 250, 250, "/win.png", itemList);
		//text
		this.winText = new GraphicTextItem(Game.Width/2, Game.Height/2+110, 50, "恭喜 玩家" + (championID+1), itemList);
		this.winText2 = new GraphicTextItem(Game.Width/2, Game.Height/2+170, 50, "獲得勝利！", itemList);
		this.winText.setColor(new Color(this.getColorCode()));
		this.winText2.setColor(new Color(this.getColorCode()));
		//z
		this.setZ(20000);
		this.winImage.setZ(20001);
		this.winText.setZ(20001);
		this.winText2.setZ(20001);
		
	}
	public int getColorCode() {
		int[] rgb = new int[3];
		for(int i=0; i<3; ++i) {
			if(this.colorTimer < (i+1)*colorPeriod) {
				rgb[i] = (int) ((1.0 - (double)(colorTimer % colorPeriod)/colorPeriod) * 0xFF);
				rgb[i+1>=3?i-2:i+1] = (int) ((double)(colorTimer % colorPeriod)/colorPeriod * 0xFF);
				rgb[i+2>=3?i-1:i+2] = 0x00;
				break;
			}
		}
		return rgb[0] << 16 | rgb[1] << 8 | rgb[2];
	}
	@Override
	public void kill() {
		super.kill();
		winImage.kill();
		winText.kill();
		winText2.kill();
	}
	@Override
	public void timerRun() {
		super.timerRun();
		this.winText.setColor(new Color(this.getColorCode()));
		this.winText2.setColor(new Color(this.getColorCode()));
		if(++colorTimer>=colorPeriod*3) colorTimer = 0;
	}
	private int colorTimer, colorPeriod;
	private GraphicImgItem winImage;
	private GraphicTextItem winText, winText2;
}
