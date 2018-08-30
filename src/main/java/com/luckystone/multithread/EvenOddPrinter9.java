package com.luckystone.multithread;

import java.util.Date;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.LockSupport;

/**
 * 目的：两个线程交替打印1~100奇偶数
 * 使用LockSupport实现
 */
public class EvenOddPrinter9 {

    private static final int MAX_NUM = 1000;

    private Thread oddThread;

    private Thread evenThread;

    public EvenOddPrinter9() {
        EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();
        OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

        oddThread = new Thread(oddThreadRunnable);
        oddThread.setName("odd");

        evenThread = new Thread(evenThreadRunnable);
        evenThread.setName("even");
    }

    public void startThread() {
        oddThread.start();
        evenThread.start();
    }

    public void joinThread() throws Exception {
        evenThread.join();
        oddThread.join();

    }

    private void print(int index) {
        try {
            if((index & 1) == 0 ) {
                LockSupport.park();
            } else {

            }
            System.out.println("Thread-" + Thread.currentThread().getName() + ": " + index);
            if((index & 1) == 1 ) {
                LockSupport.unpark(evenThread);
                LockSupport.park();
            } else {
                LockSupport.unpark(oddThread);
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

        EvenOddPrinter9 printer = new EvenOddPrinter9();
        printer.startThread();
        printer.joinThread();


        Date end = new Date();
        System.out.println("finish！execute time=" + (end.getTime()-start.getTime()) + "ms");
    }
}
