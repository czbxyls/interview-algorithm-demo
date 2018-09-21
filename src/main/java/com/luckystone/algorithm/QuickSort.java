package com.luckystone.algorithm;

import java.util.Arrays;

/**
 * 经典算法：快速排序
 */
public class QuickSort {

    public void quickSort(int[] nums) {
        if(nums == null || nums.length <= 1) return;
        quickSort(nums, 0, nums.length-1);
    }

    public void quickSort(int[] nums, int left, int right) {
        if(left >= right) return;
        swap(nums, right, left + (right-left)/2);

        int pivot = nums[right];
        int i=left, j=right;
        while(i<j) {
            while(i<j && nums[i] < pivot) i++;
            if(i<j) nums[j--] = nums[i];
            while(i<j && nums[j] > pivot) j--;
            if(i<j) nums[i++] = nums[j];
        }
        //System.out.println("nums:" + Arrays.toString(nums));
        nums[j] = pivot;
        quickSort(nums, left, j-1);
        quickSort(nums, j+1, right);
    }

    private void swap(int[] nums, int i, int j) {
        //System.out.println("i=" + i + ",j=" + j);
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int [] nums = {3, 2, 10, 34, 8, 20, 30, 32, -100, 9, 100, -1};
        QuickSort sort = new QuickSort();
        sort.quickSort(nums);
        System.out.println(Arrays.toString(nums));
    }

}
