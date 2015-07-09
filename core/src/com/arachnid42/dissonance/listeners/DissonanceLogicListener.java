package com.arachnid42.dissonance.listeners;

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
    public void onReset(){
        DissonanceResources.getDissonanceState().setGameFailed(false);
        dissonanceLogicRenderer.stopAllAnimations();
    }
    public void onFail(){
        DissonanceResources.getDissonanceState().setGameFailed(true);
       // dissonanceLogicRenderer.stopAllAnimations();
    }
}
