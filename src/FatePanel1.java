//author: 邱顯倫、邱顯倫
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import ingame.Game;
import ingame.GraphicImgItem;
import ingame.GraphicTextItem;

public class FatePanel1 extends FatePanel{
	//ctor
	public FatePanel1(Dimension dim) {
		super(dim, 1);
		//game start
		//...
		GraphicImgItem background = new GraphicImgItem(800, 450, 1600, 900, "/KnowledgeBackground.png", this.graphicItems);
		background.setZ(-1.0f);
		title = new GraphicTextItem(800, 300, 50, "成大知識王", this.graphicItems);
		double sc = (double)this.getWidth() / Game.Width;
		start_button = new ClickButton(Game.Width/2, Game.Height/2, 400, 100, sc, "開始遊戲");
		startButtonListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				start = true;
				startSetting();
				title.kill();
			}
		};
		start_button.addActionListener(startButtonListener);
		this.add(start_button);
			
		timeout = new GraphicTextItem(800,100,50,"", this.graphicItems);
		timeout.setColor(new Color(255,0,0));
	}
		//method
		@Override
	public void doTick() {
		super.doTick();
		//game playing
		//...
		if(start) {
			
			
			
			if(pressed == false) 
			{	
				if(tick == 30) 
				{
					timeleft--;
					tick=0;
				}
				timeCount.setText(String.valueOf(timeleft));
				if(timeleft==0) 
				{
					timeout.setText("Time Out!");
					point-=10;
					pointCount.setText(String.valueOf(point));
					pressed=true;
				}
				
			}
			else 
			{	
				if(tick == 29) {
					if(turn==3) 
					{
						this.gameEnd(point);
					}
				}
				if(tick == 30) 
				{	
					turn++;
					timeout.setText("");
					while(true) {
						numQuestion = ThreadLocalRandom.current().nextInt(0, 20);
						System.out.println(numQuestion);
						numRandom[turn-1] = numQuestion;
						if(turn == 2) 
						{
							if(numQuestion != numRandom[0]) 
								break;
						}
						else if(turn == 3) 
						{
							if(numQuestion != numRandom[0] && numQuestion != numRandom[1])
								break;
						}
						else 
							break;
					}
					question.setText(String.valueOf(turn)+". "+text[numQuestion*5]);
					choose[0].setText(text[numQuestion*5+1]);
					choose[1].setText(text[numQuestion*5+2]);
					choose[2].setText(text[numQuestion*5+3]);
					pressed = false;
					timeleft=10;
					tick = 0;
				}
			}

		    double scale = (Math.sin(tick%30/30.0*2*Math.PI)*0.1+0.95);
		    this.question.setFontSize((int) (50*scale));
			tick++;
		}
	}
		
	private void startSetting() 
	{
		timeTitle = new GraphicTextItem(1400, 25, 30, "Time:", this.graphicItems);
		timeCount = new GraphicTextItem(1500, 25, 30, "", this.graphicItems);
		pointTitle = new GraphicTextItem(1400, 75, 30, "point:", this.graphicItems);
		pointCount = new GraphicTextItem(1500, 75, 30, String.valueOf(point), this.graphicItems);
		question = new GraphicTextItem(800,(int) (Game.Height/2+150*(-1.5)), 50, "", this.graphicItems);
		double sc = (double)this.getWidth() / Game.Width;
		for(int i = 0; i<3;i++) {
			choose[i] = new ClickButton(Game.Width/2,(int) (Game.Height/2+150*(i-0.5)), 400, 100, sc, "");
			this.add(choose[i]);			
		}
		choose[0].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				answerCheck(1);
				
			}
		});
		choose[1].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				answerCheck(2);
				
			}
		});
		choose[2].addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				answerCheck(3);
				
			}
		});
		start_button.setVisible(false);
		start_button.removeActionListener(startButtonListener);
	}
	
	private void answerCheck(int numChoose) 
	{
		if(pressed==false) {
			pressed = true;
			tick = 0;
			if(numChoose == Integer.valueOf(text[numQuestion*5+4])) 
			{
				correct = true;
				correctImg = new GraphicImgItem(Game.Width/2-250, (int) (Game.Height/2+150*(numChoose-1.5)), 50, 50, "/correct.png", this.graphicItems);
				correctImg.setLifeTime(30);
				point+=10;
			}
				else
			{
				correct = false;
				wrongImg = new GraphicImgItem(Game.Width/2-250, (int) (Game.Height/2+150*(numChoose-1.5)), 50, 50, "/wrong.png", this.graphicItems);
				wrongImg.setLifeTime(30);
				point-=10;
			}
			pointCount.setText(String.valueOf(point));
		}
	}
		
	private JButton[] choose = new JButton[3];
	private JButton start_button;
	
	private boolean start = false, correct = false,pressed = true;
	
	private int[] numRandom = {20,20,20};
	private int timeleft = 10,tick = 30,numQuestion = 0,point = 0,turn = 0;
	
	private GraphicTextItem title,timeCount,timeTitle,pointCount,pointTitle, question,timeout;
	private GraphicImgItem correctImg,wrongImg;
	
	private ActionListener startButtonListener;
	
	private String[] text = { 
	"以下何者為成大校訓?",
	"窮理致知 ",
	"敦品勵學",
	"發大財",
	"1",

	"以下哪位不是成大校友?",
	"林飛帆",
	"蔣月惠",
	"陳水扁",
	"3",

	"以下哪個不是成大的前身?",
	"台灣省立工學院",
	"台灣總督府台南工業專門學校",
	"台灣省立台南工業學院",
	"3",

	"成大創立於哪一年?",
	"1931",
	"1945",
	"1928",
	"1",

	"校長室分機號碼?",
	"50006",
	"50120",
	"50928",
	"1",

	"成大的縮寫?",
	"ntu",
	"ncku",
	"uccu",
	"2",

	"成大校長是誰?",
	"蘇慧貞",
	"蘇貞昌",
	"蘇花改",
	"1",

	"成大棒球隊拿過幾次甲二級冠軍?",
	"0",
	"1",
	"2",
	"1",

	"成大代表色?",
	"紅白",
	"黑白",
	"藍綠",
	"1",

	"成大代表物?",
	"鳳凰花",
	"鳳凰樹",
	"鳳凰花跟鳳凰樹",
	"3",

	"成大有幾個校區?",
	"15",
	"13",
	"11",
	"3",

	"以下何者不是成大姐妹校?",
	"南京大學",
	"首爾大學",
	"松阪大學",
	"3",

	"成大機械系系館有幾樓?",
	"12",
	"3",
	"8",
	"1",

	"沿著自強草皮跑一圈約幾公尺?",
	"700",
	"1200",
	"500",
	"1",

	"光復校區有幾個籃球場?",
	"5",
	"10",
	"8",
	"1",

	"校徽上有幾個花瓣?",
	"4",
	"5",
	"6",
	"2",

	"光復在自強的什麼方位?",
	"東",
	"西",
	"南",
	"2",
	
	"飛撲雕像俗稱什麼?",
	"六四天安門",
	"二一門",
	"西門",
	"2",
	
	"生科系在哪一校區",
	"成功校區",
	"光復校區",
	"力行校區",
	"3",
	
	"哪隻校犬不屬於光復校區",
	"乖黃",
	"白米",
	"豆皮",
	"2"
	};
		
}


