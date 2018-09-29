package com.luckystone.algorithm;
import java.util.*;

/**
 * https://leetcode.com/problems/top-k-frequent-words/
 *
 * 经典算法：Trie树求单词词典中单词出现频率最高的K个单词
 */
public class TopKFrequent {

    public List<String> topKFrequent(String[] words, int k) {
        Trie trie = new Trie();
        int index = 0;
        for(String word : words) {
            trie.insert(word, index++);
        }
        List<String> list = new ArrayList<String>();
        Queue<Node> minHeap = new PriorityQueue<Node>(new Comparator<Node>() {
            @Override
            public int compare(Node o1, Node o2) {
                if(o1.count == o2.count) return words[o2.end].compareTo(words[o1.end]);
                return o1.count < o2.count ? -1 : 1;
            }
        });
        dfs(minHeap, trie.root, k);
        while (!minHeap.isEmpty()) {
            list.add(words[minHeap.poll().end]);
        }
        Collections.reverse(list);
        return list;
    }

    public void dfs(Queue<Node> queue, Node root, int k) {
        if(root.end >= 0) {
            queue.add(root);
            if(queue.size() > k) queue.poll();
        }
        for(Node node  : root.children) {
            if(node != null) dfs(queue, node, k);
        }
    }

    class Node {
        char c;
        Node[] children;
        int count;
        int end;

        public Node(char c){
            this.c = c;
            this.count = 0;
            this.children = new Node[26];
            this.end = -1;
        }
    }

    class Trie {
        Node root;

        public Trie() {
            root = new Node('\0');
        }

        public void insert(String word, int index) {
           Node cur = root;
            int i = 0;
            for (char c : word.toCharArray()) {
                i = (int)(c-'a');
                if(cur.children[i] == null) {
                    cur.children[i] = new Node(c);
                }
                cur = cur.children[i];
            }
            cur.end = index;
            cur.count++;
        }
    }

    public static void main(String[] args) {
        TopKFrequent topKFrequent = new TopKFrequent();
        System.out.println(Arrays.toString(topKFrequent.topKFrequent(new String[]{"i", "love", "leetcode", "i", "love", "coding"}
        , 1).toArray()));
    }
}
