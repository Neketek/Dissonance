package com.arachnid42.dissonance.logic.parts.field;

import java.util.ArrayList;
import java.util.List;

import com.arachnid42.dissonance.logic.parts.shape.Shape;

public class FallPath {
	private static final int FIRST = 0;
	private float top = 0;
	private float bottom = 0;
	private float center = 0;
	private Shape droppedShape = null;
	private List<Shape> shapes = null;
	private List<Shape> gameFieldShapeBank = null;
	private boolean isFirstDropped(){
		Shape s = shapes.get(FIRST);
		for(int i = 0;i<s.getPointCount();i++){
			if(s.getPoint(i).getY()<=bottom){
				return true;
			}
		}
		return false;
	}
	public FallPath(float center,float top,float bottom,List<Shape>gameFieldShapeBank){
		this.center = center;
		this.bottom = bottom;
		this.top = top;
		this.shapes = new ArrayList<Shape>();
		this.gameFieldShapeBank = gameFieldShapeBank;
	}
	public void put(Shape s){
		this.shapes.add(s);
		s.setX(center);
		s.setY(top+s.getRadius());
		gameFieldShapeBank.add(s);
	}
	/**
	 * This method is moving and rotating all shapes at the fall path
	 * @return true if shape has been dropped
	 */
	public boolean update(){
		if(droppedShape!=null)
			droppedShape = null;
		if(shapes.isEmpty())
			return false;
		for(Shape s:shapes){
			s.update();
		}
		if(isFirstDropped()){
			droppedShape = shapes.remove(FIRST);
			gameFieldShapeBank.remove(droppedShape);
			return true;
		}
		return false;
	}
	public Shape getDroppedShape(){
		return this.droppedShape;
	}
	public void clear(){
		this.shapes.clear();
	}
	public int getShapeCount(){
		return shapes.size();
	}
	public float getTop() {
		return top;
	}
	public float getBottom() {
		return bottom;
	}
	public float getCenter() {
		return center;
	}
	public Shape getShape(int index){
		return shapes.get(index);
	}
	public List<Shape> getGameFieldShapeBank() {
		return gameFieldShapeBank;
	}
	public boolean canPutShapeIntoFallPath(){
		if(shapes.isEmpty())
			return true;
		return this.shapes.get(getShapeCount()-1).getY()<getTop()-this.shapes.get(getShapeCount()-1).getRadius();
	}
}
