package com.arachnid42.dissonance.opengl.render.game;

import com.arachnid42.dissonance.logic.parts.shape.Shape;
import com.arachnid42.dissonance.opengl.ShapeTriangulationTool;
import com.arachnid42.dissonance.opengl.SimpleShapeShader;
import com.arachnid42.dissonance.opengl.render.ColorPalette;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.math.Matrix4;

/**
 * Created by neketek on 10.06.15.
 */
public class DissonanceShapeRenderer{
    private static final int FLOATS_PER_POSITION = 2;
    private static final String A_POSITION = "a_Position";
    private ShapeTriangulationTool shapeTriangulationTool = null;
    private SimpleShapeShader shaderProgram = null;
    private Mesh shapeMesh = null;
    private ColorPalette colorPalette = null;
    private void createMeshShader(){
        this.shaderProgram = new SimpleShapeShader();
    }
    private void createMesh(int maxVertexCount){
        shapeMesh = new Mesh(Mesh.VertexDataType.VertexArray,false,
                maxVertexCount,0,
                new VertexAttribute(VertexAttributes.Usage.Position,FLOATS_PER_POSITION,A_POSITION));
    }
    public DissonanceShapeRenderer(int maxVertexCount){
        createMesh(maxVertexCount);
        createMeshShader();
        shapeTriangulationTool = new ShapeTriangulationTool(shapeMesh.getVerticesBuffer());
    }
    public void begin(Matrix4 projection){
        shaderProgram.begin();
        shaderProgram.setProjectonMatrix(projection);
    }
    public void end(){
        shaderProgram.end();
    }
    public void render(Shape shape){
       render(shape,shape.getColor());
    }
    public void render(Shape shape,int colorPaletteIndex){
        shapeTriangulationTool.triangulate(shape);
        shaderProgram.setColor4f(colorPalette.getColorArray(colorPaletteIndex));
        shapeMesh.render(shaderProgram, GL20.GL_TRIANGLES, 0, shapeTriangulationTool.getVertexCount());
    }
    public void setColorPalette(ColorPalette colorPalette){
        this.colorPalette = colorPalette;
    }
    public ColorPalette getColorPalette(){
        return this.colorPalette;
    }
}
