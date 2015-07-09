package com.arachnid42.dissonance;

import com.arachnid42.dissonance.logic.DissonanceLogic;
import com.badlogic.gdx.Gdx;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceLogicUpdater{
    private static final int MPU_CHANGE_EPS = 3;
    private DissonanceState dissonanceState = null;
    private DissonanceLogic dissonanceLogic = null;
    public DissonanceLogicUpdater(){
        dissonanceLogic = DissonanceResources.getDissonanceLogic();
        dissonanceState = DissonanceResources.getDissonanceState();
    }
    public void update(float delta){
        if(dissonanceState.isCameraMoving()||dissonanceState.getActiveMenu()!=null)
            return;
        int newMpu = (int)(Gdx.graphics.getDeltaTime()*1000);
        if(newMpu==0)
            return;
        if(Math.abs(dissonanceLogic.getMPU() - newMpu)>MPU_CHANGE_EPS)
            dissonanceLogic.setMPU(newMpu);
        dissonanceLogic.update();
    }
}
