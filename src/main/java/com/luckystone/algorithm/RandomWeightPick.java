package com.luckystone.algorithm;

import java.util.*;

public class RandomWeightPick {

    private TreeMap<Integer, Integer> map;
    private int weightSum = 0;
    private Random random = new Random();

    public RandomWeightPick(int[] w) {
        map = new TreeMap<>();
        for(int i = 0;i<w.length;i++) {
            weightSum += w[i];
            map.put(weightSum, i);
        }
    }

    /**
     * 带权重问题：random-weight
     * 在一个带权重的数据流中随机抽取元素
     * 采用TreeMap搜索边界
     * https://leetcode.com/problems/random-pick-with-weight/
     * @param
     * @return
     */
    public int pickIndex() {
        int r = random.nextInt(weightSum) + 1;
        return map.get(map.tailMap(r).firstKey());
    }

    public static void main(String[] args) {
        RandomWeightPick randomPick = new RandomWeightPick(new int[]{1,3,3,2,1});
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<10000;i++) {
            int res = randomPick.pickIndex();
            map.put(res, map.getOrDefault(res, 0) + 1);
        }
        System.out.println(Arrays.toString(map.entrySet().toArray()));
    }
}
