package com.arachnid42.dissonance.utils;

import com.arachnid42.dissonance.logic.DissonanceLogic;
import com.badlogic.gdx.Gdx;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceLogicUpdater{
    private static final int MPU_CHANGE_EPS = 2;
    private int changeMpuCounter = 0;
    private DissonanceState dissonanceState = null;
    private DissonanceLogic dissonanceLogic = null;
    public DissonanceLogicUpdater(){
        dissonanceLogic = DissonanceResources.getDissonanceLogic();
        dissonanceState = DissonanceResources.getDissonanceState();
    }
    private void checkMPU(){
        int newMpu = 1000/(Gdx.graphics.getFramesPerSecond()+1);
        if(newMpu==0)
            return;
        if(Math.abs(dissonanceLogic.getMPU() - newMpu)>MPU_CHANGE_EPS) {
            changeMpuCounter++;
        }else
            changeMpuCounter = 0;
        if(changeMpuCounter>10){
            dissonanceLogic.setMPU(newMpu);
        }
    }
    public void update(float delta){
        checkMPU();
        if(dissonanceState.isCameraMoving()
                || dissonanceState.getActiveMenu()!=null
                ||dissonanceState.isTutorialStarted())
            return;
        dissonanceLogic.update();
    }
}
