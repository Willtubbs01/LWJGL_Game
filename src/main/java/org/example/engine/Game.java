package org.example.engine;

import static org.lwjgl.glfw.GLFW.glfwSetWindowTitle;

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

    }

    private void input() {

    }

    private void update() {


    }


    private void cleanup() {
        window.destroy();
    }
}
