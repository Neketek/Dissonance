package com.arachnid42.dissonance.logic.parts.interfaces;

public interface ShapeDC {
	public float getRadius();
	public float getAngle();
	public float getX();
	public float getY();
	public int getType();
	public int getColor();
	public SPointDC getPoint(int index);
	public int getPointCount();
}
