package engine.io;

import engine.maths.Matrix4f;
import engine.maths.Vector3f;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Window {
    private int width , height;
    private String title;
    private long window;
    public int frames;
    public static long time;
    public Input input;
    private GLFWWindowSizeCallback sizeCallback;
    private boolean isResized;
    public Vector3f background = new Vector3f(1.0f,1.0f,1.0f);
    private boolean isFullScreen;
    private Matrix4f projection;
    private int[] windowPosX=new int[1] ,windowPosY=new int[1];
    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getWindow() {
        return window;
    }

    public void setWindow(long window) {
        this.window = window;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
        isResized = true;
        if(isFullScreen){
            glfwGetWindowPos(window,windowPosX,windowPosY);
            glfwSetWindowMonitor(window,glfwGetPrimaryMonitor(),0,0,width,height,0);
        }else {
            glfwSetWindowMonitor(window,0,windowPosX[0],windowPosY[0],width,height,0);
        }
    }

    public Window(int width, int height, String title){
        this.height = height;
        this.width = width;
        this.title = title;
        this.projection = Matrix4f.projection(70.0f,(float)width/(float) height,0.1f,1000.0f );
    }

    public Matrix4f getProjectionMatrix() {
        return projection;
    }

    public void mouseState(boolean lock){
        glfwSetInputMode(window,GLFW_CURSOR,lock ? GLFW_CURSOR_DISABLED:GLFW_CURSOR_NORMAL);
    }
    public void create(){
        if(!glfwInit()){
            System.err.println("Cannot initialize glfw");
            return ;
        }
        input = new Input();
        window = glfwCreateWindow(width,height,title,isFullScreen?glfwGetPrimaryMonitor():0,0);
        if(window == 0){
            System.err.println("window is not created");
        return;
        }

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        windowPosX[0] = (vidMode.width()-width)/2;
        windowPosY[0] = (vidMode.height()-height)/2;
        glfwSetWindowPos(window,windowPosX[0],windowPosY[0]);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();
        glEnable(GL_DEPTH_TEST);
        createCallbacks();
        glViewport(0,0,width,height);
        glfwShowWindow(window);
        glfwSwapInterval(1);
        time = System.currentTimeMillis();

    }
    public void update(){
        if(isResized){
            glViewport(0,0,width,height);
            isResized = false;
        }
        glClearColor(background.getX(),background.getY(),background.getZ(),1.0f);
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        glfwPollEvents();
        frames++;
        if(System.currentTimeMillis() > time+1000){
            glfwSetWindowTitle(window,title+" Fps: "+frames);
            time = System.currentTimeMillis();
            frames = 0;
        }
    }
    public void swapBuffers(){
        glfwSwapBuffers(window);
    }
    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }
    public void destroy(){
        input.destroy();
        glfwWindowShouldClose(window);
        glfwDestroyWindow(window);
        glfwTerminate();
    }
    public void setBackgroundColor(float R,float G,float B){
        background.set(R, G, B);
    }
    private void createCallbacks(){
        sizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int w, int h) {
                width = w;
                height =h;
                isResized = true;
            }
        };
        glfwSetKeyCallback(window, input.getKeyboardCallback());
        glfwSetCursorPosCallback(window,input.getMouseMoveCallback());
        glfwSetMouseButtonCallback(window,input.getMouseButtonCallback());
        glfwSetWindowSizeCallback(window,sizeCallback);
    }
}

