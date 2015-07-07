package com.arachnid42.dissonance.logic.parts.field;

import java.util.ArrayList;
import java.util.List;

import com.arachnid42.dissonance.logic.parts.interfaces.GameFieldDC;
import com.arachnid42.dissonance.logic.parts.shape.Shape;

public class GameField implements GameFieldDC{
	private FallPathField fallPathField = null;
	private ShapeBasket shapeBasket = null;
	private List<Shape> gameFieldShapeBank = null;
	public GameField(float top,float bottom,float left,float right,float shapeBasketHeightCoef,float fallPathMarginCoef,int fallPathCount){
		this.gameFieldShapeBank = new ArrayList<Shape>();
		this.shapeBasket = new ShapeBasket(bottom+(top-bottom)*shapeBasketHeightCoef, bottom, left, right,fallPathCount);
		this.fallPathField = new FallPathField(fallPathCount, top, shapeBasket.getIndicatorTop(), left, right,(right-left)*fallPathMarginCoef,gameFieldShapeBank);
	}
	public FallPathField getFallPathField(){
		return this.fallPathField;
	}
	public ShapeBasket getShapeBasket(){
		return this.shapeBasket;
	}
	public Shape getShape(int i){
		return this.gameFieldShapeBank.get(i);
	}
	public int getShapesOnFieldCount(){
		return this.gameFieldShapeBank.size();
	}
	public boolean update(){
		return this.fallPathField.update();
	}
	public void clear(){
		this.fallPathField.clear();
		this.shapeBasket.randomize();
		this.gameFieldShapeBank.clear();
	}
	public Shape getDroppedShape(){
		return this.fallPathField.getDroppedShape();
	}
	public void scaleSpeedOfShapesOnScreen(float scale) {
		for(int i = 0;i<this.getShapesOnFieldCount();i++){
			this.getShape(i).setSpeedY(this.getShape(i).getSpeedY()*scale);
		}
	}
	public float getWidth(){
		return this.getShapeBasket().getWidth();
	}
	public float getHeight(){
		return this.getShapeBasket().getHeight()+getFallPathField().getHeight();
	}
	public float getFallPathFieldHeight(){
		return this.fallPathField.getHeight();
	}
	public float getFallPathFieldBottom(){
		return this.fallPathField.getBottom();
	}
	public float getFallPathFieldLeft(){
		return this.fallPathField.getLeft();
	}
}
