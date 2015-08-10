package com.balakin.dissonance.opengl.render;

import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neketek on 09.06.15.
 */
public class ColorPalette{
    private static final int BYTE_RGB_MAX_VALUE = 255;
    public static final int COLOR_RED = 0;
    public static final int COLOR_BLUE = 1;
    public static final int COLOR_GREEN = 2;
    public static final int COLOR_MAGENTA = 3;
    public static final int COLOR_YELLOW = 4;
    public static final int RED = 0;
    public static final int GREEN = 1;
    public static final int BLUE = 2;
    public static final int ALPHA = 3;
    public static final int COLOR_ARRAY_SIZE = 4;
    public static final int BACKGROUND = 6;
    public static final int FOREGROUND = 5;
    private List<float[]> colors = null;
    private float[]currentColor = null;
    private int currentColorIndex = 0;
    private boolean forceAlpha;
    private float forceAlphaValue = 0;
    public static ColorPalette createColorPalette(){
        ColorPalette colorPalette = new ColorPalette();
        colorPalette.colors.add(byteRgbToFloatRgb(255,23,68,255)); // color for red  255,23,68
        colorPalette.colors.add(byteRgbToFloatRgb(79,195,247,255)); // color for blue 79,195,247
        colorPalette.colors.add(byteRgbToFloatRgb(118,255,3,255)); // color for green 118,255,3
        colorPalette.colors.add(byteRgbToFloatRgb(213,0,249,255)); // color for magenta 213,0,249
        colorPalette.colors.add(byteRgbToFloatRgb(255,235,59,255)); // color for yellow 255,235,59
        colorPalette.colors.add(byteRgbToFloatRgb(245,245,245,255)); // color for foreground 207,216,220
        colorPalette.colors.add(byteRgbToFloatRgb(245,245,245,255)); // color for background 238,238,238
        colorPalette.currentColorIndex = 0;
        colorPalette.currentColor = colorPalette.colors.get(0);
        return colorPalette;
    }
    private static float getFloatRgb(int byteRgb){
        return ((float)(byteRgb))/BYTE_RGB_MAX_VALUE;
    }
    private static final float[] byteRgbToFloatRgb(int red,int green,int blue,int alpha){
        float color[] = new float[COLOR_ARRAY_SIZE];
        color[RED] = getFloatRgb(red);
        color[GREEN] = getFloatRgb(green);
        color[BLUE] = getFloatRgb(blue);
        color[ALPHA] = getFloatRgb(alpha);
        return color;
    }
    private ColorPalette(){
        this.colors = new ArrayList<float[]>();
    }
    public int getColorsCount(){
        return this.colors.size();
    }
    public float getCurrentColorComponent(int componentIndex){
        return currentColor[componentIndex];
    }
    public void setCurrentColor(int colorIndex){
        this.currentColor = colors.get(colorIndex);
        this.currentColorIndex = colorIndex;
    }
    public int getCurrentColorIndex(){
        return this.currentColorIndex;
    }
    public float getRed(){
        return this.currentColor[RED];
    }
    public float getBlue(){
        return this.currentColor[BLUE];
    }
    public float getGreen(){
        return this.currentColor[GREEN];
    }
    public float getAlpha(){
        return this.currentColor[ALPHA];
    }
    public float[] getColorArray(int index){
        return this.colors.get(index);
    }
    public void setColor(SpriteBatch spriteBatch,int colorId){
        if(forceAlpha) {
            spriteBatch.setColor(
                    colors.get(colorId)[RED],
                    colors.get(colorId)[GREEN],
                    colors.get(colorId)[BLUE],
                    forceAlphaValue
            );
        }else{
            spriteBatch.setColor(
                    colors.get(colorId)[RED],
                    colors.get(colorId)[GREEN],
                    colors.get(colorId)[BLUE],
                    colors.get(colorId)[ALPHA]
            );
        }
     // System.out.println("COLOR:" + colors.get(colorId)[RED] + ";" + colors.get(colorId)[GREEN] + ";" + colors.get(colorId)[BLUE]+";"+colors.get(colorId)[ALPHA]);
    }
    public void setColor(ShapeRenderer renderer,int colorId){
        if(forceAlpha){
            renderer.setColor(
                    colors.get(colorId)[RED],
                    colors.get(colorId)[GREEN],
                    colors.get(colorId)[BLUE],
                    forceAlphaValue
            );
        }else {
            renderer.setColor(
                    colors.get(colorId)[RED],
                    colors.get(colorId)[GREEN],
                    colors.get(colorId)[BLUE],
                    colors.get(colorId)[ALPHA]
            );
        }
    }
    public void setColor(BitmapFont font,int colorId){
        if(forceAlpha){
            font.setColor(
                    colors.get(colorId)[RED],
                    colors.get(colorId)[GREEN],
                    colors.get(colorId)[BLUE],
                    forceAlphaValue
            );
        }else {
            font.setColor(
                    colors.get(colorId)[RED],
                    colors.get(colorId)[GREEN],
                    colors.get(colorId)[BLUE],
                    colors.get(colorId)[ALPHA]
            );
        }
    }
    public void setColor(GL20 gl20,int colorId){
        if(forceAlpha){
            gl20.glClearColor(
                    colors.get(colorId)[RED],
                    colors.get(colorId)[GREEN],
                    colors.get(colorId)[BLUE],
                    forceAlphaValue
            );
        }else {
            gl20.glClearColor(
                    colors.get(colorId)[RED],
                    colors.get(colorId)[GREEN],
                    colors.get(colorId)[BLUE],
                    colors.get(colorId)[ALPHA]
            );
        }
    }

    public void setForceAlpha(boolean forceAlpha) {
        this.forceAlpha = forceAlpha;
    }

    public boolean isForceAlpha() {
        return forceAlpha;
    }

    public float getForceAlphaValue() {
        return forceAlphaValue;
    }

    public void setForceAlphaValue(float forceAlphaValue) {
        this.forceAlphaValue = forceAlphaValue;
    }
}
