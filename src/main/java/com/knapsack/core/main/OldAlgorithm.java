package com.knapsack.core.main;

import com.knapsack.core.MatrixManipulation;
import com.knapsack.core.entity.ParcelCombinations;
import com.knapsack.core.entity.Polycubes;

import java.util.ArrayList;
import java.util.List;

public class OldAlgorithm {
    // take a combination of parcels and create a list out of it
    int calls = 0;
    boolean solutionFound = false;

    int[] aAmount = ParcelCombinations.aFits;
    List<int[][][]> parcelsList = MatrixManipulation.createParcels(aAmount);
    List<int[][][]> allRotations = MatrixManipulation.getAllRotations(Polycubes.aParcel);


    public void init() throws InterruptedException {
        // attempt to fit only A's
        recursiveSearch(Main.getField(), parcelsList);
    }

    private void recursiveSearch(float[][][] field, List<int[][][]> parcels) throws InterruptedException {
        calls++;

        if (!solutionFound){
            // loop through all parcels
            for (int currentParcel = 0; currentParcel < parcels.size(); currentParcel++) {
//                List<int[][][]> allRotations = MatrixManipulation.getAllRotations(parcels.get(currentParcel));
                MatrixManipulation.removeDuplicates(allRotations);

                // loop through all permutation of given pent
                for (int mut = 0; mut < allRotations.size(); mut++) {

                    // loop through every position on the field
                    for (int z = 0; z < field.length; z++) { //depth
                        for (int y = 0; y < field[0].length; y++) { // rows
                            for (int x = 0; x < field[0][0].length; x++) { // columns

                                // check if solution was found
                                 if (MatrixManipulation.amountEmptySpace(field) < 5) {
                                    System.out.println("Solution found");
                                    System.out.println(calls);

                                    solutionFound = true;
                                 }

                                // choose mutation of the current parcel
                                int[][][] blockToPlace = allRotations.get(mut);

                                // copy field in order to use it for branching
                                float[][][] copiedField = MatrixManipulation.copyField(field);

                                // check if current permutation of parcel can be added on the x,y,z coordinates of the field
                                if (MatrixManipulation.canAdd(copiedField, blockToPlace, z, y, x)) {

                                    // add parcel to the copied field
                                    copiedField = MatrixManipulation.add(copiedField, blockToPlace, z, y, x);
                                    // TODO: the original field remains unchanged
                                    Main.setField(copiedField);

                                    // includes all parcels except for the current one
                                    List<int[][][]> filteredParcels = MatrixManipulation.deepCopy(parcels);
                                    //check if filtered parcels are empty
                                    if(!parcels.isEmpty()){
                                        filteredParcels.remove(currentParcel);
                                        // if array of available parcels is not exhausted, recursively call the function again
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
