package com.luckystone.collections;

/**
 * https://leetcode.com/problems/design-linked-list/
 * 设计一个链表
 */
public class MyLinkedList {

    private class Node {
        int val;
        Node next;

        public Node(int v) {
            this.val = v;
        }
    }

    private Node head = null;
    private Node tail = null;
    private int size = 0;

    /** Initialize your data structure here. */
    public MyLinkedList() {

    }

    /** Get the value of the index-th node in the linked list. If the index is invalid, return -1. */
    public int get(int index) {
        if(head == null) return -1;
        int i = 0;
        Node node = head;
        while(node!=null && i++ <index) {
            node = node.next;
        }
        return i==index+1 ? node.val : -1;
    }

    /** Add a node of value val before the first element of the linked list. After the insertion, the new node will be the first node of the linked list. */
    public void addAtHead(int val) {
        Node node = new Node(val);
        if(head != null) {
            node.next = head;
        } else {
            tail = node;
        }
        head = node;
        size++;
    }

    /** Append a node of value val to the last element of the linked list. */
    public void addAtTail(int val) {
        Node node = new Node(val);
        if(tail != null) {
            tail.next = node;
        } else {
            head = node;
        }
        tail = node;
        size++;
    }

    /** Add a node of value val before the index-th node in the linked list. If index equals to the length of linked list, the node will be appended to the end of linked list. If index is greater than the length, the node will not be inserted. */
    public void addAtIndex(int index, int val) {
        if(index == 0) {
            addAtHead(val);
            return;
        }
        if(index == size) {
            addAtTail(val);
            return;
        }

        int i = 0;
        Node node = head;
        Node prev = null;
        while(node!=null && i++<index) {
            prev = node;
            node = node.next;
        }
        if(i==index+1 && prev != null) {
            Node cur = new Node(val);
            cur.next = prev.next;
            prev.next = cur;
            size++;
        }
    }

    /** Delete the index-th node in the linked list, if the index is valid. */
    public void deleteAtIndex(int index) {
        if(size == 0) return;
        else if(size == 1 && index == 0) {
            head = null;
            tail = null;
            return;
        } else if(size > 1 &&index == 0) {
            head = head.next;
            return;
        }
        int i = 0;
        Node node = head;
        Node prev = null;
        while(node!=null && i++<index) {
            prev = node;
            node = node.next;
        }
        if(i==index+1 && prev!= null) {
            prev.next = node.next;
            if(index == size - 1) {
                tail = prev;
            }
            size--;
        }
    }

    public void print() {
        Node node = head;
        while(node != null) {
            System.out.print(node.val);
            node = node.next;
            if(node!=null) System.out.print(",");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        MyLinkedList myLinkedList = new MyLinkedList();
        myLinkedList.addAtIndex(0, 1);

        System.out.println(myLinkedList.get(0));
        myLinkedList.print();

        myLinkedList.deleteAtIndex(0);
        myLinkedList.print();
    }
}
