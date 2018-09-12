package com.luckystone.other;

import java.util.Arrays;

public class TriangleNumber {

    /**
     * https://leetcode.com/problems/valid-triangle-number/description/
     * 在一个数组中求组成三角形的个数
     * 算法复杂度O(n^2)
     * @param nums
     * @return
     */
    public int triangleNumber(int[] nums) {
        Arrays.sort(nums);
        int i=0, j=1, k=2;
        int count = 0;
        for(;i<nums.length-2;i++) {
            if(nums[i] <= 0) continue;
            k= i + 2;
            for(j=i+1;j<nums.length-1;j++) {
                while(k < nums.length && nums[i]+nums[j]>nums[k]) k++;
                if(k < nums.length) count += k -1- j;
                else {
                    count += (k -1- j) * (k - j)/2;
                    break;
                }
            }
        }
        return count;
    }

    /**
     * 写法二：参考3sum问题，从数组右边往左边倒推
     * 算法复杂度O(n^2)
     * @param nums
     * @return
     */
    public int triangleNumber2(int[] nums) {
        Arrays.sort(nums);
        if (nums.length == 0) return 0;
        int count = 0;
        int n = nums.length;
        for (int i = n - 1; i >= 1; i --) {
            int l = 0;
            int r = i - 1;
            while(l < r) {
                if (nums[l] + nums[r] > nums[i]) {
                    count += (r - l);
                    r--;
                } else {
                    l++;
                }
            }
        }
        return count;
    }
}
