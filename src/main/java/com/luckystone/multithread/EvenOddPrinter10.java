package com.luckystone.multithread;

import java.util.Date;
import java.util.concurrent.Exchanger;

/**
 * 目的：两个线程交替打印1~100奇偶数
 * 通过两个Exchanger + volatile实现
 * 生产者生产数字，通过Exchanger交换给消费者，并打印出来
 */
public class EvenOddPrinter10 {

    private static final int MAX_NUM = 1000;

    private volatile int consumerIndex = 0;

    private Exchanger<Integer> oddExchanger = new Exchanger<Integer>();

    private Exchanger<Integer> edenExchanger = new Exchanger<Integer>();

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private int print(Exchanger<Integer> exchanger) {
        int value = 0;
        try {
            value = exchanger.exchange(value);
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
                index = print(edenExchanger);
                if(index >= MAX_NUM) break;
            }
            System.out.println("consumer: " + Thread.currentThread().getName() + " finish!");
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            int index = 0;
            while (true) {
                index = print(oddExchanger);
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
            if((value & 1) == 1) oddExchanger.exchange(value);
            else edenExchanger.exchange(value);
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

        EvenOddPrinter10 printer = new EvenOddPrinter10();
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
