package engine.objects;

import engine.graphics.Mesh;
import engine.graphics.Shader;
import engine.io.Window;
import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

public class GameObject {
    private Vector3f position,rotation,scale;
    protected Mesh mesh;

    public GameObject(Vector3f position, Vector3f rotation, Vector3f scale, Mesh mesh) {
        mesh.create();
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
        this.mesh = mesh;
    }
    protected GameObject(Vector3f position, Vector3f rotation, Vector3f scale){
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }


    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public Mesh getMesh() {
        return mesh;
    }
    public void update(){
    }
    public void render(Camera camera, Shader shader, Window window){
        GL30.glBindVertexArray(this.getMesh().getVao());
        GL30.glEnableVertexAttribArray(0);
        GL30.glEnableVertexAttribArray(1);
        GL30.glEnableVertexAttribArray(2);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, this.getMesh().getIbo());
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, this.getMesh().getCbo());
        shader.bind();
        shader.setUniform("model", Matrix4f.transform(this.getPosition(), this.getRotation(), this.getScale()));
        shader.setUniform("view", Matrix4f.view(camera.getPosition(), camera.getRotation()));
        shader.setUniform("projection", window.getProjectionMatrix());
//        GL11.glDrawArrays(GL11.GL_TRIANGLES,0,this.getMesh().getVertices().length);
        GL11.glDrawElements(GL11.GL_TRIANGLES, this.getMesh().getIndices().length, GL11.GL_UNSIGNED_INT, 0);
        shader.unbind();
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, 0);
        GL30.glDisableVertexAttribArray(0);
        GL30.glDisableVertexAttribArray(1);
        GL30.glDisableVertexAttribArray(2);
        GL30.glBindVertexArray(0);
    }

}
