package com.knapsack.core;

import com.knapsack.core.utils.Constants;

public class Launcher {
    private static WindowManager window;
    private static EngineManager engine;
    public static void main(String[] args){
        window = new WindowManager(Constants.TITLE,600, 450, false);
        engine = new EngineManager();

        try{
            engine.start();
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    public static WindowManager getWindow() {
        return window;
    }
}
