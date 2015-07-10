package com.arachnid42.dissonance.listeners;

import com.arachnid42.dissonance.DissonanceConfig;
import com.arachnid42.dissonance.DissonanceResources;
import com.arachnid42.dissonance.logic.DissonanceLogic;
import com.arachnid42.dissonance.menu.layout.DissonanceVirtualGrid;
import com.arachnid42.dissonance.opengl.render.game.DissonanceLogicRenderer;
import com.badlogic.gdx.Gdx;

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
}
