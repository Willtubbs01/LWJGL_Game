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
        int i = 0;
        while(!window.shouldClose()){

            window.update();
            i++;
            window.setTitle("Island Survival Game: " + i / 10000);

        }
    }

    private void cleanup() {
        window.destroy();
    }
}
