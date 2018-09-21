package com.luckystone.concurrent;

import com.luckystone.utils.ConcurrencyRun;

import java.util.concurrent.atomic.AtomicReference;

/**
 * https://blog.csdn.net/vernonzheng/article/details/8206349
 * 基于原子类实现的线程安全栈
 * @param <T>
 */
public class LockFreeStack<T> {

    private AtomicReference<Node<T>> stacks = new AtomicReference<Node<T>>();

    public T push(T e) {
        Node<T> oldNode, newNode;
        newNode = new Node<T>(e, null);
        while (true) { //这里的处理非常的特别，也是必须如此的。
            oldNode = stacks.get();
            newNode.next = oldNode;
            if (stacks.compareAndSet(oldNode, newNode)) {
                return e;
            }
        }
    }

    public T pop() {
        if(isEmpty()) return null;
        Node<T> oldNode, newNode;
        while (true) {
            oldNode = stacks.get();
            newNode = oldNode.next;
            if (stacks.compareAndSet(oldNode, newNode)) {
                return oldNode.object;
            }
        }
    }

    public T top() {
        return stacks.get() == null ? null : stacks.get().object;
    }

    public boolean isEmpty() {
        return stacks.get() == null;
    }

    private static final class Node<T> {
        private T object;

        private volatile Node<T> next;

        private Node(T object, Node<T> next) {
            this.object = object;
            this.next = next;
        }
    }

    public static void main(String[] args) {
        LockFreeStack<Integer> stack = new LockFreeStack<>();

        ConcurrencyRun.run(100, ()-> {
            System.out.println("push: " + stack.push(10));
            System.out.println("push: " + stack.push(9));
            System.out.println("push: " + stack.push(8));

            System.out.println("top: " + stack.top());
            System.out.println("pop: " + stack.pop());
            System.out.println("top: " + stack.top());
            System.out.println("pop: " + stack.pop());

            System.out.println("top: " + stack.top());
            System.out.println("pop: " + stack.pop());
            //System.out.println("pop: " + stack.pop());
        });

    }
}
