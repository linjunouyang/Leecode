package DepthFirstSearch;

import java.util.ArrayDeque;
import java.util.Deque;

public class _112PathSum {
    /**
     * 1. Recursion, DFS (pre-order)
     *
     * Time complexity: O(n)
     *
     * Space complexity:
     * O(n) for skewed trees
     * O(lgn) for balanced trees
     * @param root
     * @param sum
     * @return
     */
    public boolean hasPathSum(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null) {
            return sum == root.val;
        }

        return hasPathSum(root.left, sum - root.val) || hasPathSum(root.right, sum - root.val);
    }

    /**
     * 2. Iteration, DFS (pre-order)
     */
    public boolean hasPathSum2(TreeNode root, int sum) {
        if (root == null) {
            return false;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Integer> sums = new ArrayDeque<>();

        stack.push(root);
        sums.push(sum);

        while (!stack.isEmpty()) {
            int value = sums.pop();
            TreeNode top = stack.pop();

            if (top.left == null && top.right == null && top.val == value) {
                return true;
            }

            if (top.right != null) {
                stack.push(top.right);
                sums.push(value - top.val);
            }

            if (top.left != null) {
                stack.push(top.left);
                sums.push(value - top.val);
            }
        }

        return false;
    }

    /**
     * 3. Iteration, DFS (post-order?)
     *
     * pre is always the previous node has been visited.
     * In post order traversal, If it's right child has been visited, pre must be it's right child.
     * cur->right && pre != cur->right means that cur has a right child, but has not been visited.
     * So we should post order the right child tree.
     *
     * an example:
     *      a
     *    b   c
     *  d  e
     *
     * cur = b, pre = e. We have visited the right tree e.
     * cur = a, pre = b. We have not visited the right tree c.
     *
     *
     * Time complexity: O(n)
     * Space complexity:
     * O(n) for skewed trees
     * O(lgn) for balanced trees
     *
     *
     * Runtime: 1 ms, faster than 11.32% of Java online submissions for Path Sum.
     * Memory Usage: 36.9 MB, less than 100.00% of Java online submissions for Path Sum.
     *
     */
    public boolean hasPathSum3(TreeNode root, int sum) {
        TreeNode pre = null;
        TreeNode cur = root;
        int SUM = 0;
        Deque<TreeNode> stack = new ArrayDeque<>();

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                SUM += cur.val;
                cur = cur.left;
            }

            // 1. null because of previous inner while loop (cannot go more left)
            // 2. null because of last outer while loop (a path is exhausted)
            cur = stack.peek();

            if (cur.left == null && cur.right == null && SUM == sum) {
                return true;
            }

            if (cur.right != null && pre != cur.right) {
                cur = cur.right;
            } else {
                // no left child (because of previous while loop)
                // 1) no right child or
                // 2) has right child but already visited
                // either way, a path is exhausted.
                pre = cur;
                stack.pop();
                SUM -= cur.val;
                cur = null;
            }
        }

        return false;
    }

}
