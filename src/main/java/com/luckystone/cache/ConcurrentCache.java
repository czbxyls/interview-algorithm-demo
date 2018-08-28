package com.luckystone.cache;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 目的：实现一个简单基于LRU的缓存
 * 参考：https://lijf.me/2017/06/28/from-weakreference-to-weakhashmap/
 * 来自Tomcat源码ConcurrentCache
 * ConcurrentCache是一种LRU Cache，它将最近没有被使用的数据放在WeakHashMap中，
 * 利用其特性，如果数据被GC回收，那么Cache就会将这个键值对清除，
 * 从而避免长期不使用的数据一直存放在Cache中（强引用）
 * 而不被回收从而导致内存泄漏。
 * @param <K>
 * @param <V>
 */
public class ConcurrentCache<K, V> {
    private final int size;

    private final Map<K,V> eden;

    private final Map<K,V> longterm;

    public ConcurrentCache(int size) {
        this.size = size;
        this.eden = new ConcurrentHashMap<K,V>(size);
        this.longterm = new WeakHashMap<K,V>(size);
    }

    public V get(K k) {
        V v = this.eden.get(k);
        if (v == null) {
            synchronized (longterm) {
                v = this.longterm.get(k);
            }
            if (v != null) {
                this.eden.put(k, v);
            }
        }
        return v;
    }

    public void put(K k, V v) {
        if (this.eden.size() >= size) {
            synchronized (longterm) {
                this.longterm.putAll(this.eden);
            }
            this.eden.clear();
        }
        this.eden.put(k, v);
    }
}
