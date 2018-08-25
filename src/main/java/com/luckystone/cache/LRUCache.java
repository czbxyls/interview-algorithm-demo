package com.luckystone.cache;

import java.util.HashMap;
import java.util.Iterator;

public class LRUCache<K, V> implements Iterable<K> {

    private Node head;

    private Node tail;

    private HashMap<K, Node> map;

    private int maxSize;

    private int nodeCount;

    private class Node {
        private Node prev;
        private Node next;
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    public LRUCache(int maxSize) {
        this.maxSize = maxSize;
        this.map = new HashMap<K, Node>(maxSize * 4 /3);

        this.head = new Node(null, null);
        this.tail = new Node(null, null);

        head.next = tail;
        tail.prev = head;

        nodeCount = 0;
    }

    public V get(K key) {
        if(!map.containsKey(key)) return null;
        Node node = map.get(key);

        unlinkNode(node);
        appendHead(node);
        return node.value;
    }

    public void put(K key, V value) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            unlinkNode(node);
        } else {
            nodeCount++;
        }

        if(map.size() >= maxSize) { //LRU
            Node removeNode = removeTail();
            map.remove(removeNode.key);
            nodeCount--;
        }

        Node node = new Node(key, value);
        map.put(key, node);
        appendHead(node);

    }

    public Node remove(K key) {
        if (map.containsKey(key)) {
            Node node = map.get(key);
            unlinkNode(node);
            map.remove(node.key);
            nodeCount--;
            return node;
        }
        return null;
    }

    public int size() {
        return nodeCount;
    }

    public String toString() {
        Iterator<K> it = this.iterator();
        StringBuilder sb = new StringBuilder();
        sb.append("size=").append(size());
        if(size()>0) sb.append("|");
        while(it.hasNext()) {
            K key = it.next();
            sb.append("(").append(key).append(":").append(map.get(key).value).append(")");
            if(it.hasNext()) sb.append("-->");
        }
        return sb.toString();
    }

    private void appendHead(Node node) {
        node.next = head.next;
        node.prev = head;

        head.next.prev = node;
        head.next = node;
    }

    private Node removeTail() {
        Node node = tail.prev;
        node.prev.next = tail;
        tail.prev = node.prev;

        return node;
    }

    private void unlinkNode(Node node) {
        Node pre = node.prev;
        Node next = node.next;
        pre.next = next;
        next.prev = pre;

        node.prev = null;
        node.next = null;
    }

    public Iterator<K> iterator() {
        return new Iterator<K>() {

            private Node cur = head.next;

            public boolean hasNext() {
                return cur != tail;
            }

            public K next() {
                Node node = cur;
                cur = cur.next;
                return node.key;
            }

            public void remove() {
                LRUCache.this.remove(cur.key);
            }
        };
    }

    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<Integer, String>(3);
        cache.put(1, "a");

        System.out.println(cache.toString());
        cache.put(2, "b");

        System.out.println(cache.toString());
        cache.put(3, "c");

        System.out.println(cache.toString());

        cache.get(1);
        cache.put(4, "d");
        System.out.println(cache.toString());

        cache.put(5, "f");
        System.out.println(cache.toString());
    }

}
