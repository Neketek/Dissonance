package com.arachnid42.dissonance.logic;

import com.arachnid42.dissonance.listeners.DissonanceLogicListener;
import com.arachnid42.dissonance.logic.parts.field.GameField;
import com.arachnid42.dissonance.logic.parts.interfaces.DissonanceLogicDC;
import com.arachnid42.dissonance.logic.parts.shape.Shape;

public class DissonanceLogic implements DissonanceLogicDC{
	private static final int STANDARD_SHAPE_PULL_SIZE = 20;
	private static final int DEFAULT_FALL_PATH_COUNT = 4;
	private static final int MAX_FAILS_COUNT = 1; // TODO:change this constant to 1
	private static final float FALL_SPEED_SCALE_AFTER_FAIL = 1.1f;
	private static final float FALL_SPEED_SCALE_AFTER_SUCC = 1.1f;
	private static final float SECOND_FOR_REACTION_CHANGE_AFTER_DROP = 0.2f;
	private static final float FALL_SPEED_SCALE_AFTER_GM_SWAP = 1f;
	private GlobalShapePull globalShapePull = null;
	private ShapeThrower shapeThrower = null;
	private GameStageData gameStageData = null;
	private PerfomanceData perfomanceData = null;
	private DissonanceLogicListener logicListener = null;
	private GameField gameField = null;
	private long msCatch = 0;
	private void resetMPU(int newMpu){
		int oldMpu = perfomanceData.getMsPerUpdate();
		float coef = ((float)newMpu)/oldMpu;
		Shape s = null;
		for(int i = 0;i<gameField.getShapesOnFieldCount();i++){
			s = gameField.getShape(i);
			s.setSpeedY(s.getSpeedY()*coef);
			s.setRotationSpeed(s.getRotationSpeed()*coef);
		}
	}
	public DissonanceLogic(){
		this.gameStageData = new GameStageData();
		this.perfomanceData = new PerfomanceData();
		this.globalShapePull = new GlobalShapePull(DEFAULT_FALL_PATH_COUNT, STANDARD_SHAPE_PULL_SIZE,perfomanceData);
		this.shapeThrower = new ShapeThrower(gameStageData, perfomanceData, globalShapePull);
	}
	public void setGameField(GameField gameField){
		if(gameField==null)
			throw new IllegalArgumentException();
		if(this.gameField!=null)
			for(int i = 0;i<this.gameField.getShapesOnFieldCount();i++)
				this.globalShapePull.put(this.gameField.getShape(i));
		this.gameField = gameField;
		this.globalShapePull.setFallPathCount(gameField.getFallPathField().getFallPathCount());
		this.globalShapePull.setSuitableShapeRadius(gameField);
		this.shapeThrower.setGameField(gameField);
		this.perfomanceData.setMsPerUpdate(perfomanceData.getMsPerUpdate(), gameField);
	}
	public GameField getGameField(){
		return this.gameField;
	}
	public void setMPU(int ms){
		if(gameField!=null)
			this.resetMPU(ms);
		this.perfomanceData.setMsPerUpdate(ms,gameField);
	}
	public GameStageData getGameStageData(){
		return this.gameStageData;
	}
	public boolean update(){
		if(gameField==null)
			throw new IllegalStateException();
		if(gameStageData.getFails()>=MAX_FAILS_COUNT){
			logicListener.onFail();
		//	gameStageData.setGameMode(GameStageData.GAME_FAILED);
			return false;
		}
		if(shapeThrower.tryToThrowShapeToGameField())
			this.msCatch = System.currentTimeMillis();
		if(this.gameField.update()){
			switch (gameStageData.getGameMode()) {
			case GameStageData.COLOR_COMPARISON:
					if(gameField.getShapeBasket().isShapeColorEqualsActivePlateColor(gameField.getDroppedShape()))
						gameStageData.increaseScore();
					else
						gameStageData.increaseFails();
				break;
			case GameStageData.SHAPE_COMPARISON:
				if(gameField.getShapeBasket().isShapeTypeEqualsActivePlateType(gameField.getDroppedShape()))
					gameStageData.increaseScore();
				else 
					gameStageData.increaseFails();
				break;
			default:
				throw new Error();
			}
			globalShapePull.put(gameField.getDroppedShape());
			gameStageData.increaseDroppedShapeCount();
			if(gameStageData.isFailsIncreased()){
				gameField.scaleSpeedOfShapesOnScreen(FALL_SPEED_SCALE_AFTER_FAIL);
				gameStageData.decreaseSecondForReaction(SECOND_FOR_REACTION_CHANGE_AFTER_DROP);
			}
			else{
				logicListener.onShapeFall();
				gameStageData.decreaseSecondForReaction(SECOND_FOR_REACTION_CHANGE_AFTER_DROP);
				gameField.scaleSpeedOfShapesOnScreen(FALL_SPEED_SCALE_AFTER_SUCC);
			}
		}
		if(gameStageData.gameModeSwapCondition(gameField,perfomanceData.getMsPerUpdate())){
			logicListener.onGameModeChange();
			gameStageData.swapGameModes();
			gameField.scaleSpeedOfShapesOnScreen(FALL_SPEED_SCALE_AFTER_GM_SWAP);
		}
		return true;
	}
	public void reset(){
		logicListener.beforeReset();
		gameStageData.reset();
		if(gameField==null)
			return;
		if(this.gameField!=null)
			for(int i = 0;i<this.gameField.getShapesOnFieldCount();i++)
				this.globalShapePull.put(this.gameField.getShape(i));
		gameField.clear();
		logicListener.afterReset();
	}
	public int getMPU(){
		return this.perfomanceData.getMsPerUpdate();
	}

	public DissonanceLogicListener getLogicListener() {
		return logicListener;
	}

	public void setLogicListener(DissonanceLogicListener logicListener) {
		this.logicListener = logicListener;
	}
}
