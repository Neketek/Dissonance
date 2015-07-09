package com.arachnid42.dissonance.menu.layout;

/**
 * Created by neketek on 08.07.15.
 */
public class DissonanceVirtualGrid{
    public static final int GRID_COUNT = 5;
    public static final int COORDINATE_COUNT = 2;
    public static final int RECT = 0;
    public static final int CENTER = 1;
    public static final int X_COORDINATE = 0;
    public static final int Y_COORDINATE = 1;
    public static final int MAIN_MENU = 0;
    public static final int SETTINGS_MENU = 1;
    public static final int PAUSE_MENU = 2;
    public static final int SCORE_MENU = 3;
    public static final int GAME = 4;
    private static final float MARGIN_COEFFICIENT = 0.0f;
    private float screenWidth = 0;
    private float screenHeight = 0;
    private float gridRectCoordinates[][] = null;
    private float gridCenterCoordinates[][] = null;
    private void writeRectCoordinatesTo(float[] arr,int cellX,int cellY,float margin){
        arr[X_COORDINATE] = (margin+screenWidth)*cellX;
        arr[Y_COORDINATE] = (margin+screenHeight)*cellY;
    }
    private void writeCenterCoordinatesTo(float[] arr,int cellId){
        arr[X_COORDINATE] = gridRectCoordinates[cellId][X_COORDINATE]+screenWidth/2;
        arr[Y_COORDINATE] = gridRectCoordinates[cellId][Y_COORDINATE]+screenHeight/2;
    }
    private void createArrays(){
        gridCenterCoordinates = new float[GRID_COUNT][COORDINATE_COUNT];
        gridRectCoordinates = new float[GRID_COUNT][COORDINATE_COUNT];
    }
    private void calculateGridCoordinates(){
        float margin = screenWidth*MARGIN_COEFFICIENT;
        writeRectCoordinatesTo(gridRectCoordinates[GAME],0,0,margin);
        writeRectCoordinatesTo(gridRectCoordinates[PAUSE_MENU],1,0,margin);
        writeRectCoordinatesTo(gridRectCoordinates[MAIN_MENU],-1,0,margin);
        writeRectCoordinatesTo(gridRectCoordinates[SETTINGS_MENU],-1,1,margin);
        writeRectCoordinatesTo(gridRectCoordinates[SCORE_MENU],-1,-1,margin);
        for(int i = 0;i<GRID_COUNT;i++)
            writeCenterCoordinatesTo(gridCenterCoordinates[i],i);
    }
    public DissonanceVirtualGrid(float screenWidth,float screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.createArrays();
        this.calculateGridCoordinates();
    }
    public float getRectX(int gridId){
        return gridRectCoordinates[gridId][X_COORDINATE];
    }
    public float getRectY(int gridId){
        return gridRectCoordinates[gridId][Y_COORDINATE];
    }
    public float getCenterX(int gridId){
        return gridCenterCoordinates[gridId][X_COORDINATE];
    }
    public float getCenterY(int gridId){
        return gridCenterCoordinates[gridId][Y_COORDINATE];
    }
}
