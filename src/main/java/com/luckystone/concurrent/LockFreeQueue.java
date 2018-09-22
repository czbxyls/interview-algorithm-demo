package com.luckystone.concurrent;

import com.luckystone.utils.ConcurrencyRun;

import java.util.concurrent.atomic.AtomicReference;

/**
 * 基于AtomicReference实现的线程安全队列
 * 参考：https://pdfs.semanticscholar.org/f871/5db70cf6b06ebeb90cbc3ce29756e6f3141e.pdf
 *      https://codereview.stackexchange.com/questions/224/thread-safe-and-lock-free-queue-implementation
 * @param <T>
 */
public class LockFreeQueue<T> {

    private AtomicReference<Node<T>> tail = new AtomicReference<Node<T>>();
    private AtomicReference<Node<T>> header = new AtomicReference<Node<T>>();

    public T put(T e) {
        Node<T> newNode = new Node<>(e, null);
        Node<T> prevTail = tail.getAndSet(newNode);
        if(prevTail != null) {
            prevTail.next = newNode;
        } else {
            header.set(newNode);
        }
        return e;
    }

    public T take() {
        Node<T> oldHead, newNode;
        while (true) {
            oldHead = header.get();

            if(oldHead == null) {
                return null;
            } else {
                newNode = oldHead.next;
                if(header.compareAndSet(oldHead, newNode)) {
                    return oldHead.object;
                }
            }
        }
    }

    public T getHead() {
        return header.get() == null ? null : header.get().object;
    }

    public T getTail() {
        return tail.get() == null ? null : tail.get().object;
    }

    public boolean isEmpty() {
        return tail == header && tail == null;
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
        LockFreeQueue<Integer> queue = new LockFreeQueue<>();

        ConcurrencyRun.run(100, ()-> {
            System.out.println("put: " + queue.put(1));
            System.out.println("put: " + queue.put(2));
            System.out.println("put: " + queue.put(3));

            System.out.println("take: " + queue.take());
            System.out.println("take: " + queue.take());
            System.out.println("take: " + queue.take());
        });

    }
}
