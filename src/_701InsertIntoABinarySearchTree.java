/*
9.27 First. Solved in 7 minutes. While loop.
 */

public class _701InsertIntoABinarySearchTree {
    /*
    1. Recursion

    More clear and concise than iterative version.

    Time complexity: O(N) N: number of nodes. Skewed tree.
    Space complexity: O(h) h: the height of tree. Skewed tree.
    Space needed for recursive stack.
    */

    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        if (root.val > val) {
            root.left = insertIntoBST(root.left, val);
        } else {
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }

    /*
    2. Iteration

    Time complexity: O(n) n: number of nodes of skewed tree.
    Space complexity: O(1)

     */
    public TreeNode insertIntoBST2(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        TreeNode cur = root;
        while (true) {
            if (cur.val <= val) {
                if (cur.right != null) {
                    cur = cur.right;
                } else {
                    cur.right = new TreeNode(val);
                    break;
                }
            } else {
                if (cur.left != null) {
                    cur = cur.left;
                } else {
                    cur.left = new TreeNode(val);
                    break;
                }
            }
        }
        return root;
    }
}
