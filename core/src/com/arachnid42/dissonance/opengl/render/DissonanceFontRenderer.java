package com.arachnid42.dissonance.opengl.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.DistanceFieldFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.Disposable;

import java.awt.Font;
import java.util.ArrayList;
import java.util.logging.FileHandler;

/**
 * Created by neketek on 03.07.15.
 */
public class DissonanceFontRenderer{
    public static final int SMALL = 0;
    public static final int MEDIUM = 1;
    public static final int LARGE = 2;
    private ArrayList<BitmapFont>fonts = null;
    private float size[] = null;
    private float colorArray[] = null;
    private SpriteBatch spriteBatch = null;
    private void createFontsUsingScreenSize(int width,int height){
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Roboto-Bold.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        //small
        parameter.size = width/20;
        fonts.add(generator.generateFont(parameter));
        size[SMALL] = parameter.size;
        //medium
        parameter.size = width/14;
        fonts.add(generator.generateFont(parameter));
        size[MEDIUM] = parameter.size;
        //large
        parameter.size = width/7;
        fonts.add(generator.generateFont(parameter));
        size[LARGE] = parameter.size;
        generator.dispose();
    }
    public DissonanceFontRenderer(SpriteBatch spriteBatch){
        size = new float[3];
        colorArray = new float[4];
        colorArray[3] = 1;
        fonts = new ArrayList<BitmapFont>();
        createFontsUsingScreenSize(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        this.spriteBatch = spriteBatch;
    }
    public void begin(Matrix4 projection){
        spriteBatch.setProjectionMatrix(projection);
        spriteBatch.begin();
    }
    public void setTextColor(float red,float green,float blue,float alpha){
        this.colorArray[0] = red;
        this.colorArray[1] = green;
        this.colorArray[2] = blue;
        this.colorArray[3] = alpha;
    }
    public void setTextColor(float[]rgbaColorArray){
        this.setTextColor(rgbaColorArray[0], rgbaColorArray[1], rgbaColorArray[2], rgbaColorArray[3]);
    }
    public void renderText(String text,float x,float y,int textSize,int halign,boolean verticalCentered){
        fonts.get(textSize).setColor(colorArray[0],colorArray[1],colorArray[2],colorArray[3]);//TODO: Установить смену цвета текста через метод
        if(!verticalCentered)
            fonts.get(textSize).draw(spriteBatch,text,x,y,0,halign,false);
        else
            fonts.get(textSize).draw(spriteBatch,text,x,y-size[textSize]/2,0,halign,false);
    }
    public void end(){
        spriteBatch.end();
    }
    public float getFontSize(int fontId){
        return this.size[fontId];
    }
}
