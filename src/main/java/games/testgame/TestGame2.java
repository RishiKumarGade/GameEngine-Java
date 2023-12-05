package games.testgame;

import engine.graphics.*;
import engine.io.Input;
import engine.io.ModelLoader;
import engine.io.Window;
import engine.maths.Vector2f;
import engine.maths.Vector3f;
import engine.objects.Camera;
import engine.objects.GameObject;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.Sys;

public class TestGame2 implements Runnable {
    public Thread game;
    public int nofupdates,nofrenders;
    public Window window;
    public Renderer renderer;
    public Shader colorShader;
    public final int WIDTH = 1280, HEIGHT = 760;

    private static final float X = 0.525731112119133606f;
    private static final float Z = 0.850650808352039932f;
    private static final float N = 0f;

    public Mesh  mesh = new Mesh(new Vertex[]{
            new Vertex(new Vector3f(-X,N,Z) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(X,Z,N) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(-X,N,-Z) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(X,N,-Z) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(N,X,Z) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(N,Z,-X) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(N,-Z,N) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(-Z,X,N) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(0,0,1) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(0,0,-1) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(-Z,X,N) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(Z,X,N) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(-Z,-X,N) ,new Vector3f(0,0,0)),
            new Vertex(new Vector3f(Z,-X,N) ,new Vector3f(0,0,0)),
    },new int[]{
            0, 4, 1, 0, 9, 4, 9, 5, 4, 4, 5, 8, 4, 8, 1,
            8, 10, 1, 8, 3, 10, 5, 3, 8, 5, 2, 3, 2, 7, 3,
            7, 10, 3, 7, 6, 10, 7, 11, 6, 11, 0, 6, 0, 1, 6,
            6, 1, 10, 9, 0, 11, 9, 11, 2, 9, 2, 5, 7, 2, 11
    });


    public GameObject[] objects = new GameObject[1];

    public GameObject object = new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);

    public Camera camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }
    public void init() {
        window = new Window(WIDTH, HEIGHT, "Game");
        colorShader = new Shader("resources/shaders/mainVertex.glsl", "resources/shaders/colorFragment.glsl");
        renderer = new Renderer(window);
        window.setBackgroundColor(0.52f , 0.80f, 1.0f);
        window.create();
        mesh.create();
        colorShader.create();
        for (int i = 0; i < objects.length; i++) {
            int x = i%100;
            int z = i/100;
            if(i==0)
                objects[i] = new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);
            else
                objects[i] = new GameObject(new Vector3f((float) (x), 0, (float) (-z)), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);
        }
    }

    public void run() {
        init();
        while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
            update();
            render();
            if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullScreen(!window.isFullScreen());
            if (Input.isButtonDown(GLFW.GLFW_MOUSE_BUTTON_LEFT)) window.mouseState(true);
        }
        close();
    }
    private void update() {
        window.update();
        camera.update();
    }
    private void render() {
        for (GameObject gameObject : objects) {
//            renderer.renderMeshwithColor(objects[i], camera, colorShader);
            gameObject.render(camera, colorShader, window);
        }
        window.swapBuffers();

    }
    private void close() {
        window.destroy();
        mesh.destroy();
        colorShader.destroy();
    }
}
