package com.balakin.dissonance;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.balakin.dissonance.opengl.render.ColorPalette;
import com.balakin.dissonance.opengl.render.DissonanceMainRenderer;
import com.balakin.dissonance.utils.DissonanceConfig;
import com.balakin.dissonance.utils.DissonanceLogicUpdater;
import com.balakin.dissonance.utils.DissonanceResources;
import com.balakin.dissonance.utils.androidControllers.DissonanceAdsController;
import com.balakin.dissonance.utils.androidControllers.DissonancePSController;


public class Dissonance extends ApplicationAdapter {
	private boolean correctDeltaTime = false;
	DissonanceMainRenderer dissonanceMainRenderer = null;
	DissonanceLogicUpdater dissonanceLogicUpdater = null;
	DissonanceAdsController dissonanceAdsController = null;
	DissonancePSController psController = null;
	public Dissonance(DissonanceAdsController adsController,DissonancePSController psController){
		this.dissonanceAdsController = adsController;
		this.psController = psController;
	}
	@Override
	public void create (){
		DissonanceResources.initializeDissonanceResources(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), dissonanceAdsController, psController);
		DissonanceResources.getColorPalette().setColor(Gdx.gl20, ColorPalette.BACKGROUND);
		//DissonanceResources.getDissonanceScreenGrid().getMainMenu().startFadeIn();
		Gdx.input.setInputProcessor(DissonanceResources.getInMenuTouchListener());
		//Gdx.graphics.setVSync(true);
		dissonanceMainRenderer = new DissonanceMainRenderer();
		dissonanceLogicUpdater = new DissonanceLogicUpdater();
		//DissonanceResources.getDissonancePSController().synchronizeScoreWithLeaderBoard();
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

	@Override
	public void pause() {
		super.pause();
		DissonanceConfig.saveConfig();
	}
	@Override
	public void dispose(){
		DissonanceConfig.saveConfig();
		super.dispose();
	}

}
