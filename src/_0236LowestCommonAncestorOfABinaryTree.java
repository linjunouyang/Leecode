import java.util.*;

public class _0236LowestCommonAncestorOfABinaryTree {
    /**
     * 1. Recursion (Pre-order)
     *
     * Definition of this recursion:
     *
     * from this point (root) (looking below), what is the lowest common ancestor
     *
     * https://www.youtube.com/watch?v=py3R23aAPCA
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // write your code here
        if (root == null) {
            return null;
        }

        if( root == p || root == q) {
            // we don't care about which exact node we found
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }

        // Suppose we just [return root;] from a previous frame,
        // which could be current root's left or right
        // we just return this non-null value
        // because nothing will come up on the other side because q and p are UNIQUE
        if (left != null ) {
            return left;
        }
        if (right != null) {
            return right;
        }
        return null;
    }

    /**
     * 2. Iteration (pre-order traversal)
     *
     * Summary:
     * How to store extra info (besides traversed node) during traversal:
     * 1) same type: Stack<TreeNode[]>
     * 2) different type (a single value): another stack with a different type
     * 3) different type (a mapping relationship that doesn't use integer as key): a map
     * In this example, we go with 3).
     *
     * Possible Improvement:
     * Have two boolean indicating whether p or q is found
     * If both are found, early exit the pre-order traversal
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        Map<TreeNode, TreeNode> map = new HashMap<>();

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();

            if (node.right != null) {
                stack.push(node.right);
                map.put(node.right, node);
            }

            if (node.left != null) {
                stack.push(node.left);
                map.put(node.left, node);
            }
        }

        Set<TreeNode> set = new HashSet<>();
        TreeNode cur = p;

        while (cur != null) {
            set.add(cur);
            cur = map.get(cur);
        }

        cur = q;
        while (cur != null) {
            if (set.contains(cur)) {
                return cur;
            }
            cur = map.get(cur);
        }

        // unreachable code
        return null;
    }
}
