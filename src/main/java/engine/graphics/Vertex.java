package engine.graphics;

import engine.maths.Vector2f;
import engine.maths.Vector3f;

public class Vertex {
    private Vector3f position,normal,color;
    private Vector2f textureCoord;

    public Vector3f getNormal() {
        return normal;
    }

    public Vertex(Vector3f position, Vector3f normal, Vector2f textureCoord){
        this.position=position;
        this.normal=normal;
        this.textureCoord = textureCoord;
    }
    public Vertex(Vector3f position, Vector2f textureCoord){
        this.position=position;
        this.textureCoord = textureCoord;
    }

    public Vertex(Vector3f position, Vector3f color ){
        this.position=position;
        this.color = color;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector2f getTextureCoord() {
        return textureCoord;
    }
}
