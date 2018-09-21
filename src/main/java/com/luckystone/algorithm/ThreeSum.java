package com.luckystone.algorithm;

import java.util.*;

public class ThreeSum {

    /**
     * Leetcode经典求和问题总结(leetcode 3Sum)
     * find all solution for x+y+z=0
     * 算法复杂度O(n^2)
     * 其他sum问题可类似推导
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);

        int start, end;
        int x, y, z;
        int tmp;
        List<List<Integer>> result = new ArrayList<>();
        Set<String> resultSet = new HashSet<>();
        for(int i=0;i<nums.length;i++) {
            x = nums[i];
            tmp = -x;
            start = i + 1;
            end = nums.length - 1;
            while(start < end) {
                y = nums[start];
                z = nums[end];
                //贪心策略：从两边到中间开始找
                if(y + z > tmp) {
                    end--;
                } else if(y + z < tmp) {
                    start++;
                } else {//find
                    String key = x + "+" +  y + "+" + z;
                    if(!resultSet.contains(key)) {
                        List<Integer> ans = new ArrayList<>(3);
                        ans.add(x);
                        ans.add(y);
                        ans.add(z);
                        result.add(ans);
                    }
                    start++;
                    end--;
                    resultSet.add(key);
                }
            }
        }
        return result;
    }

    public static void main(String[] args) {
        int [] nums = new int[] {-4,-2,1,-5,-4,-4,4,-2,0,4,0,-2,3,1,-5,0};
        List<List<Integer>> result = new ThreeSum().threeSum(nums);
        System.out.println(result);
    }

}
