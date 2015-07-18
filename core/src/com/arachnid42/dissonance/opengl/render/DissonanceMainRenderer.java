package com.arachnid42.dissonance.opengl.render;

import com.arachnid42.dissonance.utils.DissonanceResources;
import com.arachnid42.dissonance.menu.layout.DissonanceScreenGrid;
import com.arachnid42.dissonance.menu.layout.DissonanceScreenGridController;
import com.arachnid42.dissonance.opengl.render.game.DissonanceLogicRenderer;
import com.arachnid42.dissonance.opengl.render.menu.DissonanceMenuRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Matrix4;

import java.awt.SplashScreen;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceMainRenderer{
    private DissonanceMenuRenderer menuRenderer = null;
    private DissonanceLogicRenderer logicRenderer = null;
    private DissonanceCameraTool cameraTool = null;
    private DissonanceScreenGrid screenGrid = null;
    private DissonanceScreenGridController screenGridController = null;
    public DissonanceMainRenderer(){
        logicRenderer = DissonanceResources.getDissonanceLogicRenderer();
        cameraTool = DissonanceResources.getDissonanceCameraTool();
        menuRenderer = DissonanceResources.getDissonanceMenuRenderer();
        screenGrid = DissonanceResources.getDissonanceScreenGrid();
        screenGridController = DissonanceResources.getDissonanceScreenGridController();
    }
    public void render(){
        Matrix4 projection = cameraTool.getProjectionMatrix();
        logicRenderer.render(projection);
        if(!DissonanceResources.getDissonanceState().isCameraMoving()&&
                DissonanceResources.getDissonanceState().getActiveMenu()==null)
            return;
        menuRenderer.render(screenGrid.getMainMenu(), projection);
        menuRenderer.render(screenGrid.getPauseMenu(), projection);
        menuRenderer.render(screenGrid.getSettingsMenu(), projection);
        if(!screenGridController.updateMoveTask(Gdx.graphics.getDeltaTime())){
            DissonanceResources.getDissonanceState().setCameraMoving(false);
        }
    }
}
