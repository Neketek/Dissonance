package com.arachnid42.dissonance.opengl.render.game;

import com.arachnid42.dissonance.logic.parts.field.ShapeBasket;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Matrix4;

import java.util.ArrayList;

/**
 * Created by neketek on 03.07.15.
 */
public class DissonanceShapeBasketRenderer{
    private static final float HALF_SCREEN_WIDTH = Gdx.graphics.getWidth();
    private static final float ANIMATION_SPEED = HALF_SCREEN_WIDTH;
    private static final int ANIM_SEGMENTS = 20;
    private ShapeBasket basket = null;
    private ShapeRenderer gdxShapeRenderer = null;
    private DissonanceShapeRenderer dissonanceShapeRenderer = null;
    private ArrayList<PlateChangeAnimation> playingAnimations = null;
    private ArrayList<PlateChangeAnimation> animationObjectCache = null;
    private int savedActivePlateIndex = -1;
    private class PlateChangeAnimation{
        private float x,y,rad;
        private int color,activePlateIndex = 0;
        public boolean update(float update){
            if(rad>HALF_SCREEN_WIDTH){
                return false;
            }
            rad+=HALF_SCREEN_WIDTH*6*update;
            return true;
        }
        public void setCenterLocation(float x,float y){
            this.x=x;
            this.y=y;
        }
        public void setColor(int colorPaletteId){
            this.color = colorPaletteId;
        }
        public void setActivePlateIndex(int activePlateIndex){
            this.activePlateIndex = activePlateIndex;
        }
        public void render(){
            setGDXShapeRendererColor(color);
            gdxShapeRenderer.circle(x,y,rad,ANIM_SEGMENTS);
        }
        public void reset(){
            activePlateIndex = 0;
            color = 0;
            rad = 0;
            x = 0;
            y = 0;
        }
    }
    private void tryLaunchNewAnimation(){
        if(savedActivePlateIndex!=basket.getActivePlateIndex())
            setPlateChangeAnimationForPlate(basket.getActivePlateIndex());
    }
    private void setPlateChangeAnimationForPlate(int plateIndex){
        PlateChangeAnimation plateChangeAnimation = null;
        if(animationObjectCache.size()>0)
            plateChangeAnimation = animationObjectCache.remove(0);
        else
            plateChangeAnimation = new PlateChangeAnimation();
        plateChangeAnimation.reset();
        plateChangeAnimation.setColor(basket.getPlateShape(plateIndex).getColor());
        plateChangeAnimation.setActivePlateIndex(plateIndex);
        plateChangeAnimation.setCenterLocation(
                basket.getLeft()+basket.getPlateWidth()*plateIndex+basket.getPlateWidth()/2,
                basket.getIndicatorBottom());
        this.playingAnimations.add(plateChangeAnimation);
    }
    private void updatePlateChangeAnimations(){
        float delta = Gdx.graphics.getDeltaTime();
        for(int i = 0;i<playingAnimations.size();i++){
            if(!playingAnimations.get(i).update(delta)){
                savedActivePlateIndex = playingAnimations.get(i).activePlateIndex;
                animationObjectCache.add(playingAnimations.remove(i));
                i--;
            }
        }
    }
    private void renderAnimations(){
        for(PlateChangeAnimation animation:playingAnimations){
            animation.render();
        }
    }
    private void setGDXShapeRendererColor(int index){
        float color[] = dissonanceShapeRenderer.getColorPalette().getColorArray(index);
        gdxShapeRenderer.setColor(
                color[ColorPalette.RED], color[ColorPalette.GREEN],
                color[ColorPalette.BLUE], color[ColorPalette.ALPHA]);
    }
    private void drawBasketPlate(int index){
        setGDXShapeRendererColor(basket.getPlateShape(index).getColor());
        gdxShapeRenderer.rect(basket.getLeft() + basket.getPlateWidth() * index, basket.getBottom(), basket.getPlateWidth(), basket.getPlateHeight());
    }
    private void drawShapeBasketBounds(){
        setGDXShapeRendererColor(ColorPalette.FOREGROUND);
        gdxShapeRenderer.set(ShapeRenderer.ShapeType.Line);
        for(int i = 0;i<basket.getPlateCount();i++)
            drawBasketPlateBound(i);
        drawIndicatorBound();
    }
    private void drawIndicatorBound(){
        float cache = 0;
        gdxShapeRenderer.line(
                basket.getLeft(),basket.getIndicatorBottom(),
                basket.getLeft(),basket.getIndicatorTop());
        gdxShapeRenderer.line(
                basket.getLeft(),basket.getIndicatorTop(),
                basket.getRight(),basket.getIndicatorTop());
        gdxShapeRenderer.line(
                basket.getRight(),basket.getIndicatorTop(),
                basket.getRight(),basket.getIndicatorBottom());
        cache = basket.getLeft()+(basket.getActivePlateIndex()+1)*basket.getPlateWidth();
        gdxShapeRenderer.line(
                basket.getRight(),basket.getIndicatorBottom(),
                cache,
                basket.getIndicatorBottom());
        gdxShapeRenderer.line(
                cache,basket.getIndicatorBottom(),
                cache,basket.getBottom());
        gdxShapeRenderer.line(
                cache,basket.getBottom(),
                (cache-=basket.getPlateWidth()),basket.getBottom());
        gdxShapeRenderer.line(
                cache,basket.getBottom(),
                cache,basket.getIndicatorBottom());
        gdxShapeRenderer.line(
                cache,basket.getIndicatorBottom(),
                basket.getLeft(),basket.getIndicatorBottom());
    }
    private void drawBasketPlateBound(int index){
        if(basket.getActivePlateIndex()==index)
            return;
        gdxShapeRenderer.rect(
                basket.getLeft()+basket.getPlateWidth()*index,basket.getBottom(),
                basket.getPlateWidth(),basket.getPlateHeight());
    }
    private void drawBasketPlates(){
        for(int i = 0;i<basket.getPlateCount();i++){
            drawBasketPlate(i);
        }
    }
    private void drawShapePlatesShapes(Matrix4 projection){
        dissonanceShapeRenderer.begin(projection);
        for(int i = 0;i<basket.getPlateCount();i++){
            dissonanceShapeRenderer.render(basket.getPlateShape(i),ColorPalette.BACKGROUND);
        }
        dissonanceShapeRenderer.end();
    }
    private void drawBasketIndicator(){
        setGDXShapeRendererColor(basket.getPlateShape(savedActivePlateIndex).getColor());
        gdxShapeRenderer.rect(basket.getLeft(), basket.getIndicatorBottom(), basket.getWidth(), basket.getIndicatorHeight());
    }
    private void drawBasketGDXPart(Matrix4 projection){
        tryLaunchNewAnimation();
        gdxShapeRenderer.setProjectionMatrix(projection);
        gdxShapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        drawBasketIndicator();
        renderAnimations();
        drawBasketPlates();
        drawShapeBasketBounds();
        updatePlateChangeAnimations();
        gdxShapeRenderer.end();
    }
    public DissonanceShapeBasketRenderer(ShapeBasket shapeBasket,
                                         DissonanceShapeRenderer dissonanceShapeRenderer,
                                         ShapeRenderer gdxShapeRenderer){
        setShapeBasket(shapeBasket);
        setDissonanceShapeRenderer(dissonanceShapeRenderer);
        setGDXShapeRenderer(gdxShapeRenderer);
        this.playingAnimations = new ArrayList<PlateChangeAnimation>();
        this.animationObjectCache = new ArrayList<PlateChangeAnimation>();
    }
    public void setDissonanceShapeRenderer(DissonanceShapeRenderer shapeRenderer){
        if(shapeRenderer==null)
            throw new IllegalArgumentException();
        this.dissonanceShapeRenderer=shapeRenderer;
    }
    public void setShapeBasket(ShapeBasket basket){
        if(basket==null)
            throw new IllegalArgumentException();
        this.basket = basket;
        this.savedActivePlateIndex = basket.getActivePlateIndex();
    }
    public void setGDXShapeRenderer(ShapeRenderer gdxShapeRenderer){
        if(gdxShapeRenderer==null)
            throw new IllegalArgumentException();
        this.gdxShapeRenderer = gdxShapeRenderer;
    }
    public void render(Matrix4 projection){
        drawBasketGDXPart(projection);
        drawShapePlatesShapes(projection);
    }
    public void stopAllAnimations(){
        savedActivePlateIndex = basket.getActivePlateIndex();
        while(!playingAnimations.isEmpty()){
            animationObjectCache.add(playingAnimations.remove(0));
        }
    }
}
