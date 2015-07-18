package com.arachnid42.dissonance.menu;

import com.arachnid42.dissonance.menu.DissonanceMenu;
import com.arachnid42.dissonance.menu.button.DissonanceButton;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceTexturePack;

import java.awt.Button;

/**
 * Created by neketek on 18.07.15.
 */
public class DissonanceSplashScreenMenu extends DissonanceMenu{
    private DissonanceButton splashScreen = null;
    private boolean startFadeOut = false;
    public DissonanceSplashScreenMenu(float x, float y, float w, float h) {
        super(x, y, w, h);
        DissonanceButton logo = new DissonanceButton();
        float logoH = h/6;
        float logoW = w/1.1f;
        logo.setY(h/2-logoH/2);
        logo.setX((w - logoW) / 2);
        logo.setWidth(logoW);
        logo.setHeight(logoH);
        logo.setColorPaletteId(ColorPalette.COLOR_GREEN);
        logo.setShowIcon(true);
        logo.setTexturePackId(DissonanceTexturePack.PLAY_ICON);
        logo.setTextureColor(ColorPalette.COLOR_BLUE);
        this.add(logo);
        startFadeIn(5);
    }
    public void updateAnimation(float delta){
        super.updateAnimation(delta);
    }
}
