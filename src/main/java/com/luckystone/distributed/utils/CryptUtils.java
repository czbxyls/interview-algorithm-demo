package com.luckystone.distributed.utils;

import java.security.MessageDigest;

public class CryptUtils {

    private static final char hexChar[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8' , '9', 'a', 'b', 'c', 'd', 'e', 'f'};


    /**
     *
     * @param input 要进行加密的字符串
     * @return  字符串的md5值
     */
    public static String md5(String input) {
        return md5(input, null) ;
    }



    /**
     *
     * @param input 要进行加密的字符串
     * @return  字符串的md5值
     */
    public static String md5(String input, String charset) {
        if(input==null) return null ;
        //md5加密算法的加密对象为字符数组，这里是为了得到加密的对象
        try
        {
            byte[] b = charset==null ? input.getBytes() : input.getBytes(charset);
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(b);
            byte[] bytes = md.digest();// 进行加密并返回字符数组
            char md5[] = new char[bytes.length << 1];
            int len = 0;
            //将字符数组转换成十六进制串，形成最终的密文
            for (int i = 0; i < bytes.length; i++) {
                byte val = bytes[i];
                md5[len++] = hexChar[(val >>> 4) & 0xf];
                md5[len++] = hexChar[val & 0xf];
            }
            return new String(md5);
        } catch (Exception e) {throw new RuntimeException(e);}
    }
}
