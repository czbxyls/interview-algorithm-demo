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
    private AtomicReference<Node<T>> refHead = new AtomicReference<Node<T>>();
    private AtomicReference<Node<T>> refTail = new AtomicReference<Node<T>>();

    public LockFreeQueue() {
        Node<T> dummy = new Node<T>(null, null);
        refHead = new AtomicReference<Node<T>>(dummy);
        refTail = new AtomicReference<Node<T>>(dummy);
    }

    public T put(T e) {
        Node<T> newNode = new Node<>(e, null);
        Node<T> prevTail = refTail.getAndSet(newNode);
        prevTail.next = newNode;
        return e;
    }

    public T take() {
        Node<T> head, next;
        // move head node to the next node using atomic semantics
        // as long as next node is not null
        do {
            head = refHead.get();
            next = head.next;
            if (next == null) {
                // empty list
                return null;
            }
            // try until the whole loop executes pseudo-atomically
            // (i.e. unaffected by modifications done by other threads)
        } while (!refHead.compareAndSet(head, next));

        T value = next.object;
        // release the value pointed to by head, keeping the head node dummy
        next.object = null;
        return value;
    }

    public T getHead() {
        return refHead.get() == null ? null : refHead.get().object;
    }

    public T getTail() {
        return refTail.get() == null ? null : refTail.get().object;
    }

    public boolean isEmpty() {
        return refHead == refTail && refHead.get() == null;
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
            System.out.println("take: " + queue.take());
            System.out.println("put: " + queue.put(2));
            System.out.println("take: " + queue.take());
            System.out.println("put: " + queue.put(3));
            System.out.println("take: " + queue.take());


//            System.out.println("take: " + queue.take());
//            System.out.println("take: " + queue.take());
//            System.out.println("take: " + queue.take());
        });

        int count = 0;
        while(queue.getTail() != null) {
            queue.take();
            count++;
        }
        System.out.println(count);

    }
}
