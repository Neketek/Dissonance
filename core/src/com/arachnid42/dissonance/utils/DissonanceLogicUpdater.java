package com.arachnid42.dissonance.utils;

import com.arachnid42.dissonance.logic.DissonanceLogic;
import com.badlogic.gdx.Gdx;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceLogicUpdater{
    private static final int MPU_CHANGE_EPS = 2;
    private DissonanceState dissonanceState = null;
    private DissonanceLogic dissonanceLogic = null;
    public DissonanceLogicUpdater(){
        dissonanceLogic = DissonanceResources.getDissonanceLogic();
        dissonanceState = DissonanceResources.getDissonanceState();
    }
    public void update(float delta){
       // System.out.println("FPS:"+Gdx.graphics.getFramesPerSecond());
        if(dissonanceState.isCameraMoving()
                || dissonanceState.getActiveMenu()!=null
                ||dissonanceState.isTutorialStarted())
            return;
        int newMpu = 1000/Gdx.graphics.getFramesPerSecond();
        if(newMpu==0)
            return;
        if(Math.abs(dissonanceLogic.getMPU() - newMpu)>MPU_CHANGE_EPS)
            dissonanceLogic.setMPU(newMpu);
        dissonanceLogic.update();
    }
}
