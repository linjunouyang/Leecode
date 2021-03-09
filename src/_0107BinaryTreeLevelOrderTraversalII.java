import java.util.*;

public class _0107BinaryTreeLevelOrderTraversalII {

    /**
     * 1. BFS Iteration
     *
     * Note: the returned object is of interface List, which doesn't support addFirst
     * but list interface has another method:
     * add(int index, E element): Inserts the specified element at the specified position
     *
     * Time: O(n)
     * Space: O(n)
     */
    public List<List<Integer>> levelOrderBottom(TreeNode root) {
        // You can declare it as LinkedList
        List<List<Integer>> res = new LinkedList<>();

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
     * Time: O(n)
     * Space:
     * O(lgn) for balanced trees
     * O(n) for unbalanced trees
     */
    public List<List<Integer>> levelOrderBottom2(TreeNode root) {
        List<List<Integer>> res = new LinkedList<>();
        levelHelper(res, root, 0);
        return res;
    }

    private void levelHelper(List<List<Integer>> res, TreeNode root, int level) {
        if (root == null) {
            return;
        }

        // can't put any of left or right here because last level (root) list is not created
        if (level == res.size()) {
            res.add(0, new ArrayList<>());
        }

        // From here, the order of left, right and root doesn't matter
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
