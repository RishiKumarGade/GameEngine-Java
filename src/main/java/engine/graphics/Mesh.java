package engine.graphics;

import org.lwjgl.system.MemoryUtil;
import org.lwjglx.Sys;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;

import static org.lwjgl.opengl.GL30.*;

public class Mesh {
    protected Vertex[] vertices;

    protected int[] indices = null;
    private int vao,pbo,ibo,cbo,tbo;
    private Material material;
    public Mesh(Vertex[] vertices,int[] indices,Material material){
        this.indices = indices;
        this.vertices = vertices;
        this.material=material;
    }
    public Mesh(Vertex[] vertices,int[] indices){
        this.indices = indices;
        this.vertices = vertices;
    }
    public Mesh(Vertex[] vertices){
        this.vertices = vertices;
    }
    public void create(){
        vao = glGenVertexArrays();
        glBindVertexArray(vao);
        FloatBuffer positionBuffer = MemoryUtil.memAllocFloat(vertices.length*3);
        float[] positionData = new float[vertices.length*3];
        for (int i = 0; i < vertices.length; i++){
            positionData[i* 3] = vertices[i].getPosition().getX();
            positionData[i* 3 + 1] = vertices[i].getPosition ().getY();
            positionData[i* 3 + 2] = vertices[i].getPosition().getZ();
        }
        positionBuffer.put(positionData).flip();
        pbo = storeData(positionBuffer,0,3);


        if(material != null) {
            material.create();
            FloatBuffer textureBuffer = MemoryUtil.memAllocFloat(vertices.length * 2);
            float[] textureData = new float[vertices.length * 2];
            for (int i = 0; i < vertices.length; i++) {
                textureData[i * 2] = vertices[i].getTextureCoord().getX();
                textureData[i * 2 + 1] = vertices[i].getTextureCoord().getY();
            }
            textureBuffer.put(textureData).flip();
            tbo = storeData(textureBuffer, 2, 2);
        }
        else{
            FloatBuffer colorBuffer = MemoryUtil.memAllocFloat(vertices.length*3);
            float[] colorData = new float[vertices.length*3];
            for (int i = 0; i < vertices.length; i++){
                colorData[i* 3 ] = vertices[i].getColor().getX();
                colorData[i* 3 +1] = vertices[i].getColor ().getY();
                colorData[i* 3+2 ] = vertices[i].getColor().getZ();
            }
            colorBuffer.put(colorData).flip();
            cbo = storeData(colorBuffer,1,3);
            }

        if(indices !=null){
            IntBuffer indicesBuffer = MemoryUtil.memAllocInt(indices.length);
            indicesBuffer.put(indices).flip();
            ibo = glGenBuffers();
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ibo);
            glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
            glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        }
    }
    public int storeData(FloatBuffer buffer,int index,int size){
        int bufferId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER,bufferId);
        glBufferData(GL_ARRAY_BUFFER,buffer,GL_STATIC_DRAW);
        glVertexAttribPointer(index,size,GL_FLOAT,false,0,0);
        glBindBuffer(GL_ARRAY_BUFFER,0);
        return bufferId;
    }

    public int getCbo() {
        return cbo;
    }

    public Vertex[] getVertices() {
        return vertices;
    }

    public int getTbo() {
        return tbo;
    }

    public void destroy(){
        glDeleteBuffers(pbo);
        glDeleteBuffers(cbo);
        glDeleteBuffers(ibo);
        glDeleteBuffers(tbo);
        glDeleteVertexArrays(vao);
        if (material !=null)
            material.destroy();
    }

    public Material getMaterial() {
        return material;
    }

    public int[] getIndices() {
        return indices;
    }

    public int getVao() {
        return vao;
    }

    public int getPbo() {
        return pbo;
    }

    public int getIbo() {
        return ibo;
    }

}
