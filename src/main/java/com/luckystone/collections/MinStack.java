package com.luckystone.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * 如何实现一个最小栈
 * 问题见： https://leetcode-cn.com/problems/min-stack/description/
 */
public class MinStack {

    int minVal = Integer.MAX_VALUE;
    List<Integer> stack = new ArrayList<Integer>();
    List<Integer> minVals = new ArrayList<Integer>();
    int currentIndex = 0;

    /** initialize your data structure here. */
    public MinStack() {

    }

    public void push(int x) {
        stack.add(x);
        currentIndex = stack.size() - 1;
        if(minVal >= x) {
            if(minVal!=Integer.MAX_VALUE) minVals.add(minVal);
            minVal = x;
        }
    }

    public void pop() {
        if(currentIndex >= 0) {
            if(minVal == top()) {
                minVal = minVals.get(minVals.size()-1);
                minVals.remove(minVals.size()-1);
            }
            stack.remove(currentIndex);
            currentIndex--;
        }
    }

    public int top() {
        return stack.get(currentIndex);
    }

    public int getMin() {
        return minVal;
    }

    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(0);
        minStack.push(1);
        minStack.push(0);
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.top());
        System.out.println(minStack.getMin());
    }
}
