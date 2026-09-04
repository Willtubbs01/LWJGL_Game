package org.example.engine;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private long window;

    private int width;
    private int height;

    private String title;


    public Window(int width, int height, String title) {
        this.width = width;
        this.height = height;
        this.title = title;
    }

    public void init(){


        if(!glfwInit()){
            throw new RuntimeException("Unable to initialize GLFW");
        }

        window = glfwCreateWindow(width, height, title, NULL, NULL);

        if(window == NULL){
            throw new RuntimeException("Unable to create GLFW window");
        }

        glfwMakeContextCurrent(window);
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
    }
}
