package BinarySearch;

import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * 8.23 Learned a BFS solution.
 * Need to check out other solutions
 *
 */

private class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
}

public class _297SerializeAndDeserializeBinaryTree {


    /**
     * 1. BFS
     *
     * Notice:
     * 1. LinkedList allows null, while ArrayDeque doesnt'
     *
     * Optimization:
     * delete unnecessary n to save storage
     *
     *
     * Time complexity: O(nn)
     * Space complexity: O(width)
     *
     * Runtime: 13 ms, faster than 53.13% of Java online submissions for Serialize and Deserialize Binary Tree.
     * Memory Usage: 40.3 MB, less than 31.43% of Java online submissions for Serialize and Deserialize Binary Tree.
     *
     * @param root
     * @return
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }

        Queue<TreeNode> q = new LinkedList<>();
        StringBuilder res = new StringBuilder();
        q.add(root);

        while (!q.isEmpty()) {
            TreeNode node = q.poll();

            if (node == null) {
                res.append("n ");
                continue;
            }

            res.append(node.val + " ");
            q.add(node.left);
            q.add(node.right);
        }

        return res.toString();
    }

    public TreeNode deserialize(String data) {
        if (data == "") {
            return null;
        }

        Queue<TreeNode> q = new LinkedList<>();
        String[] values = data.split(" ");
        // We need to store the root for final return
        TreeNode root = new TreeNode(Integer.parseInt(values[0]));
        q.add(root);

        for (int i = 1; i < values.length; i++) {
            TreeNode parent = q.poll();
            if (!values[i].equals("n")) {
                TreeNode left = new TreeNode(Integer.parseInt(values[i]));
                parent.left = left;
                q.add(left);
            }

            if (!values[++i].equals("n")) {
                TreeNode right = new TreeNode(Integer.parseInt(values[i]));
                parent.right = right;
                q.add(right);
            }
        }

        return root;
    }
}
