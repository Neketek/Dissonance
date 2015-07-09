package com.arachnid42.dissonance.menu;

import com.arachnid42.dissonance.menu.button.DissonanceButton;

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
    private boolean visible = true;
    private int type;

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
        this.setAbsoluteCoordinatesForButton(button);
    }
    public DissonanceButton remove(int index){
        DissonanceButton button = buttons.remove(index);
        setRelativeCoordinatesForButton(button);
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
}
