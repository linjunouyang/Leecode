import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class _0098ValidBinarySearchTree {
    /**
     * Intuition:
     * node.right.val > node.val && node.left.val < node.val
     *   [5]
     *  1  6
     *   [4] 7
     *
     * Not just right child should > root, but all the nodes in right subtree should > root
     * -> keep upper and lower limits
     */

    /**
     * 1. Recursive pre-order traversal (Divide and conquer top down)
     *
     * Why use Integer instead of int?
     * Because node val might be Integer.MIN_VALUE or Integer.MAX_VALUE
     *
     * We could also use long: Long.MIN_VALUE, long min, ...
     * Don't forget (long) root.val
     *
     * Time: O(n)
     * Space: O(h)
     */
    public boolean isValidBST1(TreeNode root) {
        return preorder(root, null, null);
    }

    private boolean preorder(TreeNode root, Integer min, Integer max) {
        if (root == null) {
            return true;
        }

        if ((min != null && root.val <= min) || (max != null && root.val >= max)) {
            return false;
        }

        return preorder(root.left, min, root.val) && preorder(root.right, root.val, max);
    }

    /**
     * 1.1 Iterative pre-order traversal
     *
     * Time: O(n)
     * Space: O(h)
     */
    public boolean isValidBST11(TreeNode root) {
        if (root == null) {
            return true;
        }

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<long[]> rangeStack = new ArrayDeque<>();
        nodeStack.push(root);
        rangeStack.push(new long[]{Long.MIN_VALUE, Long.MAX_VALUE});

        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            long[] range = rangeStack.pop();
            if (node.val < range[0] || node.val > range[1]) {
                return false;
            }

            if (node.right != null) {
                nodeStack.push(node.right);
                rangeStack.push(new long[]{(long) node.val + 1, range[1]});
            }

            if (node.left != null) {
                nodeStack.push(node.left);
                rangeStack.push(new long[]{range[0], (long) node.val - 1});
            }
        }

        return true;
    }

    /**
     * 2. Post-order traversal
     *
     * Recursive version similar to recursive pre-order traversal
     * Technically iterative would work too, but I couldn't figure it out.
     */


    /**
     * 3. Recursive in-order traversal
     *
     * We want to change min in the 'previous' function call,
     * we can't use Integer, because it is immutable.
     */
    public boolean isValidBST3(TreeNode root) {
        return inorder(root, new long[]{Long.MIN_VALUE});
    }

    private boolean inorder(TreeNode root, long[] min) {
        if (root == null) {
            return true;
        }

        if (!inorder(root.left, min))  {
            return false;
        }

        if (root.val <= min[0]) {
            return false;
        }

        min[0] = root.val;

        return inorder(root.right, min);
    }

    /**
     * 3.1. Iterative in-order traversal
     *
     * No need to keep the traversal list:
     * The last added inorder element is enough to ensure at each step that the tree is BST (or not).
     * Hence one could merge both steps into one and reduce the used space.
     *
     * Time complexity: O(n)
     * Space complexity: O(length of longest left going path)
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        long prev = Long.MIN_VALUE;

        // boolean onRightSideOfPrev = false;

        // initially stack is empty, so we need root != null
        while (curr != null || !stack.isEmpty()) {
            // go left, keep going down
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();
            if (curr.val <= prev) {
                // pre != null && ((!onRightSideofPrev && pre.val > root.val)
                // || (onRightSideofPrev && pre.val >= root.val)))
                return false;
            }

            prev = curr.val;
            curr = curr.right;

            // handle duplicate values on the left side. for right side, switch > and >=
            // onRightSideOfPrev = root == null ? false : true;
        }
        return true;
    }


    /**
     * 4. Divide and Conquer with custom return class - Bottom up
     *
     * Similar to recursive post-order,
     * but DC uses result from sub-tasks and combine
     * In recursive post-order, curr node's range is passed from parent,
     * we don't utilize child function calls to determine the validity of cur node's val
     *
     * Time : O(n)
     * Space: O(h)
     */
    class ResultType {
        public boolean isBST;
        public TreeNode maxNode, minNode;
        public ResultType(boolean isBST) {
            this.isBST = isBST;
            this.maxNode = null;
            this.minNode = null;
        }
    }

    public boolean isValidBST21(TreeNode root) {
        return divideConquer(root).isBST;
    }

    private ResultType divideConquer(TreeNode root) {
        if (root == null) {
            return new ResultType(true);
        }

        ResultType left = divideConquer(root.left);
        ResultType right = divideConquer(root.right);

        // Not BST, we don't care about max and min
        if (!left.isBST || !right.isBST) {
            return new ResultType(false);
        }

        if (left.maxNode != null && left.maxNode.val >= root.val) {
            return new ResultType(false);
        }

        if (right.minNode != null && right.minNode.val <= root.val) {
            return new ResultType(false);
        }

        // is BST
        ResultType res = new ResultType(true);
        res.minNode = left.minNode == null ? root : left.minNode;
        res.maxNode = right.maxNode == null ? root : right.maxNode;
        return res;
    }


}
