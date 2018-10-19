package com.luckystone.collections;

/**
 * https://leetcode.com/problems/design-circular-queue/
 *  设计一个循环队列
 *  方法二：增加一个count，记录当前队列节点数量
 */
public class MyCircularQueue2 {

    private int [] array;
    private final int capatity;
    private int header = 0;
    private int tail = 0;
    private int count = 0;

    /** Initialize your data structure here. Set the size of the queue to be k. */
    public MyCircularQueue2(int k) {
        this.array = new int[k];
        this.capatity = k;
    }

    /** Insert an element into the circular queue. Return true if the operation is successful. */
    public boolean enQueue(int value) {
        if(isFull()) return false;
        array[tail] =value;
        tail++;
        tail = tail >= capatity ? 0 : tail;
        count++;
        return true;
    }

    /** Delete an element from the circular queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if(isEmpty()) return false;
        header++;
        header = header >= capatity ? 0 : header;
        count--;
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
        int rear = tail - 1 < 0 ?  tail - 1 + capatity : tail - 1;
        return array[rear];
    }

    /** Checks whether the circular queue is empty or not. */
    public boolean isEmpty() {
        return count == 0;
    }

    /** Checks whether the circular queue is full or not. */
    public boolean isFull() {
        return count == capatity;
    }

    public static void main(String[] args) {
        MyCircularQueue2 myCircularQueue = new MyCircularQueue2(3);
        System.out.println(myCircularQueue.enQueue(1));
        System.out.println(myCircularQueue.enQueue(2));
        System.out.println(myCircularQueue.enQueue(3));
        System.out.println(myCircularQueue.enQueue(4));

    }
}
