package com.knapsack.core;
public class Launcher {
    public static void main(String[] args){
        System.out.println("Idk");
        WindowManager window = new WindowManager("Knapsack",600, 450, false);
        window.init();

        while(!window.windowShouldClose()){
            window.update();
        }

        window.cleanup();
    }
}
