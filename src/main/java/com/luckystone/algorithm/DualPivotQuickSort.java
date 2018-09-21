package com.luckystone.algorithm;

import java.util.Arrays;

/**
 * jdk1.8采用的双pivot快速排序算法
 * 这里的算法参考自：
 * https://www.geeksforgeeks.org/dual-pivot-quicksort/，略有调整
 */
public class DualPivotQuickSort {

    public void dualPivotQuickSort(int[] nums) {
        if(nums == null || nums.length <= 1) return;
        dualPivotQuickSort(nums, 0, nums.length-1);
    }

    public void dualPivotQuickSort(int[] nums, int low, int high) {
        if(low >= high) return;
        if (nums[low] > nums[high])
            swap(nums, low, high);
        // p is the left pivot, and q is the right pivot.
        int j = low + 1;
        int g = high - 1, k = low + 1, p = nums[low], q = nums[high];
        while (k <= g) {
            // if elements are less than the left pivot
            if (nums[k] < p) {
                swap(nums, k, j);
                j++; k++;
            }
            // if elements are greater than or equal
            // to the right pivot
            else if (nums[k] >= q) {
                while (nums[g] > q && k < g)
                    g--;
                swap(nums, k, g);
                g--;
            } else {
                k++;
            }
        }
        j--;
        g++;

        // bring pivots to their appropriate positions.
        swap(nums, low, j);
        swap(nums, high, g);

        dualPivotQuickSort(nums, low, j - 1);
        dualPivotQuickSort(nums, j + 1, g - 1);
        dualPivotQuickSort(nums, g + 1, high);
    }

    private void swap(int[] nums, int i, int j) {
        //System.out.println("i=" + i + ",j=" + j);
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }

    public static void main(String[] args) {
        int [] nums = {3, 2, 10, 34, 8, 20, 30, 32, -100, 9, 100, -1};
        DualPivotQuickSort sort = new DualPivotQuickSort();
        sort.dualPivotQuickSort(nums);
        System.out.println(Arrays.toString(nums));
    }
}
