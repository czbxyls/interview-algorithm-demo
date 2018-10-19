package com.luckystone.collections;


/**
 * https://leetcode.com/problems/design-circular-queue/
 *  设计一个循环队列
 *  方法四：同方法三，小优化
 */
public class MyCircularQueue4 {

    private int [] array;
    private final int capatity;
    private int header = -1;
    private int tail = -1;

    /** Initialize your data structure here. Set the size of the queue to be k. */
    public MyCircularQueue4(int k) {
        this.array = new int[k];
        this.capatity = k;
    }

    /** Insert an element into the circular queue. Return true if the operation is successful. */
    public boolean enQueue(int value) {
        if(isFull()) return false;
        tail++;
        if(tail == capatity) tail = 0;

        array[tail] = value;
        if(header == -1) header = tail;
        return true;
    }

    /** Delete an element from the circular queue. Return true if the operation is successful. */
    public boolean deQueue() {
        if(isEmpty()) return false;
        if(header == tail) header = -1;
        else {
            header++;
            if(header == capatity) header = 0;
        }
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
        return array[tail];
    }

    /** Checks whether the circular queue is empty or not. */
    public boolean isEmpty() {
        return header == -1;
    }

    /** Checks whether the circular queue is full or not. */
    public boolean isFull() {
        return (tail + 1 == capatity ? 0 : tail + 1)== header;
    }

    public static void main(String[] args) {
        MyCircularQueue myCircularQueue = new MyCircularQueue(3);
        System.out.println(myCircularQueue.enQueue(1));
        System.out.println(myCircularQueue.enQueue(2));
        System.out.println(myCircularQueue.enQueue(3));

    }
}
