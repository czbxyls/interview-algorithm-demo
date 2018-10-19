package com.luckystone.collections;

/**
 * https://leetcode.com/problems/design-circular-queue/
 * 设计一个循环队列
 * 方法一-增加一个dummy节点，减少代码量，不需要专门的count记忆当前节点数量，这种思想可用于无锁的RingBuffer设计
 */
public class MyCircularQueue {

    private int [] array;
    private final int capatity;
    private int header = 0;
    private int tail = 0;

    /** Initialize your data structure here. Set the size of the queue to be k. */
    public MyCircularQueue(int k) {
        this.array = new int[k+1];
        this.capatity = k + 1;
    }

    /** Insert an element into the circular queue. Return true if the operation is successful. */
    public boolean enQueue(int value) {
        if(isFull()) return false;
        array[tail] =value;
        tail = (tail + 1) % capatity;
        return true;
    }

    /** Delete an element from the circular queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if(isEmpty()) return false;
        header = (header + 1) % capatity;
        return true;
    }

    /** Get the front item from the queue. */
    public int Front() {
        if(isEmpty()) return -1;
        return array[header];
    }

    /** Get the last item from the queue. */
    public int Rear() {
        if(isEmpty()) return -1;
        return array[(tail - 1 + capatity) %capatity];
    }

    /** Checks whether the circular queue is empty or not. */
    public boolean isEmpty() {
        return header == tail;
    }

    /** Checks whether the circular queue is full or not. */
    public boolean isFull() {
        return (tail + 1)%capatity == header;
    }

    public static void main(String[] args) {
        MyCircularQueue myCircularQueue = new MyCircularQueue(3);
        System.out.println(myCircularQueue.enQueue(1));
        System.out.println(myCircularQueue.enQueue(2));
        System.out.println(myCircularQueue.enQueue(3));
        System.out.println(myCircularQueue.enQueue(4));

    }
}
