public class _0250CountUnivalueSubtrees {
    /**
     * 1. Post-order Traversal
     *
     * Why post-order:
     * because A Uni-value subtree means all nodes of the subtree have the same value.
     * All nodes -> you need to check left and right subtrees.
     *
     * Time complexity: O(n)
     * Space complexity: O(h)
     *
     * @param root
     * @return
     */
    public int countUnivalSubtrees(TreeNode root) {
        int[] count = new int[1];
        isUnival(root, count);
        return count[0];
    }

    private boolean isUnival(TreeNode root, int[] count) {
        if (root == null) {
            return true;
        }

        boolean left = isUnival(root.left, count);
        boolean right = isUnival(root.right, count);

        if (left && right) {
            if (root.left != null && root.val != root.left.val) {
                return false;
            }

            if (root.right!= null && root.val != root.right.val) {
                return false;
            }

            count[0]++;
            return true;
        }

        return false;
    }
}
