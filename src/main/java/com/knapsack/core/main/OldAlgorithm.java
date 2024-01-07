package com.knapsack.core.main;

import com.knapsack.core.MatrixManipulation;
import com.knapsack.core.entity.ParcelCombinations;
import com.knapsack.core.entity.Polycubes;

import java.util.List;

public class OldAlgorithm {
    int aAmount = ParcelCombinations.aFits;

    public void init() {
        // attempt to fit only A's
    }

    private static void recursiveSearch(float[][][] field, int aAmount) throws InterruptedException {
        // loop through all parcels

        // TODO: create list with all parcels and use them further in the algorithm
        for (int currentParcel = 0; currentParcel < aAmount; currentParcel++) {
            List<int[][][]> allRotations = MatrixManipulation.getAllRotations(Polycubes.bParcel);

            // loop through all permutation of given pent
            for (int mut = 0; mut < allRotations.size(); mut++) {

                // loop through every position on the field
                for (int z = 0; z < field.length; z++) {
                    for (int y = 0; y < field[0].length; y++) {
                        for (int x = 0; x < field[0][0].length; x++) {

                            // check if solution was found
                            if (currentParcel > aAmount - 10) {
                                System.out.println("Solution found");
                                Thread.sleep(20000);
                            }

                            // choose mutation of the current pentomino
                            int[][][] blockToPlace = allRotations.get(mut);

                            // copy field in order to use it for branching
                            float[][][] copiedField = MatrixManipulation.copyField(field);

                            // check if current permutation of pentomino can be added on the x,y,z
                            // coordinates of the field
                            if (MatrixManipulation.canAdd(field, blockToPlace, z, y, x)) {
                                // add pentomino to the copied field
                                MatrixManipulation.add(field, blockToPlace, z, y, x);
//                                ui.setState(copiedField); // display the field

                                // includes all pents except for the current one
                                char[] filteredPents = new char[input.length - 1];
                                int newArrCounter = 0;

                                for (int i = 0; i < input.length; i++) {
                                    if (input[i] != input[currentPent]) {
                                        filteredPents[newArrCounter] = input[i];
                                        newArrCounter++;
                                    }
                                }

                                // if array of available pentominoes is not exhausted, recursively call the
                                // function again
                                if (filteredPents.length != 0) {
                                    recursiveSearch(copiedField, filteredPents);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
