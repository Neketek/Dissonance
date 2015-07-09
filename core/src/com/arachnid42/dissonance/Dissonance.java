package com.arachnid42.dissonance;

import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceCameraTool;
import com.arachnid42.dissonance.opengl.render.DissonanceFontRenderer;
import com.arachnid42.dissonance.opengl.render.DissonanceMainRenderer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.awt.Font;

public class Dissonance extends ApplicationAdapter {
	DissonanceMainRenderer dissonanceMainRenderer = null;
	DissonanceLogicUpdater dissonanceLogicUpdater = null;
	@Override
	public void create (){
		DissonanceResources.initializeDissonanceResources(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		DissonanceResources.getColorPalette().setColor(Gdx.gl20, ColorPalette.BACKGROUND);
		Gdx.input.setInputProcessor(DissonanceResources.getInMenuTouchListener());
		Gdx.graphics.setVSync(true);
		dissonanceMainRenderer = new DissonanceMainRenderer();
		dissonanceLogicUpdater = new DissonanceLogicUpdater();
		//Gdx.gl20.glClearColor(0,0,0,0);
	}
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		dissonanceMainRenderer.render();
		dissonanceLogicUpdater.update(Gdx.graphics.getDeltaTime());
		//renderGameIcon();
	}
	public void renderGameIcon(){
		DissonanceCameraTool cameraTool = DissonanceResources.getDissonanceCameraTool();
		ColorPalette colorPalette = DissonanceResources.getColorPalette();
		DissonanceFontRenderer fontRenderer = DissonanceResources.getDissonanceFontRenderer();
		cameraTool.setLocation(0, 0);
		ShapeRenderer shapeRenderer = DissonanceResources.getGdxShapeRenderer();
		shapeRenderer.setProjectionMatrix(cameraTool.getProjectionMatrix());
		shapeRenderer.setAutoShapeType(true);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		colorPalette.setColor(shapeRenderer, ColorPalette.COLOR_BLUE);
		shapeRenderer.rect(0, 0, 74, 74);
		colorPalette.setColor(shapeRenderer, ColorPalette.COLOR_YELLOW);
		shapeRenderer.rect(74, 0, 90, 74);
		colorPalette.setColor(shapeRenderer, ColorPalette.RED);
		shapeRenderer.rect(74, 74,90,90);
		colorPalette.setColor(shapeRenderer, ColorPalette.COLOR_MAGENTA);
		shapeRenderer.rect(0, 74, 74,90);
		shapeRenderer.circle(64, 64,37, 100);
		shapeRenderer.set(ShapeRenderer.ShapeType.Line);
		colorPalette.setColor(shapeRenderer, ColorPalette.BACKGROUND);
		for(int i = 64;i>=54;i-=0.1f)
			shapeRenderer.circle(74, 74,i, 200);
		shapeRenderer.end();
		fontRenderer.begin(cameraTool.getProjectionMatrix());
		fontRenderer.setTextColor(colorPalette.getColorArray(ColorPalette.BACKGROUND));
		fontRenderer.renderText("D", 78, 146.5f, DissonanceFontRenderer.LARGE, Font.CENTER_BASELINE, true);
		fontRenderer.end();
	}
}
