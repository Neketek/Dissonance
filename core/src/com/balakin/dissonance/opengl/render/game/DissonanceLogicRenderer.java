package com.balakin.dissonance.opengl.render.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.balakin.dissonance.logic.DissonanceLogic;
import com.balakin.dissonance.logic.parts.field.GameField;
import com.balakin.dissonance.logic.parts.shape.Shape;
import com.balakin.dissonance.opengl.render.ColorPalette;
import com.balakin.dissonance.opengl.render.DissonanceFontRenderer;
import com.balakin.dissonance.utils.DissonanceConfig;
import com.balakin.dissonance.utils.DissonanceResources;
import com.balakin.dissonance.utils.DissonanceState;

import java.awt.Font;
import java.util.Random;

import static com.balakin.dissonance.opengl.render.ColorPalette.ALPHA;
import static com.balakin.dissonance.opengl.render.ColorPalette.BACKGROUND;
import static com.balakin.dissonance.opengl.render.ColorPalette.BLUE;
import static com.balakin.dissonance.opengl.render.ColorPalette.GREEN;
import static com.balakin.dissonance.opengl.render.ColorPalette.RED;

/**
 * Created by neketek on 03.07.15.
 */
public class DissonanceLogicRenderer {
    private static final int SHAPE_TUTORIAL = 0;
    private static final int GAME_MODE_TUTORIAL = 1;
    private static final int SHAPE_BASKET_TUTORIAL = 2;
    private static final int GAME_RULES_TUTORIAL = 3;
    private static final String COLOR_COMPARSION = "COLOR";
    private static final String SHAPE_COMPARSION = "SHAPE";
    private static final String[] GAME_MODE_LABEL = {COLOR_COMPARSION,SHAPE_COMPARSION};
    private static  final  String SCORE = "SCORE ";
    private static final String[] NUMBERS = {"0","1","2","3","4","5","6","7","8","9"};
    private static final String SPACE = " ";
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
    private float grayForGameMode = 230f/255f;
    private float grayForScoreLabel = 0;
    private float scoreLabelAlpha = 0.6f;
    private TutorialAnimator tutorialAnimator = null;
    private int previous_score = -1;
    private int scoreArraySize = 2;
    private int tutorialStage = 0;
    private DissonanceState state = null;
    private class TutorialAnimator{
        private int textSizeId = DissonanceFontRenderer.SMALL;
        private float lineHeight = dissonanceFontRenderer.getFontSize(textSizeId);
        private float characterWidth = lineHeight/2;
        private float fillAlpha = 0.0f;
        private float fillAlphaUpLimit = 0.8f;
        private float fillAlphaDownLimit = 0;
        private boolean fadeIn = true;
        private boolean animated = false;
        private void drawShapeTutorialLabel(Matrix4 projection){
            dissonanceFontRenderer.begin(projection);
            dissonanceFontRenderer.setTextColor(grayForGameMode, grayForGameMode, grayForGameMode, fillAlpha);
            dissonanceFontRenderer.renderText("This is a figure.", gameField.getWidth() / 2 + characterWidth * 13,
                    gameField.getShape(0).getY() - gameField.getShape(0).getRadius() * 2,
                    textSizeId, 0, false);
            dissonanceFontRenderer.renderText("It has two parameters:",gameField.getWidth()/2+characterWidth*16,
                    gameField.getShape(0).getY()-gameField.getShape(0).getRadius()*2-lineHeight*1.5f,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("1)COLOR",gameField.getWidth()/2+characterWidth*16,
                    gameField.getShape(0).getY()-gameField.getShape(0).getRadius()*2-lineHeight*1.5f*2,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("2)SHAPE",gameField.getWidth()/2+characterWidth*16,
                    gameField.getShape(0).getY()-gameField.getShape(0).getRadius()*2-lineHeight*1.5f*3,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("Tap to continue.",gameField.getWidth()/2+characterWidth*13,
                    gameField.getShape(0).getY()-gameField.getShape(0).getRadius()*2-lineHeight*1.5f*4,
                    textSizeId, 0,false);
            dissonanceFontRenderer.end();
        }
        private void drawGameModeTutorialLabel(Matrix4 projection){
            dissonanceFontRenderer.begin(projection);
            dissonanceFontRenderer.setTextColor(grayForGameMode, grayForGameMode, grayForGameMode, fillAlpha);
            dissonanceFontRenderer.renderText("This is a game mode.", gameField.getWidth() / 2 + characterWidth * 13,gameField.getHeight()/2,
                    textSizeId, 0, false);
            dissonanceFontRenderer.renderText("It can change during the game to",gameField.getWidth()/2+characterWidth*18,
                    gameField.getHeight()/2-lineHeight*1.5f,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("SHAPE or COLOR",gameField.getWidth()/2+characterWidth*11,
                    gameField.getHeight()/2-lineHeight*1.5f*2,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("Tap to continue.",gameField.getWidth()/2+characterWidth*10,
                    gameField.getHeight()/2-lineHeight*1.5f*3,
                    textSizeId, 0,false);
            dissonanceFontRenderer.end();
        }
        private void drawShapeBasketTutorialLabel(Matrix4 projection){
            dissonanceFontRenderer.begin(projection);
            dissonanceFontRenderer.setTextColor(grayForGameMode, grayForGameMode, grayForGameMode, fillAlpha);
            dissonanceFontRenderer.renderText("This is a shape basket.", gameField.getWidth() / 3 + characterWidth * 13, gameField.getHeight() / 2,
                    textSizeId, 0, false);
            dissonanceFontRenderer.renderText("You can switch between plates",gameField.getWidth()/3+characterWidth*18,
                    gameField.getHeight()/2-lineHeight*1.5f,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("by tap or swipe.",gameField.getWidth()/3+characterWidth*11,
                    gameField.getHeight()/2-lineHeight*1.5f*2,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("But remember, it reacts to",gameField.getWidth()/3+characterWidth*15,
                    gameField.getHeight()/2-lineHeight*1.5f*3,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("only one finger at the same time.",gameField.getWidth()/3+characterWidth*18,
                    gameField.getHeight()/2-lineHeight*1.5f*4,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("Tap to continue.",gameField.getWidth()/3+characterWidth*9,
                    gameField.getHeight()/2-lineHeight*1.5f*5,
                    textSizeId, 0,false);
            dissonanceFontRenderer.end();
        }

        private void drawGameRulesTutorial(Matrix4 projection) {
            dissonanceFontRenderer.begin(projection);
            dissonanceFontRenderer.setTextColor(grayForGameMode, grayForGameMode, grayForGameMode, fillAlpha);
            dissonanceFontRenderer.renderText("This is game concept.", gameField.getWidth() / 2 + characterWidth * 10,
                    gameField.getHeight() / 1.5f,
                    textSizeId, 0, false);
            dissonanceFontRenderer.renderText("You should switch between plates",gameField.getWidth()/2+characterWidth*15,
                    gameField.getHeight()/1.5f-lineHeight*1.5f,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("to catch figure to the basket",gameField.getWidth()/2+characterWidth*11,
                    gameField.getHeight()/1.5f-lineHeight*1.5f*2,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("choosing the plates depending of ",gameField.getWidth()/2+characterWidth*15,
                    gameField.getHeight()/1.5f-lineHeight*1.5f*3,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("game mode, by shape or color.",gameField.getWidth()/2+characterWidth*12,
                    gameField.getHeight()/1.5f-lineHeight*1.5f*4,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("You can enable tutorial again",gameField.getWidth()/2+characterWidth*12,
                    gameField.getHeight()/1.5f-lineHeight*1.5f*5,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("in the settings menu.",gameField.getWidth()/2+characterWidth*9,
                    gameField.getHeight()/1.5f-lineHeight*1.5f*6,
                    textSizeId, 0,false);
            dissonanceFontRenderer.renderText("Tap to continue the game.",gameField.getWidth()/2+characterWidth*10,
                    gameField.getHeight()/1.5f-lineHeight*1.5f*7,
                    textSizeId, 0,false);
            dissonanceFontRenderer.end();
        }
        private void captureFill(Matrix4 projection, float x, float y, float w, float h){
            GL20 gl20 = Gdx.gl20;
            gl20.glEnable(GL20.GL_BLEND);
            gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            gdxShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            gdxShapeRenderer.setColor(0, 0, 0, fillAlpha);
            gdxShapeRenderer.rect(x, y, w, h);
            gdxShapeRenderer.end();
            gl20.glDisable(GL20.GL_BLEND);
        }
        private void captureShapeBasketFill(Matrix4 projection){
            captureFill(projection, 0, gameField.getShapeBasket().getIndicatorTop(), gameField.getWidth(),
                    gameField.getHeight());
        }
        private void captureWholeScreen(Matrix4 projection){
            captureFill(projection,0,0,gameField.getWidth(),gameField.getHeight());
        }
        private void releaseCapture() {
            fadeIn = false;
            animated = true;
        }
        private void nextStage(){
            if(animated)
                return;
            animated = true;
            fadeIn = false;
        }
        private void renderCurrentStageFill(Matrix4 projection){
            switch(tutorialStage){
                case SHAPE_TUTORIAL:
                    renderShapeTutorial(projection);
                    break;
                case GAME_MODE_TUTORIAL:
                    renderGameModeTutorial(projection);
                    break;
                case SHAPE_BASKET_TUTORIAL:
                    renderShapeBasketTutorial(projection);
                    break;
                case GAME_RULES_TUTORIAL:
                    renderGameConceptTutorial(projection);
                    break;
                default:
                    DissonanceConfig.tutorialEnabled = false;
                    state.setTutorialStarted(false);
                    break;
            }
        }
        private void updateAnimation(float delta){
            if (fadeIn){
                fillAlpha += delta;
                if(fillAlpha>=fillAlphaUpLimit){
                    fillAlpha = fillAlphaUpLimit;
                    animated = false;
                }
            }else{
                fillAlpha-=delta;
                if(fillAlpha<=fillAlphaDownLimit) {
                    fillAlpha = fillAlphaDownLimit;
                    // animated = false;
                    tutorialStage++;
                    fadeIn = true;
                    //animated=true;
                }
            }
        }

        public void renderShapeTutorial(Matrix4 projection){
            drawShapeBasket(projection);
            drawGameFieldBackGround(projection);
            drawLabels(projection);
            captureWholeScreen(projection);
            drawShapesOnField(projection);
            drawShapeTutorialLabel(projection);
        }
        public void renderGameModeTutorial(Matrix4 projection){
            drawShapeBasket(projection);
            drawGameFieldBackGround(projection);
            drawShapesOnField(projection);
            captureWholeScreen(projection);
            drawLabels(projection);
            drawGameModeTutorialLabel(projection);
        }
        public void renderShapeBasketTutorial(Matrix4 projection){
            drawShapeBasket(projection);
            drawGameFieldBackGround(projection);
            drawLabels(projection);
            drawShapesOnField(projection);
            captureShapeBasketFill(projection);
            drawShapeBasketTutorialLabel(projection);
        }
        public void renderGameConceptTutorial(Matrix4 projection){
            drawShapeBasket(projection);
            drawGameFieldBackGround(projection);
            drawLabels(projection);
            drawShapesOnField(projection);
            captureWholeScreen(projection);
            drawGameRulesTutorial(projection);
        }
        public void renderCurrentStage(Matrix4 projection){
            renderCurrentStageFill(projection);
            //TODO:add texts
        }
    }
    private class GameModeChangeAnimation{
        private final float labelWidth = dissonanceFontRenderer.getFontSize(DissonanceFontRenderer.EXTRA_LARGE)*5/2;
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
                    finalLabelPositionY, DissonanceFontRenderer.EXTRA_LARGE,
                    Font.CENTER_BASELINE,true);
            dissonanceFontRenderer.renderText(
                    GAME_MODE_LABEL[dissonanceLogic.getGameStageData().getGameMode()],
                    currentLabelPositionX,
                    finalLabelPositionY, DissonanceFontRenderer.EXTRA_LARGE,
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
                    gameField.getHeight() / 2 + gameField.getShapeBasket().getTop(), DissonanceFontRenderer.EXTRA_LARGE, Font.CENTER_BASELINE, true);
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
            scoreArraySize = 4;
            return;
        }
        if(dissonanceLogic.getGameStageData().getScore()>=10){
            SCORE_COUNT[1] = NUMBERS[(score/10)];
            SCORE_COUNT[2] = NUMBERS[(score%10)];
            SCORE_COUNT[3] = SPACE;
            scoreArraySize = 3;
            return;
        }
        SCORE_COUNT[1] = NUMBERS[score];
        SCORE_COUNT[2] = SPACE;
        SCORE_COUNT[3] = SPACE;
        scoreArraySize = 2;
    }
    private void drawScoreLabel() {
        recalculateScoreLabel();
        for(int i = 0;i<scoreArraySize;i++)
            dissonanceFontRenderer.renderText(SCORE_COUNT[i],
                gameField.getWidth()
                        -dissonanceFontRenderer.getFontSize(DissonanceFontRenderer.SMALL)*
                        (scoreArraySize-i-1)/2
                        -dissonanceFontRenderer.getFontSize(DissonanceFontRenderer.SMALL)/2,
                gameField.getHeight(),
                DissonanceFontRenderer.SMALL,0,true);
    }
    private void drawLabels(Matrix4 projection){
        dissonanceFontRenderer.begin(projection);
        dissonanceFontRenderer.setTextColor(grayForGameMode,grayForGameMode,grayForGameMode,1);
        drawGameModeLabel();
        dissonanceFontRenderer.setTextColor(grayForScoreLabel,grayForScoreLabel,
                grayForScoreLabel,scoreLabelAlpha);
        drawScoreLabel();
        dissonanceFontRenderer.end();
    }
    public void stopAllAnimations(){
        gameModeChanged = false;
        savedGameMode = dissonanceLogic.getGameStageData().getGameMode();
        shapeBasketRenderer.stopAllAnimations();
    }
    private boolean tutorialStartCondition(){
        if(gameField.getShapesOnFieldCount()>0){
            Shape shape = gameField.getShape(0);
            float r = shape.getRadius();
            float y = shape.getY();
            float gfh = gameField.getHeight();
            return gfh-y >=r*2;
        }
        return false;
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
        tutorialAnimator = new TutorialAnimator();
        this.state = DissonanceResources.getDissonanceState();
    }
    public void render(Matrix4 projection){
        if(!DissonanceResources.getDissonanceScreenGrid().isGameFieldVisible()){
            drawGameFieldBackGround(projection);
            return;
        }
        if(DissonanceConfig.tutorialEnabled){
            if(!state.isTutorialStarted()){
                if(tutorialStartCondition()){
                    state.setTutorialStarted(true);
                    tutorialStage = 0;
                    tutorialAnimator.fadeIn = true;
                    tutorialAnimator.animated = true;
                }
            }else{
                tutorialAnimator.renderCurrentStage(projection);
                if(tutorialAnimator.animated)
                    tutorialAnimator.updateAnimation(Gdx.graphics.getDeltaTime());
                if(DissonanceConfig.tutorialEnabled)
                    return;
                else
                    DissonanceResources.getDissonanceScreenGrid().getSettingsMenu().setTutorialEnabled(false);
            }
        }
        checkGameModeChange();
        drawShapeBasket(projection);
        drawGameFieldBackGround(projection);
        drawLabels(projection);
        drawShapesOnField(projection);
    }
    public void requestTutorialNextStage(){
        this.tutorialAnimator.nextStage();
    }
}
