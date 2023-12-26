package com.knapsack.core;

public interface ILogic {
    void init() throws  Exception;
    void input();
    void update();
    void render();
    void cleanup();
}
