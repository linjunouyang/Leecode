/**
 * 9.24 1st Solved in 7 minutes. Better than the Solution
 */

public class _814BinaryTreePruning {
    /*
    Recursion:

    Kind of divide and conquer process. Beginning from the bottom, for null nodes we just return null.
    If the node is 1, or if any child of it is not null, we return itself, otherwise we return null.
    Only when a node has two null children, and itself is also 0, we return null
    and the null will be assigned to its parent’s left/right, so we pruned the tree.

    Time complexity:  O(n) n = number of nodes
    Space complexity: O(h) h = height of the tree, the size of the implicit stack in our recursion
     */
    public TreeNode pruneTree(TreeNode root) {
        if (root == null) {
            return null;
        }

        root.left = pruneTree(root.left);
        root.right = pruneTree(root.right);

        if (root.val == 0 && root.left == null && root.right == null) {
            return null;
        }

        return root;
    }
}
