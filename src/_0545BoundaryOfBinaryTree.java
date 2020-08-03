import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class _0545BoundaryOfBinaryTree {

    /**
     * 1. Left Boundary + leaves + Right Boundary
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public List<Integer> boundaryOfBinaryTree(TreeNode root) {
        List<Integer> nodes = new ArrayList<>();

        if(root == null) {
            return nodes;
        }

        nodes.add(root.val);
        leftBoundary(root.left, nodes);
        /**
         * Notice we add root first, and pass root.left and root.right instead of root here
         * This avoids duplicate root
         */
        leaves(root.left, nodes);
        leaves(root.right, nodes);
        rightBoundary(root.right, nodes);

        return nodes;
    }

    /**
     * Modified Pre-order
     * 2 versions: Recursion and iteration
     * [root, left-most node)
     */
    public void leftBoundary(TreeNode root, List<Integer> nodes) {
        if(root == null || (root.left == null && root.right == null)) {
            /** Second condition can't be omitted (gives [1, 2, 2, 3] instead of [1, 2, 3])
             *   1
             *  2 3
             *  2 and 3 will be processed by leaves()
             */
            return;
        }
        nodes.add(root.val);
        if(root.left == null) {
            leftBoundary(root.right, nodes);
        } else {
            leftBoundary(root.left, nodes);
        }
    }

    public void leftBoundary2(TreeNode root, List<Integer> nodes) {
        if (root == null || (root.left == null && root.right == null)) {
            return;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null || node.right != null) {
                nodes.add(node.val);
            }
            if (node.left != null) {
                stack.push(node.left);
            } else if (node.right != null) {
                stack.push(node.right);
            }
        }
    }

    /**
     * [root, right-most node)
     *
     * Remember we need the reversed order
     */
    public void rightBoundary(TreeNode root, List<Integer> nodes) {
        if(root == null || (root.right == null && root.left == null)) {
            /** Second condition can't be omitted (gives [1, 2, 3, 3] instead of [1, 2, 3])
             *   1
             *  2 3
             *  2 and 3 will be processed by leaves()
             */
            return;
        }

        if(root.right == null) {
            rightBoundary(root.left, nodes);
        } else {
            rightBoundary(root.right, nodes);
        }
        nodes.add(root.val); // add after child visit(reverse)
    }

    /**
     * Modified Post-order
     */
    private void getRightBoundary2(TreeNode root, List<Integer> res) {
        if (root == null || (root.left == null && root.right == null)) {
            return;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;

        while (curr != null) {
            stack.push(curr);
            if (curr.right != null) {
                curr = curr.right;
            } else if (curr.left != null) {
                curr = curr.left;
            } else {
                break;
            }
        }

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left != null || node.right != null) {
                res.add(node.val);
            }
        }
    }

    public void leaves(TreeNode root, List<Integer> nodes) {
        if(root == null) {
            return;
        }
        if(root.left == null && root.right == null) {
            nodes.add(root.val);
            return;
        }
        leaves(root.left, nodes);
        leaves(root.right, nodes);
    }

    /**
     * Modified pre-order
     *
     * if a base case in recursion is simply return,
     * then in iteration, we prevent pushing such elements onto stack
     *
     */
    private void getLeaves(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.left == null && node.right == null) {
                res.add(node.val);
            } else {
                if (node.right != null) {
                    stack.push(node.right);
                }
                if (node.left != null) {
                    stack.push(node.left);
                }
            }
        }
    }

    /**
     * 2. Simplified 1
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param root
     * @return
     */
    public List<Integer> boundaryOfBinaryTree2(TreeNode root) {
        List<Integer> res = new ArrayList<Integer>();
        if (root != null) {
            res.add(root.val);
            getBounds(root.left, res, true, false);
            getBounds(root.right, res, false, true);
        }
        return res;
    }

    private void getBounds(TreeNode node, List<Integer> res, boolean isLeft, boolean isRight) {
        if (node == null) {
            return;
        }
        // without !isLeft && !isRight
        //    1
        //      2
        //     3  4
        // [1, 3, 4, 4, 2]
        // without !isLeft
        //    1
        //   2 3
        // [1, 2, 2, 3]
        // without !isRight
        //      1
        //        2
        //       3 4
        // [1, 3, 4, 4, 2]
        if ( !isLeft && !isRight && node.left == null && node.right == null) {
            // leaf nodes (not part of left/right boundary)
            res.add(node.val);
            return;
        }


        if (isLeft) {
            res.add(node.val);
        }
        getBounds(node.left, res, isLeft, isRight && node.right == null);
        getBounds(node.right, res, isLeft && node.left == null, isRight);
        if (isRight) {
            res.add(node.val);
        }
    }
}
