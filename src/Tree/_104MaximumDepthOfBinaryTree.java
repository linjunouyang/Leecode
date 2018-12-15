package Tree;

import java.util.*;

public class _104MaximumDepthOfBinaryTree {
    /*
    1. Recursion

    DFS.

    Time complexity: O(N)
    Space complexity:
    Completely unbalanced tree (left-skewed): the recursion call would occur N times,
    the call stack storage would be O(N).
    Completely balanced tree: O(logN)
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        } else {
            int left_height = maxDepth(root.left);
            int right_height = maxDepth(root.right);
            return Math.max(left_height, right_height) + 1;
        }
    }

    /*
    2. Iteration

    DFS.

    Time complexity: O(N)
    Space complexity:
    Skewed tree: O(N)
    Balanced tree: O(logN)
     */
    public int maxDepth2(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Stack<TreeNode> stack = new Stack<>();
        Stack<Integer> value = new Stack<>();
        stack.push(root);
        value.push(1);
        int max = 0;

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            int temp = value.pop();
            max = Math.max(temp, max);
            if (node.right != null) {
                stack.push(node.right);
                value.push(temp+1);
            }
            if (node.left != null) {
                stack.push(node.left);
                value.push(temp + 1);
            }
        }
        return max;
    }

    /*
    3. Iteration

    BFS

    Time complexity: O(N), N: number of nodes
    Space complexity:
    Balanced tree: O(N), N: number of nodes
    Skewed tree: O(1)
     */
    public int maxDepth3(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);
        int count = 0;
        while (!queue.isEmpty()) {
            int size = queue.size(); // number of nodes at a specific level
            while (size-- > 0) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
            count++;
        }
        return count;
    }
}
