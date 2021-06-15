import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class _114FlattenBinaryTreeToLinkedList {
    /**
     * 1. Recursive Post-order
     *
     * Time: O(n)
     * Space: O(h)
     */
    public void flatten(TreeNode root) {
        flattenHelper(root);
    }

    private TreeNode flattenHelper(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode leftTail = flattenHelper(root.left);
        TreeNode rightTail = flattenHelper(root.right);

        if (root.left != null) {
            leftTail.right = root.right;
            root.right = root.left;
            root.left = null;
        }

        if (rightTail != null) {
            return rightTail;
        }

        if (leftTail != null) {
            return leftTail;
        }

        return root;
    }

    /**
     * 2. Iterative Post-order
     *
     * Time: O(n)
     * Space: O(h)
     */
    public void flatten2(TreeNode root) {
        if (root == null) {
            return;
        }

        Deque<TreeNode> nodes = new ArrayDeque<>();
        HashMap<TreeNode, TreeNode> nodeToRightMost = new HashMap<>();
        TreeNode curr = root;
        TreeNode prev = null;

        while (!nodes.isEmpty() || curr != null) {
            while (curr != null) {
                nodes.push(curr);
                curr = curr.left;
            }

            curr = nodes.peek();

            if (curr.right == null || prev == curr.right) {
                curr = nodes.pop();

                // do work
                TreeNode leftTail = nodeToRightMost.get(curr.left);
                TreeNode rightTail = nodeToRightMost.get(curr.right);
                if (curr.left != null) {
                    leftTail.right = curr.right;
                    curr.right = curr.left;
                    curr.left = null;
                }

                // key<->value is equivalent of parameter<->return value in recursion
                if (rightTail != null) {
                    nodeToRightMost.put(curr, rightTail);
                } else if (leftTail != null) {
                    nodeToRightMost.put(curr, leftTail);
                } else {
                    nodeToRightMost.put(curr, curr);
                }

                prev = curr;
                curr = null;
            } else {
                curr = curr.right;
            }
        }
    }

    /**
     * With recursion, we only re-wire the connections for the "current node" once we are already done processing the left and the right subtrees completely.
     *
     * the postponing of rewiring of connections on the current node until the left subtree is done,
     * is basically what recursion is.
     * Recursion is all about postponing decisions until something else is completed.
     * In order for us to be able to postpone stuff, we need to use the stack
     */

    /**
     * 3. Iteration
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
     */
    public void flatten3(TreeNode root) {
        if (root == null) {
            return;
        }

        TreeNode node = root;

        while (node != null) {

            // If the node has a left child
            if (node.left != null) {

                // Find the first rightmost node with no right child
                /**
                 * The reason we stopped at the first rightmost node with no right child was
                 * because we would eventually end up rightyfy-ing all the subtrees later on
                 */
                TreeNode rightmost = node.left;
                while (rightmost.right != null) {
                    rightmost = rightmost.right;
                }

                // rewire the connections
                rightmost.right = node.right;
                node.right = node.left;
                node.left = null;
            }

            // move on to the right side of the tree
            node = node.right;
        }
    }

    /**
     * 4. Recursion, reversed post order (right -> left -> cur)
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
     */
    public void flatten4(TreeNode root) {
        flattenHelper4(root, null);
    }

    private TreeNode flattenHelper4(TreeNode root, TreeNode pre) {
        if (root == null) {
            return pre;
        }

        pre = flattenHelper4(root.right, pre);
        pre = flattenHelper4(root.left, pre);

        root.right = pre;
        root.left = null;

        pre = root;
        return pre;
    }
}
