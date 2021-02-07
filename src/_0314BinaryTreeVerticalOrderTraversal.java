import java.util.*;

public class _0314BinaryTreeVerticalOrderTraversal {
    /**
     * 1. BFS
     *
     * TreeMap -> minMax:
     * at we only need to know the range of the column index (i.e. [min_column, max_column]).
     * Then we can simply iterate through this range to generate the outputs without the need for sorting.
     *
     * The above insight would work under the condition that there won't be any missing column index in the given range.
     * And the condition always holds, since there won't be any broken branch in a binary tree.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public List<List<Integer>> verticalOrder(TreeNode root) {
        if (root == null) {
            // null elements are prohibited in ArrayDeque
            return new ArrayList<>();
        }

        HashMap<Integer, List<Integer>> colToList = new HashMap<>();
        int[] minMax = new int[]{0, 0};
        levelOrder(root, colToList, minMax);

        List<List<Integer>> res = new ArrayList<>();
        for (int col = minMax[0]; col <= minMax[1]; col++) {
            res.add(colToList.get(col));
        }

        return res;
    }

    // BFS guarantes row(top->down) order, minMax guarantees col(left->right) order
    // DFS requires (row, col) info, even for pre-order
    // Check example 3 (2 is 0's right child, and 5 is 1's left child)
    // it's possible that a deep node in the left subtree has same col as a shallow node in the right subtree
    // -> violates row order
    private void levelOrder(TreeNode root, HashMap<Integer, List<Integer>> colToList, int[] minMax) {
        Deque<TreeNode> nodeQueue = new ArrayDeque<>(); // null permitted?
        Deque<Integer> colQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        colQueue.offer(0);

        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            int col = colQueue.poll();
            List<Integer> list = colToList.getOrDefault(col, new ArrayList<>());
            list.add(node.val);
            colToList.put(col, list);
            minMax[0] = Math.min(minMax[0], col);
            minMax[1] = Math.max(minMax[1], col);

            if (node.left != null) {
                nodeQueue.offer(node.left);
                colQueue.offer(col - 1);
            }

            if (node.right != null) {
                nodeQueue.offer(node.right);
                colQueue.offer(col + 1);
            }
        }
    }

    /**
     * 2. DFS
     *
     * w: width of binary tree
     * h: height of tree
     * Time: O(w * hlogh)
     *
     * completely skewed: O(n)
     */
}
