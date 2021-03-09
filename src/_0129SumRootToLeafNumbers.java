import java.util.ArrayDeque;
import java.util.Deque;

/**
 * 6.20 No. Stuck in recursion.
 */

public class _0129SumRootToLeafNumbers {
    /**
     * 1. Recursion (pre-oder)
     *
     * the parameter inside sumHelper function means the sum so far
     * (root not included)
     *
     * Time complexity: O(n)
     *
     * Space complexity:
     * O(n) for skewed strees
     * O(lgn) for balanced trees
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sum Root to Leaf Numbers.
     * Memory Usage: 34.3 MB, less than 100.00% of Java online submissions for Sum Root to Leaf Numbers.
     *
     */
    public int sumNumbers(TreeNode root) {
        return sumHelper(root, 0);
    }

    private int sumHelper(TreeNode root, int sum) {
        if (root == null) {
            return 0;
        }

        if (root.right == null && root.left == null) {
            return sum * 10 + root.val;
        }

        return sumHelper(root.left, sum * 10 + root.val) +
                sumHelper(root.right, sum * 10 + root.val);
    }

    /**
     * 2. Iteration, BFS (DFS)
     *
     * BFS -> DFS (Pre-order)
     * Queue -> Stack
     * offer & poll -> push & pop
     *
     *
     *
     * Time complexity: O(n)
     *
     * Space complexity:
     * O(n) for balanced trees
     * O(1) for skewed trees
     *
     * Runtime: 1 ms, faster than 27.86% of Java online submissions for Sum Root to Leaf Numbers.
     * Memory Usage: 34.5 MB, less than 100.00% of Java online submissions for Sum Root to Leaf Numbers.
     */
    public int sumNumbers2(TreeNode root) {
        int total = 0;
        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        Deque<Integer> numQueue = new ArrayDeque<>();

        if (root != null) {
            nodeQueue.offer(root);
            numQueue.offer(root.val);
        }

        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int num = numQueue.poll();

            if (node.left == null && node.right == null) {
                total += num;
            } else {
                if (node.right != null) {
                    nodeQueue.offer(node.right);
                    numQueue.offer(num * 10 + node.right.val);
                }
                if (node.left != null) {
                    nodeQueue.offer(node.left);
                    numQueue.offer(num * 10 + node.left.val);
                }
            }
        }

        return total;
    }
}
