package Tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class _101SymmetricTree {
    /**
     * 1. Recursion
     *
     *
     * Time complexity: O(n)
     * Runtime: 1 ms, faster than 40.13% of Java online submissions for Symmetric Tree.
     *
     * Space complexity:
     * O(lgn) for balanced trees
     * O(1) for unbalanced trees
     * O(n) for below trees
     *               1
     *              / \
     *             2   2
     *            /    \
     *           3     3
     *          /       \
     *         4        4
     *
     * Memory Usage: 38.6 MB, less than 74.16% of Java online submissions for Symmetric Tree.
     *
     * @param root
     * @return
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }

        return isSymmetricHelper(root.left, root.right);
    }

    private boolean isSymmetricHelper(TreeNode p, TreeNode q) {
        if (p == null || q == null) {
            return p == q;
        }

        if (p.val != q.val) {
            return false;
        }

        return isSymmetricHelper(p.left, q.right) && isSymmetricHelper(p.right, q.left);
    }

    /**
     * 2. Iteration
     *
     * Time complexity: O(n)
     *
     * Space complexity: O(lgn)
     *
     * ArrayDeque cannot hold null, while stack can hold null
     * stack version
     * https://leetcode.com/problems/symmetric-tree/discuss/33054/Recursive-and-non-recursive-solutions-in-Java
     *
     */
    public boolean isSymmetric2(TreeNode root) {
        if (root == null) {
            return true;
        }

        if (root.left == null && root.right == null) {
            return true;
        } else if (root.left == null || root.right == null) {
            return false;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root.right);
        stack.push(root.left);

        while (!stack.isEmpty()) {
            TreeNode p = stack.pop();
            TreeNode q = stack.pop();

            if (p.val != q.val) {
                return false;
            }

            if (p.left != null && q.right != null) {
                stack.push(q.right);
                stack.push(p.left);
            } else if (p.left != q.right ) {
                return false;
            }

            if (p.right != null && q.left != null) {
                stack.push(q.left);
                stack.push(p.right);
            } else if (p.right !=  q.left) {
                return false;
            }
        }

        return true;
    }

    /**
     * 3 Iteration using queue
     */
    public boolean isSymmetric3(TreeNode root) {
        if(root == null)
            return true;
        Queue<TreeNode> q = new LinkedList();

        q.add(root.left);
        q.add(root.right);
        while(!q.isEmpty()){
            TreeNode left = q.poll();
            TreeNode right = q.poll();
            if(left == null && right == null)
                continue;
            if(left == null || right == null ||left.val != right.val )
                return false;
            q.add(left.left);
            q.add(right.right);
            q.add(left.right);
            q.add(right.left);

        }
        return true;

    }
}
