package com.luckystone.collections;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * https://leetcode.com/problems/implement-stack-using-queues
 * 利用两个队列实现一个栈
 */
public class MyStack {

    Queue<Integer> queue1 = null;
    Queue<Integer> queue2 = null;
    int topIndex = 1;


    /** Initialize your data structure here. */
    public MyStack() {
        queue1 = new ArrayDeque<>();
        queue2 = new ArrayDeque<>();
    }

    /** Push element x onto stack. */
    public void push(int x) {
        if(topIndex == 1) {
            queue1.add(x);
        } else {
            queue2.add(x);
        }
    }

    /** Removes the element on top of the stack and returns that element. */
    public int pop() {
        if(topIndex == 1) {
            if(queue1.isEmpty()) return -1;
            while(queue1.size()>1) {
                queue2.add(queue1.poll());
            }
            topIndex = 2;
            return queue1.poll();
        } else {
            if(queue2.isEmpty()) return -1;
            while(queue2.size()>1) {
                queue1.add(queue2.poll());
            }
            topIndex = 1;
            return queue2.poll();
        }
    }

    /** Get the top element. */
    public int top() {
        if(topIndex == 1) {
            if(queue1.isEmpty()) return -1;
            while(queue1.size()>1) {
                queue2.add(queue1.poll());
            }
            return queue1.peek();
        } else {
            if(queue2.isEmpty()) return -1;
            while(queue2.size()>1) {
                queue1.add(queue2.poll());
            }
            return queue2.peek();
        }
    }

    /** Returns whether the stack is empty. */
    public boolean empty() {
        return queue1.isEmpty() && queue2.isEmpty();
    }
}
