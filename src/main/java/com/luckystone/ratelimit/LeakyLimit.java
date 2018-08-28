package com.luckystone.ratelimit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 目的：实现一个限流算法
 * 参考：https://juejin.im/entry/57cce5d379bc440063066d09
 * 漏斗算法
 */
public class LeakyLimit {

    private long timestamp = getTime();

    private double water; //当前水的容量
    private int interval = 1000; //水流出的时间间隔，默认s
    private double rate; //水的流出速度
    private int capacity; //漏斗容量

    public LeakyLimit(double rate, int capacity) {
        this.rate = rate;
        this.capacity = capacity;
    }

    private boolean grant() {
        long now = getTime();
        //漏水操作
        water = Math.max(0, water - rate * (now - timestamp)/interval);

        timestamp = now;
        if(water + 1 < capacity) {
            water++;
            return true;
        }
        return false;
    }

    private long getTime() {
        return new Date().getTime();
    }

    public static void main(String[] args) throws InterruptedException {
        LeakyLimit limit = new LeakyLimit(2, 10);
        int terms = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (true) {
            System.out.println("[" + dateFormat.format(new Date()) + "][第" + terms++ + "个] " + limit.grant());
            Thread.sleep(250);
        }
    }
}
