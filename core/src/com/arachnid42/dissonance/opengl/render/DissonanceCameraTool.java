package com.arachnid42.dissonance.opengl.render;

import com.arachnid42.dissonance.Dissonance;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceCameraTool{
    private OrthographicCamera camera2d = null;
    private float x = 0;
    private float y = 0;
    private float moveDistanceX = 0;
    private float moveDistanceY = 0;
    private float moveDistanceXStop = 0;
    private float moveDistanceYStop = 0;
    private float moveEndX = 0;
    private float moveEndY = 0;
    private boolean moveTaskEnabled = false;
    private void createCamera(float screenWidth,float screenHeight){
        Vector3 worldCoordinates = new Vector3(-screenWidth/2,-screenHeight/2,0);
        camera2d = new OrthographicCamera();
        camera2d.setToOrtho(false,screenWidth,screenHeight);
        camera2d.project(worldCoordinates);
        camera2d.update();
    }
    public DissonanceCameraTool(float screenWidth,float screenHeight){
        this.createCamera(screenWidth, screenHeight);
    }
    public void setMoveTask(float moveEndX,float moveEndY){
        this.moveDistanceX = moveEndX - x;
        this.moveDistanceY = moveEndY - y;
        this.moveDistanceXStop = Math.abs(moveDistanceX);
        this.moveDistanceYStop = Math.abs(moveDistanceY);
        this.moveTaskEnabled = true;
        this.moveEndX = moveEndX;
        this.moveEndY = moveEndY;
    }
    public boolean updateMoveTask(float secondsForFrame,float estimatedArrivalTime){
        if(!moveTaskEnabled)
            return false;
        float moveX = moveDistanceX*secondsForFrame/estimatedArrivalTime;
        float moveY = moveDistanceY*secondsForFrame/estimatedArrivalTime;
        moveDistanceXStop-=Math.abs(moveX);
        moveDistanceYStop-=Math.abs(moveY);
        if(moveDistanceYStop<=0&&moveDistanceXStop<=0){
            camera2d.translate(moveEndX-x,moveEndY-y);
            camera2d.update();
            x = moveEndX;
            y = moveEndY;
            moveDistanceX = 0;
            moveDistanceY = 0;
            moveDistanceXStop = 0;
            moveDistanceX = 0;
            moveTaskEnabled = false;
            return false;
        }
        x+=moveX;
        y+=moveY;
        camera2d.translate(moveX,moveY);
        camera2d.update();
        return true;
    }
    public void setLocation(float x,float y){
        if(this.x == x&&this.y == y)
            return;
       // System.out.println("CAMERA LOC:"+x+","+y);
        this.camera2d.translate(x-this.x,y-this.y);
        this.x = x;
        this.y = y;
        this.camera2d.update();
    }
    public Matrix4 getProjectionMatrix(){
        return camera2d.combined;
    }
    public boolean isMoveTaskEnabled() {
        return moveTaskEnabled;
    }
}
