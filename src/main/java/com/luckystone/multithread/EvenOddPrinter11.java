package com.luckystone.multithread;

import java.util.Date;
import java.util.concurrent.SynchronousQueue;

/**
 * 目的：两个线程交替打印1~100奇偶数
 * 通过两个SynchronousQueue + volatile实现
 * 生产者生产数字，通过SynchronousQueue发送给消费者，并打印出来
 * SynchronousQueue与Exchanger类似，SynchronousQueue是单向的，Exchanger是双向的
 */
public class EvenOddPrinter11 {

    private static final int MAX_NUM = 1000;

    private volatile int consumerIndex = 0;

    private SynchronousQueue<Integer> oddQueue = new SynchronousQueue<Integer>();

    private SynchronousQueue<Integer> edenQueue = new SynchronousQueue<Integer>();

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private int print(SynchronousQueue<Integer> queue) {
        try {
            int value = queue.take();
            System.out.println("Thread-" + Thread.currentThread().getName() + ": " + value);
            consumerIndex = value;
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return consumerIndex;
    }

    public class EvenThreadRunnable implements Runnable {

        public void run() {
            int index = 0;
            while (true) {
                index = print(edenQueue);
                if(index >= MAX_NUM) break;
            }
            System.out.println("consumer: " + Thread.currentThread().getName() + " finish!");
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            int index = 0;
            while (true) {
                index = print(oddQueue);
                if(index >= MAX_NUM-1) break;
            }
            System.out.println("consumer: " + Thread.currentThread().getName() + " finish!");
        }
    }

    private boolean isCanPut(int value) {
        return consumerIndex == value;
    }

    private void putValue(int value) {
        try {
            if((value & 1) == 1) oddQueue.put(value);
            else edenQueue.put(value);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void produceNumber() {
        int i = 0;
        while(i < MAX_NUM) {
            if(isCanPut(i)) {
                i++;
                putValue(i);
            }
        }
        System.out.println("producer finish!");
    }

    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter11 printer = new EvenOddPrinter11();
        Thread oddThread = new Thread(printer.oddThreadRunnable);
        oddThread.setName("odd");

        Thread evenThread = new Thread(printer.evenThreadRunnable);
        evenThread.setName("even");

        oddThread.start();
        evenThread.start();

        printer.produceNumber();

        evenThread.join();
        oddThread.join();
        Date end = new Date();

        System.out.println("finish！execute time=" + (end.getTime()-start.getTime()) + "ms");
    }
}
