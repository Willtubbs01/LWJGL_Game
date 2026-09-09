package org.example.graphics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL20.*;

public class ShaderProgram {

    private int programID;

    private int vertexShaderID;
    private int fragmentShaderID;

    public ShaderProgram(String vertexPath, String fragmentPath) {
        String vertexSource = loadResource(vertexPath);
        String fragmentSource = loadResource(fragmentPath);

        vertexShaderID = compileShader(GL_VERTEX_SHADER, vertexSource);
        fragmentShaderID = compileShader(GL_FRAGMENT_SHADER, fragmentSource);

        programID = glCreateProgram();

        if (programID == 0) {
            glDeleteShader(vertexShaderID);
            glDeleteShader(fragmentShaderID);
            throw new RuntimeException("Could not create program");
        }

        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);

        glLinkProgram(programID);

        int success = glGetProgrami(programID, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            String log = glGetProgramInfoLog(programID);
            glDeleteProgram(programID);
            glDeleteShader(vertexShaderID);
            glDeleteShader(fragmentShaderID);
            programID = 0;
            throw new RuntimeException("Shader progeam linking failed:\n"+log);
        }

        glDetachShader(programID, vertexShaderID);
        glDetachShader(programID, fragmentShaderID);

        glDeleteShader(vertexShaderID);
        glDeleteShader(fragmentShaderID);
    }

    private int compileShader(int type, String source){
        int shaderID = glCreateShader(type);

        if(shaderID == 0){
            throw new RuntimeException("Could not create shader.");
        }

        glShaderSource(shaderID, source);

        glCompileShader(shaderID);

        int success = glGetShaderi(shaderID, GL_COMPILE_STATUS);

        if(success == GL_FALSE){
            String log =  glGetShaderInfoLog(shaderID);

            glDeleteShader(shaderID);

            throw new RuntimeException("Shader compilation failed:\n"+log);
        }

        return shaderID;
    }

    private String loadResource(String fileName) {
        InputStream in = ShaderProgram.class.getResourceAsStream(fileName);
        if (in == null) {
            throw new RuntimeException("File not found: " + fileName);
        }

        StringBuilder out = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))){
            String line;
            while((line = reader.readLine()) != null){
                out.append(line).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return out.toString();

    }

    public void bind(){
        glUseProgram(programID);
    }

    public void unbind(){
        glUseProgram(0);
    }

    public void destroy(){
        if(programID != 0){
            glDeleteProgram(programID);
            programID = 0;
        }
    }

    public int getProgramID() {
        return programID;
    }
}
