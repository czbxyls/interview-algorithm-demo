package com.luckystone.multithread;

import java.util.Date;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 两个线程交替打印1~100奇偶数
 * Condition + ReentrantLock实现
 */
public class EvenOddPrinter2 {

    private final int MAX_NUM = 1000;

    private int index = 1;

    private ReentrantLock lock = new ReentrantLock();

    private Condition evenCondition = lock.newCondition();

    private Condition oddCondition = lock.newCondition();

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private void print(int index, int flag, Condition signal, Condition wait) {
        lock.lock();
        try {
            if((index & 1) == flag) {
                System.out.println("Thread-" + Thread.currentThread().getName() + ": " + index);
            }
            signal.signal();
            if(index + 1 <= MAX_NUM) wait.await();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    public class EvenThreadRunnable implements Runnable {

        public void run() {
            while(index <= MAX_NUM) {
                print(index++, 0, oddCondition, evenCondition);
            }
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            while(index <= MAX_NUM) {
                print(index++, 1, evenCondition, oddCondition);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter2 printer = new EvenOddPrinter2();
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
