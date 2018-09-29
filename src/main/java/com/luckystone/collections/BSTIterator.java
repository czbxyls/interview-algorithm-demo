package com.luckystone.collections;

import com.luckystone.collections.bean.TreeNode;
import java.util.Stack;

/**
 * 实现一个二叉树的迭代器
 *
 */
public class BSTIterator {

    Stack<TreeNode> stack = new Stack<>();
    TreeNode root = null;

    public BSTIterator(TreeNode root) {
        while(root!=null) {
            stack.push(root);
            root = root.left;
        }
    }

    /** @return whether we have a next smallest number */
    public boolean hasNext() {
        while( root!=null ) {
            stack.push(root);
            root = root.left;
        }
        return root!=null || !stack.empty();
    }

    /** @return the next smallest number */
    public int next() {
        TreeNode result = stack.pop();
        root = result.right;
        return result.val;
    }
}
