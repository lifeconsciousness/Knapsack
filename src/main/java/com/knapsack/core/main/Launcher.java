package com.knapsack.core.main;

import com.knapsack.core.EngineManager;
import com.knapsack.core.WindowManager;
import com.knapsack.core.utils.Constants;

public class Launcher {
    private static WindowManager window;
    private static Main main;

    public static void main(String[] args) throws Exception {
        window = new WindowManager(Constants.TITLE,1600, 900, false);
        main = new Main();
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

    public static Main getGame() {
        return main;
    }
}
