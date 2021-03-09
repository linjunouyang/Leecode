import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Ask what to return for root == null
 */
public class _0112PathSum {
    /**
     * 1. Recursive Pre-order
     *
     * can be modified into in, post order
     *
     * Time: O(n)
     * Space: O(h)
     */
    public boolean hasPathSum(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }

        if (root.left == null && root.right == null && targetSum == root.val) {
            return true;
        }

        return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);
    }

    /**
     * 2. Iterative Pre-order
     *
     * Iterative in/post-order, BFS should work too.
     *
     * Time: O(n)
     * Space: O(h)
     */
    public boolean hasPathSum2(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        nodeStack.push(root);
        Deque<Integer> sumStack = new ArrayDeque<>();
        sumStack.push(root.val);

        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int sum = sumStack.pop();

            if (node.left == null && node.right == null && sum == targetSum) {
                return true;
            }

            if (node.right != null) {
                nodeStack.push(node.right);
                sumStack.push(sum + node.right.val);
            }

            if (node.left != null) {
                nodeStack.push(node.left);
                sumStack.push(sum + node.left.val);
            }
        }

        return false;
    }

    public boolean hasPathSum3(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<Integer> sumStack = new ArrayDeque<>();
        TreeNode curr = root;
        int currSum = 0;

        while (!nodeStack.isEmpty() || curr != null) {
            while (curr != null) {
                nodeStack.push(curr);
                currSum += curr.val;
                sumStack.push(currSum);
                curr = curr.left;
            }

            curr = nodeStack.pop();
            currSum = sumStack.pop();

            // we reach here when we couldn't go left anymore or we came back from right
            if (curr.left == null && curr.right == null && currSum == targetSum) {
                // [1, 2] 1 should return false
                // If we don't check curr.left == null, we will return true;
                return true;
            }
            curr = curr.right;
        }

        return false;
    }

    public boolean hasPathSum4(TreeNode root, int targetSum) {
        if (root == null) {
            return false;
        }
        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<Integer> sumStack = new ArrayDeque<>();
        TreeNode curr = root;
        TreeNode prev = null;
        int currSum = 0;

        while (!nodeStack.isEmpty() || curr != null) {
            while (curr != null) {
                nodeStack.push(curr);
                currSum += curr.val;
                sumStack.push(currSum);
                curr = curr.left;
            }

            curr = nodeStack.peek();
            currSum = sumStack.peek();

            if (curr.right == null || prev == curr.right) {
                curr = nodeStack.pop();
                currSum = sumStack.pop();

                if (curr.left == null && curr.right == null && currSum == targetSum) {
                    return true;
                }

                prev = curr;
                curr = null;
                // currSum = 0;
            } else {
                curr = curr.right;
                // currSum will add curr.right.val in the next while loop
            }
        }

        return false;
    }
}

