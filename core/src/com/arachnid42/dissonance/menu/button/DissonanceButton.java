package com.arachnid42.dissonance.menu.button;

import java.util.ArrayList;
/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceButton{
    public static DissonanceButton createStandardButton(float menuWidth,float menuHeight){
        DissonanceButton button = new DissonanceButton();
        button.setWidth(menuWidth/3);
        button.setHeight(button.getWidth());
        button.setIconMarginHeight(button.getHeight()/4);
        button.setIconMarginWidth(button.getWidth()/4);
        button.setShowIcon(true);
        return button;
    }
    public static final int PLAY = 0;
    public static final int SETTINGS = 1;
    public static final int SCORE = 2;
    public static final int EXIT = 3;
    public static final int SOUND = 4;
    public static final int NO_ADS = 5;
    public static final int EXIT_TO_MAIN = 6;
    public static final int RESUME = 7;
    private ArrayList<DissonanceButtonListener>listeners = null;
    private float x = 0;
    private float y = 0;
    private float width = 0;
    private float height = 0;
    private float iconWidth = 0;
    private float iconHeight = 0;
    private float iconX = 0;
    private float iconY = 0;
    private float iconMarginHeight= 0;
    private float iconMarginWidth = 0;
    private int texturePackId = 0;
    private int colorPaletteId = 0;
    private int textureColor = 0;
    private int buttonId = 0;
    private boolean showIcon = true;
    private void recalculateIconData(){
        this.iconX = x+iconMarginWidth;
        this.iconWidth = width-2*iconMarginWidth;
        this.iconY = y+iconMarginHeight;
        this.iconHeight = height-2*iconMarginHeight;
    }
    public DissonanceButton(){
        this.listeners = new ArrayList<DissonanceButtonListener>();
    }
    public void invokeTouchDownEvent(){
        for(DissonanceButtonListener listener:listeners)
            listener.onTouchDown(this);
    }
    public void invokeTouchUpEvent(){
        for(DissonanceButtonListener listener:listeners)
            listener.onTouchUp(this);
    }
    public void addDissonanceButtonListener(DissonanceButtonListener listener){
        if(listener == null)
            throw new IllegalArgumentException();
        this.listeners.add(listener);
    }
    public DissonanceButtonListener getDissonanceButtonListener(int index){
        return this.listeners.get(index);
    }
    public DissonanceButtonListener removeDissonanceButtonListener(int index){
        return  this.listeners.remove(index);
    }
    public boolean removeDissonanceButtonListener(DissonanceButtonListener listener){
        return this.listeners.remove(listener);
    }
    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
        recalculateIconData();
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
        recalculateIconData();
    }

    public float getIconMarginHeight() {
        return iconMarginHeight;
    }

    public void setIconMarginHeight(float iconMarginHeight) {
        this.iconMarginHeight = iconMarginHeight;
        recalculateIconData();
    }
    public float getIconMarginWidth() {
        return iconMarginWidth;
    }

    public void setIconMarginWidth(float iconMarginWidth) {
        this.iconMarginWidth = iconMarginWidth;
       recalculateIconData();
    }
    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
        recalculateIconData();
    }

    public int getColorPaletteId() {
        return colorPaletteId;
    }

    public void setColorPaletteId(int colorPaletteId) {
        this.colorPaletteId = colorPaletteId;
    }

    public boolean isShowIcon() {
        return showIcon;
    }

    public void setShowIcon(boolean showIcon) {
        this.showIcon = showIcon;
    }

    public int getTexturePackId() {
        return texturePackId;
    }

    public void setTexturePackId(int texturePackId) {
        this.texturePackId = texturePackId;
    }

    public int getTextureColor() {
        return textureColor;
    }

    public void setTextureColor(int colorPaletteId) {
        this.textureColor = colorPaletteId;
    }

    public float getIconWidth() {
        return iconWidth;
    }

    public float getIconHeight() {
        return iconHeight;
    }

    public float getIconX() {
        return iconX;
    }

    public float getIconY() {
        return iconY;
    }

    public int getButtonId() {
        return buttonId;
    }

    public void setButtonId(int buttonId) {
        this.buttonId = buttonId;
    }
}

