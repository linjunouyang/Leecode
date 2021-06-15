import java.util.*;

/**
 * Different cases, how to calculate max and what to return from recursion
 *
 * A good visualization:
 * https://leetcode.com/problems/binary-tree-maximum-path-sum/discuss/603423/Python-Recursion-stack-thinking-process-diagram
 */
public class _0124BinaryTreeMaximumPathSum {
    /**
     * 1. Recursion, Post-order traversal
     *
     * It's like divide and conquer, in the combine phase
     * instead of simply adding or subtracting, we use 0 as a threshold to filter results from sub-problems
     * At each level, we calculate the max that we could find in this subarea and update global max if possible
     * and we also need to inform parent: what's the biggest contribution I can give to you.
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
     * Space: O(h)
     */
    public int maxPathSum(TreeNode root) {
        int[] max = new int[]{Integer.MIN_VALUE}; // max might be negative
        maxPathHelper(root, max);
        return max[0];
    }

    /**
     * (1) computes the maximum path sum with the input node as the highest node, update maximum if necessary
     * (2) returns the maximum sum of the path that can be extended to input node's parent
     */
    private int maxPathHelper(TreeNode root, int[] max) {
        if (root == null) {
            return 0;
        }

        // we only care about GAINS from subtrees
        int left = Math.max(0, maxPathHelper(root.left, max));
        int right = Math.max(0, maxPathHelper(root.right, max));

        // compare max with max path sum within this subtree
        max[0] = Math.max(max[0], left + root.val + right);

        // Tell parent: what is the max contribution we can give.
        // Another implementation is that we threshold the return val with Math.max(0, ...)
        // so that we don't need Math.max(0, ..) for child results above
        return root.val + Math.max(left, right);
    }

    /**
     * 2. Iteration
     *
     * hashmap.get:
     * if this map contains a mapping from a key k to a value v such that (key==null ? k==null : key.equals(k)),
     * then this method returns v;
     * otherwise it returns null. (There can be at most one such mapping.)
     *
     * A return value of null does not necessarily indicate that the map contains no mapping for the key;
     * it's also possible that the map explicitly maps the key to null.
     * The containsKey operation may be used to distinguish these two cases.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int maxPathSum2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        // node -> max contribution to its parent / max path that can be extended to parent
        HashMap<TreeNode, Integer> nodeToMax = new HashMap<>();
        int max = Integer.MIN_VALUE;
        TreeNode curr = root;
        TreeNode prev = null;

        while (!stack.isEmpty() || curr != null) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.peek();

            if (curr.right == null || prev == curr.right) {
                curr = stack.pop();

                // The null key won't be dealt with hashCode(), and it will go to bucket 0.
                int left = Math.max(0, nodeToMax.getOrDefault(curr.left, 0));
                int right = Math.max(0, nodeToMax.getOrDefault(curr.right, 0));
                max = Math.max(max, left + right + curr.val);
                nodeToMax.put(curr, Math.max(left, right) + curr.val);

                prev = curr;
                curr = null;
            } else {
                curr = curr.right;
            }
        }

        return max;
    }
}
