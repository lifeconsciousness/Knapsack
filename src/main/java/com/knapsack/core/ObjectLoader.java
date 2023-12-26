package com.knapsack.core;

import com.knapsack.core.entity.Model;
import com.knapsack.core.utils.Utils;
import jdk.jshell.execution.Util;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {
    //lists of data such as coordinates of an object, coordinate of a texture

    // vertex array objects list (s for plural)
    private List<Integer> vaos = new ArrayList<>();
    //vertex buffer objects
    private List<Integer> vbos = new ArrayList<>();

    public Model loadModel(float[] verticies){
        int id = createVAO();
        storeDataInAttributeList(0, 3, verticies);
        unbind();

        return new Model(id, verticies.length / 3);
    }

    // VAO is an object that has multiple vertex buffers and their associated attributes.
    private int createVAO(){
        int id = GL30.glGenVertexArrays();
        vaos.add(id);

        // (Binding a VAO means making it the currently active VAO, and any subsequent OpenGL
        // calls related to vertex attributes and buffers will operate on the bound VAO)
        GL30.glBindVertexArray(id);

        return id;
    }

    // This method is used to store vertex data in a VAO
    // attrNo: Attribute number (e.g., position, color, texture coordinates)
    // data: Array containing the vertex data
    private void storeDataInAttributeList(int attrNo, int vertexCount, float[] data){
        int vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        FloatBuffer buffer = Utils.storeDataInFLoatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attrNo, vertexCount, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    // (Note: binding a VAO is a way to switch between different configurations of vertex data in OpenGL
    // Allows efficient rendering of multiple objects with different vertex attribute layouts)
    private void unbind(){
        GL30.glBindVertexArray(0);
    }

    public void cleanup(){
        for (int vao : vaos){
            GL30.glDeleteVertexArrays(vao);
        }
        for (int vbo : vbos){
            GL30.glDeleteBuffers(vbo);
        }
    }
}
