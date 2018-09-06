package com.luckystone.multithread;

import java.util.Date;
import java.util.concurrent.SynchronousQueue;

/**
 * 目的：两个线程交替打印1~100奇偶数
 * 通过一个SynchronousQueue实现
 * SynchronousQueue特点：对于每个put/offer操作,必须等待一个take/poll操作
 */
public class EvenOddPrinter12 {

    private static final int MAX_NUM = 1000;

    private volatile int index = 0;

    private SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private void print(SynchronousQueue<Integer> queue, int flag) {
        int value = 0;
        try {
            if(flag == 1) {
                value = queue.take();
            } else {
                queue.put(++index);
                value = queue.take();
            }
            System.out.println("Thread-" + Thread.currentThread().getName() + ": " + value);
            if(flag == 1) {
                queue.put(++index);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public class EvenThreadRunnable implements Runnable {

        public void run() {
            while (true) {
                print(queue, 0);
                if(index >= MAX_NUM) break;
            }
            System.out.println("consumer: " + Thread.currentThread().getName() + " finish!");
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            while (true) {
                print(queue, 1);
                if(index >= MAX_NUM-1) break;
            }
            System.out.println("consumer: " + Thread.currentThread().getName() + " finish!");
        }
    }


    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter12 printer = new EvenOddPrinter12();
        Thread oddThread = new Thread(printer.oddThreadRunnable);
        oddThread.setName("odd");

        Thread evenThread = new Thread(printer.evenThreadRunnable);
        evenThread.setName("even");

        oddThread.start();
        evenThread.start();

        evenThread.join();
        oddThread.join();
        Date end = new Date();

        System.out.println("finish！execute time=" + (end.getTime()-start.getTime()) + "ms");
    }
}
