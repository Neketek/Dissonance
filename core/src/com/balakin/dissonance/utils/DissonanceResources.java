package com.balakin.dissonance.utils;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.balakin.dissonance.listeners.DissonanceButtonsListener;
import com.balakin.dissonance.listeners.DissonanceLogicListener;
import com.balakin.dissonance.listeners.InGameTouchListener;
import com.balakin.dissonance.listeners.InMenuTouchListener;
import com.balakin.dissonance.logic.DissonanceLogic;
import com.balakin.dissonance.menu.layout.DissonanceScreenGrid;
import com.balakin.dissonance.menu.layout.DissonanceScreenGridController;
import com.balakin.dissonance.menu.layout.DissonanceVirtualGrid;
import com.balakin.dissonance.opengl.render.ColorPalette;
import com.balakin.dissonance.opengl.render.DissonanceCameraTool;
import com.balakin.dissonance.opengl.render.DissonanceFontRenderer;
import com.balakin.dissonance.opengl.render.DissonanceTexturePack;
import com.balakin.dissonance.opengl.render.game.DissonanceLogicRenderer;
import com.balakin.dissonance.opengl.render.game.DissonanceShapeRenderer;
import com.balakin.dissonance.opengl.render.menu.DissonanceButtonRenderer;
import com.balakin.dissonance.opengl.render.menu.DissonanceMenuRenderer;
import com.balakin.dissonance.opengl.render.menu.SplashScreenRenderer;
import com.balakin.dissonance.utils.androidControllers.DissonanceAdsController;
import com.balakin.dissonance.utils.androidControllers.DissonancePSController;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceResources {
    private static DissonanceState dissonanceState = null;
    private static ShapeRenderer gdxShapeRenderer = null;
    private static SpriteBatch gdxSpriteBatch = null;
    private static DissonanceFontRenderer dissonanceFontRenderer = null;
    private static DissonanceCameraTool dissonanceCameraTool = null;
    private static DissonanceMenuRenderer dissonanceMenuRenderer = null;
    private static DissonanceTexturePack dissonanceTexturePack = null;
    private static DissonanceLogicRenderer dissonanceLogicRenderer = null;
    private static DissonanceScreenGrid dissonanceScreenGrid = null;
    private static DissonanceScreenGridController dissonanceScreenGridController = null;
    private static DissonanceButtonRenderer dissonanceButtonRenderer = null;
    private static DissonanceShapeRenderer dissonanceShapeRenderer = null;
    private static ColorPalette colorPalette = null;
    private static InMenuTouchListener inMenuTouchListener = null;
    private static InGameTouchListener inGameTouchListener = null;
    private static DissonanceButtonsListener dissonanceButtonsListener = null;
    private static DissonanceLogicListener dissonanceLogicListener = null;
    private static boolean initialized = false;
    private static DissonanceAdsController dissonanceAdsController = null;
    private static DissonanceSound dissonanceSound = null;
    private static DissonancePSController dissonancePSController = null;
    private static SplashScreenRenderer splashScreenRenderer = null;
    public static void initializeDissonanceResources(float screenWidth,float screenHeight, DissonanceAdsController adsController,
    DissonancePSController psController){
        if (initialized)
            return;
        if(screenHeight<screenWidth){
            float swap = screenWidth;
            screenWidth = screenHeight;
            screenHeight = swap;
        }
        DissonanceConfig.initConfig();
        dissonancePSController = psController;
        dissonanceState = new DissonanceState();
        gdxShapeRenderer = new ShapeRenderer();
        gdxSpriteBatch = new SpriteBatch();
        colorPalette = ColorPalette.createColorPalette();
        dissonanceShapeRenderer = new DissonanceShapeRenderer(500);
        dissonanceShapeRenderer.setColorPalette(colorPalette);
        dissonanceFontRenderer = new DissonanceFontRenderer(gdxSpriteBatch);
        dissonanceScreenGrid = new DissonanceScreenGrid(screenWidth,screenHeight);
        dissonanceCameraTool = new DissonanceCameraTool(screenWidth,screenHeight);
        dissonanceScreenGridController = new DissonanceScreenGridController(dissonanceScreenGrid,dissonanceCameraTool);
        dissonanceTexturePack = new DissonanceTexturePack();
        dissonanceButtonRenderer = new DissonanceButtonRenderer(
                dissonanceTexturePack,
                colorPalette,
                gdxSpriteBatch,
                gdxShapeRenderer);
        dissonanceMenuRenderer = new DissonanceMenuRenderer(
                dissonanceButtonRenderer,
                gdxShapeRenderer,
                colorPalette);
        dissonanceLogicRenderer = new DissonanceLogicRenderer(
                dissonanceScreenGrid.getDissonanceLogic(),
                dissonanceShapeRenderer,
                gdxShapeRenderer,
                dissonanceFontRenderer,
                colorPalette);
        dissonanceState.setActiveMenu(dissonanceScreenGrid.getMainMenu());
        dissonanceScreenGridController.setCameraLocation(DissonanceVirtualGrid.MAIN_MENU);
        splashScreenRenderer = new SplashScreenRenderer(dissonanceScreenGrid.getMainMenu().getX(),
                dissonanceScreenGrid.getMainMenu().getY(),screenWidth,screenHeight);
        dissonanceButtonsListener = new DissonanceButtonsListener();
        inMenuTouchListener = new InMenuTouchListener(dissonanceState,dissonanceButtonsListener);
        dissonanceLogicListener = new DissonanceLogicListener();
        dissonanceSound = new DissonanceSound();
        getDissonanceLogic().setLogicListener(dissonanceLogicListener);
        getDissonanceLogic().reset();
        dissonanceAdsController = adsController;
        inGameTouchListener = new InGameTouchListener();
        initialized = true;
    }
    public static DissonanceCameraTool getDissonanceCameraTool() {
        return dissonanceCameraTool;
    }

    public static DissonanceFontRenderer getDissonanceFontRenderer() {
        return dissonanceFontRenderer;
    }

    public static DissonanceLogicRenderer getDissonanceLogicRenderer() {
        return dissonanceLogicRenderer;
    }


    public static DissonanceMenuRenderer getDissonanceMenuRenderer() {
        return dissonanceMenuRenderer;
    }

    public static DissonanceScreenGrid getDissonanceScreenGrid() {
        return dissonanceScreenGrid;
    }

    public static DissonanceScreenGridController getDissonanceScreenGridController() {
        return dissonanceScreenGridController;
    }

    public static DissonanceTexturePack getDissonanceTexturePack() {
        return dissonanceTexturePack;
    }

    public static ShapeRenderer getGdxShapeRenderer() {
        return gdxShapeRenderer;
    }

    public static SpriteBatch getGdxSpriteBatch() {
        return gdxSpriteBatch;
    }

    public static ColorPalette getColorPalette() {
        return colorPalette;
    }

    public static DissonanceShapeRenderer getDissonanceShapeRenderer() {
        return dissonanceShapeRenderer;
    }

    public static DissonanceButtonRenderer getDissonanceButtonRenderer() {
        return dissonanceButtonRenderer;
    }

    public static DissonanceLogic getDissonanceLogic(){
        return dissonanceScreenGrid.getDissonanceLogic();
    }

    public static DissonanceState getDissonanceState() {
        return dissonanceState;
    }

    public static DissonanceButtonsListener getDissonanceButtonsListener() {
        return dissonanceButtonsListener;
    }

    public static InGameTouchListener getInGameTouchListener() {
        return inGameTouchListener;
    }

    public static InMenuTouchListener getInMenuTouchListener() {
        return inMenuTouchListener;
    }
    public static void resetDissonanceLogic(){
        getDissonanceLogic().reset();
    }

    public static DissonanceAdsController getDissonanceAdsController() {
        return dissonanceAdsController;
    }

    public static DissonanceSound getDissonanceSound() {
        return dissonanceSound;
    }

    public static DissonancePSController getDissonancePSController() {
        return dissonancePSController;
    }

    public static SplashScreenRenderer getSplashScreenRenderer() {
        return splashScreenRenderer;
    }
}
