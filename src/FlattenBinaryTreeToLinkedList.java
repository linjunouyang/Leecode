import java.util.Stack;

public class FlattenBinaryTreeToLinkedList {
    private class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) {
            val = x;
        }
    }

    /**
     * Divide & Conquer
     * Time Complexity: If the tree is completely left-skewed, O(n^2).
     * If right-skewed, O(n).
     * Space Complexity: O(h) (due to recursive program stack, where h is tree height)
     * If the tree is completely skewed, O(h) = O(n)
     * @param root
     */
    public void flatten1(TreeNode root) {
        if (root == null) {
            return;
        }
        flatten1(root.left);
        flatten1(root.right);

        TreeNode right = root.right;
        root.right = root.left;
        root.left = null;
        TreeNode cur = root;
        while (cur.right != null) {
            cur = cur.right;
        }
        cur.right = right;
    }

    /**
     * Divide and Conquer Optimization
     * Time Complexity: O(n). Since helper2 returns the last node, we can link left
     * subtree with right subtree in O(1) time.
     * Space Complexity: O(h) (due to recursive program stack, where h is tree height)
     * If the tree is completely skewed, O(h) = O(n)
     * @param root
     */
    public void flattern2(TreeNode root) {
        helper2(root);
    }

    private TreeNode helper2(TreeNode root) {
        if (root == null) {
            return null;
        }

        TreeNode leftLast = helper2(root.left);
        TreeNode rightLast = helper2(root.right);

        // connect leftLast to root.right
        if (leftLast != null) {
            leftLast.right = root.right;
            root.right = root.left;
            root.left= null;
        }

        if (rightLast != null) {
            return rightLast;
        }

        if (leftLast != null) {
            return leftLast;
        }

        return root;
    }

    /**
     * Traverse
     * Time Complexity: O(n)
     * Space Complexity:  O(h) (due to recursive program stack, where h is tree height)
     * If the tree is completely skewed, O(h) = O(n)
     *
     * 1. prev is set to a field, so flatten3() would be unreusable
     * 2. Using class member variables to pass values between method
     * calls can be considered bad practice, especially when multithreading.
     * 如果一个变量是成员变量，那么多个线程对同一个对象的成员变量进行操作时，
     * 它们对该成员变量是彼此影响的，也就是说一个线程对成员变量的改变会影响到另一个线程。
     */
    private TreeNode prev = null;

    public void flatten3(TreeNode root) {
        if (root == null) {
            return;
        }

        flatten3(root.right);
        flatten3(root.left);
        root.right = prev;
        root.left = null;
        prev = root;
    }

    /**
     * Non-recursion
     * @param root
     */
    public void flatten4(TreeNode root) {
        TreeNode now = root;
        while (now != null) {
            if (now.left != null) {
                // find current node's prenode and links prenode to current
                // node's right subtree.
                TreeNode pre = now.left;
                while(pre.right != null) {
                    pre = pre.right;
                }
                pre.right = now.right;
                // use current node's left subtree to replace its
                // right subtree, subtree is already linked by current node's prenode
                now.right = now.left;
                now.left = null;
            }
            now = now.right;
        }
    }


    public void flatten5(TreeNode root) {
        if (root == null) return;
        Stack<TreeNode> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode curr = stack.pop();
            if (curr.right != null) {
                stack.push(curr.right);
            }
            if (curr.left != null) {
                stack.push(curr.left);
            }
            if (!stack.isEmpty()) {
                curr.right = stack.peek();
            }
            curr.left = null;
        }
    }
}
