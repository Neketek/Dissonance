package com.balakin.dissonance.menu;

import com.badlogic.gdx.math.Matrix4;
import com.balakin.dissonance.menu.button.DissonanceButton;
import com.balakin.dissonance.opengl.render.ColorPalette;
import com.balakin.dissonance.opengl.render.DissonanceFontRenderer;
import com.balakin.dissonance.opengl.render.DissonanceTexturePack;
import com.balakin.dissonance.utils.DissonanceConfig;
import com.balakin.dissonance.utils.DissonanceResources;

import java.awt.Font;

/**
 * Created by neketek on 22.07.15.
 */
public class DissonanceScoreMenu extends DissonanceMenu {
    private DissonanceFontRenderer fontRenderer = null;
    private static final int TEXT_SIZE_ID = DissonanceFontRenderer.LARGE;
    private static final String MAX_SCORE_LABEL = "BEST SCORE";
    private String maxScoreValueString = "0";
    private int maxScoreValueCache = 0;
    private float textXPosition = 0;
    private float textYPosition = 0;
    private float intValueTextYPosition = 0;
    private void calculateTextPosition(){
        float charHeight = fontRenderer.getFontSize(TEXT_SIZE_ID);
        float charWidth = charHeight/2;
        textXPosition =getX()+ (getWidth()-MAX_SCORE_LABEL.length()*charWidth)/2
                +MAX_SCORE_LABEL.length()*charWidth/2;
        textYPosition = getButtonList().get(0).getY()+getButtonList().get(0).getHeight()+charHeight*2.7f;
        intValueTextYPosition = textYPosition - charHeight*1.3f;
    }
    private void getRenderingResources() {
        fontRenderer = DissonanceResources.getDissonanceFontRenderer();
    }
    private void renderMaxScoreLabel(Matrix4 projection){
        fontRenderer.begin(projection);
        fontRenderer.setTextColor(0, 0, 0, 0.5f);
        fontRenderer.renderText(MAX_SCORE_LABEL, textXPosition, textYPosition,
                TEXT_SIZE_ID, Font.CENTER_BASELINE, false);
        fontRenderer.renderText(maxScoreValueString,textXPosition,
                intValueTextYPosition,
                TEXT_SIZE_ID,Font.CENTER_BASELINE,false);
        fontRenderer.end();
    }
    private void createButtons(){
        DissonanceButton leaderBoard = DissonanceButton.createStandardButton(getWidth(), getHeight());
        DissonanceButton exit = DissonanceButton.createStandardButton(getWidth(), getHeight());
        float marginBetweenButtons = leaderBoard.getIconMarginWidth();
        float textHeight = fontRenderer.getFontSize(TEXT_SIZE_ID)*2.7f;
        float x = (getWidth()-leaderBoard.getWidth()*2-marginBetweenButtons)/2;
        float y =(getHeight()-(getHeight()-leaderBoard.getHeight())/2)-leaderBoard.getHeight()-textHeight/2;
        leaderBoard.setButtonId(DissonanceButton.LEADER_BOARD);
        leaderBoard.setTexturePackId(DissonanceTexturePack.LEADER_BOARDS);
        leaderBoard.setColorPaletteId(ColorPalette.COLOR_GREEN);
        leaderBoard.setTextureColor(ColorPalette.BACKGROUND);
        leaderBoard.setX(x);
        leaderBoard.setY(y);
        exit.setButtonId(DissonanceButton.EXIT_TO_MAIN);
        exit.setTexturePackId(DissonanceTexturePack.EXIT_ICON);
        exit.setColorPaletteId(ColorPalette.COLOR_RED);
        exit.setTextureColor(ColorPalette.BACKGROUND);
        exit.setX(x + marginBetweenButtons + leaderBoard.getWidth());
        exit.setY(y);
        add(leaderBoard);
        add(exit);
    }
    public DissonanceScoreMenu(float x, float y, float w, float h) {
        super(x, y, w, h);
        getRenderingResources();
        createButtons();
        calculateTextPosition();
        setType(SCORE_MENU);
    }
    public void checkMaxScoreChange(){
        if(maxScoreValueCache!= DissonanceConfig.maxScore) {
            maxScoreValueString = "" + DissonanceConfig.maxScore;
            maxScoreValueCache = DissonanceConfig.maxScore;
        }
    }
    @Override
    public void postRendering(Matrix4 projection){
        this.renderMaxScoreLabel(projection);
    }
}
