package com.luckystone.concurrent;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RingBuffer<T> implements Iterable<T>  {
    private T[] buffer;                     // queue elements
    private volatile int header = 0;        // index of first element of queue
    private volatile int tail = 0;          // index of last element of queue
    private final int capacity;

    public RingBuffer(int capacity) {
        this.buffer = (T[]) new Object[capacity];
        this.capacity = capacity;
    }

    public boolean isEmpty() {
        return header == tail;
    }

    public int size() {
        return Math.abs(tail - header);
    }

    public boolean put(T item) {
        if ((tail + 1) % buffer.length == header) {
            //throw new RuntimeException("Ring buffer overflow");
            return false;
        }
        buffer[tail] = item;
        tail = (tail + 1) % buffer.length;
        return true;
    }

    public T take() {
        if (isEmpty()) {
            //throw new RuntimeException("Ring buffer underflow");
            return null;
        }
        T item = buffer[header];
        buffer[header] = null;                  // to help with garbage collection
        header = (header + 1) % buffer.length; // wrap-around
        return item;
    }

    public Iterator<T> iterator() {
        return new RingBufferIterator();
    }

    // an iterator, doesn't implement remove() since it's optional
    private class RingBufferIterator implements Iterator<T> {

        private int i = header;

        public boolean hasNext() {
            return i < tail;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return buffer[i++];
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int m = 1000;
        RingBuffer r = new RingBuffer<Integer>(100);
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=0;i<m;++i) {
                    while(!r.put(i));
                    System.out.println(Thread.currentThread().getName() + " put " + i);
                }
                System.out.println("t1 end");
            }
        });
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                Object data;
                for(int j=1;j<=m;++j) {
                    while((data=r.take())==null);
                    System.out.println(Thread.currentThread().getName() + " take " + data);
                }
                System.out.println("t2 end");
            }
        });
        long tot = - System.currentTimeMillis();
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        tot = tot + System.currentTimeMillis();
        System.out.println("time: " + tot);
    }
}
