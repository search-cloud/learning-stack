package io.vincent.learning.stack.algorithm.leetcode;

import io.vincent.learning.stack.algorithm.leetcode.tree.TreeNode;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Vincent on 2019/3/25.
 *
 * @author Vincent
 * @since 1.0, 2019/3/25
 */
public class No145TreePostorderTraversal {

    List<Integer> result = new ArrayList<>();

    public List<Integer> postorderTraversal(TreeNode root) {
        if (root != null)
            inorder(root);

        return result;
    }

    private void inorder(TreeNode root) {
        if (root.left != null) {
            inorder(root.left);
        }
        if (root.right != null) {
            inorder(root.right);
        }
        result.add(root.val);
    }

    public List<Integer> postorderTraversal1(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) return null;

        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.peek();

            if (node.right != null || node.left != null) {
                if (node.right != null) {
                    stack.push(node.right);
                    node.right = null;
                }
                if (node.left != null) {
                    stack.push(node.left);
                    node.left = null;
                }
            } else {
                result.add(node.val);
                stack.pop();
            }
        }

        return result;
    }
}
