package com.knapsack.core;

import com.knapsack.core.entity.Model;
import com.knapsack.core.utils.Utils;
import jdk.jshell.execution.Util;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class ObjectLoader {
    //lists of data such as coordinates of an object, coordinate of a texture

    // vertex array objects list (s for plural)
    private List<Integer> vaos = new ArrayList<>();
    //vertex buffer objects
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    public Model loadModel(float[] verticies, float[] textureCoords, int[] indices){
        int id = createVAO();
        storeIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, verticies);
        storeDataInAttributeList(1, 2, textureCoords);
        unbind();

        return new Model(id, indices.length);
    }

    public int loadTexture(String filename) throws Exception {
        int width, height;
        ByteBuffer buffer;
        try(MemoryStack stack = MemoryStack.stackPush()){
            // buffer for width, height and the number of chanels
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer c = stack.mallocInt(1);

            buffer = STBImage.stbi_load(filename, w, h, c, 4);
            if(buffer == null){
                throw new Exception("Image File: " + filename + " not loaded " + STBImage.stbi_failure_reason());
            }

            width = w.get();
            height = h.get();
        }

        int id = GL11.glGenTextures();
        textures.add(id);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        STBImage.stbi_image_free(buffer);

        return id;
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

    private void storeIndicesBuffer(int[] indices){
        int vbo = GL15.glGenBuffers();
        vbos.add(vbo);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vbo);
        IntBuffer buffer = Utils.storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
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
        for (int texture : textures){
            GL30.glDeleteTextures(texture);
        }
    }
}
