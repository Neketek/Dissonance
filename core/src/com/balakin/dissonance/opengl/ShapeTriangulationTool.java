package com.balakin.dissonance.opengl;

import com.balakin.dissonance.logic.parts.shape.SPoint;
import com.balakin.dissonance.logic.parts.shape.Shape;

import java.nio.FloatBuffer;

public class ShapeTriangulationTool {
	private static final int FIRST = 0;
	private static final int SECOND = 1;
	private static final int THIRD = 2;
	private static final int FOURTH = 3;
	private static final int FIFTH = 4;
	private static final int SIXTH = 5;
	private static final int SEVENTH = 6;
	private static final int EIGHTH = 7;
	private static final int NINTH = 8;
	private static final int TENTH = 9;
	private static final int TRIANGLE_BUFFER_SIZE = 6;
	private static final int RECTANGLE_BUFFER_SIZE = TRIANGLE_BUFFER_SIZE*2;
	private FloatBuffer vertexBuffer = null;
	private int coordinatesCount = 0;
	private void writePointToBuffer(float x,float y){
		vertexBuffer.put(x);
		vertexBuffer.put(y);
	}
	private void writePointToBuffer(SPoint point){
		writePointToBuffer(point.getX(),point.getY());
	}
	private void triangulateTriangle(Shape triangle){
		coordinatesCount = TRIANGLE_BUFFER_SIZE;
		for(int i = 0;i<triangle.getPointCount();i++){
			writePointToBuffer(triangle.getPoint(i));
		}
	}
	private void triangulateRectangle(Shape rectangle){
		coordinatesCount = RECTANGLE_BUFFER_SIZE;
		writePointToBuffer(rectangle.getPoint(FIRST));
		writePointToBuffer(rectangle.getPoint(SECOND));
		writePointToBuffer(rectangle.getPoint(THIRD));
		writePointToBuffer(rectangle.getPoint(THIRD));
		writePointToBuffer(rectangle.getPoint(FOURTH));
		writePointToBuffer(rectangle.getPoint(FIRST));
	}
	private void triangulateHromb(Shape hromb){
		this.triangulateRectangle(hromb);
	}
	private void triangulateCircle(Shape circle){
		if(this.vertexBuffer.capacity()<TRIANGLE_BUFFER_SIZE*circle.getPointCount())
			throw new Error("NOT ENOUGH SPACE INT BUFFER FOR CIRCLE");
		coordinatesCount = TRIANGLE_BUFFER_SIZE*circle.getPointCount();
		for(int i = 0;i<circle.getPointCount()-1;i++){
			writePointToBuffer(circle.getPoint(i));
			writePointToBuffer(circle.getX(),circle.getY());
			writePointToBuffer(circle.getPoint(i+1));
		}
		writePointToBuffer(circle.getPoint(circle.getPointCount()-1));
		writePointToBuffer(circle.getX(),circle.getY());
		writePointToBuffer(circle.getPoint(FIRST));
	}
	private void triangulatePentagram(Shape penta){
		coordinatesCount = TRIANGLE_BUFFER_SIZE*8;
		writePointToBuffer(penta.getPoint(SECOND));
		writePointToBuffer(penta.getPoint(THIRD));
		writePointToBuffer(penta.getPoint(FOURTH));

		writePointToBuffer(penta.getPoint(FOURTH));
		writePointToBuffer(penta.getPoint(FIFTH));
		writePointToBuffer(penta.getPoint(SIXTH));

		writePointToBuffer(penta.getPoint(SIXTH));
		writePointToBuffer(penta.getPoint(SEVENTH));
		writePointToBuffer(penta.getPoint(EIGHTH));

		writePointToBuffer(penta.getPoint(EIGHTH));
		writePointToBuffer(penta.getPoint(NINTH));
		writePointToBuffer(penta.getPoint(TENTH));

		writePointToBuffer(penta.getPoint(TENTH));
		writePointToBuffer(penta.getPoint(FIRST));
		writePointToBuffer(penta.getPoint(SECOND));
		// inner pentagon
		writePointToBuffer(penta.getPoint(SECOND));
		writePointToBuffer(penta.getPoint(TENTH));
		writePointToBuffer(penta.getPoint(EIGHTH));

		writePointToBuffer(penta.getPoint(SECOND));
		writePointToBuffer(penta.getPoint(FOURTH));
		writePointToBuffer(penta.getPoint(EIGHTH));

		writePointToBuffer(penta.getPoint(FOURTH));
		writePointToBuffer(penta.getPoint(SIXTH));
		writePointToBuffer(penta.getPoint(EIGHTH));

	}
	public void setVertexBuffer(FloatBuffer vertexBuffer){
		if(vertexBuffer==null)
			throw new IllegalArgumentException();
		this.vertexBuffer = vertexBuffer;
	}
	public FloatBuffer getPointBuffer(){
		return this.vertexBuffer;
	}
	public ShapeTriangulationTool(FloatBuffer vertexBuffer){
		this.setVertexBuffer(vertexBuffer);
	}
	public void triangulate(Shape shape){
		vertexBuffer.clear();
		switch (shape.getType()) {
			case Shape.TRIANGLE:
				triangulateTriangle(shape);
				break;
			case Shape.RECTANGLE:
				triangulateRectangle(shape);
				break;
			case Shape.HROMB:
				triangulateHromb(shape);
				break;
			case Shape.CIRCLE:
				triangulateCircle(shape);
				break;
			case Shape.PENTAGRAM:
				triangulatePentagram(shape);
				break;
			default:
				throw new IllegalArgumentException();
		}
	}
	public int getVertexCount(){
		return coordinatesCount/2;
	}
	public int size(){
		return this.coordinatesCount;
	}
}
