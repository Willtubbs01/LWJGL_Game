package org.example.engine;

import org.example.graphics.Mesh;
import org.example.graphics.ShaderProgram;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.*;

public class Game {

    private Window window;

    private static final int TARGET_FPS = 60;
    private static final double UPDATE_INTERVAL = 1.0 / TARGET_FPS;

    private static final double MAX_FRAME_TIME = 0.25;

    private double lastTime;
    private double statsTimer;

    private int frames;
    private int updates;

    private ShaderProgram shaderProgram;

    private Mesh triangleMesh;

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

        float[] vertices = {

                // triangle 1
                -0.5f,  0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,

                // triangle 2
                -0.5f,  0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
                0.5f,  0.5f, 0.0f
        };

        triangleMesh = new Mesh(vertices);



        shaderProgram = new ShaderProgram("/shaders/vertex.glsl", "/shaders/fragment.glsl");

        glClearColor(
                0.1f,
                0.6f,
                0.85f,
                1.0f
        );


        lastTime = glfwGetTime();
        statsTimer = lastTime;


    }

    private void loop() {
        double accumulator = 0.0;

        while(!window.shouldClose()){

            double currentTime = glfwGetTime();
            double frameTime = currentTime - lastTime;
            lastTime = currentTime;

            if (frameTime > MAX_FRAME_TIME) {
                frameTime = MAX_FRAME_TIME;
            }

            accumulator += frameTime;

            input();

            while(accumulator >= UPDATE_INTERVAL) {
                update(UPDATE_INTERVAL);
                updates++;
                accumulator -= UPDATE_INTERVAL;
            }
            render();
            frames++;

            updateStats(currentTime);

            window.endFrame();
        }
    }

    private void render() {

        glClear(GL_COLOR_BUFFER_BIT);

        shaderProgram.bind();

        triangleMesh.render();

        shaderProgram.unbind();

    }

    private void input() {

    }

    private void update(double dt) {


    }

    private void cleanup() {

        if(triangleMesh != null){
            triangleMesh.destroy();
        }


        if(shaderProgram != null) {
            shaderProgram.destroy();
        }

        window.destroy();
    }

    private void updateStats(double currentTime) {

        if(currentTime - statsTimer >= 1.0){
            window.setTitle("Island Escape | FPS: "+
                    frames +
                    " | UPS: " +
                    updates
            );

            frames = 0;
            updates = 0;
            statsTimer += 1.0;
        }
    }
}
