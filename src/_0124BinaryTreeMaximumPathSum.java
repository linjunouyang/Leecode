import java.util.*;

/**
 * Different cases, how to calculate max and what to return from recursion
 *
 * A good visualization:
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/603423/Python-Recursion-stack-thinking-process-diagram
 *
 */
public class _0124BinaryTreeMaximumPathSum {
    /**
     * 1. Recursion, Post-order traversal
     *
     * How to avoid global variable?
     * 1) Make max int[1]
     * 2) return new int[2]{max extendable from node, maxSoFar}
     *
     * Passing Integer max as a parameter doesn't work:
     * Java pass by value, when you pass a Object, its value (the object address) is copied
     * So initially parent and child function calls point to the same Integer Object.
     * Integer is immutable and in the child function call, when i am assigning new value to it,
     * its creating new object and assigning the new object reference to the variable.
     * Now the object variable in parent and child calls point to different objects.
     *
     * Time: O(n)
     * Space: O(n)
     */
    int maxValue;

    public int maxPathSum(TreeNode root) {
        maxValue = Integer.MIN_VALUE;
        maxPathDown(root);
        return maxValue;
    }

    /**
     *  (1) computes the maximum path sum with the input node as the highest node, update maximum if necessary
     *  (2) returns the maximum sum of the path that can be extended to input node's parent
     *
     *  Notice that the returned is not the final result, but it will contribute to / affect final result
     */
    private int maxPathDown(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int left = Math.max(0, maxPathDown(node.left));
        int right = Math.max(0, maxPathDown(node.right));
        /**
         * 1) left + right + node
         * 2) left + node
         * 3) right + node
         * 4) node
         */
        maxValue = Math.max(maxValue, left + right + node.val);
        /**
         * 1) left + node
         * 2) right + node
         * 3) node (left = right = 0)
         */
        return Math.max(left, right) + node.val;
    }

    /**
     * 2. Iteration
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int maxPathSum2(TreeNode root) {
        /**
         * Problem related variables, data structure
         */
        int result = Integer.MIN_VALUE;
        Map<TreeNode, Integer> maxRootPath = new HashMap<>(); // node -> max path that's up-extendable
        maxRootPath.put(null, 0);

        Deque<TreeNode> stack = new ArrayDeque<>(); // all the root we haven't visited
        TreeNode cur = root;
        TreeNode pre = null;

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.peek();

            if (cur.right == null || pre == cur.right) {
                cur = stack.pop();

                // Problem specific handling
                int left = Math.max(maxRootPath.get(cur.left), 0);
                int right = Math.max(maxRootPath.get(cur.right), 0);
                maxRootPath.put(cur, Math.max(left, right) + cur.val);
                result = Math.max(left + right + cur.val, result);

                pre = cur;
                // 此处为了跳过下一次循环的访问左子节点的过程
                cur = null;
            } else {
                cur = cur.right;
            }
        }
        return result;
    }
}
