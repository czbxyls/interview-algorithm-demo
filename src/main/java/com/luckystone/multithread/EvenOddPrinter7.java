package com.luckystone.multithread;

import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * 目的：两个线程交替打印1~100奇偶数
 * 两个semaphore分别做PV操作来实现
 */
public class EvenOddPrinter7 {

    private static final int MAX_NUM = 1000;

    private Semaphore oddSem = new Semaphore(1);

    private Semaphore evenSem = new Semaphore(0);

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private void print(int index) {
        try {
            if((index & 1) == 0 ) {
                evenSem.acquire();
            } else {
                oddSem.acquire();
            }
            System.out.println("Thread-" + Thread.currentThread().getName() + ": " + index);
            if((index & 1) == 1 ) {
                evenSem.release();
            } else {
                oddSem.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class EvenThreadRunnable implements Runnable {

        public void run() {
            int index = 2;
            while(index <= MAX_NUM) {
                print(index);
                index += 2;
            }
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            int index = 1;
            while(index <= MAX_NUM) {
                print(index);
                index += 2;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter7 printer = new EvenOddPrinter7();
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
