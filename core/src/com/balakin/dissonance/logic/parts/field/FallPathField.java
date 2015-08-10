package com.balakin.dissonance.logic.parts.field;

import com.balakin.dissonance.logic.parts.shape.Shape;

import java.util.ArrayList;
import java.util.List;

public class FallPathField{
	private float top = 0;
	private float bottom = 0;
	private float left = 0;
	private float right = 0;
	private float margin = 0;
	private float fallPathWidth = 0;
	private Shape droppedShape = null;
	private List<FallPath>fallPathes = null;
	public FallPathField(int fallPathCount,float top,float bottom,float left,float right,float margin, List<Shape> gameFieldShapeBank){
		fallPathes = new ArrayList<FallPath>();
		float center = (right-left-margin*(fallPathCount+1))/fallPathCount/2;
		for(int i = 0;i<fallPathCount;i++){
			fallPathes.add(new FallPath(center*2*(i)+center+margin+margin*i, top, bottom,gameFieldShapeBank));
		}
		this.bottom = bottom;
		this.top = top;
		this.left = left;
		this.right = right;
		this.margin = margin;
		this.fallPathWidth = center*2;
	}
	public boolean update(){
		boolean droppedShape = false;
		this.droppedShape = null;
		for(FallPath fallPath:fallPathes){
			if(fallPath.update()){
				droppedShape = true;
				this.droppedShape = fallPath.getDroppedShape();
			}
		}
		return droppedShape;
	}
	public int getFallPathCount(){
		return fallPathes.size();
	}
	public FallPath getFallPath(int index){
		return fallPathes.get(index);
	}
	public float getTop() {
		return top;
	}
	public float getBottom() {
		return bottom;
	}
	public float getLeft() {
		return left;
	}
	public float getRight() {
		return right;
	}
	public float getMargin(){
		return margin;
	}
	public float getWidth(){
		return this.right - left;
	}
	public float getHeight(){
		return this.top - this.bottom;
	}
	public float getFallPathWidth(){
		return this.fallPathWidth;
	}
	public void clear(){
		for(FallPath fallPath:fallPathes){
			fallPath.clear();
		}
	}
	public Shape getDroppedShape(){
		return droppedShape;
	}
}

