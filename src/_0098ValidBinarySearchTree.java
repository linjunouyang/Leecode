import java.util.Stack;

public class _0098ValidBinarySearchTree {

    /**
     * 1. Iterative in-order treaversal
     *
     * No need to keep the traversal list:
     * The last added inorder element is enough to ensure at each step that the tree is BST (or not).
     * Hence one could merge both steps into one and reduce the used space.
     *
     * Time complexity: O(n)
     * Space complexity: O(length of longest left going path)
     *
     * @param root
     * @return
     */
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode pre = null;

        //boolean onRightSideOfPrev = false;

        // initially stack is empty, so we need root != null
        while (root != null || !stack.isEmpty()) {
            // go left, keep going down
            while (root != null) {
                stack.push(root);
                root = root.left;
            }

            root = stack.pop();
            if ( pre != null && root.val <= pre.val) {
                // pre != null && ((!onRightSideofPrev && pre.val > root.val)
                // || (onRightSideofPrev && pre.val >= root.val)))
                return false;
            }

            pre = root;
            root = root.right;

            // handle duplicate values on the left side. for right side, switch > and >=
            // onRightSideOfPrev = root == null ? false : true;
        }
        return true;
    }

    /**
     * 2. Divide and Conquer - Top Down
     *
     * If we use Integer.MAX_VALUE and Integer.MIN_VALUE to compare
     * it won't work with nodes whose value is Integer.MAX_VALUE or min value.
     *
     *
     * Time complexity: O(nlogn)
     * Space complexity: O(h)
     *
     * @param root
     * @return
     */
    public boolean isValidBST2(TreeNode root) {
        return helper(root, null, null);
    }

    // min, max: min and max from top to here so far
    boolean helper(TreeNode root, Integer min, Integer max) {
        if (root == null)
            return true;

        if ((min != null && root.val <= min) || (max != null && root.val >= max))
            // left subtree: max is parent value, root.val must < max
            // right subtree: min is parent value, root.val must > min
            return false;

        return helper(root.left, min, root.val) && helper(root.right, root.val, max);
    }

    /**
     * 2.1 Divide and Conquer with custom return class - Bottom up
     *
     * Time complexity:
     * T(n) = 2T(n/2) + O(1)
     * O(nlogn)
     *
     * Space complexity:
     * O(h)
     *
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
