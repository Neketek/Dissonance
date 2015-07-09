package com.arachnid42.dissonance.opengl.render.game;

import com.arachnid42.dissonance.DissonanceResources;
import com.arachnid42.dissonance.logic.DissonanceLogic;
import com.arachnid42.dissonance.logic.parts.field.GameField;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceFontRenderer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import java.awt.Font;
import java.util.Random;

import static com.arachnid42.dissonance.opengl.render.ColorPalette.*;

/**
 * Created by neketek on 03.07.15.
 */
public class DissonanceLogicRenderer {
    private static final String COLOR_COMPARSION = "COLOR";
    private static final String SHAPE_COMPARSION = "SHAPE";
    private static final String[] GAME_MODE_LABEL = {COLOR_COMPARSION,SHAPE_COMPARSION};
    private static  final  String SCORE = "SCORE:";
    private static final String[] NUMBERS = {"0","1","2","3","4","5","6","7","8","9"};
    private static final String[] SCORE_COUNT = new String[4];
    private boolean gameModeChanged = false;
    private int savedGameMode = 0;
    private GameField gameField = null;
    private DissonanceShapeBasketRenderer shapeBasketRenderer = null;
    private DissonanceShapeRenderer shapeRenderer = null;
    private ShapeRenderer gdxShapeRenderer = null;
    private ColorPalette colorPalette = null;
    private float[]backGroundColor = null;
    private DissonanceLogic dissonanceLogic = null;
    private DissonanceFontRenderer dissonanceFontRenderer = null;
    private GameModeChangeAnimation gameModeChangeAnimation = null;
    private int previous_score = -1;
    private class GameModeChangeAnimation{
        private final float labelWidth = dissonanceFontRenderer.getFontSize(DissonanceFontRenderer.LARGE)*5/2;
        private final float finalLabelPositionX = gameField.getWidth() / 2;
        private final float finalLabelPositionY = gameField.getHeight() / 2 + gameField.getShapeBasket().getTop();
        private final float startLeftBorderLabelPositionX = gameField.getFallPathFieldLeft()-labelWidth*1.2f;
        private final float startRightBorderLabelPositionX = gameField.getFallPathFieldLeft()+gameField.getWidth()+labelWidth*1.2f;
        private final float moveDistance = finalLabelPositionX+labelWidth*1.2f;
        private boolean mode = false; // left or right
        private float currentLabelPositionX = 0;
        private float oldLabelMove = 0;
        private Random random = new Random();
        public boolean update(float delta){
            if(mode&&currentLabelPositionX>=finalLabelPositionX||!mode&&currentLabelPositionX<=finalLabelPositionX)
                return false;
            if(mode) {
                currentLabelPositionX += moveDistance * 2 * delta;
                oldLabelMove += moveDistance * 2 * delta;
            }else{
                currentLabelPositionX -= moveDistance * 2 * delta;
                oldLabelMove -= moveDistance * 2 * delta;
            }
            return true;
        }
        public void render(){
            dissonanceFontRenderer.renderText(
                    GAME_MODE_LABEL[savedGameMode],
                    finalLabelPositionX+oldLabelMove,
                    finalLabelPositionY,DissonanceFontRenderer.LARGE,
                    Font.CENTER_BASELINE,true);
            dissonanceFontRenderer.renderText(
                    GAME_MODE_LABEL[dissonanceLogic.getGameStageData().getGameMode()],
                    currentLabelPositionX,
                    finalLabelPositionY,DissonanceFontRenderer.LARGE,
                    Font.CENTER_BASELINE,true);
        }
        public void reset(){
            mode = random.nextBoolean();
            if(mode) {
                currentLabelPositionX = startLeftBorderLabelPositionX;
            }else{
                currentLabelPositionX = startRightBorderLabelPositionX;
            }
            oldLabelMove = 0;
        }
    }
    private void checkGameModeChange(){
        if(!gameModeChanged&&savedGameMode!=dissonanceLogic.getGameStageData().getGameMode()) {
            gameModeChangeAnimation.reset();
            gameModeChanged = true;
        }
    }
    private void drawGameFieldBackGround(Matrix4 projection){
        gdxShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        gdxShapeRenderer.setColor(
                backGroundColor[RED],backGroundColor[GREEN],
                backGroundColor[BLUE],backGroundColor[ALPHA]);
        gdxShapeRenderer.rect(
                gameField.getFallPathFieldLeft(), gameField.getFallPathFieldBottom(),
                gameField.getWidth(), gameField.getFallPathFieldHeight());
        gdxShapeRenderer.end();
    }
    private void drawShapesOnField(Matrix4 projection){
        shapeRenderer.begin(projection);
        for(int i = 0;i<gameField.getShapesOnFieldCount();i++)
            shapeRenderer.render(gameField.getShape(i));
        shapeRenderer.end();
    }
    private void drawShapeBasket(Matrix4 projection){
        shapeBasketRenderer.render(projection);
    }
    private void drawGameModeLabel(){
        if(!gameModeChanged)
            dissonanceFontRenderer.renderText(GAME_MODE_LABEL[savedGameMode], gameField.getWidth() / 2,
                gameField.getHeight() / 2 + gameField.getShapeBasket().getTop(), DissonanceFontRenderer.LARGE, Font.CENTER_BASELINE, true);
        else{
            gameModeChanged = gameModeChangeAnimation.update(Gdx.graphics.getDeltaTime());
            if(!gameModeChanged)
                savedGameMode = dissonanceLogic.getGameStageData().getGameMode();
            gameModeChangeAnimation.render();
        }
    }
    private void recalculateScoreLabel(){
        int score = dissonanceLogic.getGameStageData().getScore();
        if(previous_score==score)
            return;
        SCORE_COUNT[0] = SCORE;
        if(score>=100){
            SCORE_COUNT[1] = NUMBERS[(score/100)];
            SCORE_COUNT[2] = NUMBERS[(score%100/10)];
            SCORE_COUNT[3] = NUMBERS[(score%100%10)];
            return;
        }
        if(dissonanceLogic.getGameStageData().getScore()>=10){
            SCORE_COUNT[1] = NUMBERS[0];
            SCORE_COUNT[2] = NUMBERS[(score/10)];
            SCORE_COUNT[3] = NUMBERS[(score%10)];
            return;
        }
        SCORE_COUNT[1] = NUMBERS[0];
        SCORE_COUNT[2] = NUMBERS[0];
        SCORE_COUNT[3] = NUMBERS[score];
    }
    private void drawScoreLabel() {
        recalculateScoreLabel();
        for(int i = 0;i<4;i++)
            dissonanceFontRenderer.renderText(SCORE_COUNT[i],
                Gdx.graphics.getWidth()-dissonanceFontRenderer.getFontSize(DissonanceFontRenderer.SMALL)*(3-i)/2,
                Gdx.graphics.getHeight(),
                DissonanceFontRenderer.SMALL,0,true);
    }
    private void drawLabels(Matrix4 projection){
        dissonanceFontRenderer.begin(projection);
        dissonanceFontRenderer.setTextColor(0,0,0,0.3f);
        drawGameModeLabel();
        dissonanceFontRenderer.setTextColor(0,0,0,0.6f);
        drawScoreLabel();
        dissonanceFontRenderer.end();
    }
    public void stopAllAnimations(){
        gameModeChanged = false;
        savedGameMode = dissonanceLogic.getGameStageData().getGameMode();
        shapeBasketRenderer.stopAllAnimations();
    }
    public DissonanceLogicRenderer(DissonanceLogic dissonanceLogic,
                                   DissonanceShapeRenderer dissonanceShapeRenderer,
                                   ShapeRenderer gdxShapeRenderer,
                                   DissonanceFontRenderer fontRenderer,
                                   ColorPalette colorPalette){
        this.dissonanceLogic = dissonanceLogic;
        this.gameField = dissonanceLogic.getGameField();
        this.colorPalette = colorPalette;
        this.gdxShapeRenderer = gdxShapeRenderer;
        this.gdxShapeRenderer.setAutoShapeType(true);
        this.shapeRenderer = dissonanceShapeRenderer;
        this.shapeBasketRenderer = new DissonanceShapeBasketRenderer(gameField.getShapeBasket(),
                shapeRenderer,this.gdxShapeRenderer);
        this.backGroundColor = colorPalette.getColorArray(BACKGROUND);
        this.dissonanceFontRenderer = fontRenderer;
        this.gameModeChangeAnimation = new GameModeChangeAnimation();
    }
    public void render(Matrix4 projection){
        if(!DissonanceResources.getDissonanceScreenGrid().isGameFieldVisible()){
            drawGameFieldBackGround(projection);
            return;
        }
        checkGameModeChange();
        drawShapeBasket(projection);
        drawGameFieldBackGround(projection);
        drawLabels(projection);
        drawShapesOnField(projection);
    }
}
