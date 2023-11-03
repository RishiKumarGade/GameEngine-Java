package engine.io;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.*;

import static org.lwjgl.glfw.GLFW.*;

public class Input {
    private GLFWKeyCallback keyboard;
    private GLFWMouseButtonCallback mouseButton ;
    private GLFWCursorPosCallback mouseMove ;
    private GLFWScrollCallback mouseScroll;
    private static boolean[] keys = new boolean[GLFW_KEY_LAST];
    private static boolean[] buttons = new boolean[GLFW_MOUSE_BUTTON_LAST];
    private static double mouseX,mouseY;
    private static double scrollX,scrollY;

    public Input(){
        keyboard = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                keys[key] = (action != GLFW_RELEASE);
            }
        };
        mouseButton = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                buttons[button] = (action != GLFW_RELEASE);
            }
        };
        mouseMove = new GLFWCursorPosCallback() {
            @Override
            public void invoke(long window, double xpos, double ypos) {
                mouseX = xpos;
                mouseY = ypos;
            }
        };
        mouseScroll = new GLFWScrollCallback() {
            @Override
            public void invoke(long window, double xoffset, double yoffset) {
                scrollX += xoffset;
                scrollY += yoffset;
            }
        };
    }

    public GLFWKeyCallback getKeyboardCallback() {
        return keyboard;
    }

    public GLFWMouseButtonCallback getMouseButtonCallback() {
        return mouseButton;
    }

    public GLFWCursorPosCallback getMouseMoveCallback() {
        return mouseMove;
    }

    public GLFWScrollCallback getMouseScrollCallback(){
        return mouseScroll;
    }
    public static double getMouseX() {
        return mouseX;
    }

    public static double getMouseY() {
        return mouseY;
    }
    public void destroy(){
        keyboard.free();
        mouseMove.free();
        mouseButton.free();
        mouseScroll.free();
    }
    public static boolean isKeyDown(int key){
        return keys[key];
    }
    public static boolean isButtonDown(int button){
        return buttons[button];
    }
}
