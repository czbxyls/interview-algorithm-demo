package com.luckystone.algorithm;

import java.util.Arrays;

/**
 * 经典算法：KMP算法：字符串查找字串问题
 * https://blog.csdn.net/v_july_v/article/details/7041827
 *
 */
public class KMPStr {

    public int strStr(String haystack, String needle) {
        if(needle.length() == 0) return 0;
        if(haystack.length() == 0) return -1;
        int [] next = next(needle);
        int i = 0, j = 0;
        while(i<haystack.length()&&j<needle.length()) {
            if(j==-1 || haystack.charAt(i) == needle.charAt(j)) {
                i++; j++;
            } else {
                j = next[j];
            }
        }
        if(j == needle.length()) return i-j;
        else return -1;
    }

    public int [] next(String needle) {
        int [] next = new int[needle.length()];
        int i = 0, j = -1;
        next[i] = j;

        while(i < needle.length() - 1) {
            if(j == -1 || needle.charAt(i) == needle.charAt(j)) {
                i++; j++;
                next[i] = j;
            } else {
                j = next[j];
            }
        }
        return next;
    }

    public static void main(String[] args) {
        KMPStr kmpStr = new KMPStr();
        System.out.println(Arrays.toString(kmpStr.next("ABCDABD")));

    }
}
