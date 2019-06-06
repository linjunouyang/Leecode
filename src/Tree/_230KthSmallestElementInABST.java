package Tree;

import java.util.ArrayDeque;
import java.util.Deque;

public class _230KthSmallestElementInABST {
    /**
     * 1 Recursion, In-order traversal
     *
     * Time complexity: O(k)
     * Space complexity: O(n)
     *
     */
    private static int number = 0;
    private static int count = 0;

    public int kthSmallest(TreeNode root, int k) {
        count = k;
        helper(root);
        return number;
    }

    public void helper(TreeNode n) {
        if (n.left != null) helper(n.left);

        count--;
        if (count == 0) {
            number = n.val;
            return;
        }

        if (n.right != null) helper(n.right);
    }

    /**
     * 2 Iteration, In-order traversal
     *
     * Time complexity: O(k)
     * Space complexity: O(n)
     */
    public int kthSmallest2(TreeNode root, int k) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode p = root;
        int count = 0;

        while (p != null || !stack.isEmpty()) {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }

            p = stack.pop();
            count++;
            if (count == k) {
                return p.val;
            }

            p = p.right;
        }

        return -1;
    }

    /**
     * 3 Binary Search (DFS)
     *
     * (not very clear)
     *
     * Time complexity:
     * O(N) best,
     * O(N * lgN) average,
     * O(N^2) worst
     *
     * Space complexity:
     * [UNKNOWN]
     *
     */
    public int kthSmallest3(TreeNode root, int k) {
        int count = countNodes(root.left);
        if (k <= count) {
            return kthSmallest3(root.left, k);
        } else if (k > count + 1) {
            return kthSmallest3(root.right, k-1-count);
        }

        return root.val;
    }

    private int countNodes(TreeNode n) {
        if (n == null) {
            return 0;
        }

        return 1 + countNodes(n.left) + countNodes(n.right);
    }
}
