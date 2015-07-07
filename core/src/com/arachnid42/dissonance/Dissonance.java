package com.arachnid42.dissonance;

import com.arachnid42.dissonance.listeners.InGameTouchListener;
import com.arachnid42.dissonance.logic.DissonanceLogic;
import com.arachnid42.dissonance.logic.parts.field.GameField;
import com.arachnid42.dissonance.logic.parts.field.ShapeBasket;
import com.arachnid42.dissonance.logic.parts.interfaces.ShapeBasketDC;
import com.arachnid42.dissonance.logic.parts.shape.Shape;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.arachnid42.dissonance.opengl.render.DissonanceFontRenderer;
import com.arachnid42.dissonance.opengl.render.game.DissonanceLogicRenderer;
import com.arachnid42.dissonance.opengl.render.game.DissonanceShapeRenderer;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.awt.Font;
import java.lang.reflect.Field;

public class Dissonance extends ApplicationAdapter {
	OrthographicCamera camera2d = null;
	SpriteBatch batch = null;
	static DissonanceShapeRenderer shapeRenderer = null;
	static DissonanceLogic dissonanceLogic = null;
	static ShapeRenderer renderer = null;
	float width = 0;
	float height = 0;
	Vector3 vector3 = null;
	Shape shape = null;
	Shape star = null;
	Texture texture = null;
	Texture settings = null;
	Texture play = null;
	Texture score = null;
	Texture exit = null;
	static DissonanceLogicRenderer gameFieldRenderer = null;
	static DissonanceFontRenderer fontRenderer = null;
	@Override
	public void create (){
		fontRenderer = new DissonanceFontRenderer();
		renderer = new ShapeRenderer();
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		camera2d = new OrthographicCamera();
		camera2d.setToOrtho(false, width, height);
		vector3 = new Vector3(-width / 2, -height / 2, 0);
		camera2d.project(vector3);
		camera2d.update();
		Gdx.gl.glClearColor(1, 1, 1, 1);
		renderer.setProjectionMatrix(camera2d.combined);
		Gdx.graphics.setVSync(true);
		if(gameFieldRenderer!=null)
			return;
		shapeRenderer = new DissonanceShapeRenderer(500);
		shapeRenderer.setColorPalette(ColorPalette.createColorPalette());
		GameField gameField = new GameField(height,0,0,width,0.17f,0.01f,5);
		DissonanceLogic logic = new DissonanceLogic();
		logic.setGameField(gameField);
		gameFieldRenderer = new DissonanceLogicRenderer(logic,ColorPalette.createColorPalette());
		InGameTouchListener inGameTouchListener = new InGameTouchListener();
		inGameTouchListener.setShapeBasket(gameField.getShapeBasket());
		inGameTouchListener.setGameStageData(logic.getGameStageData());
		inGameTouchListener.setDissonanceLogic(logic);
		inGameTouchListener.setGameField(gameField);
		Gdx.input.setInputProcessor(inGameTouchListener);
		if(dissonanceLogic==null)
			dissonanceLogic = logic;
		logic.reset();
		shape = new Shape();
		shape.setType(Shape.TRIANGLE);
		star = new Shape();
		star.setType(Shape.PENTAGRAM);
		play = new Texture(Gdx.files.internal("textures/play.png"),true);
		settings = new Texture(Gdx.files.internal("textures/settings.png"),true);
		score = new Texture(Gdx.files.internal("textures/score.png"),true);
		exit = new Texture(Gdx.files.internal("textures/exit.png"),true);
		play.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
		settings.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
		exit.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
		score.setFilter(Texture.TextureFilter.MipMap, Texture.TextureFilter.Nearest);
		batch = new SpriteBatch();
	}
	public void renderGameLogic(){
		gameFieldRenderer.render(camera2d.combined);
		if(Gdx.graphics.getDeltaTime()!=0&&Math.abs(dissonanceLogic.getMPU()-(int)(Gdx.graphics.getDeltaTime()*1000))>=4)
			dissonanceLogic.setMPU((int)(Gdx.graphics.getDeltaTime()*1000));
		dissonanceLogic.update();
	}
	private void drawCrossAt(float x,float y,float rad,float width){
		renderer.setColor(Color.WHITE);
		rad = (float)(2*rad/Math.sqrt(2))/2;
		renderer.rectLine(x - rad, y - rad, x + rad, y + rad, width);
		renderer.rectLine(x + rad, y - rad, x - rad, y + rad, width);
	}
	private void drawMechAt(float x,float y,float rad){
		renderer.setColor(Color.WHITE);
		rad = (float)(2*rad/Math.sqrt(2))/2;
		renderer.rectLine(x-rad,y-rad,x+rad,y+rad,20);
		renderer.rectLine(x+rad,y-rad,x-rad,y+rad,20);
		rad = rad*(float)Math.sqrt(2);
		renderer.rectLine(x-rad,y,x+rad,y,20);
		renderer.rectLine(x,y+rad,x,y-rad,20);
		rad = rad/1.2f;
		renderer.circle(x, y,rad);
		renderer.setColor(Color.BLUE);
		renderer.circle(x, y, rad / 3);
	}
	private void drawSoundIcon(float x,float y,float rad){
		renderer.setColor(Color.WHITE);
		float ky = 2.6665f;
		float kx = 0.7f;
		float xcoord = x+rad*0.75f/2*kx;
		float ycoordDown = y-rad*0.75f/2*ky;
		float ycoordUp = y+rad*0.75f/2*ky;
		renderer.arc(x + rad / 1.5f, y, rad / 4, 0, (float) Math.PI);
		//renderer.setColor(Color.RED);
		//renderer.arc(x + rad / 1.5f - rad / 8, y, rad / 4, 0, (float) Math.PI);
		renderer.setColor(Color.WHITE);
		renderer.rect(x - rad * 0.75f, y - rad * 0.75f / 2, rad * 0.75f, rad * 0.75f);
		renderer.triangle(x - rad * 0.75f, y,
				xcoord, ycoordUp,
				xcoord, ycoordDown);

		//drawCrossAt(,y,rad/4,5);
	}
	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL20.GL_COVERAGE_BUFFER_BIT_NV : 0));
		ShapeBasketDC basket = dissonanceLogic.getGameField().getShapeBasket();
		float width = this.width/3;
		float height = width;
		float margin = width/4;
		float marginTop = this.height - ((this.height-(height*2+margin))/2+height);
		float marginLeft = (this.width-(width*2+margin))/2;
		renderer.setProjectionMatrix(camera2d.combined);
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.GREEN);
		renderer.rect(marginLeft, marginTop, width, height);
		renderer.setColor(Color.WHITE);
		renderer.setColor(Color.BLUE);
		renderer.rect(marginLeft + margin + width, marginTop, width, height);
		renderer.setColor(Color.WHITE);
		//drawMechAt(marginLeft + margin + width + width / 2, marginTop + height / 2, (width - margin * 2) / 2);
		renderer.setColor(Color.RED);
		renderer.rect(marginLeft + margin + width, marginTop - margin - height, width, height);
		//drawSoundIcon(marginLeft + margin + width + width / 2, marginTop - margin - height + height / 2, (width - margin * 2) / 2);
		renderer.setColor(Color.YELLOW);
		renderer.rect(marginLeft, marginTop - margin - height, width, height);
		renderer.end();
		shape.setRadius((width - margin * 2) / 2);
		shape.setY(marginTop + height / 2);
		shape.setX(marginLeft + width / 2);
		shape.rotate(-(float) Math.PI / 2);
		star.setRadius((width - margin * 2) / 2);
		star.setY(marginTop + height / 2 - height - margin);
		star.setX(marginLeft + width / 2);
		batch.setProjectionMatrix(camera2d.combined);
		batch.begin();
		batch.setColor(Color.WHITE);
		batch.draw(play, marginLeft + margin, marginTop + margin, width - margin * 2, height - margin * 2);
		batch.draw(score,marginLeft+margin,marginTop-margin*2-height/2,width-margin*2,height-margin*2);
		batch.draw(exit,marginLeft+margin*2+width,marginTop-margin*2-height/2,width-margin*2,height-margin*2);
		batch.draw(settings,marginLeft+margin*2+width,marginTop+margin,width-margin*2,height-margin*2);
		batch.end();
		//System.out.println("SHAPES ON SCREEN:" + dissonanceLogic.getGameField().getShapesOnFieldCount());

	}
	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}
}
