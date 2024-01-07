//package com.knapsack.core.main;
//
//import com.knapsack.core.MatrixManipulation;
//import com.knapsack.core.entity.ParcelCombinations;
//import com.knapsack.core.entity.Polycubes;
//
//import java.util.List;
//
//public class OldAlgorithm {
//    int aAmount = ParcelCombinations.aFits;
//
//    public void init() {
//        // attempt to fit only A's
//    }
//
//    private static void recursiveSearch(int[][][] field, int aAmount) throws InterruptedException {
//        // loop through all parcels
//        for (int currentParcel = 0; currentParcel < aAmount; currentParcel++) {
//            List<int[][][]> allRotations = MatrixManipulation.getAllRotations(Polycubes.aParcel);
//
//            // loop through all permutation of given pent
//            for (int mut = 0; mut <; mut++) {
//
//                // loop through every position on the field
//                for (int z = 0; z < field.length; z++) {
//                    for (int y = 0; y < field[0].length; y++) {
//                        for (int x = 0; x < field[0][0].length; x++) {
//
//                            // check if solution was found
//                            if (aAmount < 5) {
//                                System.out.println("Solution found");
//                                Thread.sleep(20000);
//                            }
//
//                            // choose mutation of the current pentomino
//                            int[][][] blockToPlace = ;
//
//                            // copy field in order to use it for branching
//                            int[][] copiedField = copyField(field);
//
//                            // check if current permutation of pentomino can be added on the y,x
//                            // coordinates of the field
//                            if (canAdd(copiedField, pentToPlace, x, y)) {
//                                // add pentomino to the copied field
//                                addpent(copiedField, pentToPlace, pentID, x, y);
//                                ui.setState(copiedField); // display the field
//
//                                // includes all pents except for the current one
//                                char[] filteredPents = new char[input.length - 1];
//                                int newArrCounter = 0;
//
//                                for (int i = 0; i < input.length; i++) {
//                                    if (input[i] != input[currentPent]) {
//                                        filteredPents[newArrCounter] = input[i];
//                                        newArrCounter++;
//                                    }
//                                }
//
//                                // if array of available pentominoes is not exhausted, recursively call the
//                                // function again
//                                if (filteredPents.length != 0) {
//                                    recursiveSearch(copiedField, filteredPents);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
