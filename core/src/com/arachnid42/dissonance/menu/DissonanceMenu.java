package com.arachnid42.dissonance.menu;

import com.arachnid42.dissonance.menu.button.DissonanceButton;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceMenu {
    public static final int MAIN_MENU = 0;
    public static final int PAUSE_MENU = 1;
    public static final int SETTINGS_MENU = 2;
    protected ArrayList<DissonanceButton> buttons = null;
    private float x = 0,y = 0,width = 0,height = 0;
    private float alpha = 0.0f;
    private boolean fadeIn = false;
    private boolean animated = false;
    private boolean visible = true;
    private float firstDelta = 0;
    private int type;
    private float animationWaitTime = 0;

    private void setAbsoluteCoordinatesForButton(DissonanceButton button){
        button.setX(button.getX()+x);
        button.setY(button.getY()+y);
    }
    private void setRelativeCoordinatesForButton(DissonanceButton button){
        button.setX(button.getX() - x);
        button.setY(button.getY() - y);
    }
    public DissonanceMenu(float x, float y, float w, float h){
        buttons = new ArrayList<DissonanceButton>();
        this.x = x;
        this.y = y;
        if(w<=0||h<=0)
            throw new IllegalArgumentException();
        this.width = w;
        this.height = h;
    }
    public void startFadeIn(float time){
        this.animated = true;
        this.fadeIn = true;
        this.alpha = 0;
    }
    public void startFadeOut(float time){
        this.animationWaitTime = time;
        this.fadeIn = false;
        this.animated = false;
        this.alpha = 1;
    }
    public void setLocation(float x,float y){
        float dx = x - getX();
        float dy = y - getY();
        for(DissonanceButton button:buttons){
            button.setX(button.getX()+dx);
            button.setY(button.getY()+dy);
        }
        this.x = x;
        this.y = y;
    }
    public void add(DissonanceButton button){
        if(button==null)
            throw new IllegalArgumentException();
        if(buttons.contains(button))
            throw  new IllegalArgumentException();
        this.buttons.add(button);
        button.setOwner(this);
        this.setAbsoluteCoordinatesForButton(button);
    }
    public DissonanceButton remove(int index){
        DissonanceButton button = buttons.remove(index);
        setRelativeCoordinatesForButton(button);
        button.setOwner(null);
        return button;
    }
    public List<DissonanceButton> getButtonList(){
        return buttons;
    }

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }

    public float getX() {
        return x;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public float getY() {
        return y;
    }
    public int getType(){
        return this.type;
    }
    public void setType(int type){
        this.type = type;
    }

    public float getAlpha() {
        return alpha;
    }

    public boolean isFadeIn() {
        return fadeIn;
    }

    public boolean isAnimated() {
        return animated;
    }
    public void updateAnimation(float delta){
        if(!animated)
            return;
        if(animationWaitTime > 0){
            animationWaitTime-=delta;
            return;
        }
        if(firstDelta==0) {
            firstDelta = delta*2;
        }
        if(fadeIn) {
           // System.out.println("FADE IN");
            alpha += delta*2;
            if (alpha >= 1) {
                animated = false;
                firstDelta = 0;
            }
        }else{
          //  System.out.println("FADE OUT");
            alpha -= delta*2;
            if (alpha <=0) {
                firstDelta = 0;
                animated = false;
            }
        }
    }
    public float getFirstDelta() {
        return firstDelta;
    }
}
