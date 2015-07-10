package com.arachnid42.dissonance.menu;

import com.arachnid42.dissonance.DissonanceConfig;
import com.arachnid42.dissonance.menu.button.DissonanceButton;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceTexturePack;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceSettingsMenu extends DissonanceMenu{
    private static final int SOUND_BUTTON = 0;
    private static final int ADS_BUTTON = 2;
    private static final int EXIT_BUTTON = 1;
    private DissonanceButton addsButton = null;
    private void createButtons(){
        DissonanceButton sound = DissonanceButton.createStandardButton(getWidth(),getHeight());
        DissonanceButton ads = DissonanceButton.createStandardButton(getWidth(),getHeight());
        DissonanceButton exit = DissonanceButton.createStandardButton(getWidth(), getHeight());
        float w = sound.getWidth();
        float h = sound.getHeight();
        float marginBetweenButtons = sound.getIconMarginWidth();
        float x = (getWidth()-w)/2;
        float y = 0;
        if(DissonanceConfig.adsEnabled)
             y =(getHeight()-(getHeight()-h*3-marginBetweenButtons*2)/2)-h;
        else
            y = (getHeight()-(getHeight()-h*2-marginBetweenButtons)/2)-h;
        sound.setButtonId(DissonanceButton.SOUND);
        if(DissonanceConfig.soundEnabled)
            sound.setTexturePackId(DissonanceTexturePack.SOUND_ON_ICON);
        else
            sound.setTexturePackId(DissonanceTexturePack.SOUND_OFF_ICON);
        sound.setColorPaletteId(ColorPalette.COLOR_BLUE);
        sound.setTextureColor(ColorPalette.BACKGROUND);
        sound.setX(x);
        sound.setY(y);
        if(DissonanceConfig.adsEnabled) {
            ads.setButtonId(DissonanceButton.NO_ADS);
            ads.setTexturePackId(DissonanceTexturePack.NO_ADS_ICON);
            ads.setColorPaletteId(ColorPalette.COLOR_MAGENTA);
            ads.setTextureColor(ColorPalette.BACKGROUND);
            ads.setX(x);
            ads.setY(y - marginBetweenButtons - h);
        }
        exit.setButtonId(DissonanceButton.EXIT_TO_MAIN);
        exit.setTexturePackId(DissonanceTexturePack.EXIT_ICON);
        exit.setColorPaletteId(ColorPalette.COLOR_RED);
        exit.setTextureColor(ColorPalette.BACKGROUND);
        exit.setX(x);
        if(DissonanceConfig.adsEnabled) {
            exit.setY(y - (marginBetweenButtons + h) * 2);
        }else{
            exit.setY(y - marginBetweenButtons - h);
        }
        add(sound);
        if(DissonanceConfig.adsEnabled)
            add(ads);
        add(exit);
    }
    public void setSoundEnabled(boolean soundEnabled){
        if(soundEnabled)
            getButtonList().get(SOUND_BUTTON).setTexturePackId(DissonanceTexturePack.SOUND_ON_ICON);
        else
            getButtonList().get(SOUND_BUTTON).setTexturePackId(DissonanceTexturePack.SOUND_OFF_ICON);
    }
    public void tryToDisableAdsButton(){
        if(!DissonanceConfig.adsEnabled&&getButtonList().size()==3){// that means ads button is on screen
            getButtonList().remove(ADS_BUTTON);
            DissonanceButton sound = getButtonList().get(SOUND_BUTTON);
            DissonanceButton exit = getButtonList().get(EXIT_BUTTON);
            float h = sound.getHeight();
            float marginBetweenButtons = sound.getIconMarginWidth();
            float y = getHeight()-(getHeight()-(h*2+marginBetweenButtons))/2;
            sound.setY(y);
            exit.setY(y-h-marginBetweenButtons);
        }
    }
    public DissonanceSettingsMenu(float x, float y, float w, float h) {
        super(x, y, w, h);
        createButtons();
        this.setType(SETTINGS_MENU);
    }
}
