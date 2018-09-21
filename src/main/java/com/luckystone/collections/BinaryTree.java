package com.luckystone.collections;

import com.luckystone.collections.bean.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;


public class BinaryTree {

    /**
     * 树的中序遍历：用栈实现
     * leetcode: https://leetcode.com/problems/binary-tree-inorder-traversal/description/
     * @param root
     * @return
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();

        if(root == null) return list;
        TreeNode node = root;
        while(node != null || !stack.isEmpty()) {
            while(node != null) {
                stack.push(node);
                node = node.left;
            }
            node = stack.pop();
            list.add(node.val);
            node = node.right;
        }
        return list;
    }

    /**
     * 树的前序遍历：用栈实现
     * leetcode: https://leetcode.com/problems/binary-tree-preorder-traversal/
     * @param root
     * @return
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();

        if(root == null) return list;

        stack.push(root);
        TreeNode node = null;
        while(!stack.isEmpty()) {
            node = stack.pop();
            list.add(node.val);
            if(node.right!=null) stack.push(node.right);
            if(node.left!=null) stack.push(node.left);
        }
        return list;
    }

}
