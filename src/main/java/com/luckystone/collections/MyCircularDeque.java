package com.luckystone.collections;

/**
 * https://leetcode.com/problems/design-circular-deque/
 * 设计一个ArrayDeque
 *
 */
public class MyCircularDeque {

    private int [] array;
    private final int capatity;
    private int header = -1;
    private int tail = 0;
    private int count = 0; //基于count判断非空或者满

    /** Initialize your data structure here. Set the size of the deque to be k. */
    public MyCircularDeque(int k) {
        array = new int[k];
        capatity = k;
    }

    /** Adds an item at the front of Deque. Return true if the operation is successful. */
    public boolean insertFront(int value) {
        if(isFull()) return false;
        header = (header + 1) % capatity;
        array[header] = value;
        count++;
        return true;
    }

    /** Adds an item at the rear of Deque. Return true if the operation is successful. */
    public boolean insertLast(int value) {
        if(isFull()) return false;
        tail = (capatity + tail - 1) % capatity;
        array[tail] = value;
        count++;
        return true;
    }

    /** Deletes an item from the front of Deque. Return true if the operation is successful. */
    public boolean deleteFront() {
        if(isEmpty()) return false;
        header = (capatity + header - 1) % capatity;
        count--;
        return true;
    }


    /** Deletes an item from the rear of Deque. Return true if the operation is successful. */
    public boolean deleteLast() {
        if(isEmpty()) return false;
        tail = (tail + 1) % capatity;
        count--;
        return true;
    }

    /** Get the front item from the deque. */
    public int getFront() {
        if(isEmpty()) return -1;
        return array[header];
    }

    /** Get the last item from the deque. */
    public int getRear() {
        if(isEmpty()) return -1;
        return array[tail];
    }

    /** Checks whether the circular deque is empty or not. */
    public boolean isEmpty() {
        return count == 0;
    }

    /** Checks whether the circular deque is full or not. */
    public boolean isFull() {
        return count == capatity;
    }
}
