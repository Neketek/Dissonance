package com.balakin.dissonance.logic.parts.shape;

import com.balakin.dissonance.logic.parts.interfaces.ShapeDC;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Shape implements ShapeDC {
	public static void setCircleVertexCount(int vertexCount){
		if(vertexCount<PENTAGRAM_VERTEX_COUNT)
			throw new IllegalArgumentException("Can't set circle vertex count less then PENTAGRAM_VERTEX_COUNT");
		SPOINT_PULL_SIZE = vertexCount;
	}
	public static final int TRIANGLE = 0;
	public static final int RECTANGLE = 1;
	public static final int CIRCLE = 2;
	public static final int PENTAGRAM = 3;
	public static final int HROMB = 4;
	public static final int TYPE_COUNT = 5;
	public static final int COLOR_COUNT = 5;
	private static final int PENTAGRAM_VERTEX_COUNT = 10;
	private static final float TRI_HALF_EDGE_FROM_R = (float) (Math.sqrt(3)/2);
	private static final float PENTAGON_ANGLE_CONSTANT = (float)Math.PI*0.4f;
	private static int SPOINT_PULL_SIZE = 50;
	private int type = 0;
	private int color = 0;
	private float x = 0;
	private float y = 0;
	private float radius = 0;
	private float angle = 0;
	private float speedX = 0;
	private float speedY = 0;
	private float rotationSpeed = 0;
	private Deque<SPoint>pointsPull = null;
	private List<SPoint>points = null;
	private int spointPullSize = 0;
	private float circleAngle = 0;
	//initialization methods
	private void fillSPointsPull(){
		for(int i = 0;i<this.spointPullSize;i++)
			this.pointsPull.add(new SPoint(0, 0));
	}
	//Shape points setup methods
	private void setPointListSize(int size){
		if(points.size()<size)
			while(points.size()<size)
				points.add(pointsPull.poll());
		else
			while(points.size()>size)
				pointsPull.add(points.remove(0));
		SPoint first = points.get(0);
		for(int i = 1;i<points.size();i++){
			points.get(i).equalizeMoveTo(first);
		}
	}
	private void changeToTriangle(){
		setPointListSize(3);
		points.get(0).setLocation(0f,1f*radius);
		points.get(1).setLocation(TRI_HALF_EDGE_FROM_R*radius,-0.5f*radius);
		points.get(2).setLocation(-TRI_HALF_EDGE_FROM_R*radius,-0.5f*radius);
	}
	private void changeToRectangle(){
		setPointListSize(4);
		float halfRectEdge = (float) Math.sqrt(radius*radius/2);
		points.get(0).setLocation(halfRectEdge,halfRectEdge);
		points.get(1).setLocation(halfRectEdge,-halfRectEdge);
		points.get(2).setLocation(-halfRectEdge,-halfRectEdge);
		points.get(3).setLocation(-halfRectEdge,halfRectEdge);
	}
	private void changeToCircle(){
		setPointListSize(spointPullSize);
		for(int i = 0;i<spointPullSize;i++){
			points.get(i).setLocation((float)(radius*Math.cos(circleAngle*i)), (float)(radius*Math.sin(circleAngle*i)));
		}
	}
	private void changeToPentagram(){
		setPointListSize(10);
		float x = 0,y = 0;
		for(int i = 0;i<5;i++){
			x = (float)(radius*Math.cos(PENTAGON_ANGLE_CONSTANT*(i+1)-PENTAGON_ANGLE_CONSTANT/4))/1.9f;
			y = (float)(radius*Math.sin(PENTAGON_ANGLE_CONSTANT*(i+1)-PENTAGON_ANGLE_CONSTANT/4))/1.9f;
			points.get(i*2+1).setLocation(x,y);
			x = (float)(radius*Math.cos(PENTAGON_ANGLE_CONSTANT*i+PENTAGON_ANGLE_CONSTANT/4));
			y = (float)(radius*Math.sin(PENTAGON_ANGLE_CONSTANT*i+PENTAGON_ANGLE_CONSTANT/4));
			points.get(i*2).setLocation(x,y);

		}	
	}
	private void changeToHromb(){
		setPointListSize(4);
		points.get(0).setLocation(0, radius);
		points.get(1).setLocation(0.8f*radius,0);
		points.get(2).setLocation(0, -radius);
		points.get(3).setLocation(-0.8f*radius,0);
	}
	//rotation 
	public Shape(){
		this.points = new ArrayList<SPoint>();
		this.pointsPull = new ArrayDeque<SPoint>();
		this.radius = 1f;
		this.spointPullSize = SPOINT_PULL_SIZE;
		this.circleAngle = (float) (2*Math.PI/spointPullSize);
		this.fillSPointsPull();
		this.setType(TRIANGLE);
	}
	public float getRadius() {
		return radius;
	}
	public float getAngle() {
		return angle;
	}
	public float getX() {
		return x;
	}
	public float getY() {
		return y;
	}
	public float getSpeedX() {
		return speedX;
	}
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}
	public float getSpeedY() {
		return speedY;
	}
	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}
	public float getRotationSpeed() {
		return rotationSpeed;
	}
	public void setRotationSpeed(float rotationSpeed) {
		this.rotationSpeed = rotationSpeed;
	}
	public int getType() {
		return type;
	}
	public int getColor() {
		return color;
	}
	public void setRadius(float radius) {
		this.radius = radius;
		this.setType(type);
	}
	public void setAngle(float angle) {
		this.rotate(angle-this.angle);
		this.angle = angle;
	}
	public void setX(float x) {
		this.move(x-this.x,0);
		this.x = x;
	}
	public void setY(float y) {
		this.move(0,y-this.y);
		this.y = y;
	}
	public void setType(int type){
		switch (type) {
		case TRIANGLE:
			this.changeToTriangle();
			break;
		case RECTANGLE:
			this.changeToRectangle();
			break;
		case CIRCLE:
			this.changeToCircle();
			break;
		case HROMB:
			this.changeToHromb();
			break;
		case PENTAGRAM:
			this.changeToPentagram(); 
			break;
		default:
			break;
		}
		this.type = type;
		this.angle = 0;
	}
	public void setColor(int color) {
		this.color = color;
	}
	public void move(float x, float y) {
		this.x+=x;
		this.y+=y;
		for(SPoint sp:points)
			sp.move(x, y);
	}
	public void rotate(float angle) {
		this.angle+=angle;
		if(getType()!=CIRCLE)
			for(SPoint sp:points){
				sp.rotate(angle);
			}
	}
	public int getPointCount(){
		return this.points.size();
	}
	public SPoint getPoint(int index){
		return this.points.get(index);
	}
	public void update(){
		this.rotate(getRotationSpeed());
		this.move(getSpeedX(), getSpeedY());
	}
	public String toString(){
		return "SHAPE{TYPE}:"+type+"{COLOR}:"+color;
	}
}
