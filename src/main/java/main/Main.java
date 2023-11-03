package main;

import engine.io.ModelLoader;
import games.testgame.TestGame;
import org.lwjgl.glfw.GLFW;

import engine.graphics.Mesh;
import engine.graphics.Renderer;
import engine.graphics.Shader;
import engine.io.Input;
import engine.io.Window;
import engine.maths.Vector3f;
import engine.objects.Camera;
import engine.objects.GameObject;
import renderers.TerrainRenderer;

public class Main implements Runnable {
    public Thread game;
    public Window window;
    public Renderer renderer;
    public Shader shader;
    public final int WIDTH = 1280, HEIGHT = 760;

    public Mesh mesh = ModelLoader.loadModel("resources/models/dragon.obj","resources/textures/minecraft.png");

    public GameObject[] objects = new GameObject[10];

    public GameObject object = new GameObject(new Vector3f(0, 0, 0), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);

    public Camera camera = new Camera(new Vector3f(0, 0, 1), new Vector3f(0, 0, 0));

    public void start() {
        game = new Thread(this, "game");
        game.start();
    }

    public void init() {
        window = new Window(WIDTH, HEIGHT, "Game");
        shader = new Shader("resources/shaders/mainVertex.glsl", "resources/shaders/mainFragment.glsl");
        renderer = new Renderer(window, shader);
        window.setBackgroundColor(0.52f , 0.80f, 1.0f);
        window.create();
        mesh.create();
        shader.create();

        objects[0] = object;
        for (int i = 0; i < objects.length; i++) {
            objects[i] = new GameObject(new Vector3f((float) (Math.random() * 50 - 25), (float) (Math.random() * 50 - 25), (float) (Math.random() * 50 - 25)), new Vector3f(0, 0, 0), new Vector3f(1, 1, 1), mesh);
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
        camera.update(object);
    }

    private void render() {
        for (int i = 0; i < objects.length; i++) {
            renderer.renderMesh(objects[i], camera);
        }
//        renderer.renderMesh(object, camera);
        window.swapBuffers();
    }

    private void close() {
        window.destroy();
        mesh.destroy();
        shader.destroy();
    }

    public static void main(String[] args) {
        new TestGame().start();
    }
}