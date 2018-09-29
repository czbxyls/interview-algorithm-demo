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

    /**
     * 树的后序遍历：用栈实现
     * 维护一个last节点
     * leetcode: https://leetcode.com/problems/binary-tree-preorder-traversal/
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        Stack<TreeNode> stack = new Stack<>();

        if(root == null) return list;

        stack.push(root);

        TreeNode node = null;
        TreeNode last = null;
        while(!stack.isEmpty()) {
            node = stack.peek();
            if((node.left==null&&node.right==null)
                    || (node.right == null && node.left == last)
                    || (last == node.right)) {
                list.add(node.val);
                last = node;
                stack.pop();
            } else {
                if(node.right != null) stack.push(node.right);
                if(node.left != null) stack.push(node.left);
            }
        }
        return list;
    }

    /**
     * https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/submissions/
     * 求两个子节点的公共父节点
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null || root == p || root == q) return root;
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);
        if(left != null && right != null) return root;
        if(right != null) return right;
        if(left != null) return left;
        return null;
    }



    /**
     * https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/description/
     * 将一个有序的数组转成一颗二叉搜索树
     * @param nums
     * @return
     */
    public TreeNode sortedArrayToBST(int[] nums) {
        if(nums == null || nums.length == 0) return null;
        return sortedArrayToBST(nums, 0, nums.length - 1);
    }

    public TreeNode sortedArrayToBST(int[] nums, int left, int right) {
        if(left == right) return new TreeNode(nums[left]);
        int middle = left + (right - left)/2;
        TreeNode root = new TreeNode(nums[middle]);
        root.left = sortedArrayToBST(nums, left, middle - 1);
        root.right = sortedArrayToBST(nums, middle + 1, right);
        return root;
    }
}
