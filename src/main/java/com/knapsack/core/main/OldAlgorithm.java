package com.knapsack.core.main;

import com.knapsack.core.MatrixManipulation;
import com.knapsack.core.entity.ParcelCombinations;
import com.knapsack.core.entity.Polycubes;

import java.util.ArrayList;
import java.util.List;

public class OldAlgorithm {
    // take a combination of parcels and create a list out of it
//    int[] aAmount = ParcelCombinations.aFits;
    int[] aAmount = ParcelCombinations.aTest;
    List<int[][][]> parcelsList = MatrixManipulation.createParcels(aAmount);
    int calls = 0;
    boolean solutionFound = false;

    public void init() throws InterruptedException {
        // attempt to fit only A's
        recursiveSearch(Main.getField(), parcelsList);
    }

    private void recursiveSearch(float[][][] field, List<int[][][]> parcels) throws InterruptedException {
        calls++;
        System.out.println(calls);

        if (!solutionFound){
            // loop through all parcels
            for (int currentParcel = 0; currentParcel < parcels.size(); currentParcel++) {
                List<int[][][]> allRotations = MatrixManipulation.getAllRotations(parcels.get(currentParcel));

                // loop through all permutation of given pent
                for (int mut = 0; mut < allRotations.size(); mut++) {

                    // loop through every position on the field
                    for (int z = 0; z < field.length; z++) { //depth
                        for (int y = 0; y < field[0].length; y++) { // rows
                            for (int x = 0; x < field[0][0].length; x++) { // columns

                                // check if solution was found
//                                if (MatrixManipulation.amountEmptySpace(field) < 5) {
                                if (parcels.size() < 20) {
                                    System.out.println("Solution found");
//                                    Thread.sleep(20000);
                                    solutionFound = true;
                                }

                                // choose mutation of the current pentomino
                                int[][][] blockToPlace = allRotations.get(mut);

                                // copy field in order to use it for branching
                                float[][][] copiedField = MatrixManipulation.copyField(field);

                                // check if current permutation of pentomino can be added on the x,y,z coordinates of the field
                                if (MatrixManipulation.canAdd(field, blockToPlace, z, y, x)) {

                                    // add pentomino to the copied field
                                    MatrixManipulation.add(field, blockToPlace, z, y, x);
                                    // ui.setState(copiedField); // display the field

                                    // includes all pents except for the current one
                                    List<int[][][]> filteredParcels = MatrixManipulation.deepCopy(parcels);
                                    //check if filtered parcels are empty
                                    if(filteredParcels.size() > 1){
                                        filteredParcels.remove(currentParcel);
                                    }

                                    // if array of available parcels is not exhausted, recursively call the function again
                                    if (!filteredParcels.isEmpty()) {
                                        recursiveSearch(copiedField, filteredParcels);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


}
