package Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class _102BinaryTreeLevelOrderTraversal {
    /**
     * 1. Iteration, BFS (level-order traversal)
     *
     * Time complexity: O(n)
     * Space complexity: O(lgn) for balanced, O(1) for unbalanced.
     *
     * ? BUT in the end we have to store all the nodes
     * ? does it mean it always take O(n) space?
     */
    public static List<List<Integer>> levelOrder1(TreeNode root) {
        List<List<Integer>> total = new ArrayList<>();
        if (root == null) {
            return total;
        }

        Queue<TreeNode> queue = new ArrayDeque<>();
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
            total.add(level);
        }

        return total;
    }

    /**
     * 2 Recrusion, DFS (pre-order traversal)
     *
     * We add root.val into the list, and then look at left and right child;
     *
     * Time complexity: O(n)
     * Space complexity: O(lgn) for balanced, O(n) for unbalanced.
     *
     * @param args
     */
    public static List<List<Integer>> levelOrder2(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        levelHelper(res, root, 0);
        return res;
    }

    public static void levelHelper(List<List<Integer>> res, TreeNode root, int depth) {
        if (root == null) {
            return;
        }

        if (depth == res.size()) {
            res.add(new ArrayList<Integer>());
        }

        res.get(depth).add(root.val);
        levelHelper(res, root.left, depth+1);
        levelHelper(res, root.right, depth+1 );
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(3);
        root.left = new TreeNode(9);
        root.right = new TreeNode(20);
        root.right.left = new TreeNode(15);
        root.right.right = new TreeNode(7);
        levelOrder1(root);
    }
}
