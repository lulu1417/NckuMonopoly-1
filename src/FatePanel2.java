//author: 方柏翔
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import ingame.GraphicImgItem;
import ingame.GraphicTextItem;

public class FatePanel2 extends FatePanel{
	//ctor
	public FatePanel2(Dimension dim) {
		super(dim, 2);
		//game start
		//...
		
		//SetInitialImage
		stageBG= new GraphicImgItem(800,450,1600,900,"/stagebackground.png",this.graphicItems);
		stageBG.setZ(-1.0f);
		introduction = new GraphicImgItem(800,250,390,450,"/RabbitGameIntroduction.png",this.graphicItems);
		introduction.setZ(5.0f);
		turnAndScore = new GraphicImgItem(1525,50,150,100,"/TurnAndScore .png",this.graphicItems);
		turnAndScore.setZ(-0.1f);
		scoreText = new GraphicTextItem(1570,70,28,String.valueOf(score),this.graphicItems);
		scoreText.setColor(Color.WHITE);
		turnText = new GraphicTextItem(1570,30,28,String.valueOf(turn),this.graphicItems);
		turnText.setColor(Color.WHITE);
		hat[0]=	new GraphicImgItem(hatX[0],hatY,hatW,hatH,"/MagicHat.png",this.graphicItems);
		hat[1]= new GraphicImgItem(hatX[1],hatY,hatW,hatH,"/MagicHat.png",this.graphicItems);
		hat[2]= new GraphicImgItem(hatX[2],hatY,hatW,hatH,"/MagicHat.png",this.graphicItems);
		setHatZ();
		
		setBeginHat();
		
		changeCase = ThreadLocalRandom.current().nextInt(0, 3);//SetTheFirstChangeCase
	}
	//method
	@Override
	public void doTick() {
		super.doTick();
		//game playing
		//...
		
		if(time == 30*3) 
		{
			introduction.kill();
		}
		
		
		if(time>=30*2&&time<=30*4) //Hat upward from 1s to 3s
		{
			hat[initialNum].setPosition(new Point((int)hat[initialNum].getX(),(int)hat[initialNum].getY()-4));
			
		}
		
		//rabbit appear from 3s ~ 4s
		if(time==30*4) 
		{
			rabbit = new GraphicImgItem(rabbitX,hatY,hatW,hatH,"/rabbit.png",this.graphicItems);
		}
		if(time==30*5)
		{
			rabbit.kill();
		}
		
		if(time>=30*5&&time<=30*7) //Hat downward from 4s to 6s
		{
			hat[initialNum].setPosition(new Point((int)hat[initialNum].getX(),(int)hat[initialNum].getY()+4));
		}
		if(time>30*7) //Change hat from 6s
		{ 
			
			if(changeCount<=10) //changeCount from 1 to 10
			{ 
				switch(changeCase) // 3 case of hat change 
				{
				case 0:
					change(changeCase,hat[0],hat[1]);
					if(hat[0].getY()==hat[1].getY())
					{
						temphat=hat[0];
						hat[0]=hat[1];
						hat[1]=temphat;
						
						tempRabbit=hasRabbit[0];
						hasRabbit[0]=hasRabbit[1];
						hasRabbit[1]=tempRabbit;
						
						changeCase=ThreadLocalRandom.current().nextInt(0, 3);
						changeCount++;
						setHatZ();
					}
					break;
				case 1:
					change(changeCase,hat[1],hat[2]);
					if(hat[1].getY()==hat[2].getY()) 
					{
						temphat=hat[1];
						hat[1]=hat[2];
						hat[2]=temphat;
						
						tempRabbit=hasRabbit[1];
						hasRabbit[1]=hasRabbit[2];
						hasRabbit[2]=tempRabbit;
						
						changeCase=ThreadLocalRandom.current().nextInt(0, 3);
						changeCount++;
						setHatZ();
					}
					break;
				case 2:
					change(changeCase,hat[0],hat[2]);
					if(hat[0].getY()==hat[2].getY()) 
					{
						temphat=hat[0];
						hat[0]=hat[2];
						hat[2]=temphat;
						
						tempRabbit=hasRabbit[0];
						hasRabbit[0]=hasRabbit[2];
						hasRabbit[2]=tempRabbit;

						changeCase=ThreadLocalRandom.current().nextInt(0, 3);
						changeCount++;
						setHatZ();
					}
					break;
				default:
					break;
				}
				
			}
			
			else //Step_Choose
			{
				hat[0].setZ(0.0f);
				hat[1].setZ(0.0f);
				hat[2].setZ(0.0f);
				
				if(pressed) //Pressed
				{
					if(animationCount == 30) 
					{
						if(pressedNum == RabbitNum) 
						{
							rabbit = new GraphicImgItem(hatX[RabbitNum], hatY, hatW, hatH, "/rabbitHappy.png", this.graphicItems);
							result = new GraphicImgItem(800, 200, 400+animationCount*5, 300+animationCount*5, "/success.png", this.graphicItems);
							score += 10;
						}
						else 
						{
							rabbit = new GraphicImgItem(hatX[RabbitNum], hatY, hatW, hatH, "/rabbitCry.png", this.graphicItems);
							result = new GraphicImgItem(800, 200, 400+animationCount*5, 300+animationCount*5, "/fail.png", this.graphicItems);
							score -= 10;
						}
						scoreText.setText(String.valueOf(score));
					}
					else if(animationCount<30 && animationCount>=0) 
					{
						result.setSize(400+animationCount*5, 300+animationCount*5);
					}
					else if (animationCount == -30) 
					{
						
						if(turn != 3) 
						{
							reset();
							turn++;
							turnText.setText(String.valueOf(turn));
						}
						else 
						{
							this.gameEnd(score);
						}
						
					}
					animationCount--;
					
				}
				else//UnPressed
				{
					pressedCheck();
				}
			}
		}
		
		time++;
	}
	
	private void setBeginHat() //SetBeginHat
	{
		initialNum = ThreadLocalRandom.current().nextInt(0, 3);
		
		switch(initialNum) 
		{
		case 0:
			rabbitX = 200;
			break;
		case 1:
			rabbitX = 800;
			break;
		case 2:
			rabbitX = 1400;
			break;
		default:
			break;
		}
		hasRabbit[initialNum] = true;
	}
	
	private void setHatZ() 
	{
		hat[0].setZ(3.0f);
		hat[1].setZ(2.0f);
		hat[2].setZ(1.0f);
	}
	
	private void change(int Case, GraphicImgItem left,GraphicImgItem right) //TwoHatChange
	{
		
		if(left.getY() == right.getY()) //ChangeComplete
		{
			level = changeCount/3;
			changeSpeed();
			if(Case == 2) //hat1 and hat3
			{
				xoffset=xoffset*2;
			}
		}
		
		if(left.getX() == right.getX()) //yReturn
		{
			yoffset = -yoffset;
		}
		left.setPosition(new Point((int)left.getX() + xoffset,(int)left.getY() + yoffset));
		right.setPosition(new Point((int)right.getX() - xoffset,(int)right.getY() - yoffset));
		
	}
	
	private void changeSpeed()//ChangeSpeed
	{
		switch(level) //xOffset必須為兩個hatX座標差距的一半的因數，目前為 (800-200)/2=300  的因數
		{
		case 0:
			xoffset = 15;
			yoffset = 4;
			break;
		case 1:
			xoffset = 30;
			yoffset = 8;
			break;
		case 2:
			xoffset = 75;
			yoffset = 16;
			break;
		case 3:
			xoffset = 150;
			yoffset = 32;
			break;
		default:
			break;
		}
	}
	
	private void pressedCheck() //PressedCheck
	{
		chooseHat = new GraphicImgItem(800, 200, 400, 130, "/chooseHat.png", this.graphicItems);
		chooseHat.setLifeTime(1);
		
		for(int i=0;i<3;i++) 
		{
			//MousePointOnHat
			if(this.mouse().x >= (int)(hat[i].getX() - hat[i].getW()/2) && this.mouse().x <= (int)(hat[i].getX() + hat[i].getW()/2)
			&& this.mouse().y >= (int)(hat[i].getY() - hat[i].getH()/2) && this.mouse().y <= (int)(hat[i].getY() + hat[i].getH()/2))
			{
				//DrawOutline
				outline = new GraphicImgItem(hatX[i], hatY, hatW,hatH, "/outline.png", this.graphicItems);
				outline.setLifeTime(1);
				
				//LeftPressed
				if(this.getLeftPressed())
				{	
					pressedNum = i;
					hat[i].setPosition(new Point(hatX[i],hatY - hatH));
					pressed = true;
				}
			}
			
			if(hasRabbit[i]) 
			{
				RabbitNum = i;
			}
		}
	}
	public void reset() 
	{
		xoffset=12;yoffset=4;rabbitX=0;
		initialNum=0;time=-1;changeCase = 0;changeCount=1;animationCount = 31;level = 0;pressedNum = 0;RabbitNum = 0;
		tempRabbit = false;pressed = false;
		hasRabbit[0] =false;hasRabbit[1] =false;hasRabbit[2] =false;
		
		rabbit.kill();
		result.kill();
		
		hat[0].setPosition(new Point(hatX[0],hatY));
		hat[1].setPosition(new Point(hatX[1],hatY));
		hat[2].setPosition(new Point(hatX[2],hatY));
		setHatZ();
		
		setBeginHat();
		
		changeCase = ThreadLocalRandom.current().nextInt(0, 3);
	}
	
	public GraphicImgItem stageBG, rabbit, temphat, outline, result, chooseHat, introduction, turnAndScore;
	public GraphicImgItem[] hat = new GraphicImgItem[3];
	
	public GraphicTextItem scoreText,turnText;
	
	private int hatY=700,hatW=300,hatH=300,xoffset=12,yoffset=4, rabbitX=0;
	private int initialNum=0 ,time=0, changeCase = 0, changeCount=1, animationCount = 30, level = 0, pressedNum = 0, RabbitNum = 0, score = 0, turn = 1;
	private int hatX[] = {200,800,1400};
	private boolean tempRabbit = false, pressed = false;
	private boolean hasRabbit[] = {false,false,false};
		
}
