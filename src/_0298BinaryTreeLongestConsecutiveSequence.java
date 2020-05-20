public class _0298BinaryTreeLongestConsecutiveSequence {

    /**
     *  1. Pre-oder traversal, recursion
     *
     *  Time complexity: O(n)
     *  Space complexity: O(h)
     *
     *  Runtime: 2 ms, faster than 6.84% of Java online submissions for Binary Tree Longest Consecutive Sequence.
     *  Memory Usage: 48.4 MB, less than 5.88% of Java online submissions for Binary Tree Longest Consecutive Sequence.
     *
     *         1
     *        / \
     *       2   4
     *      /
     *     3
     *     helper(&1, null, 0):
     *      int length = 1;
     *      int left = helper(&2, &1, 1) = 3;
     *      int right = helper(&4, &1, 1) = 1;
     *      return max(length, max(left, right)) = max(1, max(3, 1)) = 3;
     *
     *     helper(&2, &1, 1):
     *      int length = 1 + 1 = 2;
     *      int left = helper(&3, &2, 2) = 3;
     *      int right = helper(null, &2, 2) = 0;
     *      return max(2, max(3, 0)) = 3;
     *
     *     helper(&3, &2, 2):
     *      int length = 2 + 1 = 3;
     *      int left = 0;
     *      int right = 0;
     *      return max(3, max(0, 0)) = 3;
     *
     *     helper(&4, &1, 1):
     *      int length = 1;
     *      int left = helper(null, &4, 1) = 0;
     *      int right = helper(null, &4, 1) = 0;
     *      return max(1, max(0, 0)) = 1;
     * @param root
     * @return
     */
    public int longestConsecutive(TreeNode root) {
        return helper(root, null, 0);
    }

    private int helper(TreeNode root, TreeNode parent, int prevLen) {
        if (root == null) {
            return 0;
        }

        // length: starting from furthest node above to root
        int length = (parent != null && parent.val + 1 == root.val)
                ? prevLen + 1
                : 1;
        // max length starting from furthest node above all the way down to left
        int left = helper(root.left, root, length);
        // max length starting from furthest node above all the way down to right
        int right = helper(root.right, root, length);
        return Math.max(length, Math.max(left, right));
    }
}
