package com.knapsack.core.entity;

public class ParcelCombinations {
    // field volume: 1320 units
    // parcel A: 16 units | 82 parcels fit with 8 units of empty space
    // parcel B: 24 units | 55 parcels fit without empty space
    // parcel C: 27 units | 48 parcels fit with 24 units of empty space

    // combinations: A, B, C, AB, BC, AC, ABC
    public static int[] aFits = {82};
    public static int[] aDoesntFit = {84};
    public static int[] bFits = {55};
    public static int[] cFits = {48};
    public static int[] cDoesntFit = {50};

    // numbers for combinations of parcels are random

    // a - 30 parcels, b - 37 parcels
    public static int[] aAndB = {30, 37};

    // b - 22 parcels, c - 28 parcels
    public static int[] bAndc = {22,28};

    // a - 53 parcels, c - 28 parcels
    public static int[] aAndc = {14,36};

    public static int[] abc = {7, 17, 27};
}
