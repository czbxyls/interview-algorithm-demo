package com.luckystone.multithread;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.Date;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

/**
 * 目的：两个线程交替打印1~100奇偶数
 * 通过PipedOutputStream和PipedInputStream(管道) + semaphore(同步)实现
 * 注意：该案例仅用于演示不同线程间如何通过管道传递数据，
 * 而管道方式更适用于解决生产者消费者问题，该demo只是为了学习用
 */
public class EvenOddPrinter8 {

    private static final int MAX_NUM = 1000;

    private PipedOutputStream out = new PipedOutputStream();

    private PipedInputStream in = new PipedInputStream();

    private Semaphore oddSem = new Semaphore(1);

    private Semaphore evenSem = new Semaphore(0);

    private EvenThreadRunnable evenThreadRunnable = new EvenThreadRunnable();

    private OddThreadRunnable oddThreadRunnable = new OddThreadRunnable();

    private int print(int value) {
        System.out.println("Thread-" + Thread.currentThread().getName() + ": " + value);
        return value;
    }

    public class EvenThreadRunnable implements Runnable {

        public void run() {
            try {
                int index = 0;
                while (true) {
                    evenSem.acquire();
                    index = readValue();
                    System.out.println("round=" + index);
                    oddSem.release();
                    if(index < 0)  continue;
                    index = print(index);
                    if(index >= MAX_NUM) break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("consumer: " + Thread.currentThread().getName() + " finish!");
        }
    }

    public class OddThreadRunnable implements Runnable {

        public void run() {
            try {
                int index = 0;
                while (true) {
                    oddSem.acquire();
                    index = readValue();
                    System.out.println("round=" + index);
                    evenSem.release();
                    if(index < 0)  continue;
                    index = print(index);
                    if(index >= MAX_NUM-1) break;
                }
            }catch (Exception ex) {
                ex.printStackTrace();
            }
            System.out.println("consumer: " + Thread.currentThread().getName() + " finish!");
        }
    }

    private void putValue(int value) {
        try {
            byte[] data = new byte[4];
            data = intToBytes(value);
            out.write(data);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int readValue() {
        try {
            byte[] data = new byte[4];
            int ret = in.read(data, 0, 4);
            if(ret == -1) return -1;
            return bytesToInt(data, 0);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void produceNumber() {
        try {
            int i = 1;
            while(i<=EvenOddPrinter8.MAX_NUM) {
                putValue(i);
                i++;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        System.out.println("producer finish!");
    }

    public void connect() {
        try {
            in.connect(out);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static byte[] intToBytes(int value)
    {
        byte[] byte_src = new byte[4];
        byte_src[3] = (byte) ((value & 0xFF000000)>>24);
        byte_src[2] = (byte) ((value & 0x00FF0000)>>16);
        byte_src[1] = (byte) ((value & 0x0000FF00)>>8);
        byte_src[0] = (byte) ((value & 0x000000FF));
        return byte_src;
    }

    public static int bytesToInt(byte[] ary, int offset) {
        int value;
        value = (int) ((ary[offset]&0xFF)
                | ((ary[offset+1]<<8) & 0xFF00)
                | ((ary[offset+2]<<16)& 0xFF0000)
                | ((ary[offset+3]<<24) & 0xFF000000));
        return value;
    }

    public static void main(String[] args) throws Exception {
        Date start = new Date();

        EvenOddPrinter8 printer = new EvenOddPrinter8();

        printer.connect();

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
