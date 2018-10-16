package com.luckystone.collections;

import com.luckystone.collections.bean.TreeNode;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.cnblogs.com/AnnieKim/archive/2013/06/15/MorrisTraversal.html
 * 基于Morris算法的二叉树遍历
 * 不需要辅助栈、不需要递归
 */
public class BinaryTreeMorris {


    /**
     * leetcode: https://leetcode.com/problems/binary-tree-inorder-traversal/description/
     * Morris中序遍历法：特点是利用线索化二叉树的思想，左节点的最右节点的右节点记录为当前节点，作为其后继节点
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        TreeNode cur = root, prev = null;
        List<Integer> res = new ArrayList<>();
        while (cur != null) {
            if(cur.left == null) {
                res.add(cur.val);
                cur = cur.right;
            } else {
                prev = cur.left;
                while(prev.right!=null&&prev.right!=cur) {
                    prev = prev.right;
                }
                if(prev.right == null) {
                    prev.right = cur;
                    cur = cur.left;
                } else {
                    prev.right = null;
                    res.add(cur.val);
                    cur = cur.right;
                }
            }
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/binary-tree-preorder-traversal/
     * Morris前序遍历法：中序遍历微调下即可
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        TreeNode cur = root, prev = null;
        List<Integer> res = new ArrayList<>();
        while (cur != null) {
            if(cur.left == null) {
                res.add(cur.val);
                cur = cur.right;
            } else {
                prev = cur.left;
                while(prev.right!=null&&prev.right!=cur) {
                    prev = prev.right;
                }
                if(prev.right == null) {
                    res.add(cur.val);
                    prev.right = cur;
                    cur = cur.left;
                } else {
                    prev.right = null;
                    cur = cur.right;
                }
            }
        }
        return res;
    }


    /**
     * https://leetcode.com/problems/binary-tree-postorder-traversal/
     * Morris后序遍历
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        //新增一个节点dummy，root作为dummy的左子树
        TreeNode dummy = new TreeNode(-1);
        dummy.left = root;
        TreeNode cur = dummy, prev = null;
        List<Integer> res = new ArrayList<>();
        while (cur != null) {
            if(cur.left == null) {
                cur = cur.right;
            } else {
                prev = cur.left;
                while(prev.right!=null&&prev.right!=cur) {
                    prev = prev.right;
                }
                if(prev.right == null) {
                    prev.right = cur;
                    cur = cur.left;
                } else {
                    reverseData(res, cur.left, prev);
                    prev.right = null;
                    cur = cur.right;
                }
            }
        }
        return res;
    }

    public void reverseData(List<Integer> res, TreeNode from, TreeNode to) {
        TreeNode cur = from;
        int begin = res.size();
        while(true) {
            res.add(cur.val);
            if(cur == to) break;
            cur = cur.right;
        }
        int end = res.size() - 1;
        Integer tmp;
        while(begin<end) {
            tmp = res.get(begin);
            res.set(begin, res.get(end));
            res.set(end, tmp);
            begin++;
            end--;
        }
    }
}
