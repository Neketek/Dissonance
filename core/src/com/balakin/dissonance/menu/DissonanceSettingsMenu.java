package com.balakin.dissonance.menu;

import com.balakin.dissonance.menu.button.DissonanceButton;
import com.balakin.dissonance.opengl.render.ColorPalette;
import com.balakin.dissonance.opengl.render.DissonanceTexturePack;
import com.balakin.dissonance.utils.DissonanceConfig;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceSettingsMenu extends DissonanceMenu {
    private static final int SOUND_BUTTON = 0;
    private static final int EXIT_BUTTON = 1;
    private static final int TUTORIAL_BUTTON = 2;
    private static final int ADS_BUTTON = 3;

    private DissonanceButton addsButton = null;
    private void createButtonsIfAdsEnabled(){
        DissonanceButton sound = DissonanceButton.createStandardButton(getWidth(), getHeight());
        DissonanceButton exit = DissonanceButton.createStandardButton(getWidth(), getHeight());
        DissonanceButton noAds = DissonanceButton.createStandardButton(getWidth(), getHeight());
        DissonanceButton tutorial = DissonanceButton.createStandardButton(getWidth(), getHeight());
        float marginBetweenButtons = sound.getIconMarginWidth();
        float x = (getWidth()-sound.getWidth()*2-marginBetweenButtons)/2;
        float y =(getHeight()-(getHeight()-sound.getHeight()*2-marginBetweenButtons)/2)-sound.getHeight();
        sound.setButtonId(DissonanceButton.SOUND);
        if(DissonanceConfig.soundEnabled)
            sound.setTexturePackId(DissonanceTexturePack.SOUND_ON_ICON);
        else
            sound.setTexturePackId(DissonanceTexturePack.SOUND_OFF_ICON);
        sound.setColorPaletteId(ColorPalette.COLOR_BLUE);
        sound.setTextureColor(ColorPalette.BACKGROUND);
        sound.setX(x);
        sound.setY(y);
        noAds.setButtonId(DissonanceButton.NO_ADS);
        noAds.setTextureColor(ColorPalette.BACKGROUND);
        noAds.setTexturePackId(DissonanceTexturePack.NO_ADS_ICON);
        noAds.setColorPaletteId(ColorPalette.COLOR_MAGENTA);
        noAds.setX(x + noAds.getWidth() + marginBetweenButtons);
        noAds.setY(y);
        tutorial.setButtonId(DissonanceButton.TUTORIAL);
        if(DissonanceConfig.tutorialEnabled)
            tutorial.setTexturePackId(DissonanceTexturePack.TUTORIAL_ON_ICON);
        else
            tutorial.setTexturePackId(DissonanceTexturePack.TUTORIAL_OFF_ICON);
        tutorial.setColorPaletteId(ColorPalette.COLOR_GREEN);
        tutorial.setTextureColor(ColorPalette.BACKGROUND);
        tutorial.setX(x);
        tutorial.setY(y - tutorial.getHeight() - marginBetweenButtons);
        exit.setButtonId(DissonanceButton.EXIT_TO_MAIN);
        exit.setTexturePackId(DissonanceTexturePack.EXIT_ICON);
        exit.setColorPaletteId(ColorPalette.COLOR_RED);
        exit.setTextureColor(ColorPalette.BACKGROUND);
        exit.setX(noAds.getX());
        exit.setY(tutorial.getY());
        add(sound);//0
        add(exit);//1
        add(tutorial);//2
        add(noAds);//3

    }
    private void createButtonsIfAdsDisabled(){
        DissonanceButton sound = DissonanceButton.createStandardButton(getWidth(), getHeight());
        DissonanceButton exit = DissonanceButton.createStandardButton(getWidth(), getHeight());
       // DissonanceButton noAds = DissonanceButton.createStandardButton(getWidth(),getHeight());
        DissonanceButton tutorial = DissonanceButton.createStandardButton(getWidth(), getHeight());
        float marginBetweenButtons = sound.getIconMarginWidth();
        float x = (getWidth()-sound.getWidth())/2;
        float y =(getHeight()-(getHeight()-sound.getHeight()*3-marginBetweenButtons*2)/2) - sound.getHeight();
        sound.setButtonId(DissonanceButton.SOUND);
        if(DissonanceConfig.soundEnabled)
            sound.setTexturePackId(DissonanceTexturePack.SOUND_ON_ICON);
        else
            sound.setTexturePackId(DissonanceTexturePack.SOUND_OFF_ICON);
        sound.setColorPaletteId(ColorPalette.COLOR_BLUE);
        sound.setTextureColor(ColorPalette.BACKGROUND);
        sound.setX(x);
        sound.setY(y);
        tutorial.setButtonId(DissonanceButton.TUTORIAL);
        if(DissonanceConfig.tutorialEnabled)
            tutorial.setTexturePackId(DissonanceTexturePack.TUTORIAL_ON_ICON);
        else
            tutorial.setTexturePackId(DissonanceTexturePack.TUTORIAL_OFF_ICON);
        tutorial.setColorPaletteId(ColorPalette.COLOR_GREEN);
        tutorial.setTextureColor(ColorPalette.BACKGROUND);
        tutorial.setX(x);
        tutorial.setY(y - tutorial.getHeight() - marginBetweenButtons);
        exit.setButtonId(DissonanceButton.EXIT_TO_MAIN);
        exit.setTexturePackId(DissonanceTexturePack.EXIT_ICON);
        exit.setColorPaletteId(ColorPalette.COLOR_RED);
        exit.setTextureColor(ColorPalette.BACKGROUND);
        exit.setY(tutorial.getY() - marginBetweenButtons - tutorial.getHeight());
        exit.setX(x);
        add(sound);//0
        add(exit);//1
        add(tutorial);//2
    }
    private void createButtons(){
        if(DissonanceConfig.adsEnabled)
            createButtonsIfAdsEnabled();
        else
            createButtonsIfAdsDisabled();
    }
    public void setSoundEnabled(boolean soundEnabled){
        if(soundEnabled)
            getButtonList().get(SOUND_BUTTON).setTexturePackId(DissonanceTexturePack.SOUND_ON_ICON);
        else
            getButtonList().get(SOUND_BUTTON).setTexturePackId(DissonanceTexturePack.SOUND_OFF_ICON);
    }
    public void setTutorialEnabled(boolean enabled){
        if(enabled)
            getButtonList().get(TUTORIAL_BUTTON).setTexturePackId(DissonanceTexturePack.TUTORIAL_ON_ICON);
        else
            getButtonList().get(TUTORIAL_BUTTON).setTexturePackId(DissonanceTexturePack.TUTORIAL_OFF_ICON);
    }
    public void tryToDisableAdsButton(){
        if(!DissonanceConfig.adsEnabled&&getButtonList().size()==4){// that means ads button is on screen
            getButtonList().clear();
            createButtonsIfAdsDisabled();
        }
    }
    public DissonanceSettingsMenu(float x, float y, float w, float h) {
        super(x, y, w, h);
        createButtons();
        this.setType(SETTINGS_MENU);
    }
}
