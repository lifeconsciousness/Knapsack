package com.knapsack.core.entity;

import org.joml.Vector3f;

public class Entity {
    private Model model;
    private Vector3f pos, rotation;
    private float scale;
    private float index;
    private boolean isOutlined;

    public Entity(Model model, Vector3f pos, Vector3f rotation, float scale, float index) {
        this.model = model;
        this.pos = pos;
        this.rotation = rotation;
        this.scale = scale;
        this.index = index;
    }

    public void incrementPos(float x, float y, float z){
        this.pos.x += x;
        this.pos.y += y;
        this.pos.z += z;
    }
    public void setPos(float x, float y, float z){
        this.pos.x = x;
        this.pos.y = y;
        this.pos.z = z;
    }

    public void incrementRotation(float x, float y, float z){
        this.rotation.x += x;
        this.rotation.y += y;
        this.rotation.z += z;
    }
    public void setRotation(float x, float y, float z){
        this.rotation.x = x;
        this.rotation.y = y;
        this.rotation.z = z;
    }

    public Model getModel() {
        return model;
    }

    public Vector3f getPos() {
        return pos;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public float getScale() {
        return scale;
    }

    public float getIndex() {
        return index;
    }

    public void setIndex(float newIndex){
        this.index = newIndex;
    }
}

