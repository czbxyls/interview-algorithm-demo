package com.luckystone.multithread;

import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 两个线程交替打印1~100奇偶数
 * 自定义AQS实现
 */
public class EvenOddPrinter4 {

    private static final int MAX_NUM = 1000;

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private volatile OddEdenSync sync = new OddEdenSync(1);

    private CyclicBarrier barrier = new CyclicBarrier(2);


    private void print(int index) {
        System.out.println("Thread-" + Thread.currentThread().getName() + ": " + index);
    }

    public class EvenThreadRunnable implements Runnable {

        public void run() {
            int index = 2;
            while(index <= MAX_NUM) {
                try {
                    //System.out.println("even acquire=" + index);
                    System.out.println(Arrays.toString(sync.getQueuedThreads().toArray()));

                    sync.acquire(0);
                    //System.out.println("even finish acquire=" + index);
                    print(index);
                    //System.out.println(sync.getPermissiones());
                    //System.out.println("even release=" + (index + 1));
                    boolean ret = sync.release(1);
                    //System.out.println("even finish release=" + (index + 1) + ", return=" + ret);
                    index += 2;
                    barrier.await();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            int index = 1;
            while(index <= MAX_NUM) {
                try {
                    //System.out.println("current round: " + index);
                    //System.out.println("odd acquire=" + (index));
                    System.out.println(Arrays.toString(sync.getQueuedThreads().toArray()));
                    sync.acquire(1);
                    //System.out.println(sync.getPermissiones());
                    //System.out.println("odd finish acquire=" + (index) + ", index=" + index);
                    print(index);
                    //System.out.println("odd release=" + (index + 1));
                    boolean ret = sync.release(0);
                    System.out.println("odd finish release=" + (index + 1) + ", return=" + ret);
                    index += 2;
                    barrier.await();
                }catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    static class OddEdenSync extends AbstractQueuedSynchronizer {

        private static final long serialVersionUID = 1L;


        public OddEdenSync(int state) {
            setState(state);
        }

        public int getPermissiones() {
            return getState();
        }

        @Override
        protected boolean tryAcquire(int flag) {
            System.out.println(Thread.currentThread().getName() + ":tryAcquire=" + flag + ", current=" + getState());
            return getState() == flag;
        }

        @Override
        protected boolean tryRelease(int flag) {
            System.out.println(Thread.currentThread().getName() + ":tryRelease=" + flag + ", current=" + getState());

            int c = getState();
            if (c != flag) {
                if (compareAndSetState(c, flag)) {
                    System.out.println(Thread.currentThread().getName() +": compareAndSetState=true, status=" + getState());
                    return true;
                } else {
                    System.out.println(Thread.currentThread().getName() +": compareAndSetState=false, status="+ getState());
                    return false;
                }
            } else {
                System.out.println(Thread.currentThread().getName() +": equals|compareAndSetState=true, status="+ getState());
                return true;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter4 printer = new EvenOddPrinter4();

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
