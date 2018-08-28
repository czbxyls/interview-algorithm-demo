package com.luckystone.ratelimit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 目的：实现一个限流算法
 * 参考：https://juejin.im/entry/57cce5d379bc440063066d09
 * 令牌环算法
 */
public class TokenBucketLimit {

    private long timestamp = getTime();

    private double tokens; //令牌的数量
    private int interval = 1000; //放入速度的时间单位，默认s
    private double rate; //令牌放入速度
    private int capacity; //桶的容量

    public TokenBucketLimit(double rate, int capacity) {
        this.rate = rate;
        this.capacity = capacity;
    }

    private boolean grant() {
        long now = getTime();
        tokens = Math.min(capacity, tokens + rate * (now - timestamp)/interval);

        timestamp = now;
        if(tokens < 1) { //是否有令牌
            return false;
        }
        tokens--; //领取令牌
        return true;
    }

    private long getTime() {
        return new Date().getTime();
    }

    public static void main(String[] args) throws InterruptedException {
        TokenBucketLimit limit = new TokenBucketLimit(2, 10);
        int terms = 0;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        while (true) {
            System.out.println("[" + dateFormat.format(new Date()) + "][第" + terms++ + "个] " + limit.grant());
            Thread.sleep(250);
        }
    }
}
