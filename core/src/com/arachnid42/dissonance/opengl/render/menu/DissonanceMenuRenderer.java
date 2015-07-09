package com.arachnid42.dissonance.opengl.render.menu;

import com.arachnid42.dissonance.menu.DissonanceMenu;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

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
        if(!menu.isVisible())
            return;
        colorPalette.setColor(shapeRenderer,ColorPalette.BACKGROUND);
        shapeRenderer.setProjectionMatrix(projection);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.rect(menu.getX(), menu.getY(), menu.getWidth(), menu.getHeight());
        shapeRenderer.end();
        buttonRenderer.render(menu.getButtonList(),projection);
    }
}
