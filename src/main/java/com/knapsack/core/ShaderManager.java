package com.knapsack.core;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.opengl.GL20;
import org.lwjgl.system.MemoryStack;

import java.util.HashMap;
import java.util.Map;

public class ShaderManager{
    private final int programId;
    private int vertexShaderID, fragmentShaderID;

    private final Map<String, Integer> uniforms;

    public ShaderManager() throws Exception{
        programId = GL20.glCreateProgram();
        if(programId == 0){
            throw new Exception("Could not create shader");
        }
        uniforms = new HashMap<>();
    }

    public void createUniform(String uniformName) throws Exception {
        int uniformLocation = GL20.glGetUniformLocation(programId, uniformName);

//        System.out.println("Uniform: " + uniformName + ", Location: " + uniformLocation);

        //CHANGED FROM 0 TO -1 TO FIX COULD NOT FIND ISSUE
        if(uniformLocation < -1){
            throw new Exception("Could not find uniform " + uniformName);
        }
        uniforms.put(uniformName, uniformLocation);
    }

    public void setUniform(String uniformName, Matrix4f value){
        try(MemoryStack stack = MemoryStack.stackPush()){
            GL20.glUniformMatrix4fv(uniforms.get(uniformName), false, value.get(stack.mallocFloat(16)));
        }
    }

    public void setUniform(String uniformName, Vector4f value){
        GL20.glUniform4f(uniforms.get(uniformName), value.x, value.y, value.z, value.w);
    }

    public void setUniform(String uniformName, boolean value){
        float res = 0;
        if(value){
            res = 1;
        }
        GL20.glUniform1f(uniforms.get(uniformName), res);
    }

    public void setUniform(String uniformName, Vector3f value){
        GL20.glUniform3f(uniforms.get(uniformName), value.x, value.y, value.z);
    }

    public void setUniform(String uniformName, int value){
        GL20.glUniform1i(uniforms.get(uniformName), value);
    }

    public void setUniform(String uniformName, float value){
        GL20.glUniform1f(uniforms.get(uniformName), value);
    }

    public void createVertexShader(String shaderCode) throws Exception {
        vertexShaderID = createShader(shaderCode, GL20.GL_VERTEX_SHADER);
    }

    public void createFragmentShader(String shaderCode) throws Exception {
        fragmentShaderID = createShader(shaderCode, GL20.GL_FRAGMENT_SHADER);
    }

    public int createShader(String shaderCode, int shaderType) throws Exception {
        int shaderID = GL20.glCreateShader(shaderType);

        if(shaderID == 0){
            throw new Exception("Error creating shader. Type: " + shaderType);
        }

        GL20.glShaderSource(shaderID, shaderCode);
        GL20.glCompileShader(shaderID);

        if(GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == 0){
            throw new Exception("Error compiling shader code. Type: " + shaderType + " Info: " + GL20.glGetShaderInfoLog(shaderID, 1024));
        }

        GL20.glAttachShader(programId, shaderID);

        return shaderID;
    }

    public void link() throws Exception {
        GL20.glLinkProgram(programId);

        if(GL20.glGetProgrami(programId, GL20.GL_LINK_STATUS) == 0){
            throw new Exception("Error linking shader code. Info: " + GL20.glGetProgramInfoLog(programId, 1024));
        }

        if(vertexShaderID != 0){
            GL20.glDetachShader(programId, vertexShaderID);
        }
        if(fragmentShaderID != 0){
            GL20.glDetachShader(programId, fragmentShaderID);
        }

        GL20.glValidateProgram(programId);

        if(GL20.glGetProgrami(programId, GL20.GL_VALIDATE_STATUS) == 0){
            throw new Exception("Unable to validate shader code: " + GL20.glGetProgramInfoLog(programId, 1024));
        }
    }

    public void bind(){
        GL20.glUseProgram(programId);
    }
    public void unbind(){
        GL20.glUseProgram(0);
    }

    public void cleanup(){
        unbind();
        if(programId != 0){
            GL20.glDeleteProgram(programId);
        }
    }
}
