package org.example.engine;

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

            window.update();
        }
    }

    private void cleanup() {
        window.destroy();
    }
}
