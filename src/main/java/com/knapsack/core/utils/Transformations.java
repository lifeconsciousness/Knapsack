package com.knapsack.core.utils;

import com.knapsack.core.Camera;
import com.knapsack.core.entity.Entity;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import java.util.Vector;

public class Transformations {

    public static Matrix4f createTransformationMatrix(Entity entity){
        Matrix4f matrix = new Matrix4f();
        matrix.identity().translate(entity.getPos()).
                rotateX((float) Math.toRadians(entity.getRotation().x)).
                rotateY((float) Math.toRadians(entity.getRotation().y)).
                rotateZ((float) Math.toRadians(entity.getRotation().z)).
                scale(entity.getScale());

        return matrix;
    }

    public static Matrix4f getViewMatrix(Camera camera){
        Vector3f pos = camera.getPosition();
        Vector3f rot = camera.getRotation();
        Matrix4f matrix = new Matrix4f();

        matrix.identity();
        matrix.rotate((float) Math.toRadians(rot.x), new Vector3f(1,0,0)).
                rotate((float) Math.toRadians(rot.y), new Vector3f(0,1,0)).
                rotate((float) Math.toRadians(rot.z), new Vector3f(0,0,1));

        // negative because we objects are moved opposite to the direction camera goes
        matrix.translate(-pos.x, -pos.y, -pos.z);
        return matrix;
    }
}
