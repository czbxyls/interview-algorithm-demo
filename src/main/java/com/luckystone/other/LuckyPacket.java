package com.luckystone.other;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * 参考：https://blog.csdn.net/lb_383691051/article/details/79379384
 * 微信抢红包的算法思路：额度在0.01和(剩余平均值x2)之间。
 * 例如：发100块钱，总共10个红包，那么平均值是10块钱一个，那么发出来的红包的额度在0.01元～20元之间波动。
 * 当前面3个红包总共被领了40块钱时，剩下60块钱，总共7个红包，那么这7个红包的额度在：0.01～（60/7*2）=17.14之间。
 */
public class LuckyPacket {

    public List<Double> getLuckyPacket(double totalMoney, int number) {
        int cur = 0;
        int leftNumber = number;
        int leftMoney = (int)(100 * totalMoney);
        Random random = new Random();

        List<Double> result = new ArrayList<Double>();
        while(true) {
            if(cur == number - 1) {
                result.add(roundHalf(leftMoney/100.0));
                break;
            }
            int money = 1 + random.nextInt(2 * leftMoney/leftNumber - 1);//左闭右开
            result.add(roundHalf(money/100.0));
            cur++;
            leftNumber--;
            leftMoney -= money;
        }
        return result;
    }

    public double roundHalf(double number) {
        BigDecimal decimal = new BigDecimal(number);
        return decimal.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    public static void main(String[] args) {
        LuckyPacket bag = new LuckyPacket();
        List<Double> result = bag.getLuckyPacket(100, 10);
        System.out.println(Arrays.toString(result.toArray()));
    }
}
