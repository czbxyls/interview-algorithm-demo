package com.luckystone.multithread;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 实现一个死锁
 * 采用经典的哲学家问题
 * 死锁产生的原因：
 * 1. 持有并等待
 * 2. 循环等待
 * 3. 非抢占性
 * 4. 互斥条件
 */
public class DeadLockDemo {

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");

    public static void logger(String data) {
        System.out.println("[" + dateFormat.format(new Date()) + "] " + data);
    }

    public static class Chopstick {

        private boolean isUsing = false;
        private String name;

        public Chopstick(String name) {
            this.name = name;
        }

        public synchronized void pickup(String philosopher) {
            try {
                logger("[" + philosopher + "]申请拿起[" + name + "]");
                if(isUsing) wait();
                isUsing = true;
                logger("[" + philosopher + "]拿起了[" + name + "]");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void putdown(String philosopher) {
            isUsing = false;
            logger("[" + philosopher + "]吃完，放下[" + name + "], 并通知其他等待的哲学家");
            notifyAll();
        }

    }

    public static class Philosopher implements Runnable{

        // 左边的筷子
        private Chopstick leftChopstick;
        // 右边的筷子
        private Chopstick rightChopstick;
        // 哲学家名字
        private String name;

        public Philosopher(String name, Chopstick leftChopstick, Chopstick rightChopstick) {
            this.leftChopstick = leftChopstick;
            this.rightChopstick = rightChopstick;
            this.name = name;
        }

        public void run() {
            eat();
            think();
        }

        public void eat(){
            logger("[" + name + "]饥饿，开始就餐");
            leftChopstick.pickup(name);
            rightChopstick.pickup(name);
            try {
                Thread.sleep((long) (Math.random() * 1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            leftChopstick.putdown(name);
            rightChopstick.putdown(name);
            logger("[" + name + "]结束就餐");
        }

        public void think() {
            try {
                logger("[" + name + "]开始思考");
                Thread.sleep((long) (Math.random() * 1000));
                logger("[" + name + "]结束思考");
            } catch (InterruptedException e) {
                e.printStackTrace();;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        int totalSize = 5;
        List<Chopstick> chopstickList = new ArrayList<Chopstick>(totalSize);
        List<Philosopher> philosopherList = new ArrayList<Philosopher>(totalSize);
        for(int i=1;i<=totalSize;i++) {
            chopstickList.add(new Chopstick("筷子" + i));
        }
        for(int i=1;i<=totalSize;i++) {
            philosopherList.add(new Philosopher("哲学家" + i,
                    chopstickList.get(i-1), chopstickList.get(i%totalSize)));
        }

        ExecutorService excuter = Executors.newFixedThreadPool(totalSize);
        for(int i=0;i<totalSize;i++) {
            excuter.submit(philosopherList.get(i));
        }
        excuter.shutdown();
        while (true) {
            if (excuter.isTerminated()) {
                logger("就餐结束了！");
                break;
            }
            Thread.sleep(200);
        }
    }
}
