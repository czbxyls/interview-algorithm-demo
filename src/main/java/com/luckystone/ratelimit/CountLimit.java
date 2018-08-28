package com.luckystone.ratelimit;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 目的：实现一个限流算法
 * 参考：https://juejin.im/entry/57cce5d379bc440063066d09
 * 滑动窗口计数限流
 */
public class CountLimit {

    private long timestamp = getTime();

    private int interval = 1000; //滑动窗口时间间隔
    private int capacity; //滑动窗口范围内处理的请求上限
    private int count = 0; //当前请求数

    public CountLimit(int capacity) {
        this.capacity = capacity;
    }

    private boolean grant() {
        long now = getTime();
        if(now - timestamp < interval) {
            count++;
            return count <= capacity;
        } else {
            timestamp = now;
            count = 1;
            return true;
        }
    }

    private long getTime() {
        return new Date().getTime();
    }

    public static void main(String[] args) throws InterruptedException {
        CountLimit limit = new CountLimit(2);
        int terms = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (true) {
            System.out.println("[" + dateFormat.format(new Date()) + "][第" + terms++ + "个] " + limit.grant());
            Thread.sleep(250);
        }
    }
}
