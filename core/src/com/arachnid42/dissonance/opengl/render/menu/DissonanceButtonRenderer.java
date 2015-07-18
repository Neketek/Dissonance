package com.arachnid42.dissonance.opengl.render.menu;

import com.arachnid42.dissonance.menu.button.DissonanceButton;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceTexturePack;
import com.arachnid42.dissonance.utils.DissonanceResources;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import java.util.List;

/**
 * Created by neketek on 07.07.15.
 */
public class DissonanceButtonRenderer{
    private static final float ACTIVE_COLOR_COOF = 0.85f;
    private DissonanceTexturePack dissonanceTexturePack = null;
    private ColorPalette colorPalette = null;
    private SpriteBatch spriteBatch = null;
    private ShapeRenderer shapeRenderer = null;
    private void drawButtonPlate(DissonanceButton button){
        if(button.isActive()){
            colorPalette.setCurrentColor(button.getColorPaletteId());
            shapeRenderer.setColor(colorPalette.getRed()*ACTIVE_COLOR_COOF,
                    colorPalette.getGreen()*ACTIVE_COLOR_COOF,
                    colorPalette.getBlue()*ACTIVE_COLOR_COOF,
                    colorPalette.getAlpha()*ACTIVE_COLOR_COOF);
        }else{
            if(!button.getOwner().isAnimated())
                colorPalette.setColor(shapeRenderer,button.getColorPaletteId());
            else{
                colorPalette.setCurrentColor(button.getColorPaletteId());
                shapeRenderer.setColor(colorPalette.getRed(),
                        colorPalette.getGreen(),
                        colorPalette.getBlue(),
                        button.getOwner().getAlpha());
                shapeRenderer.rect(button.getX(), button.getY(), button.getWidth(), button.getHeight());
                return;
            }
        }
        shapeRenderer.rect(button.getX(),button.getY(),button.getWidth(),button.getHeight());
    }
    private void drawButtonIcon(DissonanceButton button){
        if(!button.isShowIcon())
            return;
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
        Gdx.gl20.glEnable(GL20.GL_BLEND);
        Gdx.gl20.glBlendFunc(GL20.GL_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.setProjectionMatrix(projection);
        spriteBatch.setProjectionMatrix(projection);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        for(DissonanceButton button:dissonanceButtons) {
            drawButtonPlate(button);
            button.updateActiveTime(Gdx.graphics.getDeltaTime());
        }
        shapeRenderer.end();
        Gdx.gl20.glDisable(GL20.GL_BLEND);
        spriteBatch.begin();
        for (DissonanceButton button:dissonanceButtons)
            drawButtonIcon(button);
        spriteBatch.end();
    }
}
