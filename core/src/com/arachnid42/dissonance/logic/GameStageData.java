package com.arachnid42.dissonance.logic;

import java.util.Random;

import com.arachnid42.dissonance.logic.parts.field.GameField;
import com.arachnid42.dissonance.logic.parts.interfaces.GameStageDataDC;
import com.arachnid42.dissonance.logic.parts.optimization.Formulas;

public class GameStageData implements GameStageDataDC{
	public static final int COLOR_COMPARISON = 0;
	public static final int SHAPE_COMPARISON = 1;
	public static final int GAME_FAILED = 2;
	private static final int MODE_CHANGE_MIN_INTERVAL = 8;
	private static final int MODE_CHANGE_INTERVAL_EPS = 5;
	private static final int MODE_CHANGE_MAX_INTERVAL = 12;
	public static final float DEFAULT_SPEED_SCALE = 1.0f;
	public static final float DEFAULT_SEC_FOR_REACTION = 4f;
	private static final float MIN_SEC_FOR_REACTION = 1.5f;
	private static final float MODE_CHANGE_REACTION_SCALE = 1.0f;
	private static final boolean SCORE_INCREASED = false;
	private static final boolean FAILS_INCREASED = true;
	private float secondsForReaction = DEFAULT_SEC_FOR_REACTION;
	private int score = 0;
	private int fails = 0;
	private int gameMode = 0;
	private float speedScale = 1.0f;
	private int droppedShapeCount = 0;
	private boolean increaseFlag = false;
	private int lastChangeShapeFallCount = 0;
	private int modeChangeInterval = 0;
	private Random random = null;
	private boolean gameModeSwapRandomCondition(){
		if(random.nextBoolean())
			return droppedShapeCount-lastChangeShapeFallCount>=modeChangeInterval+Math.random()*MODE_CHANGE_INTERVAL_EPS;
		return droppedShapeCount-lastChangeShapeFallCount>=modeChangeInterval-Math.random()*MODE_CHANGE_INTERVAL_EPS;
	}
	public GameStageData() {
		this.random = new Random();
		reset();
	}
	public boolean gameModeSwapCondition(GameField gameField,int mpu){
		if(gameModeSwapRandomCondition()){
			float minFallTime = Float.MAX_VALUE;
			float time = 0;
			for(int i = 0;i<gameField.getShapesOnFieldCount();i++){
				time = Formulas.fallTimeInSeconds(gameField.getShape(i).getY()-gameField.getShape(i).getRadius(),Math.abs(gameField.getShape(i).getSpeedY()), mpu);
				if(time < minFallTime)
					minFallTime = time;
			}
			return minFallTime >=getSecondsForReaction()*MODE_CHANGE_REACTION_SCALE;
		}else{
			return false;
		}
	}
	public void swapGameModes(){
		lastChangeShapeFallCount = getDroppedShapeCount();
		if(gameMode==COLOR_COMPARISON)
			gameMode = SHAPE_COMPARISON;
		else
			gameMode = COLOR_COMPARISON;
	}
	public void reset(){
		droppedShapeCount = 0;
		score = 0;
		fails = 0;
		speedScale = DEFAULT_SPEED_SCALE;
		secondsForReaction = DEFAULT_SEC_FOR_REACTION;
		if(random.nextBoolean())
			gameMode = SHAPE_COMPARISON;
		else
			gameMode = COLOR_COMPARISON;
		lastChangeShapeFallCount = 0;
		modeChangeInterval = (int) (MODE_CHANGE_MIN_INTERVAL+Math.random()*(MODE_CHANGE_MAX_INTERVAL-MODE_CHANGE_MIN_INTERVAL));
	}
	public void increaseScore(){
		increaseFlag = SCORE_INCREASED;
		score++;
	}
	public void increaseFails(){
		increaseFlag = FAILS_INCREASED;
		fails++;
	}
	public void increaseDroppedShapeCount(){
		this.droppedShapeCount++;
	}
	public void decreaseSecondForReaction(float second){
		if(second<=0)
			throw new IllegalArgumentException();
		this.secondsForReaction-=second;
		if(secondsForReaction<MIN_SEC_FOR_REACTION)
			secondsForReaction = MIN_SEC_FOR_REACTION;
	}
	public void increaseSpeedScale(float addToScale){
		if(addToScale<0)
			throw new IllegalArgumentException();
		this.speedScale+=addToScale;
	}
	public float getSecondsForReaction(){
		return this.secondsForReaction;
	}
	public int getScore(){
		return this.score;
	}
	public int getFails(){
		return this.fails;
	}
	public int getGameMode(){
		return this.gameMode;
	}
	public int getDroppedShapeCount(){
		return this.droppedShapeCount;
	}
	public void setGameMode(int gameMode){
		if(gameMode>GAME_FAILED||gameMode<COLOR_COMPARISON)
			throw new IllegalArgumentException();
		this.gameMode = gameMode;
	}
	public float getSpeedScale(){
		return this.speedScale;
	}
	public boolean isScoreIncreased(){
		return increaseFlag==SCORE_INCREASED;
	}
	public boolean isFailsIncreased(){
		return increaseFlag==FAILS_INCREASED;
	}
	public int getModeChangeInterval(){
		return this.modeChangeInterval;
	}

}
