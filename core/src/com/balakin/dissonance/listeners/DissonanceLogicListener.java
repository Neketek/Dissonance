package com.balakin.dissonance.listeners;

import com.balakin.dissonance.logic.GameStageData;
import com.balakin.dissonance.opengl.render.game.DissonanceLogicRenderer;
import com.balakin.dissonance.utils.DissonanceConfig;
import com.balakin.dissonance.utils.DissonanceResources;
import com.balakin.dissonance.utils.DissonanceSound;
import com.balakin.dissonance.utils.androidControllers.DissonancePSController;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceLogicListener{
    private DissonanceLogicRenderer dissonanceLogicRenderer = null;
    private GameStageData gameStageData = null;
    private DissonancePSController psController = null;
    public DissonanceLogicListener(){
        dissonanceLogicRenderer = DissonanceResources.getDissonanceLogicRenderer();
        psController = DissonanceResources.getDissonancePSController();
        gameStageData = DissonanceResources.getDissonanceLogic().getGameStageData();
    }
    public void afterReset(){
        DissonanceResources.getDissonanceState().setGameFailed(false);
        dissonanceLogicRenderer.stopAllAnimations();
    }
    public void onFail(){
        DissonanceResources.getDissonanceSound().play(DissonanceSound.GAME_FAILED);
        DissonanceResources.getDissonanceState().setGameFailed(true);
    }
    public void beforeReset(){
        if(DissonanceConfig.maxScore< DissonanceResources.getDissonanceLogic().getGameStageData().getScore())
            DissonanceConfig.maxScore = DissonanceResources.getDissonanceLogic().getGameStageData().getScore();
        psController.submitPlayerScore(DissonanceConfig.maxScore);
    }
    public void onGameModeChange(){
        DissonanceResources.getDissonanceSound().play(DissonanceSound.GAME_MODE);
    }
    public void onShapeFall(){
        DissonanceResources.getDissonanceSound().play(DissonanceSound.SHAPE);
        if(gameStageData.getScore()>DissonanceConfig.maxScore){
            DissonanceConfig.maxScore++;
        }
    }
}
