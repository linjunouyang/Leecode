package Tree;

public class _700SearchInABinarySearchTree {
    /*
    1. Recursion

    Boundary checking
    if-else blocks -> recursion

    Time complexity: O(h)
    Space complexity: O(h)
    h: height of the tree. If the tree is skewed, h = n;
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        if (val < root.val) {
            return searchBST(root.left, val);
        } else if (val > root.val) {
            return searchBST(root.right, val);
        } else {
            return root;
        }
    }

    /*
    2. Iteration

    Time complexity: O(h)
    Space complexity: O(1)
    h: the tree's height. If the tree is skewed, h = number of nodes;
     */
    public TreeNode searchBST2(TreeNode root, int val) {
        if (root == null) {
            return null;
        }
        TreeNode cur = root;
        while (cur != null) {
            if (val < cur.val) {
                cur = cur.left;
            } else if (val > cur.val) {
                cur = cur.right;
            } else {
                return cur;
            }
        }
        return cur;
    }
}
