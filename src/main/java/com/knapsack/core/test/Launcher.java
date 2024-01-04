package com.knapsack.core.test;

import com.knapsack.core.EngineManager;
import com.knapsack.core.MatrixManipulation;
import com.knapsack.core.WindowManager;
import com.knapsack.core.entity.Polycubes;
import com.knapsack.core.test.TestGame;
import com.knapsack.core.utils.Constants;

public class Launcher {
    private static WindowManager window;
    private static TestGame game;

    public static void main(String[] args) throws Exception {
//        window = new WindowManager(Constants.TITLE,600, 450, false);
        window = new WindowManager(Constants.TITLE,1600, 900, false);
        game = new TestGame();
        EngineManager engine = new EngineManager();

        try{
            engine.start();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }

    public static TestGame getGame() {
        return game;
    }
}
