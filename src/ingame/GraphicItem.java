package ingame;

import java.awt.Graphics;

public abstract class GraphicItem {
	//ctor
	public GraphicItem() {
		this.timer = 0;
		this.lifetime = -1;
	}
	//method
	public abstract void draw(Graphics g, double sc);
	public void timerRun() {
		if(lifetime == -1) return;
		++this.timer;
	}
	public boolean isDead() {
		if(lifetime == -1) return false;
		return this.timer >= lifetime;
	}
	public void kill() {
		lifetime = timer;
	}
	//get-set
	public void setLifeTime(int lifetime) {
		this.lifetime = lifetime;
	}
	private int timer, lifetime;
}
