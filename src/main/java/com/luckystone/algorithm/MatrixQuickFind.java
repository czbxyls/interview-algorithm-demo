package com.luckystone.algorithm;

public class MatrixQuickFind {

    /**
     * https://leetcode.com/problems/kth-smallest-element-in-a-sorted-matrix/
     * 在有序的二维矩阵中搜索第k小的元素
     * @param matrix
     * @param k
     * @return
     */
    public int kthSmallest(int[][] matrix, int k) {
        int n = matrix.length;
        int m = n > 0 ? matrix[0].length : 0;

        int left = matrix[0][0], right = matrix[m-1][n-1];
        while(left < right) {
            int mid  = left + (right-left)/2;
            int count = upboundCount(matrix, m, n, mid);
            //System.out.println(count);
            if(count < k) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        return left;
    }

    public int upboundCount(int[][] matrix, int m ,int n, int target) {
        int count = 0;
        int i = 0, j = n-1;
        while(i<m && j>=0) {
            if(target < matrix[i][j]) j--;
            else {
                i++;
                count += j + 1;
            }
        }
        return count;
    }
}
