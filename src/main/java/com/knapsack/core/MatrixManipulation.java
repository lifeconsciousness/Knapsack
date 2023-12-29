package com.knapsack.core;

public class MatrixManipulation {
    public static void emptyField (int[][][] field){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {
                    field[i][j][k] = -1;
                }
            }
        }
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

    // x,y,z coordinates to start adding polycube to
    public static void addPolycube (int[][][] field, int[][][] polycube, int x, int y, int z){
        for (int i = 0; i < field.length; i++) {
            for (int j = 0; j < field[i].length; j++) {
                for (int k = 0; k < field[i][j].length; k++) {

                }
            }
        }
    }
}
