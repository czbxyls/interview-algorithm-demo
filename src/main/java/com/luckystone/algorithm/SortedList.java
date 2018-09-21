package com.luckystone.algorithm;

import com.luckystone.collections.bean.ListNode;

/**
 * 链表归并排序
 * https://leetcode.com/problems/sort-list/
 */
public class SortedList {

    public ListNode sortList(ListNode head) {
        if(head == null) return head;
        ListNode fast = head, slow = head;
        while(fast.next != null && fast.next.next!=null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        //System.out.println("s:" + head.val);
        //排序
        ListNode left = null, right = null;
        if(fast == slow) { //只剩下一个节点或者两个节点
            left = head;
            right = head.next;
            left.next = null;
            return merge(left, right);
        }

        left = head;
        right = slow.next;
        slow.next = null;

        left = sortList(left);
        right = sortList(right);

        return merge(left, right);
    }


    public ListNode merge(ListNode left, ListNode right) {
        if(left == null) return right;
        else if(right == null) return left;
        //System.out.println("m:" + left.val + " " + right.val);
        ListNode head = null, current = null;

        while(left != null && right != null) {
            if(left.val <= right.val) {
                if(current == null) {
                    current = left;
                    head = current;
                }
                else {
                    current.next = left;
                    current = current.next;
                }
                left = left.next;
            } else {
                if(current == null) {
                    current = right;
                    head = current;
                }
                else {
                    current.next = right;
                    current = current.next;
                }
                right = right.next;
            }
        }
        if(left != null) current.next = left;
        else current.next = right;
        return head;
    }


}
