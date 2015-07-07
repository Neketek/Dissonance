package com.arachnid42.dissonance.opengl;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by neketek on 11.06.15.
 */
public class SimpleShapeShader extends ShaderProgram{
   public static final String POSITION_ATTRIBUTE = "a_Position";
    private static final String COLOR_UNIFORM = "u_Color";
    private static final String MATRIX_UNIFORM = "u_Matrix";
    private static final int COLOR_COMPONENT_COUNT = 4;
    private Matrix4 projectionMatrix = null;
    private int matrixLocation = 0;
    private int colorLocation = 0;
    public static final String VERTEX_SHADER_CODE = "" +
            "uniform mat4 u_Matrix;\n" +
            "attribute vec2 "+POSITION_ATTRIBUTE+";\n" +
            "void main(){\n" +
            "gl_Position = u_Matrix*vec4(a_Position.xy, 0.0, 1.0);\n" +
            "}";
    public static final String FRAGMENT_SHADER_CODE = "precision mediump float;\n" +
            "uniform vec4 u_Color;\n" +
            "void main(){" +
            "gl_FragColor = u_Color;" +
            "}";
    public SimpleShapeShader(){
        super(VERTEX_SHADER_CODE,FRAGMENT_SHADER_CODE);
        matrixLocation = this.getUniformLocation(MATRIX_UNIFORM);
        colorLocation = this.getUniformLocation(COLOR_UNIFORM);
        //System.out.println("SHADER LOG:" + this.getLog() + " COLOR:" + colorLocation + " MATRIX:" + matrixLocation);
    }
    public void setProjectonMatrix(Matrix4 matrix){
        projectionMatrix = matrix;
        this.setUniformMatrix(matrixLocation,matrix);
    }
    public void setColor4f(float colorComponentArray[]){
        this.setUniform4fv(colorLocation,colorComponentArray,0,4);
    }
}
