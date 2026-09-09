package org.example.graphics;

import org.lwjgl.system.MemoryStack;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class Mesh {

    private int vao;
    private int vbo;

    private int vertexCount;


    public Mesh(float[] vertices) {

        if (vertices.length % 3 != 0) {

            throw new IllegalArgumentException(
                    "Vertex array length must be divisible by 3"
            );
        }

        if(vertices.length == 0) {
            throw new IllegalArgumentException("Mesh vertices array is empty");
        }

        vertexCount =
                vertices.length / 3;


        vao = glGenVertexArrays();

        glBindVertexArray(vao);


        vbo = glGenBuffers();

        glBindBuffer(
                GL_ARRAY_BUFFER,
                vbo
        );


        try (MemoryStack stack = MemoryStack.stackPush()) {

            FloatBuffer vertexBuffer =
                    stack.mallocFloat(vertices.length);

            vertexBuffer.put(vertices);

            vertexBuffer.flip();


            glBufferData(
                    GL_ARRAY_BUFFER,
                    vertexBuffer,
                    GL_STATIC_DRAW
            );
        }


        glVertexAttribPointer(
                0,
                3,
                GL_FLOAT,
                false,
                3 * Float.BYTES,
                0
        );


        glEnableVertexAttribArray(0);


        glBindBuffer(
                GL_ARRAY_BUFFER,
                0
        );

        glBindVertexArray(0);
    }

    public void render(){
        glBindVertexArray(vao);

        glDrawArrays(GL_TRIANGLES, 0, vertexCount);

        glBindVertexArray(0);
    }

    public void destroy(){
        if(vbo != 0) {
            glDeleteBuffers(vbo);
            vbo = 0;
        }

        if(vao != 0) {
            glDeleteVertexArrays(vao);
            vao = 0;
        }
    }
}
