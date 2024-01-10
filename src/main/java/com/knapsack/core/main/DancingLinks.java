package com.knapsack.core.main;

import com.knapsack.core.MatrixManipulation;

import java.util.List;

public class DancingLinks {

    public class Node {
        Node left, right, up, down;
        ColumnNode column;

        public Node(ColumnNode column) {
            this.column = column;
        }
    }

    public class ColumnNode extends Node {
        int size;
        String name;
        int columnIndex; // Add this field for storing the column index

        public ColumnNode(String name, int columnIndex) {
            super(null);
            size = 0;
            this.name = name;
            this.columnIndex = columnIndex;
            this.column = this;
        }
    }

    private ColumnNode header;

    public DancingLinks() {
        header = new ColumnNode("header", -1);
    }

    public void initializeMatrix(float[][][] field, List<int[][][]> parcels) {
        int numRows = calculateNumWays(field, parcels);
        int numCols = parcels.size() + field.length * field[0].length * field[0][0].length;

        // Create header nodes for each column
        ColumnNode[] columnNodes = new ColumnNode[numCols];
        for (int i = 0; i < numCols; i++) {
            columnNodes[i] = new ColumnNode("C" + i, i);
        }

        // Link the header nodes horizontally
        for (int i = 0; i < numCols - 1; i++) {
            columnNodes[i].right = columnNodes[i + 1];
            columnNodes[i + 1].left = columnNodes[i];
        }
        columnNodes[numCols - 1].right = header;
        header.left = columnNodes[numCols - 1];

        // Create nodes for each 1 in the matrix
        Node[][] matrixNodes = new Node[numRows][numCols];
        int nodeCount = 0;

        // Go through every position of the field
        for (int z = 0; z < field.length; z++) {
            for (int y = 0; y < field[0].length; y++) {
                for (int x = 0; x < field[0][0].length; x++) {
                    for (int currentParcel = 0; currentParcel < parcels.size(); currentParcel++) {
                        int[][][] parcel = parcels.get(currentParcel);

                        // Try all rotations of the parcel
                        for (int xRotations = 0; xRotations < 4; xRotations++) {
                            for (int yRotations = 0; yRotations < 4; yRotations++) {
                                for (int zRotations = 0; zRotations < 4; zRotations++) {
                                    int[][][] rotatedParcel = MatrixManipulation.rotate(parcel, xRotations, yRotations, zRotations);

                                    if (MatrixManipulation.canAdd(field, rotatedParcel, z, y, x)) {
                                        // Create a node for this combination of parcel and field position
                                        Node node = new Node(columnNodes[currentParcel]);
                                        matrixNodes[nodeCount][currentParcel] = node;

                                        // Link it horizontally
                                        node.left = (nodeCount > 0) ? matrixNodes[nodeCount - 1][currentParcel] : node;
                                        node.right = node;

                                        // Link it vertically
                                        if (nodeCount > 0) {
                                            matrixNodes[nodeCount - 1][currentParcel].down = node;
                                            node.up = matrixNodes[nodeCount - 1][currentParcel];
                                        }

                                        // Increase the size of the corresponding column
                                        columnNodes[currentParcel].size++;
                                        nodeCount++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Close the toroidal doubly linked list for each column
        for (int i = 0; i < numCols; i++) {
            columnNodes[i].up = columnNodes[i];
            columnNodes[i].down = columnNodes[i];
        }

        // Connect the last and first nodes horizontally in each row
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols - 1; j++) {
                if (matrixNodes[i][j] != null) {
                    matrixNodes[i][j].right = matrixNodes[i][(j + 1) % numCols];
                    matrixNodes[i][(j + 1) % numCols].left = matrixNodes[i][j];
                }
            }
        }
    }

    private int calculateNumWays(float[][][] field, List<int[][][]> parcels) {
        int count = 0;

        for (int z = 0; z < field.length; z++) {
            for (int y = 0; y < field[0].length; y++) {
                for (int x = 0; x < field[0][0].length; x++) {
                    for (int currentParcel = 0; currentParcel < parcels.size(); currentParcel++) {
                        int[][][] parcel = parcels.get(currentParcel);

                        // Try all rotations of the parcel
                        for (int xRotations = 0; xRotations < 4; xRotations++) {
                            for (int yRotations = 0; yRotations < 4; yRotations++) {
                                for (int zRotations = 0; zRotations < 4 ; zRotations++) {
                                    int[][][] rotatedParcel = MatrixManipulation.rotate(parcel, xRotations, yRotations, zRotations);

                                    if (MatrixManipulation.canAdd(field, rotatedParcel, z, y, x)) {
                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return count;
    }

    public boolean isEmpty() {
        return header.right == header;
    }

    public ColumnNode chooseColumn() {
        ColumnNode chosenColumn = null;
        int minSize = Integer.MAX_VALUE;

        // Choose the column with the fewest 1s
        ColumnNode column = (ColumnNode) header.right;
        while (column != header) {
            if (column.size < minSize) {
                minSize = column.size;
                chosenColumn = column;
            }
            column = (ColumnNode) column.right;
        }

        return chosenColumn;
    }

    public void coverColumn(ColumnNode column) {
        // Remove the column from the header's horizontal links
        column.right.left = column.left;
        column.left.right = column.right;

        // Remove each row that has a 1 in this column
        Node row = column.down;
        while (row != column) {
            Node right = row.right;
            while (right != row) {
                right.down.up = right.up;
                right.up.down = right.down;
                right.column.size--;
                right = right.right;
            }
            row = row.down;
        }
    }

    public void uncoverColumn(ColumnNode column) {
        // Restore each row that was removed when covering the column
        Node row = column.up;
        while (row != column) {
            Node left = row.left;
            while (left != row) {
                left.down.up = left;
                left.up.down = left;
                left.column.size++;
                left = left.left;
            }
            row = row.up;
        }

        // Restore the column to the header's horizontal links
        column.right.left = column;
        column.left.right = column;
    }

    public void includeRow(Node row) {
        // Include the row in the partial solution by removing it vertically
        Node right = row.right;
        while (right != row) {
            coverColumn(right.column);
            right = right.right;
        }
    }

    public void uncoverRow(Node row) {
        // Uncover the row for backtracking by restoring it vertically
        Node left = row.left;
        while (left != row) {
            uncoverColumn(left.column);
            left = left.left;
        }
    }

    // You might want to add more methods depending on your specific needs
}
