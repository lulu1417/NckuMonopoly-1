package ingame;

import java.awt.Graphics;

public abstract class GraphicItem {
	//ctor
	public GraphicItem() {
		this.timer = 0;
		this.lifetime = -1;
		this.opacity = 1.0;
		this.z = 0;
		this.floating = false;
		this.float_amp = 0;
		this.float_timer = 0;
	}
	//method
	public abstract void draw(Graphics g, double sc);
	public void timerRun() {
		if(this.floating) {
			if(++this.float_timer>=60) this.float_timer=0;
		}
		if(this.lifetime != -1) ++this.timer;
	}
	public boolean isDead() {
		if(this.lifetime == -1) return false;
		return this.timer >= lifetime;
	}
	public void kill() {
		lifetime = timer;
	}
	//get-set
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public void setFloating(boolean floating, int float_amp) {
		this.floating = floating;
		this.float_amp = float_amp;
	}
	public void setLifeTime(int lifetime) {
		this.lifetime = lifetime;
	}
	public void setOpacity(double opacity) {
		this.opacity = opacity;
	}
	//var
	private int timer, lifetime;
	protected double opacity;
	private double z;
	protected boolean floating;
	protected int float_amp, float_timer;
}
