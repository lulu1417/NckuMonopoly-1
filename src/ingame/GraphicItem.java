package ingame;

import java.awt.Graphics;

public abstract class GraphicItem {
	//ctor
	public GraphicItem() {
		this.timer = 0;
		this.lifetime = -1;
		this.opacity = 1.0;
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
	public double getZ() {
		return 0;
	}
	//get-set
	public void setLifeTime(int lifetime) {
		this.lifetime = lifetime;
	}
	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}
	//var
	private int timer, lifetime;
	protected double opacity;
}
