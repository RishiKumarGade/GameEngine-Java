package engine.graphics;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static org.lwjgl.opengl.GL11.GL_LINEAR;
import static org.lwjgl.opengl.GL11.glDeleteTextures;

public class Material {

    private Texture texture;
    private String path;
    private float width,height;
    private int textureId;
    public Material(String path){
        this.path = path;
    }

    public float getWidth() {
        return width;
    }


    public float getHeight() {
        return height;
    }


    public int getTextureId() {
        return textureId;
    }


    public void create(){
        try {
            texture =TextureLoader.getTexture(path.split("[.]")[0], ResourceLoader.getResourceAsStream(path),GL_LINEAR);
            width=texture.getWidth();
            height=texture.getHeight();
            textureId=texture.getTextureID();
        }catch (IOException e ){
            System.err.println("cannot load texture"+path);
        }
    }
    public void destroy(){
        glDeleteTextures(textureId);
    }
}
