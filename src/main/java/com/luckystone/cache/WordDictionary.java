package com.luckystone.cache;

import java.util.HashSet;

public class WordDictionary {

    Trie trie;
    HashSet<Integer> set;

    /** Initialize your data structure here. */
    public WordDictionary() {
        this.trie = new Trie();
        this.set = new HashSet<>();
    }

    /** Adds a word into the data structure. */
    public void addWord(String word) {
        set.add(word.length());
        trie.insert(word);
    }

    /** Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter. */
    public boolean search(String word) {
        if(!set.contains(word.length())) return false;
        return search(trie.root, word, -1);
    }

    public boolean search(Node node, String word, int index) {
        if(node != trie.root) {
            //System.out.println(word.charAt(index) + " " + node.c  + " " + (word.charAt(index) == node.c));
            if(word.charAt(index) != node.c && word.charAt(index) != '.') return false;
            else{
                if(index >= word.length() - 1) {
                    return node.end;
                }
            }
        }
        boolean find = false;
        for(Node child : node.children) {
            if(child!=null) find = search(child, word, index+1);
            if(find) return true;
        }
        return false;
    }

    class Node {
        char c;
        boolean end;
        Node[] children;

        public Node(char c){
            this.c = c;
            this.end = false;
            this.children = new Node[26];
        }
    }

    class Trie {
        Node root;

        public Trie() {
            root = new Node('\0');
        }

        public void insert(String word) {
            Node cur = root;
            int i = 0;
            for (char c : word.toCharArray()) {
                i = (int)(c-'a');
                if(cur.children[i] == null) {
                    cur.children[i] = new Node(c);
                }
                cur = cur.children[i];
            }
            cur.end = true;
        }
    }

    public static void main(String[] args) {
        WordDictionary wordDictionary = new WordDictionary();
        wordDictionary.addWord("bad");
        wordDictionary.addWord("dad");
        wordDictionary.addWord("mad");

       System.out.println(wordDictionary.search("bad"));
        System.out.println(wordDictionary.search("pad"));
        System.out.println(wordDictionary.search("b.."));
        System.out.println(wordDictionary.search(".ad"));


    }
}
