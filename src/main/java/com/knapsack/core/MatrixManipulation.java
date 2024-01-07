package com.knapsack.core;

import com.knapsack.core.utils.Constants;

import java.util.*;

public class MatrixManipulation {
    // how 3d matrix goes:
    // depth -> rows -> columns


    // like rotation of a wheel
    //  -->
    //    |
    //<-- v
    public static int[][][] rotateX(int[][][] matrix) {
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
    public static int[][][] rotateZ(int[][][] matrix) {
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

    public static boolean canAdd(float[][][] field, int[][][] polycube, int depth, int rows, int columns) {
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
                        if (field[i + depth][j + rows][k + columns] != -1) {
                            return false;
                        }
                    }
                }
            }

            return true;
        } catch (Exception e) {
            System.out.println("Exception in canAdd: " + e);
        }

        return false;
    }

    static final float indexIncreaseStep = Constants.INDEX_INCREASE_STEP;
    static float aIncrement = indexIncreaseStep;
    static float bIncrement = indexIncreaseStep;
    static float cIncrement = indexIncreaseStep;
    static int counter = 0;

    public static float[][][] add(float[][][] field, int[][][] polycube, int depth, int rows, int columns) {
        float[][][] result = field;

        boolean isA = false;
        boolean isB = false;
        boolean isC = false;

        if (counter > 15) {
            aIncrement = indexIncreaseStep;
            bIncrement = indexIncreaseStep;
            cIncrement = indexIncreaseStep;

            counter = 0;
        }
        counter++;

        //depth
        for (int i = 0; i < polycube.length; i++) {
            //rows
            for (int j = 0; j < polycube[i].length; j++) {
                //columns
                for (int k = 0; k < polycube[i][j].length; k++) {

                    float index = polycube[i][j][k];

                    if (index != -1) {
                        float resultingIndex = 0;
                        if (index >= 1 && index < 2) {
                            resultingIndex = index + aIncrement;
                            isA = true;
                        } else if (index >= 2 && index < 3) {
                            resultingIndex = index + bIncrement;
                            isB = true;
                        } else if (index >= 3 && index < 4) {
                            resultingIndex = index + cIncrement;
                            isC = true;
                        }

                        result[i + depth][j + rows][k + columns] = resultingIndex;
                    }
                }
            }
        }

        aIncrement += isA ? indexIncreaseStep : 0;
        bIncrement += isB ? indexIncreaseStep : 0;
        cIncrement += isC ? indexIncreaseStep : 0;

        return result;
    }

    public static float[][][] emptyField(float[][][] field) {
        float[][][] result = field;

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    result[i][j][k] = -1;
                }
            }
        }

        return result;
    }

    public static void displayField(int[][][] field) {
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

    public static List<int[][][]> getAllRotations(int[][][] originalMatrix) {
        List<int[][][]> rotations = new ArrayList<>();

        for (int xRotation = 0; xRotation < 4; xRotation++) {
            for (int yRotation = 0; yRotation < 4; yRotation++) {
                for (int zRotation = 0; zRotation < 4; zRotation++) {
                    int[][][] rotatedMatrix = rotate(originalMatrix, xRotation, yRotation, zRotation);
                    rotations.add(rotatedMatrix);
                }
            }
        }

        removeDuplicates(rotations);
        return rotations;
    }

    public static int[][][] rotate(int[][][] matrix, int xRotations, int yRotations, int zRotations) {
        int[][][] rotatedMatrix = matrix;

        for (int i = 0; i < xRotations; i++) {
            rotatedMatrix = rotateX(rotatedMatrix);
        }

        for (int i = 0; i < yRotations; i++) {
            rotatedMatrix = rotateY(rotatedMatrix);
        }

        for (int i = 0; i < zRotations; i++) {
            rotatedMatrix = rotateZ(rotatedMatrix);
        }

        return rotatedMatrix;
    }

    public static void removeDuplicates(List<int[][][]> matrixList) {
        Set<String> matrixSignatures = new HashSet<>();
        Iterator<int[][][]> iterator = matrixList.iterator();

        while (iterator.hasNext()) {
            int[][][] currentMatrix = iterator.next();
            String matrixSignature = getMatrixSignature(currentMatrix);

            if (!matrixSignatures.add(matrixSignature)) {
                iterator.remove();
            }
        }
    }

    private static String getMatrixSignature(int[][][] matrix) {
        StringBuilder signature = new StringBuilder();
        for (int i = 0; i < matrix.length; i++) {
            signature.append(matrix[i].length).append("x").append(matrix[i][0].length).append(";");
        }
        return signature.toString();
    }

    public static float[][][] copyField(float[][][] field) {
        float[][][] copiedField =  new float[field.length][field[0].length][field[0][0].length];

        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    copiedField[i][j][k] = field[i][j][k];
                }
            }
        }

        return copiedField;
    }

    public List<int[][][]> createParcels (int[] amountArray){

    }
}
