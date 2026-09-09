package org.example.engine;

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

    private int vbo;
    private int vao;

    private int vertexCount;

    private ShaderProgram shaderProgram;

    public void run(){
        init();
        loop();
        cleanup();
    }

    private void init(){

        float[] vertices = {
                0,0f, 0.5f, 0.0f,
                -0.5f, -0.5f, 0.0f,
                0.5f, -0.5f, 0.0f,
        };

        vertexCount = vertices.length/3;

        vao = glGenVertexArrays();
        glBindVertexArray(vao);


        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);


        try{
            MemoryStack stack = MemoryStack.stackPush();
            FloatBuffer verticesBuffer = stack.mallocFloat(vertices.length);

            verticesBuffer.put(vertices).flip();

            glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);

        } catch(OutOfMemoryError e){
            e.printStackTrace();
        }

        glVertexAttribPointer(0, 3, GL_FLOAT, false, 3*Float.BYTES, 0);

        glEnableVertexAttribArray(0);

        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindVertexArray(0);


        window = new Window(
                1280,
                720,
                "Island Survival Game"
        );

        window.init();

        shaderProgram = new ShaderProgram("/shaders/vertex.glsl", "/shaders/fragment.glsl");

        glClearColor(
                0.0f,
                0.0f,
                0.0f,
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


        shaderProgram.unbind();

    }

    private void input() {

    }

    private void update(double dt) {


    }

    private void cleanup() {

        if(vbo != 0) {
            glDeleteBuffers(vbo);
            vbo = 0;
        }

        if(vao != 0) {
            glDeleteVertexArrays(vao);
            vao = 0;
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
