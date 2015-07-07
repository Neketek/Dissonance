package com.arachnid42.dissonance.logic.parts.shape;

import com.arachnid42.dissonance.logic.parts.interfaces.SPointDC;

public class SPoint implements SPointDC{
	private float x;
	private float y;
	private float angle = 0;
	private float rad = 0;
	private float moveX = 0;
	private float moveY = 0;
	public SPoint(float x,float y){
		this.x = x;
		this.y = y;
	}
	public float getX() {
		return x+moveX;
	}
	public void setX(float x) {
		this.setLocation(x, y);
	}
	public float getY() {
		return y+moveY;
	}
	public void setY(float y) {
		this.setLocation(x, y);
	}
	public void setLocation(float x,float y){
		this.x = x;
		this.y = y;
		this.rad = (float) Math.sqrt(x*x+y*y);
		this.angle = (float)Math.atan2(y, x);
	}
	public void move(float x,float y){
		this.moveX+=x;
		this.moveY+=y;
	}
	public void rotate(float angle){
		this.angle+=angle;
		this.x = (float) (rad*Math.cos(this.angle));
		this.y = (float) (rad*Math.sin(this.angle));
	}
	public float getPolarAngle(){
		return this.angle;
	}
	public float getRad() {
		return rad;
	}
	public float getMoveX(){
		return this.moveX;
	}
	public float getMoveY(){
		return this.moveY;
	}
	public void equalizeMoveTo(SPoint p){
		this.moveX = p.moveX;
		this.moveY = p.moveY;
	}

	@Override
	public String toString() {
		return "X:"+getX()+" Y:"+getY();
	}
}
