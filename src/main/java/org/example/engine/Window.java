package org.example.engine;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private long window;

    private int width;
    private int height;

    private String title;

    private GLFWErrorCallback errorCallback;


    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void init(){

        errorCallback = GLFWErrorCallback.createPrint(System.err);
        errorCallback.set();

        if(!glfwInit()){
            throw new RuntimeException("Unable to initialize GLFW");
        }

        glfwDefaultWindowHints();

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);

        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);


        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if(window == NULL){
            throw new RuntimeException("Unable to create GLFW window");
        }

        glfwMakeContextCurrent(window);

        glfwSwapInterval(1);

        GL.createCapabilities();

        System.out.println(
                "OpenGL Version: " + glGetString(GL_VERSION)
        );

        System.out.println(
                "Renderer: " + glGetString(GL_RENDERER)
        );

        System.out.println(
                "Vendor: " + glGetString(GL_VENDOR)
        );

        glfwShowWindow(window);
    }

    public boolean shouldClose(){
        return glfwWindowShouldClose(window);
    }

    public void update(){
        glfwPollEvents();
    }

    public void destroy(){
        glfwDestroyWindow(window);
        glfwTerminate();

        if(errorCallback != null){
            errorCallback.free();
        }
    }
}
