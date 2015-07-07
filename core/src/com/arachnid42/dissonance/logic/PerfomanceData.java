package com.arachnid42.dissonance.logic;

import com.arachnid42.dissonance.logic.parts.field.GameField;
import com.arachnid42.dissonance.logic.parts.optimization.Formulas;

public class PerfomanceData{
	private static final float PIX2 = (float) (Math.PI*2);
	private int msPerUpdate = 0;
	private float speedOneSecondForWay = 0;
	private float rotationPiX2OneSecond = 0;
	public int getMsPerUpdate() {
		return msPerUpdate;
	}
	public void setMsPerUpdate(int msPerUpdate,GameField gameField) {
		this.msPerUpdate = msPerUpdate;
		this.speedOneSecondForWay = Formulas.v(gameField.getFallPathField().getHeight()+gameField.getFallPathField().getFallPathWidth()/2,1,msPerUpdate);
		this.rotationPiX2OneSecond = Formulas.v(PIX2, 1,msPerUpdate);
	}
	public float getRotationPiX2OneSecond() {
		return rotationPiX2OneSecond;
	}
	public float getSpeedOneSecondForWay(){
		return this.speedOneSecondForWay;
	}
}
