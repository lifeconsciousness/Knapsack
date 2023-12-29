package com.knapsack.core.entity;

public class Shapes {

    public class Cube{
        public float[] vertices = new float[] {
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, 0.5f, -0.5f,
                0.5f, 0.5f, -0.5f,
                -0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, 0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
                -0.5f, 0.5f, 0.5f,
                -0.5f, -0.5f, 0.5f,
                -0.5f, -0.5f, -0.5f,
                0.5f, -0.5f, -0.5f,
                -0.5f, -0.5f, 0.5f,
                0.5f, -0.5f, 0.5f,
        };

        public float[] textureCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                0.0f, 0.0f,
                0.5f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 0.5f,
                0.5f, 0.5f,
                0.0f, 1.0f,
                0.5f, 1.0f,
                0.0f, 0.0f,
                0.0f, 0.5f,
                0.5f, 0.0f,
                0.5f, 0.5f,
                0.5f, 0.0f,
                1.0f, 0.0f,
                0.5f, 0.5f,
                1.0f, 0.5f,
        };

        public int[] indices = new int[]{
                0, 1, 3, 3, 1, 2,
                8, 10, 11, 9, 8, 11,
                12, 13, 7, 5, 12, 7,
                14, 15, 6, 4, 14, 6,
                16, 18, 19, 17, 16, 19,
                4, 6, 7, 5, 4, 7,
        };
    }

    //rectangle 1x1x2
    public class Rectangle {
        public float[] vertices = new float[] {
                -0.5f,  0.5f,  1.0f,  // Vertex 0
                -0.5f, -0.5f,  1.0f,  // Vertex 1
                0.5f, -0.5f,  1.0f,  // Vertex 2
                0.5f,  0.5f,  1.0f,  // Vertex 3

                -0.5f,  0.5f, -1.0f,  // Vertex 4
                0.5f,  0.5f, -1.0f,  // Vertex 5
                -0.5f, -0.5f, -1.0f,  // Vertex 6
                0.5f, -0.5f, -1.0f   // Vertex 7
        };

        public float[] textureCoords = new float[]{
                0.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f,
                1.0f, 0.0f,

                0.0f, 0.0f,
                1.0f, 0.0f,
                0.0f, 1.0f,
                1.0f, 1.0f
        };

        public int[] indices = new int[]{
                0, 1, 3, 3, 1, 2, // Front face
                4, 5, 7, 7, 5, 6, // Back face
                0, 1, 4, 4, 1, 5, // Left face
                2, 3, 6, 6, 3, 7, // Right face
                0, 2, 4, 4, 2, 6, // Top face
                1, 3, 5, 5, 3, 7  // Bottom face
        };
    }
}
