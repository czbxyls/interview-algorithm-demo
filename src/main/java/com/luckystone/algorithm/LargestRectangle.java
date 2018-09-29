package com.luckystone.algorithm;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * https://leetcode.com/problems/largest-rectangle-in-histogram/
 * 求解最大直方图相连的矩形面积
 */
public class LargestRectangle {


    /**
     * O(n^2) 暴力搜索 800ms+
     * @param heights
     * @return
     */
    public int largestRectangleArea(int[] heights) {
        int left = 0, right = 0, width;
        int max = 0;
        for(int i=0;i<heights.length;i++) {
            right = left = i;
            width = 1;
            while(left - 1>= 0 && heights[i] <= heights[left-1])  { left--; width++;}
            while(right+1<= heights.length-1 && heights[i] <= heights[right+1]) {right++;width++;}
            max = Math.max(max, width * heights[i]);
        }
        return max;
    }


    /**
     * 左侧搜索剪枝，300ms
     * @param heights
     * @return
     */
    public int largestRectangleArea2(int[] heights) {
        int left = 0, right = 0, leftWidth = 0, rightWidth = 0, preLeftWidth = 0, preRightWidth = 0;
        int max = 0;
        for(int i=0;i<heights.length;i++) {
            right = left = i;
            leftWidth = rightWidth = 0;
            if(preLeftWidth > 0 && left - 1>= 0 &&  heights[i] <= heights[left-1]) {
                leftWidth = preLeftWidth + 1;
                left -= leftWidth;
            }
            while(left - 1>= 0 && heights[i] <= heights[left-1])  {
                left--; leftWidth++;
            }
            while(right < heights.length-1 && heights[i] <= heights[right+1]) {
                right++;rightWidth++;
            }
            preLeftWidth = leftWidth;
            preRightWidth = rightWidth;
            max = Math.max(max, (leftWidth + rightWidth + 1) * heights[i]);
            //System.out.println(preLeftWidth + " " + preRightWidth + ": " + max);
        }
        return max;
    }


    /**
     * 采用一个栈，特点是当前元素大于栈顶，则入栈，当前元素小于栈顶，则出栈计算面积.20ms
     * @param heights
     * @return
     */
    public int largestRectangleArea3(int[] heights) {
        Deque<Integer> stack = new ArrayDeque<>();
        int i =0;
        int max = 0;
        while(i < heights.length || !stack.isEmpty()) {
            if(stack.isEmpty() || (i < heights.length
                    && heights[stack.peek()] <= heights[i])){
                stack.push(i++);
            } else {
                int cur = stack.pop();
                max = Math.max(max, heights[cur] * (stack.isEmpty() ? i : (i - stack.peek()-1)));
            }
        }
        return max;
    }


    /**
     * 最佳答案：二分法，10ms
     * 特点是找到最低的柱子，左右进行二分
     * 另外，同时判断当前数组是否有序，进行剪枝
     * @param heights
     * @return
     */
    public int largestRectangleArea4(int[] heights) {
        if(heights == null || heights.length < 1){
            return 0;
        }
        return largestArea(heights, 0, heights.length - 1);
    }

    public int largestArea(int nums[], int left, int right){
        if(right < left){
            return 0;
        } else if(left == right){
            return nums[left];
        }
        int minIndex = left;
        boolean sorted = true;
        for(int i = left + 1; i <= right; i++){
            if(nums[i] < nums[i-1]){
                sorted = false; //是否排序
            }
            if(nums[i] < nums[minIndex]){
                minIndex = i; //最小值
            }
        }
        if(sorted){
            int maxArea = 0;
            for(int i = right; i >=left; i--){
                maxArea = Math.max(maxArea, nums[i] * (right - i + 1));
            }
            return maxArea;
        } else {
            int leftMax = largestArea(nums, left,minIndex - 1);
            int rightMax = largestArea(nums, minIndex + 1, right);
            int currentMax = nums[minIndex] * (right - left + 1);
            return Math.max(Math.max(leftMax, rightMax), currentMax);
        }
    }

    public static void main(String[] args) {
        LargestRectangle largestRectangle = new LargestRectangle();
        System.out.println(largestRectangle.largestRectangleArea3(new int[] {1}));
    }

}
