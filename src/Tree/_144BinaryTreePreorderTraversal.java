package Tree;

import java.util.*;

public class _144BinaryTreePreorderTraversal {
    /*
    1. Recursion

    Time complexity:
    O(n), n : number of nodes

    Space complexity:
    average: O(h) h : the height of tree.
    worst: O(n) for left, right skewed trees.

    Advantage of helper method:
    Avoid addAll() method.
    Don't have to instantiate a new List at each recursive call.
     */
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    private void helper(TreeNode root, List<Integer> res) {
        if (root == null) {
            return;
        }
        res.add(root.val);
        helper(root.left, res);
        helper(root.right, res);
    }

    /*
    2. Iteration

    Optimization:
    ** No need to store left children **

    Time complexity:
    O(n)

    Space complexity:
    average: O(logn)
    worst: O(n) for left skewed trees.

    Note:
    ArrayDeque class: Resizable, null prohibited.
    Faster than Stack when used as stacks, and faster than LinkedList when used as queues

     */
    public List<Integer> preorderTraversal2(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        Deque<TreeNode> stack = new LinkedList<>();
        TreeNode node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                res.add(node.val);
                stack.push(node.right);
                node = node.left;
            } else {
                node = stack.pop();
            }
        }
        return res;
    }
}
