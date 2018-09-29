package com.luckystone.algorithm;

public class MajorityElement {

    /**
     * LeetCode：169. Majority Element https://leetcode.com/problems/majority-element/description/
     * 问题：给定一个int型数组，找出该数组中出现次数大于数组长度一半的int值
     * 求众数 - 摩尔投票法
     * 摩尔投票法的基本思想很简单，在每一轮投票过程中，从数组中找出一对不同的元素，将其从数组中删除。
     * 这样不断的删除直到无法再进行投票，如果数组为空，则没有任何元素出现的次数超过该数组长度的一半。
     * 如果只存在一种元素，那么这个元素则为目标元素。
     * @param nums
     * @return
     */
    public int majorityElement(int[] nums) {
        int candidate = 0;
        int count = 0;
        for(int i=0;i<nums.length;i++) {
            if(count == 0) {
                candidate = nums[i];
                count++;
            } else{
                if(candidate == nums[i]) count++;
                else count--;
            }
        }
        return candidate;
    }
}
