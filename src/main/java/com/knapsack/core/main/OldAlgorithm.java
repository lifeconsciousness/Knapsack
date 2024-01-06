package com.knapsack.core.main;

import com.knapsack.core.entity.Polycubes;

public class OldAlgorithm {
    public void init(){
        Game.addBlock(Game.rotateZ(Polycubes.aParcel), 0,0,0);
        Game.addBlock(Polycubes.bParcel, 0,0,2);
        Game.addBlock(Polycubes.cParcel, 6,0,0);
    }

    public static void main(String[] args) {
    }
}
