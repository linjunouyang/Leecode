package Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class _0099RecoverBinarySearchTree {
    class TreeNode {
          int val;
          TreeNode left;
          TreeNode right;
          TreeNode() {}
          TreeNode(int val) { this.val = val; }
          TreeNode(int val, TreeNode left, TreeNode right) {
              this.val = val;
              this.left = left;
              this.right = right;
          }
    }

    /**
     * 1. In-order + identify reversion, swap
     *
     * Time: O(n)
     * Space: O(n)
     */
    public void recoverTree(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque();
        TreeNode x = null, y = null, last = null;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.add(root);
                root = root.left;
            }
            root = stack.pop();

            if (last != null && root.val < last.val) {
                y = root;
                if (x == null) {
                    x = last;
                } else {
                    break;
                }
            }
            last = root;

            root = root.right;
        }

        swap(x, y);
    }

    public void swap(TreeNode a, TreeNode b) {
        int tmp = a.val;
        a.val = b.val;
        b.val = tmp;
    }


    /**
     * 2. Morris Traversal
     *
     * https://www.cnblogs.com/AnnieKim/archive/2013/06/15/morristraversal.html
     *
     * Time: O(n)
     * We visited each edge 3 times
     * 1) find the node
     * 2) find precedent node and connect with curr
     * 3) find precedent node and break precedent and curr
     *
     * Space: O(1)
     */
    public void recoverTree2(TreeNode root) {
        // predecessor is a Morris predecessor.
        // In the 'loop' cases it could be equal to the node itself predecessor == curr.
        // last is a 'true' predecessor: the previous node in the inorder traversal.
        TreeNode curr = root;
        TreeNode x = null, y = null, last = null, predecessor = null;

        while (curr != null) {
            // 1. If there is a left child, compute the predecessor.
            // one step left and then right till you can
            // 2. Link Management:
            // a) If there is no link predecessor.right = root --> set it.
            // b) If there is a link predecessor.right = root --> break it.
            if (curr.left != null) {
                // Predecessor node is one step left and then right till you can.

                predecessor = curr.left;
                while (predecessor.right != null && predecessor.right != curr) {
                    //    1(c)
                    //  3
                    //   2(p) pred.right is not null, but = curr
                    predecessor = predecessor.right;
                }

                if (predecessor.right == null) {
                    // set link predecessor.right = curr,
                    // set curr to curr's left child (go to explore left subtree)
                    predecessor.right = curr;
                    curr = curr.left;
                } else {
                    // break link predecessor.right = root
                    // link is broken : time to change subtree and go right

                    // check for the swapped nodes
                    if (last != null && curr.val < last.val) {
                        y = curr;
                        if (x == null) x = last;
                    }
                    last = curr;

                    predecessor.right = null;
                    curr = curr.right;
                }
            } else {
                /**
                 *  If there is no left child,
                 *  process curr node
                 *  set curr = curr.right (then just go right)
                 */

                // processing current node. Problem specific operation
                // In this problem, we check for the swapped nodes
                if (last != null && curr.val < last.val) {
                    y = curr;
                    if (x == null) x = last;
                }
                last = curr;

                curr = curr.right;
            }
        }
        swap(x, y);
    }
}
