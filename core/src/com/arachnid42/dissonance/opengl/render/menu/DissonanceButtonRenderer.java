package com.arachnid42.dissonance.opengl.render.menu;

import com.arachnid42.dissonance.menu.button.DissonanceButton;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceTexturePack;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import java.util.List;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceButtonRenderer{
    private DissonanceTexturePack dissonanceTexturePack = null;
    private ColorPalette colorPalette = null;
    private SpriteBatch spriteBatch = null;
    private ShapeRenderer shapeRenderer = null;
    private void drawButtonPlate(DissonanceButton button){
        colorPalette.setColor(shapeRenderer,button.getColorPaletteId());
        shapeRenderer.rect(button.getX(),button.getY(),button.getWidth(),button.getHeight());
    }
    private void drawButtonIcon(DissonanceButton button){
        colorPalette.setColor(spriteBatch, button.getTextureColor());
        spriteBatch.draw(dissonanceTexturePack.getTextureById(button.getTexturePackId()),
                button.getIconX(), button.getIconY(), button.getIconWidth(), button.getIconHeight());
    }
    public DissonanceButtonRenderer(DissonanceTexturePack texturePack,ColorPalette colorPalette,SpriteBatch spriteBatch,ShapeRenderer shapeRenderer){
        this.colorPalette = colorPalette;
        this.dissonanceTexturePack = texturePack;
        this.shapeRenderer = shapeRenderer;
        this.spriteBatch = spriteBatch;
    }

    public void render(List<DissonanceButton> dissonanceButtons,Matrix4 projection){
        shapeRenderer.setProjectionMatrix(projection);
        spriteBatch.setProjectionMatrix(projection);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(DissonanceButton button:dissonanceButtons)
            drawButtonPlate(button);
        shapeRenderer.end();
        spriteBatch.begin();
        for (DissonanceButton button:dissonanceButtons)
            drawButtonIcon(button);
        spriteBatch.end();
    }
}
