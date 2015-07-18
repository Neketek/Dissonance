package com.arachnid42.dissonance.menu.layout;

import com.arachnid42.dissonance.opengl.render.DissonanceCameraTool;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceScreenGridController {
    private static float MOVE_TIME = 0.2f;
    private DissonanceScreenGrid dissonanceScreenGrid = null;
    private DissonanceCameraTool dissonanceCameraTool = null;
    public DissonanceScreenGridController(DissonanceScreenGrid screenGrid,DissonanceCameraTool cameraTool){
        setDissonanceCameraTool(cameraTool);
        setDissonanceScreenGrid(screenGrid);
    }
    public void setCameraMoveTask(int cellId){
        dissonanceScreenGrid.setCameraToolMoveTask(cellId,dissonanceCameraTool);
    }
    public void setCameraLocation(int cellId){
        dissonanceScreenGrid.setCameraLocation(cellId,dissonanceCameraTool);
    }
    public boolean updateMoveTask(float delta){
        if(dissonanceCameraTool.isMoveTaskEnabled())
            return this.dissonanceCameraTool.updateMoveTask(delta,MOVE_TIME);
        return false;
    }
    public DissonanceCameraTool getDissonanceCameraTool() {
        return dissonanceCameraTool;
    }

    public void setDissonanceCameraTool(DissonanceCameraTool dissonanceCameraTool) {
        this.dissonanceCameraTool = dissonanceCameraTool;
    }

    public DissonanceScreenGrid getDissonanceScreenGrid() {
        return dissonanceScreenGrid;
    }

    public void setDissonanceScreenGrid(DissonanceScreenGrid dissonanceScreenGrid) {
        this.dissonanceScreenGrid = dissonanceScreenGrid;
    }
}
