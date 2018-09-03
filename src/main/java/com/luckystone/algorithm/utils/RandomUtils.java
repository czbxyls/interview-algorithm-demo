package com.luckystone.algorithm.utils;

import java.util.Random;

public class RandomUtils {

    private static Random rand = new Random();
    private static String key = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    static char randomChar() {
        return key.charAt(rand.nextInt(key.length()));
    }

    public static String randomString(int n) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<n;i++) {
            sb.append(randomChar());
        }
        return sb.toString();
    }
}
