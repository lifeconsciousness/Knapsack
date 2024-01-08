package com.knapsack.core.main;
import com.knapsack.core.MatrixManipulation;
import com.knapsack.core.entity.ParcelCombinations;

import java.util.List;

public class NewAlgorithm {
        private boolean solutionFound = false;
        private int calls = 0;

        int[] amount = ParcelCombinations.aFits;
        List<int[][][]> parcelsList = MatrixManipulation.createParcels(amount);

        public void init() {
            DancingLinks dlx = new DancingLinks();

            // Create the exact cover matrix
            dlx.initializeMatrix(Main.getField(), parcelsList);

            // Start the search
            recursiveSearch(dlx, Main.getField(), parcelsList);
        }

    private void recursiveSearch(DancingLinks dlx, float[][][] field, List<int[][][]> parcels) {
        calls++;

        if (!solutionFound) {
            if (dlx.isEmpty()) {
                // Exact cover found, print the solution or take any required action
                System.out.println("Solution found");
                System.out.println(calls);
                solutionFound = true;
                return;
            }

            // Choose a column with the fewest 1s
            DancingLinks.ColumnNode chosenColumn = dlx.chooseColumn();

            // Cover the chosen column
            dlx.coverColumn(chosenColumn);

            // Loop through all rows in the covered column
            DancingLinks.Node row = chosenColumn.down;
            while (row != chosenColumn) {
                // Include the chosen row in the partial solution
                dlx.includeRow(row);

                // Update the field matrix based on the chosen row
                updateField(field, parcels, row);

                // Recursively search for the next solution
                recursiveSearch(dlx, field, parcels);

                // Uncover the row for backtracking
                dlx.uncoverRow(row);

                // Update the field matrix back to its original state
                updateField(field, parcels, row);

                row = row.down;
            }

            // Uncover the chosen column for backtracking
            dlx.uncoverColumn(chosenColumn);
        }
    }

    private void updateField(float[][][] field, List<int[][][]> parcels, DancingLinks.Node row) {
        // Decode information from the chosen row
        DancingLinks.ColumnNode columnNode = (DancingLinks.ColumnNode) row.column;
        int parcelIndex = columnNode.columnIndex;
        int[][][] parcel = parcels.get(parcelIndex);
        int z = row.right.column.columnIndex;
        int y = row.right.right.column.columnIndex;
        int x = row.right.right.right.column.columnIndex;

        // Update the field matrix based on the chosen parcel and position
        for (int i = 0; i < parcel.length; i++) {
            for (int j = 0; j < parcel[0].length; j++) {
                for (int k = 0; k < parcel[0][0].length; k++) {
                    if (parcel[i][j][k] == 1) {
                        field[z + i][y + j][x + k] = 1;
                    }
                }
            }
        }

        Main.setField(field);
    }


}

