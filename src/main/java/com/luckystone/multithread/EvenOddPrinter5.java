package com.luckystone.multithread;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * 两个线程交替打印1~100奇偶数
 * semaphore + barrier实现
 */
public class EvenOddPrinter5 {

    private static final int MAX_NUM = 1000;

    private CyclicBarrier barrier = new CyclicBarrier(2);

    private Semaphore semaphore = new Semaphore(0);

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private void print(int index, CyclicBarrier barrier, Semaphore semaphore) {
        try {
            if((index & 1) == 0 ) {
                //System.out.println("Thread-" + Thread.currentThread().getName() + " acquire! index=" + index);
                semaphore.acquire();
                //System.out.println("Thread-" + Thread.currentThread().getName() + " finish acquire! index=" + index);
            }
            System.out.println("Thread-" + Thread.currentThread().getName() + ": " + index);
            if((index & 1) == 1 ) {
                //System.out.println("Thread-" + Thread.currentThread().getName() + " release! index=" + index);
                semaphore.release();
                //System.out.println("Thread-" + Thread.currentThread().getName() + " finish release! index=" + index);
            }
            //用栅栏技术，凑齐两个，保证每一轮一个奇数一个偶数
            barrier.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class EvenThreadRunnable implements Runnable {

        public void run() {
            int index = 2;
            while(index <= MAX_NUM) {
                print(index, barrier, semaphore);
                index += 2;
            }
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            int index = 1;
            while(index <= MAX_NUM) {
                print(index, barrier, semaphore);
                index += 2;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter5 printer = new EvenOddPrinter5();
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
