import javafx.util.Pair;

import java.util.ArrayDeque;

public class _0111MinimumDepthOfBinaryTree {

    /**
     * 1. Preorder Traversal
     *
     * Time complexity: O(n)
     * Space complexity: O(h)
     *
     * @param root
     * @return
     */
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return getMin(root);
    }

    public int getMin(TreeNode root) {
        if (root == null) {
            // 'root' parent is not a leaf node, can't stop at root -> MAX_VALUE
            // Can't return 0 here, consider this tree:
            //   1
            //  2
            return Integer.MAX_VALUE;
        }

        if (root.left == null && root.right == null) {
            return 1;
        }

        return Math.min(getMin(root.left), getMin(root.right)) + 1;
    }

    /**
     * 2. DFS Iteration
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     *
     * @param root
     * @return
     */
    public int minDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<Pair<TreeNode, Integer>> stack = new ArrayDeque<>();
        stack.push(new Pair(root, 1)); // note init syntax

        int minDepth = Integer.MAX_VALUE;

        while (!stack.isEmpty()) {
            Pair<TreeNode, Integer> pair = stack.pop();
            TreeNode node = pair.getKey();
            int curDepth = pair.getValue();

            if (node.left == null && node.right == null) {
                minDepth = Math.min(minDepth, curDepth);
            }

            if (node.right != null) {
                stack.push(new Pair(node.right, curDepth + 1));
            }

            if (node.left != null) {
                stack.push(new Pair(node.left, curDepth + 1));
            }
        }

        return minDepth;
    }

    /**
     * 3. BFS Iteration
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     * 
     * @param root
     * @return
     */
    public int minDepth3(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<Pair<TreeNode, Integer>> q = new ArrayDeque<>();
        q.offer(new Pair(root, 1));

        int curDepth = 0;

        while (!q.isEmpty()) {
            Pair<TreeNode, Integer> pair = q.poll();
            TreeNode node = pair.getKey();
            curDepth = pair.getValue();

            if (node.left == null && node.right == null) {
                break;
            }

            if (node.right != null) {
                q.offer(new Pair(node.right, curDepth + 1));
            }

            if (node.left != null) {
                q.offer(new Pair(node.left, curDepth + 1));
            }
        }

        return curDepth;
    }



}
