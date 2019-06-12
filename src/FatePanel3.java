//author: 邱顯倫
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import ingame.GraphicImgItem;
import ingame.GraphicTextItem;

public class FatePanel3 extends FatePanel{
	
	private int pointsCount=0;
    private int timeleft=59;
    private int time=0;
    private static int manY=780;
    private int manX=800;
    private int i=0,j=0,k=0,m=0;
    private int heartX;
    boolean del=false;
    
    public GraphicTextItem score,timeLeft;        
    public GraphicImgItem heart,man,background; 
    public GraphicImgItem[] heartArray=new GraphicImgItem[150];
    Random rand=new Random();
        
	public FatePanel3(Dimension dim) {
		super(dim, 3);
		background=new GraphicImgItem(800, 450, 1600, 900, "/background.png", this.graphicItems);
		man=new GraphicImgItem(manX, manY,68 ,100 , "/man.png", this.graphicItems);
		score=new GraphicTextItem(1500, 100,60  , "score="+pointsCount, this.graphicItems);
		timeLeft=new GraphicTextItem(1500, 200, 60, "timeLeft:"+timeleft, this.graphicItems);
	}
	@Override
	public void doTick() {
		super.doTick();   		
		//game start
		
		move(man);
		
		//random produce heart
		if(time%15==0) {	             		
			heartX=ThreadLocalRandom.current().nextInt(50,1550);
			heartArray[i]=new GraphicImgItem(heartX,3,81,74, "/heart.png", this.graphicItems);				
			i++;
		}
		if(time%30==0) {
			timeleft--;
		}
		timeLeft.setText("time:"+timeleft);	
		//heart falling
		for(int m=0;m<i;m++) {
			if(heartArray[m]!=null)
			    fallHeart(heartArray[m]);
		}
		
		for(int j=0;j<i;j++) { 
			if(heartArray[j]!=null) {
			    checkCollision(man, heartArray[j]);
			    if(del) {
			    	heartArray[j]=null;
			    }
			}
		}
		
		score.setText("score:"+pointsCount);
		time++;
		if(timeleft==0) {
			gameEnd(pointsCount);
		}
	}
	public void fallHeart(GraphicImgItem g){
		if(g.getY()<900) {
			g.setPosition(new Point((int)g.getX(),(int)g.getY()+10));
		}
		else {
		g.kill();
		g=null;
		}
	}
	public void move(GraphicImgItem e) {
		if(this.getLeftPressed()) {
			e.setPosition(new Point((int)e.getX()-20,(int)manY));
		}
		if(this.getRightPressed()) {
			e.setPosition(new Point((int)e.getX()+20,(int)manY));
		}
	}
	public void checkCollision(GraphicImgItem a,GraphicImgItem b) {
		Rectangle aRectangle=new Rectangle((int)a.getX(),(int)a.getY(),(int)a.getW(),(int)a.getH());
		Rectangle bRectangle=new Rectangle((int)b.getX(),(int)b.getY(),(int)b.getW(),(int)b.getH());
		if(aRectangle.intersects(bRectangle)) {
			pointsCount=pointsCount+1;
			b.kill();
			del=true;
		}
		else {
			del=false;
		}
	}
}

