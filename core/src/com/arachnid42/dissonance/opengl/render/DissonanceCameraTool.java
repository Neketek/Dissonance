package com.arachnid42.dissonance.opengl.render;

import com.arachnid42.dissonance.Dissonance;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceCameraTool{
    private static final float WAIT_BEFORE_MOVE_LIMIT = 0.15f;
    private static final double PIX2 = Math.PI-Math.PI/10;
    private float waitBeforeMove = 0.0f; // TODO:Wait before move
    private boolean waitBeforeMoveEnabled = true;
    private OrthographicCamera camera2d = null;
    private Vector2 targetCoordinates = null;
    private Vector2 currentSpeed = null;
    private Vector3 worldCoordinates = null;
    private double previousDistance = 0;
    private double fullDistance = 0;
    private boolean moveTaskEnabled = false;
    private double getDistance(){
        float sqrY = targetCoordinates.y - getY();
        float sqrX = targetCoordinates.x - getX();
        sqrX*=sqrX;
        sqrY*=sqrY;
        return Math.sqrt(sqrX+sqrY);
    }
    private void calculateSpeedVector(){
        currentSpeed.y = targetCoordinates.y-getY();
        currentSpeed.x = targetCoordinates.x-getX();
    }
    private void createVectors(){
        this.currentSpeed = new Vector2();
        this.targetCoordinates = new Vector2();
    }
    private void createCamera(float screenWidth,float screenHeight){
        worldCoordinates = new Vector3(-screenWidth/2,-screenHeight/2,0);
        camera2d = new OrthographicCamera();
        camera2d.setToOrtho(false, screenWidth, screenHeight);
        camera2d.project(worldCoordinates);
        camera2d.update();
    }

    private void setX(float x){
        camera2d.position.set(x,getY(),0);
    }
    private void setY(float y){
        camera2d.position.set(getX(), y, 0);
    }
    private float getSpeedX(){
        return this.currentSpeed.x;
    }
    private float getSpeedY(){
        return this.currentSpeed.y;
    }
    public DissonanceCameraTool(float screenWidth,float screenHeight){
        this.createCamera(screenWidth, screenHeight);
        this.createVectors();
    }
    public void setMoveTask(float moveEndX,float moveEndY){
        waitBeforeMove = 0;
        waitBeforeMoveEnabled = true;
        targetCoordinates.set(moveEndX-worldCoordinates.x,moveEndY-worldCoordinates.y);
        calculateSpeedVector();
        moveTaskEnabled = true;
        previousDistance = getDistance();
        fullDistance = previousDistance;
    }
    public boolean updateMoveTask(float secondsForFrame,float estimatedArrivalTime){
        if(!moveTaskEnabled)
            return false;
        if(waitBeforeMoveEnabled&&waitBeforeMove<WAIT_BEFORE_MOVE_LIMIT) {
            waitBeforeMove+=secondsForFrame;
            return true;
        }
        float speedScale = 0;
        speedScale = 1 - (float)(previousDistance/fullDistance)+0.01f;
        speedScale = (float)Math.sin(speedScale*PIX2);
        float moveX = getSpeedX()*secondsForFrame/estimatedArrivalTime*speedScale;
        float moveY = getSpeedY()*secondsForFrame/estimatedArrivalTime*speedScale;
        camera2d.position.add(moveX,moveY,0);
        double currentDistance = getDistance();
        if(currentDistance<previousDistance){
            previousDistance = currentDistance;
            camera2d.update();
            return true;
        }else {
            camera2d.position.set(targetCoordinates, 0);
            camera2d.update();
            moveTaskEnabled = false;
            return false;
        }
    }
    public void setLocation(float x,float y){
        if(this.getX() == x&&this.getY() == y)
            return;
        camera2d.position.set(x-worldCoordinates.x,y-worldCoordinates.y,0);
        camera2d.update();
    }
    public void moveCamera(float x,float y){
        camera2d.position.add(x,y,0);
        camera2d.update();
    }
    public float getX(){
        return this.camera2d.position.x;
    }
    public float getY(){
        return this.camera2d.position.y;
    }
    public Matrix4 getProjectionMatrix(){
        return camera2d.combined;
    }
    public boolean isMoveTaskEnabled() {
        return moveTaskEnabled;
    }

    public boolean isWaitBeforeMoveEnabled() {
        return waitBeforeMoveEnabled;
    }

    public void setWaitBeforeMoveEnabled(boolean waitBeforeMoveEnabled) {
        this.waitBeforeMoveEnabled = waitBeforeMoveEnabled;
    }
}
