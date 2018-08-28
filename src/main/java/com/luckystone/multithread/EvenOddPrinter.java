package com.luckystone.multithread;

import java.util.Date;

/**
 * 目的：两个线程交替打印1~100奇偶数
 * volatile实现，不需要加锁
 * 缺点：部分时间花在自旋上了
 * 优点：打印的数据量大时效率高
 */
public class EvenOddPrinter {

    private final int MAX_NUM = 1000;

    private volatile int index = 1;
    //private AtomicInteger index = new AtomicInteger(1);

    private volatile boolean isOdd = true;

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private void print(int index) {
        System.out.println("Thread-" +  Thread.currentThread().getName() + ": " + index);
    }

    public class EvenThreadRunnable implements Runnable {

        public void run() {
            while(index <= MAX_NUM) {
                if(!isOdd && index <= MAX_NUM) {
                    print(index++);
                    isOdd = true; //这里的赋值操作是线程安全的
                }
            }
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            while(index <= MAX_NUM) {
                if(isOdd && index <= MAX_NUM) {
                    print(index++);
                    isOdd = false; //这里的赋值操作是线程安全的
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter printer = new EvenOddPrinter();
        Thread evenThread = new Thread(printer.evenThreadRunnable);
        evenThread.setName("even");
        Thread oddThread = new Thread(printer.oddThreadRunnable);
        oddThread.setName("odd");

        evenThread.start();
        oddThread.start();

        evenThread.join();
        oddThread.join();
        Date end = new Date();

        System.out.println("finish！execute time=" + (end.getTime()-start.getTime()) + "ms");
    }
}
