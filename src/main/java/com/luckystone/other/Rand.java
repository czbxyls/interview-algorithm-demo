package com.luckystone.other;

import java.util.HashMap;
import java.util.Random;

/**
 * google面试题：已知rand5求rand7
 * 代码实现参考：https://blog.csdn.net/u010025211/article/details/49668017
 */
public class Rand {

    private int rand5() {
        Random random = new Random();
        return 1+ random.nextInt(5);
    }

    public int rand7(){
        int x = Integer.MAX_VALUE;
        while(x > 21) {
            x = 5 * (rand5() - 1) + rand5();
        }
        return x % 7 + 1;
    }

    public static void main(String[] args) {
        Rand rnd = new Rand();
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i=0;i<10000;i++) {
            int result = rnd.rand7();
            map.put(result, map.get(result) == null ? 0 : map.get(result) + 1);
        }
        for(Integer i : map.keySet()) {
            System.out.println(i + ": " + map.get(i));
        }
    }
}
