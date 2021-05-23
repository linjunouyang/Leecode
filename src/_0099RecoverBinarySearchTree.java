import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 *
 * 实现二叉树的前序（preorder）、中序（inorder）、后序（postorder）遍历有两个常用的方法：
 * 一是递归(recursive)，二是使用栈实现的迭代版本(stack+iterative)。
 * 这两种方法都是O(n)的空间复杂度（递归本身占用stack空间或者用户自定义的stack），
 *
 * Morris Traversal方法可以做到这两点，与前两种方法的不同在于该方法只需要O(1)空间，而且同样可以在O(n)时间内完成。
 *
 * 要使用O(1)空间进行遍历，最大的难点在于，遍历到子节点的时候怎样重新返回到父节点（假设节点中没有指向父节点的p指针），
 * 由于不能用栈作为辅助空间。为了解决这个问题，Morris方法用到了线索二叉树（threaded binary tree）的概念。
 * 在Morris方法中不需要为每个节点额外分配指针指向其前驱（predecessor）和后继节点（successor），
 * 只需要利用叶子节点中的左右空指针指向某种顺序遍历下的前驱节点或后继节点就可以了。
 *
 */
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
     * adjacent: ... _ < _ < A > B < _ < _ ...
     *                       ^^^^^
     *                       drop #1
     *
     * not adjacent: ... _ < _ < A > X < _ < Y > B < _ < _ ... (X may be the same as Y, but it's irrelevant)
     *                           ^^^^^       ^^^^^
     *                           drop #1     drop #2
     *
     * When they are not consecutive, the first time we meet pre.val > root.val
     * the first node is the pre node, since root should be traversal ahead of pre,
     * pre should be at least at small as root.
     *
     * The second time we meet pre.val > root.val
     * the second node is the root node, since we are now looking for a node to replace with out first node,
     * which is found before.
     *
     * When they are consecutive, which means the case pre.val > cur.val will appear only once.
     * We need to take case this case without destroy the previous analysis.
     * So the first node will still be pre, and the second will be just set to root.
     * Once we meet this case again, the first node will not be affected.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public void recoverTree(TreeNode root) {
        Deque<TreeNode> stack = new ArrayDeque();
        TreeNode x = null, y = null, last = null;

        while (!stack.isEmpty() || root != null) {
            while (root != null) {
                stack.push(root);
                root = root.left;
            }
            root = stack.pop();

            if (last != null && root.val < last.val) {
                // if you put y = root before x, then you break when x != null
                if (x == null) {
                    x = last;
                }
                y = root;
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
                        if (x == null) x = last;
                        y = curr;
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
