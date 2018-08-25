package com.luckystone.collections;

import java.util.Stack;

/**
 * 如何使用两个栈实现一个FIFO队列
 * https://leetcode.com/problems/implement-queue-using-stacks/description/
 */
public class MyQueue<T> {

    //for push
    Stack<T> pushStack = new Stack<T>();
    //for pop
    Stack<T> popStack = new Stack<T>();


    /** Initialize your data structure here. */
    public MyQueue() {

    }

    /** Push element x to the back of queue. */
    public void push(T x) {
        pushStack.push(x);
    }

    /** Removes the element from in front of queue and returns that element. */
    public T pop() {
        if(popStack.isEmpty()) {
            while(!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
        return popStack.pop();
    }

    /** Get the front element. */
    public T peek() {
        if(popStack.isEmpty()) {
            while(!pushStack.isEmpty()) {
                popStack.push(pushStack.pop());
            }
        }
        return popStack.lastElement();
    }

    /** Returns whether the queue is empty. */
    public boolean empty() {
        return popStack.empty() && pushStack.empty();
    }

    public static void main(String[] args) {
        MyQueue<Integer> obj = new MyQueue<Integer>();
        System.out.println(obj.empty());
        obj.push(1);
        obj.push(2);
        obj.push(3);
        System.out.println(obj.pop());
        System.out.println(obj.pop());
        obj.push(4);
        System.out.println(obj.pop());
        obj.push(5);
        System.out.println(obj.peek());
        System.out.println(obj.empty());
    }
}
