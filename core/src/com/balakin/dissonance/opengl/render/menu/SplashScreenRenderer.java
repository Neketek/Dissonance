package com.balakin.dissonance.opengl.render.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.balakin.dissonance.opengl.render.DissonanceTexturePack;
import com.balakin.dissonance.utils.DissonanceResources;

/**
 * Created by neketek on 23.07.15.
 */
public class SplashScreenRenderer{
    private static final int TIME_BEFORE_FADE_OUT_LIMIT = 1;
    private Texture splashScreenImage = null;
    private ShapeRenderer shapeRenderer = null;
    private SpriteBatch spriteBatch = null;
    private Color color = null;
    private float timeBeforeFadeOut = 0;
    private boolean animated = true;
    private boolean fadeIn = true;
    private float splashImageX = 0;
    private float splashImageY = 0;
    private float splashImageH = 0;
    private float splashImageW = 0;
    private float sw,sh,x,y;
    private void calculateSize(float sw,float sh){
        splashImageH = splashScreenImage.getHeight();
        splashImageW = splashScreenImage.getWidth();
        float whcoef = (float)(((double)splashImageH)/splashImageW);
        splashImageW = sw-(sw/2.5f);
        splashImageH = splashImageW*whcoef;
    }
    private void calculatePosition(float x,float y,float sw,float sh){
        splashImageX =x+(sw-splashImageW)/2;
        splashImageY =y+sh-(sh-splashImageH)/2-splashImageH;
       // System.out.println("(sw-splashImageW)/2="+splashImageX);
    }
    public SplashScreenRenderer(float x,float y,float w,float h){
        //TODO:need to create right initialization of splash screen
        this.splashScreenImage = DissonanceResources.getDissonanceTexturePack().
                getTextureById(DissonanceTexturePack.DISSONANCE_LOGO);
        this.spriteBatch = DissonanceResources.getGdxSpriteBatch();
        this.shapeRenderer = DissonanceResources.getGdxShapeRenderer();
        this.color = new Color(1,1,1,0);
        this.calculateSize(w,h);
        this.calculatePosition(x, y, w, h);
        this.sw = w;
        this.sh = h;
        this.x = x;
        this.y = y;
    }
    public void update(float deltaTime){
        if(!animated)
            return;
        if(fadeIn){
            if(color.a < 1)
                color.set(color.r,color.g,color.b,color.a+deltaTime);
            else {
                fadeIn = false;
                DissonanceResources.getDissonancePSController().synchronizeScoreWithLeaderBoard();
                DissonanceResources.getDissonanceAdsController().checkAdsStatus();
            }
        }else{
            if(timeBeforeFadeOut<TIME_BEFORE_FADE_OUT_LIMIT) {
                timeBeforeFadeOut += deltaTime;
                return;
            }
            if(color.a>0)
                color.set(color.r,color.g,color.b,color.a-deltaTime);
            else
                animated = false;
        }
    }
    public void render(Matrix4 projection){
        spriteBatch.setProjectionMatrix(projection);
        shapeRenderer.setProjectionMatrix(projection);
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setColor(0, 0, 0, color.a /1.5f);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(x, y, sw, sh);
        shapeRenderer.end();
        Gdx.gl20.glDisable(GL20.GL_BLEND);
        spriteBatch.begin();
        spriteBatch.setColor(color);
        spriteBatch.draw(splashScreenImage, splashImageX, splashImageY,
                splashImageW,splashImageH);
        spriteBatch.end();
    }

    public boolean isAnimated() {
        return animated;
    }
}
