package com.knapsack.core.main;

import com.knapsack.core.entity.Polycubes;

public class OldAlgorithm {
    public void init(){
        Main.addBlock(Main.rotateZ(Polycubes.aParcel), 0,0,0);
        Main.addBlock(Polycubes.bParcel, 0,0,2);
        Main.addBlock(Polycubes.cParcel, 6,0,0);
    }
}
