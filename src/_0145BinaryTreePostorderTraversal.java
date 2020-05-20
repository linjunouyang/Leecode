import java.util.*;

public class _0145BinaryTreePostorderTraversal {
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

    ----
    Note:
    1. List interface doesn't have addFirst method.
    2. strictly speaking, this solution for the post order is incorrect.
    Even though the result is correct, but if there are topological dependencies among the nodes,
    the visiting order would be significant. Simply reversing the preorder result isn't right.


     */
    public List<Integer> postorderTraversal2(TreeNode root) {
        LinkedList<Integer> output = new LinkedList<>();

        if (root == null) {
            return output;
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.add(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            output.addFirst(node.val);

            if (node.left != null) {
                stack.push(node.left);
            }

            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return output;
    }

    public List<Integer> postorderTraversal3(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode prev = null;
        TreeNode curr = root;
        stack.push(curr);

        while (!stack.isEmpty()) {
            curr = stack.peek();

            if (prev == null || prev.left == curr || prev.right == curr) {
                // traverse down the tree
                if (curr.left != null) {
                    stack.push(curr.left);
                } else if (curr.right != null) {
                    stack.push(curr.right);
                }
            } else if (curr.left == prev) {
                // traverse up from the left
                if (curr.right != null) {
                    stack.push(curr.right);
                }
            } else if (curr == prev || curr.right == prev ) {
                // traverse up from the right
                // previously, we didn't add any new nodes to the stack, and then prev = curr
                // second condition can be neglected because:
                // for trees like [1, null, 2, 3], when curr = 2, prev = 3.
                // no code in this if-else if-else if will execute, but prev = curr will execute
                // so in the next iteration, we will fall into this case
                res.add(stack.pop().val);
            }

            prev = curr;
        }

        return res;

    }
}
