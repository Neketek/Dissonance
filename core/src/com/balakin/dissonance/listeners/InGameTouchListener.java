package com.balakin.dissonance.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.balakin.dissonance.logic.parts.field.GameField;
import com.balakin.dissonance.logic.parts.field.ShapeBasket;
import com.balakin.dissonance.menu.layout.DissonanceVirtualGrid;
import com.balakin.dissonance.utils.DissonanceResources;
import com.balakin.dissonance.utils.DissonanceState;

/**
 * Created by neketek on 04.07.15.
 */
public class InGameTouchListener extends InputAdapter{
    private static final int NO_MAIN_FINGER = -1;
    private boolean preparedToPause = false;
    private ShapeBasket basket = null;
    private GameField gameField = null;
    private int mainFingerId = NO_MAIN_FINGER;
    private DissonanceState dissonanceState = null;
    private boolean isCameraMoving(){
        return DissonanceResources.getDissonanceState().isCameraMoving();
    }
    private boolean isGameFailed(){
        return this.dissonanceState.isGameFailed();
    }
    private boolean isSomeMenuActive(){
        return dissonanceState.getActiveMenu()!=null;
    }
    private boolean isBaskedPlateChangeEnabled(){
        return !(isGameFailed()||isSomeMenuActive());
    }
    private float getYCoordinateInWorldSystem(float y){
        return Gdx.graphics.getHeight()-y;
    }
    private void toPauseMenu(){
        DissonanceResources.getDissonanceLogicRenderer().stopAllAnimations();
        Gdx.input.setInputProcessor(DissonanceResources.getInMenuTouchListener());
        DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.PAUSE_MENU);
        DissonanceResources.getDissonanceCameraTool().setWaitBeforeMoveEnabled(false);
        DissonanceResources.getDissonanceState().setActiveMenu(DissonanceResources.getDissonanceScreenGrid().getPauseMenu());
        DissonanceResources.getDissonanceState().setCameraMoving(true);
    }
    private boolean isFingerOnBasket(float x,float y){
        return (y<=basket.getTop()&&y>=basket.getBottom())&&(x<=basket.getRight()&&x>=basket.getLeft());
    }
    private boolean isFingerOnField(float x,float y){
        return  y>=gameField.getFallPathFieldBottom()&&y<=gameField.getFallPathFieldBottom()+gameField.getHeight()&&
                x>=gameField.getFallPathFieldLeft()&&x<=gameField.getFallPathFieldLeft()+gameField.getHeight();
    }
    private boolean isMainFinger(int fingerId){
        if(mainFingerId==NO_MAIN_FINGER) {
            mainFingerId = fingerId;
            return true;
        }
        return fingerId == mainFingerId;
    }
    private int getPlateIndexAt(float x){
        for(int i = 0;i<basket.getPlateCount();i++)
            if(x>=basket.getPlateWidth()*i&&basket.getPlateWidth()*(i+1)>=x)
                return i;
        return -1;
    }
    private boolean switchShapeBasketPlate(float x,float y){
        y = getYCoordinateInWorldSystem(y);
        if(isFingerOnBasket(x,y)){
            int plateIndex = getPlateIndexAt(x);
            if(plateIndex>=0) { // пофиксил тупую ошибку, вместо индекса была координата.
                basket.setActivePlate(plateIndex);
                preparedToPause = false; // если плитку переключили, то в паузу не войдешь.
                return true;
            }
            return false;
        }
        return false;
    }
    private void setPreparedToPause(float x,float y){
        y = getYCoordinateInWorldSystem(y);
        preparedToPause = isFingerOnField(x,y);
    }
    private boolean tryToTurnOffMainFingerId(int fingerId){
        if(fingerId==mainFingerId) {
            mainFingerId = NO_MAIN_FINGER;
            return true;
        }
        return false;
    }
    private void tryToGoToPauseMenu(){
        if(preparedToPause)
            toPauseMenu();
        preparedToPause = false;
    }
    public InGameTouchListener(){
        dissonanceState = DissonanceResources.getDissonanceState();
        basket = DissonanceResources.getDissonanceLogic().getGameField().getShapeBasket();
        gameField = DissonanceResources.getDissonanceLogic().getGameField();
    }
    @Override
    public boolean touchDown(int x, int y, int pointer, int button){
        if(isCameraMoving()||dissonanceState.isTutorialStarted())
            return true;
        if(isMainFinger(pointer)){
            if(isBaskedPlateChangeEnabled()&&switchShapeBasketPlate(x,y))// теперь плитки переключаются и по тапу.
                return true;
            else
                setPreparedToPause(x,y);
        }
        return true;
    }
    @Override
    public boolean touchUp(int x, int y, int pointer, int button) {
        if(dissonanceState.isTutorialStarted()){
            DissonanceResources.getDissonanceLogicRenderer().requestTutorialNextStage();
            return true;
        }
        if(tryToTurnOffMainFingerId(pointer)) {
            tryToGoToPauseMenu();
        }
        return true;
    }
    @Override
    public boolean touchDragged(int x, int y, int pointer) {
        if(isCameraMoving()||dissonanceState.isTutorialStarted())
            return true;
        if(isBaskedPlateChangeEnabled()&&isMainFinger(pointer)){
            switchShapeBasketPlate(x,y);
            return true;
        }
        return true;
    }
}
