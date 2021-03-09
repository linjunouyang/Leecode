import java.util.*;

public class _0104MaximumDepthOfBinaryTree {
    /**
     * 1. Recursive DFS
     *
     * Time: O(n). Space: O(h)
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

    public int maxDepth11(TreeNode root) {
        return depthHelper(root, 1);
    }

    private int depthHelper(TreeNode root, int curDepth) {
        if (root == null) {
            return curDepth - 1;
        }

        int leftDepth = depthHelper(root.left, curDepth + 1);
        int rightDepth = depthHelper(root.right, curDepth + 1);
        return Math.max(leftDepth, rightDepth);
    }



    /*
    2. Iteration, DFS.

    stack represents the unvisited nodes (visit from top to bottom)

    *
    Null is pushable to Java stack

    Time complexity: O(N)
    Space complexity:
    Usually O(lgN)
    Worst: O(N)
              *
             / \
            *   *
           / \
          *   *
         / \
        *   *
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
    3. Iteration, BFS

    Time complexity: O(n)
    Space complexity: O(n)
    -------
    Collection interface -> Queue interface
    Implementation: ArrayDeque, LinkedList, PriorityQueue

    Returns special value: offer(e), poll(), peek()
    Throws exception: add(e), remove(), element()

    Queue implementations generally do not allow insertion of null elements,
    although some implementations, such as LinkedList, do not prohibit insertion of null.
    Even in the implementations that permit it, null should not be inserted into a Queue,
    null : a special return value by the poll to indicate that the queue contains no elements.
     */
    public int maxDepth3(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
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
