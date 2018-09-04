package com.luckystone.algorithm.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashFunction {

    /**md5*/
    private MessageDigest md5 = null;

    /**
     * 使用MD5算法
     * @param key
     * @return
     */
    public long hash(String key) {
        if (md5 == null) {
            try {
                md5 = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException e) {
                throw new IllegalStateException("no md5 algorythm found");
            }
        }

        md5.reset();
        md5.update(key.getBytes());
        byte[] bKey = md5.digest();
        long res = ((long) (bKey[3] & 0xFF) << 24) | ((long) (bKey[2] & 0xFF) << 16) | ((long) (bKey[1] & 0xFF) << 8)
                | (long) (bKey[0] & 0xFF);
        return res & 0xffffffffL;
    }

//    public long hash(String key) {
//        final int p = 16777619;
//        int hash = (int) 2166136261L;
//        for (int i = 0; i < key.length(); i++)
//            hash = (hash ^ key.charAt(i)) * p;
//        hash += hash << 13;
//        hash ^= hash >> 7;
//        hash += hash << 3;
//        hash ^= hash >> 17;
//        hash += hash << 5;
//        return hash;
//    }
}
