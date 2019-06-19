package Tree;

import java.util.ArrayList;
import java.util.List;

/**
 * 6.18 Solved it with binary search
 *
 */
public class _235LowestCommonAncestorOfABinarySearchTree {
    /**
     * 1.
     *
     * Notice the assumptions of this problem:
     * 1. All of the nodes' values will be unique.
     * 2. p and q are different and both values will exist in the BST.
     * which means root will never be null;
     *
     * Time complexity: O(lgn) for balanced trees, O(n) for unbalanced trees;
     * Runtime: 4 ms, faster than 100.00% of Java online submissions for Lowest Common Ancestor of a Binary Search Tree.
     *
     * Space complexity: O(lgn) for balanced trees, O(n) for unbalanced trees;
     * Memory Usage: 35.9 MB, less than 7.03% of Java online submissions for Lowest Common Ancestor of a Binary Search Tree.
     *
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
       if (root.val > p.val && root.val > q.val) {
           return lowestCommonAncestor(root.left, p, q);
       } else if (root.val < p.val && root.val < q.val) {
           return lowestCommonAncestor(root.right, p, q);
       } else {
           // case1: root.val equals to p.val or q.val
           // case2: root.val is smaller than one, and larger than one.
           return root;
       }
    }


    /**
     * 2. Iteration, Binary Search
     *
     * Time complexity: O(lgn) for balanced trees, O(n) for unbalanced trees
     * Runtime: 4 ms, faster than 100.00% of Java online submissions for Lowest Common Ancestor of a Binary Search Tree.
     *
     * Space complexity: O(1)
     * Memory Usage: 37.4 MB, less than 5.01% of Java online submissions for Lowest Common Ancestor of a Binary Search Tree.
     *
     */
    public TreeNode lowestCommonAncestor2(TreeNode root, TreeNode p, TreeNode q) {
        while (true) {
            if (root.val > p.val && root.val > q.val) {
                root = root.left;
            } else if (root.val < p.val && root.val < q.val) {
                root = root.right;
            } else {
                return root;
            }
        }
    }
}
