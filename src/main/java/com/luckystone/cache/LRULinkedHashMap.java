package com.luckystone.cache;

import java.util.*;

/**
 * 目的：实现一个简单基于LRU的缓存
 * 参考：https://crossoverjie.top/%2F2018%2F04%2F07%2Falgorithm%2FLRU-cache%2F?nsukey=j6f%2FOSx73gCHekeiq0uKZfTRra2OmDv8SztIy6zIuiNoKeey7gJhSbF2tungF0TYSxd3TdEG3wMTakoXMtm0itDLyY5qiKo%2BHnq72P35Sh6sIrkJaICAyYKvLQ9kp6u7xn3rK5Cr0Vt9w19PnQSmv2g3JM8eTpiApsjSE%2BWtL2OUNl1NWBb10LxnyjGiyBAVDLkCcjyT7%2F%2FcXGD90MwuJA%3D%3D
 * 基于LinkedHashMap实现LRU算法
 * @param <K>
 * @param <V>
 */
public class LRULinkedHashMap<K,V>  {

    private LinkedHashMap<K,V> cachedHashMap;

    private int cacheSize;

    public LRULinkedHashMap(final int cacheSize) {
        this.cacheSize = cacheSize;
        this.cachedHashMap = new LinkedHashMap<K, V>(16, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K,V> eldest) {
                return size() > cacheSize;
            }
        };
    }

    public void put(K key,V value) {
        this.cachedHashMap.put(key, value);
    }

    public V get(K key) {
        return this.cachedHashMap.get(key);
    }

    public Collection<Map.Entry<K, V>> getAll() {
        return new ArrayList<Map.Entry<K, V>>(cachedHashMap.entrySet());
    }

    public int size() {
        return cachedHashMap.size();
    }

    public static void main(String[] args) {
        LRULinkedHashMap<String, String> cache = new LRULinkedHashMap<String, String>(4);
        cache.put("aa", "hello");
        cache.put("bb", "world!");
        cache.put("cc", "lucky");

        System.out.println("get aa: " + cache.get("aa"));
        cache.put("dd", "stone!");

        System.out.println("get bb: " + cache.get("bb"));
        cache.put("ee", "github");
        cache.put("ff", "gitlab");

        System.out.println("cache size=" + cache.size());
        System.out.println(Arrays.toString(cache.getAll().toArray()));
    }
}
