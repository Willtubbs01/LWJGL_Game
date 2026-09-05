package org.example.engine;

import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;
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

        double lastTime = glfwGetTime();

        while(!window.shouldClose()){

            double currentTime = glfwGetTime();
            double deltaTime = currentTime - lastTime;
            lastTime = currentTime;

            input();

            update(deltaTime);

            render();

            window.update();
        }
    }

    private void render() {

        glClear(GL_COLOR_BUFFER_BIT);

    }

    private void input() {

    }

    private void update(double dt) {


    }


    private void cleanup() {
        window.destroy();
    }
}
