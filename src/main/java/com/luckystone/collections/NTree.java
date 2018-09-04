package com.luckystone.collections;

import com.luckystone.algorithm.bean.Node;

import java.util.ArrayList;
import java.util.List;

public class NTree {

    /**
     * 多叉树前序遍历
     * https://leetcode.com/problems/n-ary-tree-preorder-traversal/description/
     * @param root
     * @return
     */
    public List<Integer> preorder(Node root) {
        List<Integer> list = new ArrayList<Integer>();
        preorder(list, root);
        return list;
    }

    public void preorder(List<Integer> list, Node root) {
        if(root == null) return;
        list.add(root.val);
        for(Node node : root.children) {
            preorder(list, node);
        }
    }


    /**
     * 多叉树中序遍历
     * https://leetcode.com/problems/n-ary-tree-postorder-traversal/description/
     * @param root
     * @return
     */
    public List<Integer> postorder(Node root) {
        List<Integer> list = new ArrayList<Integer>();
        postorder(list, root);
        return list;
    }

    public void postorder(List<Integer> list, Node root) {
        if(root == null) return;
        if(root.children != null && root.children.size() > 0) {
            for(Node node : root.children) {
                postorder(list, node);
            }
        }
        list.add(root.val);
    }
}
