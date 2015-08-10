package com.balakin.dissonance.utils;

import com.balakin.dissonance.menu.DissonanceMenu;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceState{
    private boolean tutorialStarted = false;
    private boolean gameFailed = false;
    private DissonanceMenu activeMenu = null;
    private boolean cameraMoving = false;
    public DissonanceMenu getActiveMenu() {
        return activeMenu;
    }
    public void setActiveMenu(DissonanceMenu activeMenu) {
        this.activeMenu = activeMenu;
    }

    public boolean isCameraMoving() {
        return cameraMoving;
    }

    public void setCameraMoving(boolean cameraMoving) {
        this.cameraMoving = cameraMoving;
    }

    public boolean isGameFailed() {
        return gameFailed;
    }

    public void setGameFailed(boolean gameFailed) {
        this.gameFailed = gameFailed;
    }

    public boolean isTutorialStarted() {
        return tutorialStarted;
    }

    public void setTutorialStarted(boolean tutorialStarted) {
        this.tutorialStarted = tutorialStarted;
    }
}
