package com.balakin.dissonance.listeners;

import com.badlogic.gdx.Gdx;
import com.balakin.dissonance.Dissonance;
import com.balakin.dissonance.menu.DissonanceMenu;
import com.balakin.dissonance.menu.button.DissonanceButton;
import com.balakin.dissonance.menu.button.DissonanceButtonListener;
import com.balakin.dissonance.menu.layout.DissonanceScreenGridController;
import com.balakin.dissonance.menu.layout.DissonanceVirtualGrid;
import com.balakin.dissonance.opengl.render.menu.SplashScreenRenderer;
import com.balakin.dissonance.utils.DissonanceConfig;
import com.balakin.dissonance.utils.DissonanceResources;
import com.balakin.dissonance.utils.DissonanceSound;
import com.balakin.dissonance.utils.DissonanceState;

import static com.balakin.dissonance.menu.button.DissonanceButton.EXIT;
import static com.balakin.dissonance.menu.button.DissonanceButton.EXIT_TO_MAIN;
import static com.balakin.dissonance.menu.button.DissonanceButton.LEADER_BOARD;
import static com.balakin.dissonance.menu.button.DissonanceButton.NO_ADS;
import static com.balakin.dissonance.menu.button.DissonanceButton.PLAY;
import static com.balakin.dissonance.menu.button.DissonanceButton.RESUME;
import static com.balakin.dissonance.menu.button.DissonanceButton.SCORE;
import static com.balakin.dissonance.menu.button.DissonanceButton.SETTINGS;
import static com.balakin.dissonance.menu.button.DissonanceButton.SOUND;
import static com.balakin.dissonance.menu.button.DissonanceButton.TUTORIAL;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceButtonsListener implements DissonanceButtonListener {
    private DissonanceState state = null;
    private SplashScreenRenderer splashScreenRenderer = null;
    private DissonanceScreenGridController screenGridController = null;
    public DissonanceButtonsListener(){
        state = DissonanceResources.getDissonanceState();
        splashScreenRenderer = DissonanceResources.getSplashScreenRenderer();
        screenGridController = DissonanceResources.getDissonanceScreenGridController();
    }
    @Override
    public void onTouchDown(DissonanceButton button) {
        if(splashScreenRenderer.isAnimated())
            return;
        button.setActive(true);
     //   DissonanceResources.getDissonancePSController().synchronizeScoreWithLeaderBoard();
        DissonanceResources.getDissonanceSound().play(DissonanceSound.BUTTON);
        DissonanceMenu menu = button.getOwner();
        if(DissonanceConfig.adsEnabled&&(menu.getType()==DissonanceMenu.PAUSE_MENU||menu.getType()==DissonanceMenu.MAIN_MENU||button.getButtonId()==LEADER_BOARD)) {
            if(DissonanceResources.getDissonanceAdsController().showAds()){
                return;
            }
        }
        switch (button.getButtonId()){
            case PLAY:
                DissonanceResources.getDissonanceScreenGrid().setGameFieldVisible(true);
                DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.GAME);
                DissonanceResources.getDissonanceState().setActiveMenu(null);
                Gdx.input.setInputProcessor(DissonanceResources.getInGameTouchListener());
                DissonanceResources.resetDissonanceLogic();
                break;
            case EXIT:
                DissonanceResources.getDissonanceLogic().reset();// this code check not ended game score
                // to white it in f
                DissonanceConfig.saveConfig();
                System.exit(0);
                break;
            case SETTINGS:
                DissonanceResources.getDissonanceScreenGrid().getSettingsMenu().tryToDisableAdsButton();
                DissonanceResources.getDissonanceState().setActiveMenu(DissonanceResources.getDissonanceScreenGrid().getSettingsMenu());
                DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.SETTINGS_MENU);
                break;
            case EXIT_TO_MAIN:
                if(state.getActiveMenu().getType()== DissonanceMenu.PAUSE_MENU) {
                    DissonanceResources.getDissonanceScreenGrid().setGameFieldVisible(false);
                    int maxScore = DissonanceConfig.maxScore;
                    DissonanceResources.getDissonancePSController().submitPlayerScore(maxScore);
                    DissonanceResources.getDissonancePSController().manageAchievements();
                }
                DissonanceResources.getDissonanceState().setActiveMenu(DissonanceResources.getDissonanceScreenGrid().getMainMenu());
                DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.MAIN_MENU);
                break;
            case RESUME:
                DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.GAME);
                DissonanceResources.getDissonanceState().setActiveMenu(null);
                Gdx.input.setInputProcessor(DissonanceResources.getInGameTouchListener());
                if(DissonanceResources.getDissonanceState().isGameFailed()) {
                    int maxScore = DissonanceConfig.maxScore;
                    DissonanceResources.getDissonancePSController().submitPlayerScore(maxScore);
                    DissonanceResources.getDissonancePSController().manageAchievements();
                    DissonanceResources.getDissonanceLogic().reset();
                }
                break;
            case SOUND:
                DissonanceConfig.soundEnabled=!DissonanceConfig.soundEnabled;
                DissonanceResources.getDissonanceScreenGrid().getSettingsMenu().setSoundEnabled(DissonanceConfig.soundEnabled);
                break;
            case NO_ADS:
                //DissonanceConfig.adsEnabled = false; TODO:MAKE in app no ad purchase
                DissonanceResources.getDissonanceState().setActiveMenu(DissonanceResources.getDissonanceScreenGrid().getMainMenu());
                DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.MAIN_MENU);
                DissonanceResources.getDissonanceAdsController().purchase();
                break;
            case TUTORIAL:
                DissonanceConfig.tutorialEnabled = !DissonanceConfig.tutorialEnabled;
                DissonanceResources.getDissonanceScreenGrid().getSettingsMenu().setTutorialEnabled(DissonanceConfig.tutorialEnabled);
                break;
            case SCORE:
                screenGridController.setCameraMoveTask(DissonanceVirtualGrid.SCORE_MENU);
                state.setActiveMenu(screenGridController.getDissonanceScreenGrid().getScoreMenu());
                screenGridController.getDissonanceScreenGrid().getScoreMenu().checkMaxScoreChange();
                break;
            case LEADER_BOARD:
                DissonanceResources.getDissonancePSController().showLeaderBoard();
                break;
        }
        DissonanceResources.getDissonanceState().setCameraMoving(true);
    }
    @Override
    public void onTouchUp(DissonanceButton button) {

    }
}
