package com.arachnid42.dissonance.menu;

import com.arachnid42.dissonance.menu.button.DissonanceButton;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceTexturePack;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonancePauseMenu extends DissonanceMenu{
    private void createButtons(){
        DissonanceButton play = DissonanceButton.createStandardButton(getWidth(),getHeight());
        DissonanceButton exit = DissonanceButton.createStandardButton(getWidth(),getHeight());
        float marginBetweenButtons = play.getIconMarginWidth();
        float x = (getWidth()-play.getWidth()*2-marginBetweenButtons)/2;
        float y =(getHeight()-(getHeight()-play.getHeight())/2)-play.getHeight();
        play.setButtonId(DissonanceButton.RESUME);
        play.setTexturePackId(DissonanceTexturePack.PLAY_ICON);
        play.setColorPaletteId(ColorPalette.COLOR_GREEN);
        play.setTextureColor(ColorPalette.BACKGROUND);
        play.setX(x);
        play.setY(y);
        exit.setButtonId(DissonanceButton.EXIT_TO_MAIN);
        exit.setTexturePackId(DissonanceTexturePack.EXIT_ICON);
        exit.setColorPaletteId(ColorPalette.COLOR_RED);
        exit.setTextureColor(ColorPalette.BACKGROUND);
        exit.setX(x+marginBetweenButtons+play.getWidth());
        exit.setY(y);
        add(play);
        add(exit);
    }
    public DissonancePauseMenu(float x, float y, float w, float h) {
        super(x, y, w, h);
        createButtons();
        this.setType(PAUSE_MENU);
    }
}
