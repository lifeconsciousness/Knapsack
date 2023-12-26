package com.knapsack.core.utils;

import org.lwjgl.system.MemoryUtil;

import java.nio.FloatBuffer;

public class Utils {

    public static FloatBuffer storeDataInFLoatBuffer(float[] data){
        FloatBuffer buffer = MemoryUtil.memAllocFloat(data.length);
        return buffer.put(data).flip();
    }
}
