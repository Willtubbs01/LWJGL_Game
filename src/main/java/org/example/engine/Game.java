package org.example.engine;

import org.lwjgl.glfw.GLFW;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;

public class Game {

    private Window window;

    private static final int UPS = 60;
    private static final double UPDATE_INTERVAL = 1.0 / UPS;

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

        double accumulator = 0.0;

        while(!window.shouldClose()){

            double currentTime = glfwGetTime();
            double frameTime = currentTime - lastTime;
            lastTime = currentTime;

            if (frameTime > 0.25) {
                frameTime = 0.25;
            }

            accumulator += frameTime;

            input();

            while(accumulator >= UPDATE_INTERVAL) {
                update(UPDATE_INTERVAL);
                accumulator -= UPDATE_INTERVAL;
            }
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
