package com.balakin.dissonance.opengl.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;
import com.balakin.dissonance.menu.layout.DissonanceScreenGrid;
import com.balakin.dissonance.menu.layout.DissonanceScreenGridController;
import com.balakin.dissonance.opengl.render.game.DissonanceLogicRenderer;
import com.balakin.dissonance.opengl.render.menu.DissonanceMenuRenderer;
import com.balakin.dissonance.opengl.render.menu.SplashScreenRenderer;
import com.balakin.dissonance.utils.DissonanceResources;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceMainRenderer{
    private DissonanceMenuRenderer menuRenderer = null;
    private DissonanceLogicRenderer logicRenderer = null;
    private DissonanceCameraTool cameraTool = null;
    private DissonanceScreenGrid screenGrid = null;
    private SplashScreenRenderer splashScreenRenderer = null;
    private DissonanceScreenGridController screenGridController = null;
    public DissonanceMainRenderer(){
        logicRenderer = DissonanceResources.getDissonanceLogicRenderer();
        cameraTool = DissonanceResources.getDissonanceCameraTool();
        menuRenderer = DissonanceResources.getDissonanceMenuRenderer();
        screenGrid = DissonanceResources.getDissonanceScreenGrid();
        screenGridController = DissonanceResources.getDissonanceScreenGridController();
        splashScreenRenderer = DissonanceResources.getSplashScreenRenderer();
    }
    public void render(){
        Matrix4 projection = cameraTool.getProjectionMatrix();
        if(splashScreenRenderer.isAnimated()){
            splashScreenRenderer.render(projection);
            splashScreenRenderer.update(Gdx.graphics.getDeltaTime());
            return;
        }
        logicRenderer.render(projection);
        if(!DissonanceResources.getDissonanceState().isCameraMoving()&&
                DissonanceResources.getDissonanceState().getActiveMenu()==null)
            return;
        menuRenderer.render(screenGrid.getMainMenu(), projection);
        menuRenderer.render(screenGrid.getPauseMenu(), projection);
        menuRenderer.render(screenGrid.getSettingsMenu(), projection);
        menuRenderer.render(screenGrid.getScoreMenu(),projection);
        if(!screenGridController.updateMoveTask(Gdx.graphics.getDeltaTime())){
            DissonanceResources.getDissonanceState().setCameraMoving(false);
        }
    }
}
