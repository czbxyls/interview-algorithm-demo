package com.luckystone.algorithm;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RandomPick {

    private final int[] nums;
    private Random random = new Random();

    public RandomPick(int[] nums) {
        this.nums = nums;
    }


    /**
     * 蓄水池抽样问题
     * 在一个未知的数据流中随机等概率抽取一个数据
     * 我们总是选择第一个对象，以1/2的概率选择第二个，以1/3的概率选择第三个，
     * 以此类推，以1/m的概率选择第m个对象。当该过程结束时，
     * 每一个对象具有相同的选中概率，即1/n
     * https://leetcode.com/problems/random-pick-index/description/
     * @param target
     * @return
     */
    public int pick(int target) {
        int n = 0;
        int index = -1;
        for(int i=0;i<nums.length;i++) {
            if(nums[i] == target) {
                n++;
                if(index == -1) index = i;
                else {
                    if(random.nextInt(n) == 0) index = i;
                }
            }
        }
        return index;
    }

    public static void main(String[] args) {
        RandomPick randomPick = new RandomPick(new int[]{1,2,3,3,3});
        Map<Integer, Integer> map = new HashMap<>();
        for(int i=0;i<10000;i++) {
            int res = randomPick.pick(3);
            map.put(res, map.getOrDefault(res, 0) + 1);
        }
        System.out.println(Arrays.toString(map.entrySet().toArray()));
    }
}
