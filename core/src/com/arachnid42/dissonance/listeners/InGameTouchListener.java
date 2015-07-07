package com.arachnid42.dissonance.listeners;

import com.arachnid42.dissonance.logic.DissonanceLogic;
import com.arachnid42.dissonance.logic.GameStageData;
import com.arachnid42.dissonance.logic.parts.field.GameField;
import com.arachnid42.dissonance.logic.parts.field.ShapeBasket;
import com.arachnid42.dissonance.logic.parts.interfaces.ShapeBasketDC;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

/**
 * Created by neketek on 04.07.15.
 */
public class InGameTouchListener extends InputAdapter{
    private static final int NO_MAIN_FINGER = -1;
    private boolean preparedToPause = false;
    private ShapeBasket basket = null;
    private GameStageData gameStageData = null;
    private GameField gameField = null;
    private DissonanceLogic dissonanceLogic = null;
    private int mainFingerId = -1;
    private boolean isFingerOnBasket(float x,float y){
        return (y<=basket.getTop()&&y>=basket.getBottom())&&(x<=basket.getRight()&&x>=basket.getLeft());
    }
    private boolean isFingerOnField(float x,float y){
        return  y>=gameField.getFallPathFieldBottom()&&y<=gameField.getFallPathFieldBottom()+gameField.getHeight()&&
                x>=gameField.getFallPathFieldLeft()&&x<=gameField.getFallPathFieldLeft()+gameField.getHeight();
    }
    private int getPlateIndexAt(float x){
        for(int i = 0;i<basket.getPlateCount();i++)
            if(x>=basket.getPlateWidth()*i&&basket.getPlateWidth()*(i+1)>=x)
                return i;
        return -1;
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button) {
        if(mainFingerId==-1) {
            mainFingerId = pointer;
            y = Gdx.graphics.getHeight()-y;
            if(isFingerOnField(x,y)){
                preparedToPause = true;
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if(mainFingerId==pointer) {
            mainFingerId = -1;
            y = Gdx.graphics.getHeight()-y;
            if(isFingerOnField(x,y)&&preparedToPause) {
                dissonanceLogic.reset();
                preparedToPause = false;
            }
            else
                preparedToPause = false;
        }
        return false;
    }
    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        //System.out.println("MAIN FINGER:"+mainFingerId+" FINGER:"+pointer);
        if(mainFingerId==NO_MAIN_FINGER)
            mainFingerId = pointer;
        else
            if(pointer!=mainFingerId)
                return true;
        //System.out.println("CHECK BORDER FOR BASKET");
        y = Gdx.graphics.getHeight()-y;
        if(isFingerOnBasket(x,y)){
           // System.out.println("CHECK BORDER FOR BASKET:TRUE");
            int plateIndex = getPlateIndexAt(x);
            if(x>=0)
                basket.setActivePlate(plateIndex);
        }
        return true;
    }
    public void setShapeBasket(ShapeBasket basket){
        if (basket == null)
            throw new IllegalArgumentException();
        this.basket = basket;
    }
    public void setGameStageData(GameStageData gameStageData){
        if (gameStageData == null) {
            throw new IllegalArgumentException();
        }
        this.gameStageData = gameStageData;
    }
    public void setDissonanceLogic(DissonanceLogic dissonanceLogic){
        if (dissonanceLogic == null) {
            throw new IllegalArgumentException();
        }
        this.dissonanceLogic = dissonanceLogic;
    }
    public void setGameField(GameField gameField){
        if (gameField == null) {
            throw new IllegalArgumentException();
        }
        this.gameField = gameField;
    }
}
