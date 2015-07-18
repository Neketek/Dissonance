package com.arachnid42.dissonance.menu;

import com.arachnid42.dissonance.menu.button.DissonanceButton;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceTexturePack;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceMainMenu extends DissonanceMenu {
    private void createButtons(){
        DissonanceButton play = DissonanceButton.createStandardButton(getWidth(),getHeight());
        DissonanceButton exit = DissonanceButton.createStandardButton(getWidth(),getHeight());
        DissonanceButton settings = DissonanceButton.createStandardButton(getWidth(),getHeight());
        DissonanceButton score = DissonanceButton.createStandardButton(getWidth(),getHeight());
        float marginBetweenButtons = play.getIconMarginWidth();
        float x = (getWidth()-play.getWidth()*2-marginBetweenButtons)/2;
        float y =(getHeight()-(getHeight()-play.getHeight()*2-marginBetweenButtons)/2)-play.getHeight();
        play.setButtonId(DissonanceButton.PLAY);
        play.setTexturePackId(DissonanceTexturePack.PLAY_ICON);
        play.setColorPaletteId(ColorPalette.COLOR_GREEN);
        play.setTextureColor(ColorPalette.BACKGROUND);
        play.setX(x);
        play.setY(y);
        settings.setButtonId(DissonanceButton.SETTINGS);
        settings.setTextureColor(ColorPalette.BACKGROUND);
        settings.setTexturePackId(DissonanceTexturePack.SETTINGS_ICON);
        settings.setColorPaletteId(ColorPalette.COLOR_BLUE);
        settings.setX(x + settings.getWidth() + marginBetweenButtons);
        settings.setY(y);
        score.setButtonId(DissonanceButton.SCORE);
        score.setTexturePackId(DissonanceTexturePack.SCORE_ICON);
        score.setColorPaletteId(ColorPalette.COLOR_YELLOW);
        score.setTextureColor(ColorPalette.BACKGROUND);
        score.setX(x);
        score.setY(y - score.getHeight() - marginBetweenButtons);
        exit.setButtonId(DissonanceButton.EXIT);
        exit.setTexturePackId(DissonanceTexturePack.EXIT_ICON);
        exit.setColorPaletteId(ColorPalette.COLOR_RED);
        exit.setTextureColor(ColorPalette.BACKGROUND);
        exit.setX(settings.getX());
        exit.setY(score.getY());
        add(play);
        add(exit);
        add(score);
        add(settings);
        startFadeIn(0);

    }
    public DissonanceMainMenu(float x, float y, float w, float h) {
        super(x, y,w,h);
        createButtons();
        this.setType(MAIN_MENU);
    }
}
