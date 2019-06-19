package Tree;

public class _114FlattenBinaryTreeToLinkedList {
    /**
     * 1. Recursion, reversed post order (right -> left -> cur)
     *
     * After flattenHelper(root.right, pre), we have processed the right branch of the current node,
     * and the current prev is the head of root of the right branch.
     * For now, we want to know which node is the precedent node of prev,
     * and we will set this particular node's next attribute as prev.
     * As per the problem, this particular node is actually the rightmost node of the left branch.
     *
     * Next, let's say why we can actually get it. we go to the next line, which is flatten(root.left, pre).
     * When we go deeper and deeper in the recursion, we are actually going right because we go right before we go left.
     * A small remark here, the traversal order of the code snippet is actually right->left->cur, not post-order, more like reversed post order if you like.
     * As we are going right, we will stop when we cannot go right furthermore.
     * Then we set right or next attribute of the current node as prev, which is exactly what we want.
     *
     * Now let's go back to the original function call layer, we have done flatten(root.right) and flatten(root.right).
     * The remaining is easy, we set the root node's next node as prev, which is the head of the left branch. Done!
     *
     * Time complexity: O(n)
     *
     * Space complexity:
     * O(lgn) for balanced trees
     * O(n) for skewed trees
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Flatten Binary Tree to Linked List.
     * Memory Usage: 35.7 MB, less than 99.93% of Java online submissions for Flatten Binary Tree to Linked List.
     *
     * @param root
     */
    public void flatten(TreeNode root) {
        flattenHelper(root, null);
    }

    private TreeNode flattenHelper(TreeNode root, TreeNode pre) {
        if (root == null) {
            return pre;
        }

        pre = flattenHelper(root.right, pre);
        pre = flattenHelper(root.left, pre);

        root.right = pre;
        root.left = null;

        pre = root;
        return pre;
    }

    /**
     * 2. Iteration
     *
     * Similar to Morris Traversal ??
     *
     * Time complexity: O(n) each node is visited at most twice
     * Space complexity: O(1)
     *
     * You're moving cur over all nodes and for each one potentially dive down deep into its left subtree,
     * so one might think it's more than O(n) time. But... a long path down the left subtree immediately pays off,
     * as you then insert that entire path into the "right border" of the whole tree,
     * where cur will walk over it once more but last will never have to walk over it again.
     *
     * To put it differently: Every node is visited by cur exactly once and by last at most once,
     * and the runtime is proportional to the number of steps taken by cur and last, so O(n) overall.
     *
     * about while (last.right != null) last = last->right;
     * In the worst case, given a tree whose left subtree is a full binary tree,
     * we may need log(n)-1, log(n)-2, log(n)-3, ..., 1 iterations to get last each time.
     * there are only log(n) - 1 numbers, so the sum is (logn) ^ 2
     *
     * Runtime: 1 ms, faster than 42.44% of Java online submissions for Flatten Binary Tree to Linked List.
     * Memory Usage: 36.2 MB, less than 99.93% of Java online submissions for Flatten Binary Tree to Linked List.
     */
    public void flatten2(TreeNode root) {
        TreeNode cur = root;

        while (cur != null) {
            if (cur.left != null) {
                TreeNode last = cur.left;

                while (last.right != null) {
                    last = last.right;
                }

                last.right = root.right;

                cur.right = cur.left;
                cur.left = null;
            }
            cur = cur.right;
        }
    }
}
