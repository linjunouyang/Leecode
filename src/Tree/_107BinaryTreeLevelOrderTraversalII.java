package Tree;

import java.util.*;

public class _107BinaryTreeLevelOrderTraversalII {

    /**
     * 1. Queue, Level Order Traversal, BFS
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Note: the returned object is of interface List, which doesn't support addFirst
     * but list interface has another method:
     *
     * add(int index, E element)
     * Inserts the specified element at the specified position in this list
     *
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            List<Integer> level = new ArrayList<>();
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                level.add(node.val);

                if (node.left != null) {
                    queue.offer(node.left);
                }

                if (node.right != null) {
                    queue.offer(node.right);
                }
            }

            res.add(0, level);
        }

        return res;
    }

    /**
     * 2 DFS, Recursion
     *
     * Time complexity:
     * O(n)
     *
     * Space complexity:
     * O(lgn) for balanced trees
     * O(n) for unbalanced trees
     */
    public List<List<Integer>> levelOrderBottom2(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>;
        levelHelper(res, root, 0);
        return res;
    }

    private void levelHelper(List<List<Integer>> res, TreeNode root, int level) {
        if (root == null) {
            return;
        }

        if (level == res.size()) {
            res.add(0, new ArrayList<>());
        }

        levelHelper(res, root.left, level + 1);
        levelHelper(res, root.right, level + 1);
        res.get(res.size() - level - 1).add(root.val);
    }

    /**
     * 3 DFS, Iteration
     *
     * Time complexity:
     * O(n)
     * This is slower than previous methods, possibly because of two stacks.
     *
     * Space complexity:
     * O(lgn)
     *
     */
    public List<List<Integer>> levelOrderBottom3(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();

        if (root == null) {
            return res;
        }

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<Integer> levelStack = new ArrayDeque<>();
        nodeStack.push(root);
        levelStack.push(0);

        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int level = levelStack.pop();

            if (level == res.size()) {
                res.add(0, new ArrayList<>());
            }

            if (node.right != null) {
                nodeStack.push(node.right);
                levelStack.push(level + 1);
            }

            if (node.left != null) {
                nodeStack.push(node.left);
                levelStack.push(level + 1);
            }

            // this can be put before node.right != null
            res.get(res.size() - level - 1).add(node.val);
        }

        return res;
    }
}
