package com.luckystone.algorithm;

import com.luckystone.algorithm.utils.CryptUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 短链接生成算法：
 * 生产环境中，Map可用redis或者mysql代替
 */
public class TinyUrlCodec {

    private static final String SURL_PREFIX = "https://t.cn/";
    private static final int SURL_MULTIPLE_FACTOR = 3;//默认乘数
    private static final int SURL_MD5_PRE_COUNT = 2;
    private static final int SURL_ENCRYPT_FACTOR = 98674321;//加密UID因子
    private static final String SURL_MD5_FACTOR = "SUPREDSX";//加密UID因子
    private static String key = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    int COUNTER = 0;
    Map<String, Integer> url_count_map = new HashMap<String, Integer>();
    Map<Integer, String> count_url_map = new HashMap<Integer, String>();

    // Encodes a URL to a shortened URL.
    public String encode(String longUrl) {
        COUNTER++;
        String shortUrl = longToShortUrl(COUNTER);
        url_count_map.put(longUrl, COUNTER);
        count_url_map.put(COUNTER, longUrl);

        return shortUrl;
    }

    String longToShortUrl(int counter) {
        String shortUrl = longEncode(counter);
        return SURL_PREFIX + shortUrl;
    }

    String longEncode(int counter) {
        String uidStr = base10ToBase62(counter * SURL_MULTIPLE_FACTOR + SURL_ENCRYPT_FACTOR);
        String uidSign = CryptUtils.md5(uidStr + SURL_MD5_FACTOR).substring(0, SURL_MD5_PRE_COUNT);
        return uidSign + uidStr; //Add 2 byte md5 data on the head.
    }

    String base10ToBase62(int counter) {
        StringBuilder sb = new StringBuilder();
        if (counter == 0) return "0";
        while (counter > 0) {
            sb.append(key.charAt(counter % 62));
            counter /= 62;
        }
        sb.reverse();
        while (sb.length() < 6) {
            sb.insert(0, "0");
        }
        return sb.toString();
    }

    // Decodes a shortened URL to its original URL.
    public String decode(String shortUrl) {
        String base62 = shortUrl.substring(SURL_PREFIX.length());
        String md5Subx = base62.substring(0, SURL_MD5_PRE_COUNT);
        base62 = base62.substring(2, base62.length());
        String md5check = CryptUtils.md5(base62 + SURL_MD5_FACTOR).substring(0, 2);
        if(!md5Subx.equals(md5check)) return null;
        int base10 = base62ToBase10(base62);
        base10 = (base10 - SURL_ENCRYPT_FACTOR) / SURL_MULTIPLE_FACTOR;
        return count_url_map.get(base10);
    }

    int base62ToBase10(String shortUrl) {
        int base10 = 0;
        for (char c : shortUrl.toCharArray()) {
            base10 = base10 * 62 + key.indexOf(c);
        }
        return base10;
    }

    public static void main(String[] args) {
        TinyUrlCodec codec = new TinyUrlCodec();
        String[] urls = {"https://www.baidu.com", "https://www.google.com", "https://www.qq.com"};
        for (String url : urls) {
            String t = codec.encode(url);
            System.out.println(String.format("encode: tiny=%s", t));
            url = codec.decode(t);
            System.out.println(String.format("decode: long=%s", url));
        }
    }
}
