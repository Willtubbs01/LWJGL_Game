package org.example.engine;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;
import static org.lwjgl.opengl.GL11.*;

public class Game {

    private Window window;

    public void run(){
        init();
        loop();
        cleanup();
    }

    private void init(){
        window = new Window(
                1280,
                720,
                "Island Survival Game"
        );

        window.init();

        glClearColor(
                0.0f,
                0.0f,
                0.0f,
                1.0f
        );
    }

    private void loop() {

        while(!window.shouldClose()){

            input();

            update();

            render();

            window.update();
        }
    }

    private void render() {

        glClear(GL_COLOR_BUFFER_BIT);

    }

    private void input() {

    }

    private void update() {


    }


    private void cleanup() {
        window.destroy();
    }
}
