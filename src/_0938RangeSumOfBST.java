import java.util.ArrayDeque;
import java.util.Deque;

public class _0938RangeSumOfBST {
    /**
     * Trivial
     * 1. Inorder traversal (iteration)
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public int rangeSumBST(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }

        int sum = 0;

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();
            if (L <= curr.val && curr.val <= R) {
                sum += curr.val;
            } else if (curr.val > R) {
                break;
            }

            curr = curr.right;
        }

        return sum;
    }

    /**
     * First I thought about inorder traversal gives sorted sequence,
     * so when it becomes bigger than R, we can just stop.
     *
     * but when the value is too small, no way for us to skip all small values.
     *
     * Instead, we should consider root first,
     * and then we can determine whether to search left or/and right subtrees
     */


    /**
     * 2.
     *
     * Time complexity: O(n)
     * Space complexity: O(h)
     *
     */
    public int rangeSumBST2(TreeNode root, int L, int R) {
        if (root == null) {
            return 0;
        }
        if (root.val < L) {
            return rangeSumBST(root.right, L, R);
        }
        if (root.val > R) {
            return rangeSumBST(root.left, L, R);
        }

        return root.val + rangeSumBST(root.right, L, R) + rangeSumBST(root.left, L, R); // count in both children.
    }

    public int rangeSumBST3(TreeNode root, int L, int R) {
        Deque<TreeNode> stk = new ArrayDeque<>();
        stk.push(root);
        int sum = 0;
        while (!stk.isEmpty()) {
            TreeNode n = stk.pop();
            if (n == null) { continue; }
            if (n.val > L) { stk.push(n.left); } // left child is a possible candidate.
            if (n.val < R) { stk.push(n.right); } // right child is a possible candidate.
            if (L <= n.val && n.val <= R) { sum += n.val; }
        }
        return sum;
    }
}