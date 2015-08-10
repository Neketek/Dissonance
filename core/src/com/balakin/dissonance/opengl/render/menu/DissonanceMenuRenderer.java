package com.balakin.dissonance.opengl.render.menu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;
import com.balakin.dissonance.menu.DissonanceMenu;
import com.balakin.dissonance.opengl.render.ColorPalette;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceMenuRenderer {
    private DissonanceButtonRenderer buttonRenderer = null;
    private ShapeRenderer shapeRenderer = null;
    private ColorPalette colorPalette = null;
    public  DissonanceMenuRenderer(DissonanceButtonRenderer dissonanceButtonRenderer,ShapeRenderer renderer,ColorPalette colorPalette){
        this.buttonRenderer = dissonanceButtonRenderer;
        this.shapeRenderer = renderer;
        this.colorPalette = colorPalette;
    }
    public void render(DissonanceMenu menu,Matrix4 projection){
        menu.updateAnimation(Gdx.graphics.getDeltaTime());
        if(!menu.isVisible()) {
            return;
        }
        colorPalette.setColor(shapeRenderer, ColorPalette.BACKGROUND);
        shapeRenderer.setProjectionMatrix(projection);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(menu.getX(), menu.getY(), menu.getWidth(), menu.getHeight());
        shapeRenderer.end();
        menu.preRendering(projection);
        if(menu.isAnimated()) {
            if(menu.getAlpha()>menu.getFirstDelta())
                 buttonRenderer.render(menu.getButtonList(), projection);
        }
        else{
            buttonRenderer.render(menu.getButtonList(), projection);
        }
        menu.postRendering(projection);
    }
}
