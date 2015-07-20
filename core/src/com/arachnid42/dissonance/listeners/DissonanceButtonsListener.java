package com.arachnid42.dissonance.listeners;

import com.arachnid42.dissonance.menu.DissonanceSettingsMenu;
import com.arachnid42.dissonance.menu.layout.DissonanceScreenGrid;
import com.arachnid42.dissonance.utils.DissonanceConfig;
import com.arachnid42.dissonance.utils.DissonanceResources;
import com.arachnid42.dissonance.utils.DissonanceSound;
import com.arachnid42.dissonance.utils.DissonanceState;
import com.arachnid42.dissonance.menu.DissonanceMenu;
import com.arachnid42.dissonance.menu.button.DissonanceButton;
import com.arachnid42.dissonance.menu.button.DissonanceButtonListener;
import com.arachnid42.dissonance.menu.layout.DissonanceVirtualGrid;
import com.badlogic.gdx.Gdx;

import static com.arachnid42.dissonance.menu.button.DissonanceButton.*;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceButtonsListener implements DissonanceButtonListener {
    private DissonanceState state = null;
    public DissonanceButtonsListener(){
        state = DissonanceResources.getDissonanceState();
    }
    @Override
    public void onTouchDown(DissonanceButton button) {
        button.setActive(true);
        DissonanceResources.getDissonanceSound().play(DissonanceSound.BUTTON);
        if(DissonanceConfig.adsEnabled&&state.getActiveMenu().getType()==DissonanceMenu.PAUSE_MENU)//TODO:SHOW ADS
             DissonanceResources.getDissonanceAdsController().showAds();
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
                // to white it in file
                DissonanceConfig.saveConfig();
                System.exit(0);
                break;
            case SETTINGS:
                DissonanceResources.getDissonanceScreenGrid().getSettingsMenu().tryToDisableAdsButton();
                DissonanceResources.getDissonanceState().setActiveMenu(DissonanceResources.getDissonanceScreenGrid().getSettingsMenu());
                DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.SETTINGS_MENU);
                break;
            case EXIT_TO_MAIN:
                if(state.getActiveMenu().getType()== DissonanceMenu.PAUSE_MENU)
                    DissonanceResources.getDissonanceScreenGrid().setGameFieldVisible(false);
                DissonanceResources.getDissonanceState().setActiveMenu(DissonanceResources.getDissonanceScreenGrid().getMainMenu());
                DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.MAIN_MENU);
                break;
            case RESUME:
                DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.GAME);
                DissonanceResources.getDissonanceState().setActiveMenu(null);
                Gdx.input.setInputProcessor(DissonanceResources.getInGameTouchListener());
                if(DissonanceResources.getDissonanceState().isGameFailed())
                    DissonanceResources.getDissonanceLogic().reset();
                break;
            case SOUND:
                DissonanceConfig.soundEnabled=!DissonanceConfig.soundEnabled;
                DissonanceResources.getDissonanceScreenGrid().getSettingsMenu().setSoundEnabled(DissonanceConfig.soundEnabled);
                break;
            case NO_ADS:
                //DissonanceConfig.adsEnabled = false; TODO:MAKE in app no ad purchase
                DissonanceResources.getDissonanceState().setActiveMenu(DissonanceResources.getDissonanceScreenGrid().getMainMenu());
                DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.MAIN_MENU);
                break;
            case TUTORIAL:
                DissonanceConfig.tutorialEnabled = !DissonanceConfig.tutorialEnabled;
                DissonanceResources.getDissonanceScreenGrid().getSettingsMenu().setTutorialEnabled(DissonanceConfig.tutorialEnabled);
                break;
        }
      //  DissonanceResources.getDissonanceScreenGridController().setCameraMoveTask(DissonanceVirtualGrid.GAME);
        DissonanceResources.getDissonanceState().setCameraMoving(true);
    }
    @Override
    public void onTouchUp(DissonanceButton button) {

    }
}
