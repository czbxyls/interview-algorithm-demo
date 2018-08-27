package com.luckystone.multithread;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;

/**
 * 两个线程交替打印1~100奇偶数
 * synchronized + wait/notify实现
 */
public class EvenOddPrinter6 {

    private static final int MAX_NUM = 1000;

    private BlockingQueue<Integer> oddQueue = new ArrayBlockingQueue<Integer>(MAX_NUM/2);

    private BlockingQueue<Integer> edenQueue = new ArrayBlockingQueue<Integer>(MAX_NUM/2);

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private int print(BlockingQueue<Integer> queue) {
        int value = 0;
        try {
            value = queue.take();
            System.out.println("Thread-" + Thread.currentThread().getName() + ": " + value);
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        return value;
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

    public boolean isCanPut() {
        return edenQueue.isEmpty() && oddQueue.isEmpty();
    }

    public void putValue(int value) {
        try {
            if((value & 1) == 1) oddQueue.put(value);
            else edenQueue.put(value);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void produceNumber() {
        int i = 1;
        while(i<=EvenOddPrinter6.MAX_NUM) {
            if(isCanPut()) {
                putValue(i);
                i++;
            }
        }
        System.out.println("producer finish!");
    }

    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter6 printer = new EvenOddPrinter6();
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
