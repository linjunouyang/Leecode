import java.util.*;

public class _0236LowestCommonAncestorOfABinaryTree {
    /**
     * 1. Recursion (Pre-order)
     *
     * Definition of this recursion:
     * from this point (root) (looking below), what is the lowest common ancestor
     *
     * https://www.youtube.com/watch?v=py3R23aAPCA
     *
     * if both p and q exist in Tree rooted at root, then return their LCA
     * if neither p and q exist in Tree rooted at root, then return null
     * if only one of p or q (NOT both of them), exists in Tree rooted at root, return it
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root == null) {
            return null;
        }

        if (root == p || root == q) {
            // lowest ancestor for p or q is itself
            // If the other node is in the subtree of root & even we return right here
            // when we go back to parent, the other sibling function call will return null,
            // so we know that they both exist in tree rooted at root
            return root;
        }

        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if (left != null && right != null) {
            return root;
        }

        if (left != null) {
            return left;
        }

        if (right != null) {
            return right;
        }

        // unreachable
        return null;
    }

    /**
     * 2. Iteration (pre-order traversal, other two should also work)
     *
     * Summary:
     * How to store extra info (besides traversed node) during traversal:
     * 1) same type: Stack<TreeNode[]>
     * 2) different type (a single value): another stack with a different type
     * 3) different type (a mapping relationship that doesn't use integer as key): a map
     * In this example, we go with 3).
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        // bases case
        if (root == null) {
            return null;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        HashMap<TreeNode, TreeNode> childToParent = new HashMap<>();

        // or use a counter to count p and q;
        boolean visitedP = false;
        boolean visitedQ = false;

        while (!stack.isEmpty() && (!visitedP || !visitedQ)) {
            TreeNode node = stack.pop();
            if (node == p) {
                visitedP = true;
            }
            if (node == q) {
                visitedQ = true;
            }

            if (node.right != null) {
                stack.push(node.right);
                childToParent.put(node.right, node);
            }

            if (node.left != null) {
                stack.push(node.left);
                childToParent.put(node.left, node);
            }
        }

        HashSet<TreeNode> pAncestors = new HashSet<>();
        TreeNode pAncestor = p;
        while (pAncestor != null) {
            pAncestors.add(pAncestor);
            pAncestor = childToParent.get(pAncestor);
        }

        TreeNode qAncestor = q;
        while (qAncestor != null) {
            if (pAncestors.contains(qAncestor)) {
                return qAncestor;
            }
            qAncestor = childToParent.getOrDefault(qAncestor, null);
        }

        // unreachable code for compilation
        return null;
    }
}
