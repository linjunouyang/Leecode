package Tree;

import java.util.*;

public class _145BinaryTreePostorderTraversal {
    /*
    1. Recursion

    Time complexity:
    O(n) (n: number of nodes)

    Space complexity:
    O(h) (h: height of trees)
    Worst case for skewed trees: h = n, O(h) = O(n)
     */
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    private void helper(TreeNode root, List<Integer> res) {
        if (root != null) {
            helper(root.left, res);
            helper(root.right, res);
            res.add(root.val);
        }
    }

    /*
    2. Iteration

    Highlights:
    LinkedList's addFirst method.
    Stack: storing the roots of subtrees which we haven't visited.

    Time complexity:
    O(n)

    Space complexity:
    Personal Opinion:
    Best for right or left skewed trees: O(1)
    Worst for balanced trees: O(logn)
    Average: O(logn)

    Others:
    depending on the tree structure, we could keep up to the entire tree,
    therefore, the space complexity is \mathcal{O}(N)O(N).

     */
    public List<Integer> postorderTraversal2(TreeNode root) {
        LinkedList<TreeNode> stack = new LinkedList<>();
        LinkedList<Integer> output = new LinkedList<>();
        if (root == null) {
            return output;
        }

        stack.add(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pollLast();
            output.addFirst(node.val);
            if (node.left != null) {
                stack.add(node.left);
            }
            if (node.right != null) {
                stack.add(node.right);
            }
        }

        return output;
    }
}
