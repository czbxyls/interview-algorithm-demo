package com.luckystone.collections;

import com.luckystone.collections.bean.ListNode;

public class LinkedList {

    /**
     *  问题：判断一个链表是否存在环
     *  Leetcode: 141. Linked List Cycle https://leetcode.com/problems/linked-list-cycle/description/
     *  遍历链表，一次快（每次获取后面第二个），一次慢（每次获取后面第一个）
     *  若快的节点为null或下一个为null，则不包含环
     *  在若干次之后，若快的节点与慢的节点相同则包含环
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null) {
            fast = fast.next;
            fast = fast.next;
            slow = slow.next;
            if(fast == slow) return true;
        }

        return false;
    }

    /**
     * 问题：判断一个存在环的链表，环的起点位置
     * Leetcode: https://leetcode.com/problems/linked-list-cycle-ii/description/
     * 参考：https://www.cnblogs.com/xudong-bupt/p/3667729.html
     * 在环上相遇后，记录第一次相遇点为Pos，连接点为Join，
     * 假设头结点到连接点的长度为LenA，连接点到第一次相遇点的长度为x，环长为R。
     * 第一次相遇时，slow走的长度 S = LenA + x;
     * 第一次相遇时，fast走的长度 2S = LenA + n*R + x;
     * 所以可以知道，LenA + x =  n*R;　LenA = n*R -x;
     * 因此：LenA = (n-1) * R + (R-x)
     * 表示：如果从第一个节点的位置开始，走到LenA位置，相当于从x位置开始，在环上转到起始位置
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;

        while(fast != null && fast.next != null) {
            fast = fast.next;
            fast = fast.next;
            slow = slow.next;
            if(fast == slow) break;
        }
        if(fast == null || fast.next == null) return null;
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }
        return slow;
    }


    /**
     * 扩展：https://leetcode.com/problems/find-the-duplicate-number/description/
     * 问题：数组上找重复数，可以用求环的起点位置的方法解决
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        int fast = 0;
        int slow = 0;

        while(true) {
            fast = nums[nums[fast]];
            slow = nums[slow];
            if(fast == slow) break;
        }
        slow = 0;
        while (slow != fast) {
            fast = nums[fast];
            slow = nums[slow];
        }
        return fast;
    }


    /**
     * 问题：反转一个链表
     * LeetCode: https://leetcode.com/problems/reverse-linked-list/description/
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        if(head == null || head.next == null) return head;

        ListNode pre = head;
        ListNode cur = head.next;
        pre.next = null;

        ListNode next;

        while(cur != null) {
            next = cur.next;
            cur.next = pre;

            pre = cur;
            cur = next;
        }
        return pre;
    }

    /**
     * 求链表的中间节点
     * 1. 先求长度，再取中间，遍历1.5n
     * 2. 快慢节点，快走两步，慢走一步，快走到终点，慢正好在中间节点
     * @param head
     * @return
     */
    public ListNode middleNode(ListNode head) {
        if(head == null) return head;
        int length = 1;
        ListNode current = head;
        while(current != null) {
            length++;
            current = current.next;
        }

        length = length/2;
        current = head;
        while(length > 0) {
            current = current.next;
            length--;
        }
        return current;
    }

    /**
     * 求两个链表的第一个交集节点
     * https://leetcode.com/problems/intersection-of-two-linked-lists/
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        int len1 = 0, len2 = 0;

        ListNode nodeA = headA;
        while(nodeA!=null) {
            len1++;
            nodeA = nodeA.next;
        }
        ListNode nodeB = headB;
        while(nodeB!=null) {
            len2++;
            nodeB = nodeB.next;
        }
        int count = Math.abs(len1 - len2);
        nodeA = headA;
        nodeB = headB;

        if(len1 > len2) {
            while(count-- > 0) {
                nodeA = nodeA.next;
            }
        } else if(len1 < len2) {
            while(count-- > 0) {
                nodeB = nodeB.next;
            }
        }
        while(nodeA!=nodeB) {
            nodeA = nodeA.next;
            nodeB = nodeB.next;
        }
        return nodeA;
    }
}
