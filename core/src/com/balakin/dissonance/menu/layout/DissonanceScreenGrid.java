package com.balakin.dissonance.menu.layout;

import com.balakin.dissonance.logic.DissonanceLogic;
import com.balakin.dissonance.logic.parts.field.GameField;
import com.balakin.dissonance.menu.DissonanceMainMenu;
import com.balakin.dissonance.menu.DissonancePauseMenu;
import com.balakin.dissonance.menu.DissonanceScoreMenu;
import com.balakin.dissonance.menu.DissonanceSettingsMenu;
import com.balakin.dissonance.opengl.render.DissonanceCameraTool;

import static com.balakin.dissonance.menu.layout.DissonanceVirtualGrid.GAME;
import static com.balakin.dissonance.menu.layout.DissonanceVirtualGrid.MAIN_MENU;
import static com.balakin.dissonance.menu.layout.DissonanceVirtualGrid.PAUSE_MENU;
import static com.balakin.dissonance.menu.layout.DissonanceVirtualGrid.SCORE_MENU;
import static com.balakin.dissonance.menu.layout.DissonanceVirtualGrid.SETTINGS_MENU;


/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceScreenGrid {
    private DissonanceVirtualGrid virtualGrid = null;
    private DissonanceLogic dissonanceLogic = null;
    private DissonancePauseMenu pauseMenu = null;
    private DissonanceMainMenu mainMenu = null;
    private DissonanceSettingsMenu settingsMenu = null;
    private DissonanceScoreMenu scoreMenu = null;
    private boolean gameAndPauseMenuSwapped = false;
    private boolean gameFieldVisible;

    private float getX(int id){
        return this.virtualGrid.getRectX(id);
    }
    private float getY(int id){
        return this.virtualGrid.getRectY(id);
    }
    private void createMenus(float screenWidth,float screenHeight){
        this.virtualGrid = new DissonanceVirtualGrid(screenWidth,screenHeight);
        this.pauseMenu = new DissonancePauseMenu(
                getX(PAUSE_MENU),getY(PAUSE_MENU),
                screenWidth,screenHeight);
        GameField gameField = new GameField(screenHeight,0,0,screenWidth,0.17f,0.01f,5);
        this.dissonanceLogic = new DissonanceLogic();
        this.dissonanceLogic.setGameField(gameField);
        this.mainMenu = new DissonanceMainMenu(
                getX(MAIN_MENU),getY(MAIN_MENU),
                screenWidth,screenHeight);
        this.settingsMenu = new DissonanceSettingsMenu(
                getX(SETTINGS_MENU),getY(SETTINGS_MENU),
                screenWidth,screenHeight);
        this.scoreMenu = new DissonanceScoreMenu(getX(SCORE_MENU),getY(SCORE_MENU),
                screenWidth,screenHeight);
    }
    public void swapGameAndPauseMenu(){
        if(isGameAndPauseMenuSwapped()) {
            pauseMenu.setLocation(getX(PAUSE_MENU), getY(PAUSE_MENU));
            gameAndPauseMenuSwapped = false;
        }
        else {
            pauseMenu.setLocation(getX(GAME), getY(GAME));
            gameAndPauseMenuSwapped = true;
        }

    }
    public void setCameraToolMoveTask(int cellId,DissonanceCameraTool dissonanceCameraTool){
        dissonanceCameraTool.setMoveTask(getX(cellId),getY(cellId));
    }
    public void setCameraLocation(int cellId,DissonanceCameraTool cameraTool){
        cameraTool.setLocation(getX(cellId), getY(cellId));
    }
    public DissonanceScreenGrid(float screenWidth,float screenHeight){
        this.createMenus(screenWidth, screenHeight);
    }
    public boolean isGameAndPauseMenuSwapped() {
        return gameAndPauseMenuSwapped;
    }

    public DissonanceVirtualGrid getVirtualGrid() {
        return virtualGrid;
    }

    public DissonanceMainMenu getMainMenu() {
        return mainMenu;
    }

    public DissonancePauseMenu getPauseMenu() {
        return pauseMenu;
    }

    public DissonanceSettingsMenu getSettingsMenu() {
        return settingsMenu;
    }

    public DissonanceLogic getDissonanceLogic() {
        return dissonanceLogic;
    }
    public void setGameFieldVisible(boolean visible){
        this.gameFieldVisible = visible;
    }
    public boolean isGameFieldVisible() {
        return gameFieldVisible;
    }

    public DissonanceScoreMenu getScoreMenu() {
        return scoreMenu;
    }
}
