package com.arachnid42.dissonance.listeners;

import com.arachnid42.dissonance.utils.DissonanceConfig;
import com.arachnid42.dissonance.utils.DissonanceResources;
import com.arachnid42.dissonance.opengl.render.game.DissonanceLogicRenderer;
import com.arachnid42.dissonance.utils.DissonanceSound;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceLogicListener{
    private DissonanceLogicRenderer dissonanceLogicRenderer = null;
    public DissonanceLogicListener(){
        dissonanceLogicRenderer = DissonanceResources.getDissonanceLogicRenderer();
    }
    public void afterReset(){
        DissonanceResources.getDissonanceState().setGameFailed(false);
        dissonanceLogicRenderer.stopAllAnimations();
    }
    public void onFail(){
        DissonanceResources.getDissonanceState().setGameFailed(true);
       // dissonanceLogicRenderer.stopAllAnimations();
    }
    public void beforeReset(){
        // TODO: add internet verification of max score save
        if(DissonanceConfig.maxScore<DissonanceResources.getDissonanceLogic().getGameStageData().getScore())
            DissonanceConfig.maxScore = DissonanceResources.getDissonanceLogic().getGameStageData().getScore();
    }
    public void onGameModeChange(){
        DissonanceResources.getDissonanceSound().play(DissonanceSound.GAME_MODE);
    }
    public void onShapeFall(){
        DissonanceResources.getDissonanceSound().play(DissonanceSound.SHAPE);
    }
}
