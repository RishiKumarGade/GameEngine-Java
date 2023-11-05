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

public class TestGame2 implements Runnable {
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader colorShader;
    public final int WIDTH = 1280, HEIGHT = 760;

    public Mesh  mesh = new Mesh(new Vertex[] {
            //Back face
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector3f(1.0f, 1.0f,0.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(1.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector3f(1.0f, 0.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector3f(0.0f, 1.0f,1.0f)),

            //Front face
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector3f(1.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector3f(0.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector3f(1.0f, 0.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector3f(1.0f, 1.0f,0.0f)),

            //Right face
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector3f(1.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector3f(1.0f, 0.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector3f(1.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector3f(0.0f, 1.0f,1.0f)),

            //Left face
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector3f(1.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(1.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector3f(1.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector3f(1.0f, 1.0f,1.0f)),

            //Top face
            new Vertex(new Vector3f(-0.5f,  0.5f,  0.5f), new Vector3f(1.0f, 1.0f,0.0f)),
            new Vertex(new Vector3f(-0.5f,  0.5f, -0.5f), new Vector3f(0.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f, -0.5f), new Vector3f(1.0f, 0.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f,  0.5f,  0.5f), new Vector3f(1.0f, 1.0f,0.0f)),

            //Bottom face
            new Vertex(new Vector3f(-0.5f, -0.5f,  0.5f), new Vector3f(1.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f(-0.5f, -0.5f, -0.5f), new Vector3f(1.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f, -0.5f), new Vector3f(0.0f, 1.0f,1.0f)),
            new Vertex(new Vector3f( 0.5f, -0.5f,  0.5f), new Vector3f(1.0f, 0.0f,1.0f)),
    }, new int[] {
            //Back face
            0, 1, 3,
            3, 1, 2,

            //Front face
            4, 5, 7,
            7, 5, 6,

            //Right face
            8, 9, 11,
            11, 9, 10,

            //Left face
            12, 13, 15,
            15, 13, 14,

            //Top face
            16, 17, 19,
            19, 17, 18,

            //Bottom face
            20, 21, 23,
            23, 21, 22
    });


    public GameObject[] objects = new GameObject[10000];

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
