package com.arachnid42.dissonance;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceMainRenderer;
import com.arachnid42.dissonance.utils.DissonanceAdsController;
import com.arachnid42.dissonance.utils.DissonanceLogicUpdater;
import com.arachnid42.dissonance.utils.DissonanceResources;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;


public class Dissonance extends ApplicationAdapter {
	private boolean correctDeltaTime = false;
	DissonanceMainRenderer dissonanceMainRenderer = null;
	DissonanceLogicUpdater dissonanceLogicUpdater = null;
	DissonanceAdsController dissonanceAdsController = null;
	public Dissonance(DissonanceAdsController adsController){
		this.dissonanceAdsController = adsController;
	}
	@Override
	public void create (){
		DissonanceResources.initializeDissonanceResources(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), dissonanceAdsController);
		DissonanceResources.getColorPalette().setColor(Gdx.gl20, ColorPalette.BACKGROUND);
		//DissonanceResources.getDissonanceScreenGrid().getMainMenu().startFadeIn();
		Gdx.input.setInputProcessor(DissonanceResources.getInMenuTouchListener());
		Gdx.graphics.setVSync(true);
		dissonanceMainRenderer = new DissonanceMainRenderer();
		dissonanceLogicUpdater = new DissonanceLogicUpdater();
	}
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		if(!correctDeltaTime)
			if(Gdx.graphics.getFrameId()>15)
				correctDeltaTime = true;
		else
			return;
		dissonanceMainRenderer.render();
		dissonanceLogicUpdater.update(Gdx.graphics.getDeltaTime());
	}
}
