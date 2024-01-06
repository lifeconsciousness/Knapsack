package com.knapsack.core;

public class MatrixManipulation {
    // how 3d matrix goes:
    // depth -> rows -> columns


    // like rotation of a wheel
    //  -->
    //    |
    //<-- v
    public static int[][][] rotateX (int[][][] matrix){
        int depth = matrix.length;
        int rows = matrix[0].length;
        int cols = matrix[0][0].length;

        int[][][] rotatedMatrix = new int[depth][cols][rows];

        for (int i = 0; i < depth; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    // Rotate along the x-axis (rows)
                    rotatedMatrix[i][k][rows - 1 - j] = matrix[i][j][k];
                }
            }
        }

        return rotatedMatrix;
    }

    //INCORRECT (additionally rotates along X axis)
    // like stripper on the pole
    // --->
    //  <---
    public static int[][][] rotateY(int[][][] matrix) {
        int depth = matrix.length;
        int rows = matrix[0].length;
        int cols = matrix[0][0].length;

        int[][][] rotatedMatrix = new int[cols][rows][depth];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < depth; k++) {
                    // Rotate along the y-axis (columns)
                    rotatedMatrix[i][j][k] = matrix[k][j][i];
                }
            }
        }

        return rotatedMatrix;
    }


    // like doing a backflip
    //  ^ ->
    //  |  |
    //  |  v
    public static int[][][] rotateZ (int[][][] matrix){
        int depth = matrix.length;
        int rows = matrix[0].length;
        int cols = matrix[0][0].length;

        int[][][] rotatedMatrix = new int[rows][depth][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < depth; j++) {
                for (int k = 0; k < cols; k++) {
                    // Rotate along the z-axis (depth)
                    rotatedMatrix[i][j][k] = matrix[j][rows - 1 - i][k];
                }
            }
        }

        return rotatedMatrix;
    }

    public static boolean canAdd (int[][][] field, int[][][] polycube, int depth, int rows, int columns){
        //check if trying to add out of bounds of the field
        try {
            if (depth + polycube.length > field.length || rows + polycube[0].length > field[0].length || columns + polycube[0][0].length > field[0][0].length) {
                return false;
            }

            // check if there's any other polycubes on that position
            //depth
            for (int i = 0; i < polycube.length; i++) {
                //rows
                for (int j = 0; j < polycube[i].length; j++) {
                    //columns
                    for (int k = 0; k < polycube[i][j].length; k++) {
                        if(field[i + depth][j + rows][k + columns] != -1){
                            return false;
                        }
                    }
                }
            }

            return true;
        } catch (Exception e){
            System.out.println("Exception in canAdd: " + e);
        }

        return false;
    }

    public static int[][][] add (int[][][] field, int[][][] polycube, int depth, int rows, int columns){
        int[][][] result = field;

        //depth
        for (int i = 0; i < polycube.length; i++) {
            //rows
            for (int j = 0; j < polycube[i].length; j++) {
                //columns
                for (int k = 0; k < polycube[i][j].length; k++) {
                    if(polycube[i][j][k] != -1){
                        result[i + depth][j + rows][k + columns] = polycube[i][j][k];
                    }
                }
            }
        }

        return result;
    }

    public static int[][][] emptyField (int[][][] field){
        int[][][] result = field;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    result[i][j][k] = -1;
                }
            }
        }

        return result;
    }

    public static void displayField (int[][][] field){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    System.out.print(field[i][j][k] + " ");
                }
            System.out.println("");
            }
        System.out.println("");
        }
    }
}
