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

    /**
     * 首先，我们很确定的是，后序遍历的开头和前序遍历是可以一样的，都是先经过二叉树的最左分支，直到经过的节点是个叶子节点（没有左右孩子）为止。
     *      * while(cur!=null) { // 经过所有左节点
     *      *     s.push(cur);
     *      *     cur = cur.left;
     *      * }
     *      *
     *      * 接下来很关键，我们得考虑什么时候才能访问节点。
     *      * 首先我们可以很确定一种情况：发现是叶子节点，必然会访问。这是第一种情况。
     *      * 我们回想前序遍历和中序遍历的时候，它们经过的路径都是左根右??? 对于前序和中序来说，访问路径基本上跟经过路径是一致的。
     *      * 但是在后序遍历中，我们先经过根节点，但是我们不会去访问它，而是会选择先访问它的右子节点。
     *      * 所以在这种情况下，我们会将根节点留在栈中不弹出，等到需要访问它的时候再出。
     *      * 那么，什么时候才需要访问根节点呢？答案当然就是访问完右子节点之后了。
     *      *
     *      * 我们如何获取这个信息？这并不难，我们可以记录一下上一次访问的节点，然后判断一下当前经过的节点和上一次访问的节点是什么关系就好了。
     *      * 如果当前经过的节点的右子节点是上一次访问的节点，显然我们需要访问当前节点了。
     *      * 这是第二种情况。
     *      *
     *      * 总结起来，我们什么时候才能访问节点。有如下两种情况：
     *      * 1) 当前经过节点是叶子节点
     *      * 2) 当前经过节点的右子节点是上一次访问的节点。
     *
     * @param root
     * @return
     */
    public List<Integer> postorderTraversal3(TreeNode root) {
        List<Integer> ans = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>()
        Deque<Integer> height = new ArrayDeque<>();
        TreeNode cur = root;
        TreeNode pre = null;

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.peek();

            if (cur.right == null || pre == cur.right) {
                ans.add(cur.val);
                stack.pop();
                pre = cur;
                // 此处为了跳过下一次循环的访问左子节点的过程，直接进入栈的弹出阶段，
                // 因为但凡在栈中的节点，它们的左子节点都肯定被经过且已放入栈中
                cur = null;
            } else {
                cur = cur.right;
            }
        }

        return ans;
    }


}
