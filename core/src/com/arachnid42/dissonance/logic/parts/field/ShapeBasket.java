package com.arachnid42.dissonance.logic.parts.field;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.arachnid42.dissonance.logic.parts.interfaces.ShapeBasketDC;
import com.arachnid42.dissonance.logic.parts.shape.Shape;

public class ShapeBasket implements ShapeBasketDC{
	private static final float PLATE_HEIGHT_COEF = 0.7f;
	private static final float SHAPE_R_COEF = 0.35f;
	private int color = 0;
	private int shapeType = 0;
	private float left = 0; 
	private float right = 0;
	private float top = 0;
	private float bottom = 0;
	private float indicatorTop = 0;
	private float indicatorBottom = 0;
	private float plateWidth = 0;
	private float plateHeight = 0;
	private List<Shape>plates = null;
	private int activePlateIndex = 0;
	public ShapeBasket(float top,float bottom,float left,float right,int platesCount){
		this.top = top;
		this.indicatorTop = top;
		this.bottom = bottom;
		this.right = right;
		this.left = left;
		this.plateHeight = (top - bottom)*PLATE_HEIGHT_COEF;
		this.indicatorBottom = plateHeight+bottom;
		this.plateWidth = (right-left)/platesCount;
		this.plates = new ArrayList<Shape>(platesCount);
		List<Shape>shapes = new ArrayList<Shape>(platesCount);
		List<Integer>colors = new ArrayList<Integer>();
		Random rand = new Random();
		for(int i = 0;i<platesCount;i++){
			colors.add(i);
		}
		Shape s = null;
		for(int i = 0;i<platesCount;i++){
			s = new Shape();
			s.setType(i);
			s.setColor(colors.remove(rand.nextInt(colors.size())));
			if(plateHeight>plateWidth)
				s.setRadius(plateWidth*SHAPE_R_COEF);
			else
				s.setRadius(plateHeight*SHAPE_R_COEF);
			shapes.add(s);
		}
		int i = 0;
		while(plates.size()!=platesCount){
			plates.add(shapes.remove(rand.nextInt(shapes.size())));
			plates.get(i).setX(0);
			plates.get(i).setY(0);
			plates.get(i).move(plateWidth*i+plateWidth/2,plateHeight/2+getBottom());
			if(plates.get(i).getType()==Shape.TRIANGLE)
				plates.get(i).move(0,-plates.get(i).getRadius()/4);
			i++;
		}
		this.setActivePlate(platesCount/2);
	}
	public int getActivePlateColor() {
		return color;
	}
	public int getActivePlateShapeType() {
		return shapeType;
	}
	public float getLeft() {
		return left;
	}
	public float getRight() {
		return right;
	}
	public float getTop() {
		return top;
	}
	public float getBottom() {
		return bottom;
	}
	public void setActivePlate(int activePlateIndex){
		this.activePlateIndex = activePlateIndex;
		this.shapeType = plates.get(activePlateIndex).getType();
		this.color = plates.get(activePlateIndex).getColor();
	}
	public int getActivePlateIndex(){
		return this.activePlateIndex;
	}
	public float getPlateWidth() {
		return plateWidth;
	}
	public float getPlateHeight() {
		return plateHeight;
	}
	public boolean isShapeTypeEqualsActivePlateType(Shape s){
		return this.plates.get(activePlateIndex).getType() == s.getType(); 
	}
	public boolean isShapeColorEqualsActivePlateColor(Shape s){
		return this.plates.get(activePlateIndex).getColor() == s.getColor();
	}
	public float getIndicatorTop() {
		return indicatorTop;
	}
	public float getIndicatorBottom() {
		return indicatorBottom;
	}
	public int getPlateCount(){
		return this.plates.size();
	}
	public Shape getPlateShape(int index){
		return this.plates.get(index);
	}
	public float getIndicatorWidth(){
		return this.right - left;
	}
	public float getIndicatorHeight(){
		return this.indicatorTop - indicatorBottom;
	}
	public void randomize(){
		List<Integer>random = new ArrayList<Integer>();
		Random rand = new Random();
		for(int i = 0;i<getPlateCount();i++){
			random.add(i);
		}
		Shape s = null;
		for(int i = 0;i<getPlateCount();i++){
			s = plates.get(i);
			s.setColor(random.remove(rand.nextInt(random.size())));
		}
		for(int i = 0;i<getPlateCount();i++){
			random.add(i);
		}
		for(int i = 0;i<getPlateCount();i++){
			s = plates.get(i);
			s.setType(random.remove(rand.nextInt(random.size())));
			s.setX(0);
			s.setY(0);
			s.move(plateWidth*i+plateWidth/2,plateHeight/2+getBottom());
			if(s.getType()==Shape.TRIANGLE)
				s.move(0,-plates.get(i).getRadius()/4);
		}
		this.setActivePlate(getPlateCount()/2);
	}
	public float getWidth(){
		return this.getIndicatorWidth();
	}
	public float getHeight(){
		return this.getIndicatorHeight()+getPlateHeight();
	}
}
