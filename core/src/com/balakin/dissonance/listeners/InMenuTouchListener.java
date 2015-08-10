package com.balakin.dissonance.listeners;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.balakin.dissonance.menu.DissonanceMenu;
import com.balakin.dissonance.menu.button.DissonanceButton;
import com.balakin.dissonance.utils.DissonanceState;

/**
 * Created by neketek on 05.07.15.
 */
public class InMenuTouchListener extends InputAdapter{
    private DissonanceState dissonanceState = null;
    private DissonanceButtonsListener dissonanceButtonsListener = null;
    public InMenuTouchListener(DissonanceState dissonanceState,DissonanceButtonsListener dissonanceButtonsListener){
        this.dissonanceState = dissonanceState;
        this.dissonanceButtonsListener = dissonanceButtonsListener;
    }
    private boolean isPointerOnButton(DissonanceButton button,float x,float y){
        return button.getX()<=x&&(button.getX()+button.getWidth())>=x&&
                button.getY()<=y&&(button.getY()+button.getHeight())>=y;
    }
    private DissonanceButton getButtonUnderPointer(float x,float y,DissonanceMenu menu){
        for(DissonanceButton button:menu.getButtonList())
            if(isPointerOnButton(button,x+menu.getX(),y+menu.getY()))
                return button;
        return null;
    }
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int b) {
        if(dissonanceState.isCameraMoving()||dissonanceState.getActiveMenu()==null)
            return false;
        DissonanceButton button = getButtonUnderPointer(screenX, Gdx.graphics.getHeight()-screenY,dissonanceState.getActiveMenu());
        if(button!=null)
            dissonanceButtonsListener.onTouchDown(button);
        return false;
    }
}
