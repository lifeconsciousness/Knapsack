package com.knapsack.core.entity;

public class Polycubes {
    // how 3d matrix goes:
    // depth -> rows -> columns

    // smallest unit: 0.5 - one cube
    // empty space is marked by -1

    // length (depth): 1.0, width (columns): 1.0, height (rows): 2.0
    public static final int[][][] aParcel = {
            {
                    {1, 1},
                    {1, 1},
                    {1, 1},
                    {1, 1},
            },
            {
                    {1, 1},
                    {1, 1},
                    {1, 1},
                    {1, 1},
            },
    };

    //B: 1.0 x 1.5 x 2.0 depth col row
    public static final int[][][] bParcel = {
            {
                    {2, 2, 2},
                    {2, 2, 2},
                    {2, 2, 2},
                    {2, 2, 2},
            },
            {
                    {2, 2, 2},
                    {2, 2, 2},
                    {2, 2, 2},
                    {2, 2, 2},
            },
    };

    //C: 1.5 x 1.5 x 1.5
    public static final int[][][] cParcel = {
            {
                    {3, 3, 3},
                    {3, 3, 3},
                    {3, 3, 3},
            },
            {
                    {3, 3, 3},
                    {3, 3, 3},
                    {3, 3, 3},
            },
            {
                    {3, 3, 3},
                    {3, 3, 3},
                    {3, 3, 3},
            },
    };

    public static final int[][][] testMatrix = {
            {
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
            },{
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
            },{
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
                    {-1, -1, -1, -1, -1},
            },
    };
}
