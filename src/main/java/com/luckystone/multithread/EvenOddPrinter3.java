package com.luckystone.multithread;

import java.util.Date;

/**
 * 目的：两个线程交替打印1~100奇偶数
 * synchronized + wait/notify实现
 */
public class EvenOddPrinter3 {

    private static final int MAX_NUM = 1000;

    private int index = 1;

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private synchronized void print(int index, int flag) {
        try {
            if((index & 1) == flag) {
                System.out.println("Thread-" + Thread.currentThread().getName() + ": " + index);
            }
            this.notifyAll();
            if(index + 1 <= MAX_NUM) this.wait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class EvenThreadRunnable implements Runnable {

        public void run() {
            while(index <= MAX_NUM) {
                print(index++, 0);
            }
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            while(index <= MAX_NUM) {
                print(index++, 1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter3 printer = new EvenOddPrinter3();
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
