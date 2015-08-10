package com.balakin.dissonance.logic.parts.shape;

import com.balakin.dissonance.logic.PerfomanceData;

import java.util.Random;

public class ShapeRandomizer {
	private int shapeTypeUpLimit = 5;
	private int shapeColorUpLimit = 5;
	private double shapeAngleUpLimit = Math.PI*2;
	private PerfomanceData perfomanceData = null;
	private float fullRotationInSecondUpLimit = 6;
	private float fullRotationInSecondDownLimit = 4;
	private Random random = null;
	public ShapeRandomizer(int shapeTypeUpLimit, int shapeColorUpLimit,double shapeAngleUpLimit,PerfomanceData perfomanceData){
		this.shapeAngleUpLimit = shapeAngleUpLimit;
		this.shapeColorUpLimit = shapeColorUpLimit;
		this.shapeTypeUpLimit = shapeTypeUpLimit;
		this.perfomanceData = perfomanceData;
		this.random = new Random();
	}
	public Shape randomize(Shape shape){
		shape.setType((int)(Math.random()*shapeTypeUpLimit));
	//	shape.setType(Shape.RECTANGLE);
		shape.setColor((int)(Math.random()*shapeColorUpLimit));
		shape.setAngle((float)(Math.PI*2+Math.PI/4));
		float rot = (float) (perfomanceData.getRotationPiX2OneSecond()/(fullRotationInSecondDownLimit+Math.random()*(fullRotationInSecondUpLimit-fullRotationInSecondDownLimit)));
		//rot = perfomanceData.getRotationPiX2OneSecond()/5;
		if(random.nextBoolean())
			shape.setRotationSpeed(rot);
		else
			shape.setRotationSpeed(-rot);
		return shape;
	}
	public int getShapeTypeUpLimit() {
		return shapeTypeUpLimit;
	}
	public void setShapeTypeUpLimit(int shapeTypeUpLimit) {
		this.shapeTypeUpLimit = shapeTypeUpLimit;
	}
	public int getShapeColorUpLimit() {
		return shapeColorUpLimit;
	}
	public void setShapeColorUpLimit(int shapeColorUpLimit) {
		this.shapeColorUpLimit = shapeColorUpLimit;
	}
	public double getShapeAngleUpLimit() {
		return shapeAngleUpLimit;
	}
	public void setShapeAngleUpLimit(double shapeAngleUpLimit) {
		this.shapeAngleUpLimit = shapeAngleUpLimit;
	}
	public void setFullRotationInSecondUpLimit(float second){
		if(second<=0)
			throw new IllegalArgumentException();
		if(second<=getFullRotationInSecondDownLimit())
			throw new IllegalArgumentException();
		this.fullRotationInSecondUpLimit = second;
	}
	public void setFullRotationInSecondDownLimit(float second){
		if(second<=0)
			throw new IllegalArgumentException();
		if(second>=getFullRotationInSecondUpLimit())
			throw new IllegalArgumentException();
		this.fullRotationInSecondDownLimit = second;
	}
	public float getFullRotationInSecondUpLimit(){
		return this.fullRotationInSecondUpLimit;
	}
	public float getFullRotationInSecondDownLimit(){
		return this.fullRotationInSecondDownLimit;
	}
}
