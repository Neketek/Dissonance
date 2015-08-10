package com.balakin.dissonance.logic;

import com.balakin.dissonance.logic.parts.field.FallPath;
import com.balakin.dissonance.logic.parts.field.GameField;
import com.balakin.dissonance.logic.parts.optimization.Formulas;
import com.balakin.dissonance.logic.parts.shape.Shape;

import java.util.Random;

public class ShapeThrower{
	private static final int SHAPE_ON_SCREEN_LIM_FALL_PATH_COUNT_MINUS = 2;
	private PerfomanceData perfomanceData;
	private GameStageData gameStageData;
	private GlobalShapePull globalShapePull;
	private GameField gameField;
	private float minFallTime = 0;
	private float maxFallTime = 0;
	private Random rand = null;
	private FallPath getFallPath(int i){
		return this.gameField.getFallPathField().getFallPath(i);
	}
	private int getFallPathCount(){
		return this.gameField.getFallPathField().getFallPathCount();
	}
	private float getFallSpeedForNSeconds(float n){
		return -perfomanceData.getSpeedOneSecondForWay()/n;
	}
	private Shape getRandomizedShape(){
		return this.globalShapePull.getRandomizedShape();
	}
	private FallPath selectFallPath(){
		for(int i = 0;i<getFallPathCount();i++)
			if(getFallPath(i).getShapeCount()==0&&
			rand.nextBoolean()&&rand.nextBoolean()&&rand.nextBoolean())
				return getFallPath(i);
		return null;
	}
	private int getOnScreenShapeLimit(){
		int onScreenLimit = gameStageData.getDroppedShapeCount()/getFallPathCount();
		if(onScreenLimit==0)
			return 1;
		if(onScreenLimit>getFallPathCount()-SHAPE_ON_SCREEN_LIM_FALL_PATH_COUNT_MINUS)
			return getFallPathCount()-SHAPE_ON_SCREEN_LIM_FALL_PATH_COUNT_MINUS;
		return onScreenLimit;
	}
	private float getOptimalSpeed(){
		setMinMaxFallTime();
		float time = minFallTime-gameStageData.getSecondsForReaction();
		if(time>gameStageData.getSecondsForReaction()*1.5f)
			return getFallSpeedForNSeconds(time);
		else
			return getFallSpeedForNSeconds(maxFallTime+gameStageData.getSecondsForReaction());
	}
	public void setMinMaxFallTime(){
		if(gameField.getShapesOnFieldCount()==0){
			maxFallTime = 0;
			minFallTime = 0;
			return;
		}
		maxFallTime = 0;
		minFallTime = Float.MAX_VALUE;
		float time = 0;
		Shape s = null;
		for(int i = 0;i<gameField.getShapesOnFieldCount();i++){
			s = gameField.getShape(i);
			time = Formulas.fallTimeInSeconds(s.getY() - s.getRadius(), Math.abs(s.getSpeedY()), perfomanceData.getMsPerUpdate());
			if(time > maxFallTime)
				maxFallTime = time;
			if(time < minFallTime)
				minFallTime = time;
		}
	}
	public ShapeThrower(GameStageData gameStageData,PerfomanceData perfomanceData,GlobalShapePull globalShapePull){
		this.gameStageData = gameStageData;
		this.perfomanceData = perfomanceData;
		this.globalShapePull = globalShapePull;
		this.gameField = null;
		this.rand = new Random();
	}
	public void setGameField(GameField gameField){
		this.gameField = gameField;
	}
	public boolean tryToThrowShapeToGameField(){
		if(gameField.getShapesOnFieldCount()>=getOnScreenShapeLimit())
			return false;
		FallPath fallPath = selectFallPath();
		if(fallPath==null)
			return false;
		Shape s = getRandomizedShape();
		s.setSpeedY(getOptimalSpeed());
		fallPath.put(s);
		return true;
	}
	public float getMinFallTime(){
		return this.minFallTime;
	}
}
