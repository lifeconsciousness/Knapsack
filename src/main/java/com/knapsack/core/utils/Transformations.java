package com.knapsack.core.utils;

import com.knapsack.core.entity.Entity;
import org.joml.Matrix4f;

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
}
