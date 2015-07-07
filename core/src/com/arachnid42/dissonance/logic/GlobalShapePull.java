package com.arachnid42.dissonance.logic;

import java.util.ArrayList;
import java.util.List;

import com.arachnid42.dissonance.logic.parts.field.GameField;
import com.arachnid42.dissonance.logic.parts.shape.Shape;
import com.arachnid42.dissonance.logic.parts.shape.ShapeRandomizer;

public class GlobalShapePull{
	private static final int FIRST = 0;
	public static final int THREE_FALL_PATHES = 3;
	public static final int FOUR_FALL_PATHES = 4;
	public static final int FIVE_FALL_PATHES = 5;
	private List<Shape> shapes = null;
	private ShapeRandomizer shapeRandomizer = null;
	public GlobalShapePull(int fallPathCount,int pullSize,PerfomanceData perfomanceData){
		this.shapeRandomizer = new ShapeRandomizer(fallPathCount,fallPathCount,fallPathCount,perfomanceData);
		this.shapes = new ArrayList<Shape>(pullSize);
		//Shape.setCircleVertexCount(30);
		for(int i = 0;i<pullSize;i++){
			shapes.add(new Shape());
		}
	}
	public Shape getRandomizedShape(){
		if(!shapes.isEmpty())
			return shapeRandomizer.randomize(shapes.remove(FIRST)); 
		else
			return null;
	}
	public void setFallPathCount(int fallPathCount){
		if(THREE_FALL_PATHES>fallPathCount||fallPathCount>FIVE_FALL_PATHES)
			throw new IllegalArgumentException();
		this.shapeRandomizer.setShapeColorUpLimit(fallPathCount);
		this.shapeRandomizer.setShapeTypeUpLimit(fallPathCount);
	}
	public void put(Shape shape){
		if(shape==null)
			throw new IllegalArgumentException();
		if(shapes.contains(shape))
			throw new IllegalArgumentException();
		this.shapes.add(shape);
	}
	public boolean isEmpty(){
		return this.shapes.isEmpty();
	}
	public int getShapeCount(){
		return this.shapes.size();
	}
	public void setSuitableShapeRadius(GameField gameField){
		for(Shape s:shapes){
			s.setRadius(gameField.getFallPathField().getFallPathWidth()/2);
		}
	}
}
