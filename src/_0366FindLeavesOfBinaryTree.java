import java.util.ArrayList;
import java.util.List;

public class _0366FindLeavesOfBinaryTree {
    /**
     * 1. Post-order Traversal
     *
     * https://www.cs.cmu.edu/~adamchik/15-121/lectures/Trees/trees.html
     *
     * height of a node: the number of edges from the node to the deepest leaf
     * height of a tree: the height of the root
     *
     * Time complexity: O(n)
     * Space complexity: O(h)
     *
     * @param root
     * @return
     */
    public List<List<Integer>> findLeaves(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    private int helper(TreeNode node, List<List<Integer>> res) {
        if (node == null) {
            return -1;
        }

        int height = 1 + Math.max(helper(node.left, res),
                helper(node.right, res));

        if (res.size() == height) {
            res.add(new ArrayList<>());
        }

        res.get(height).add(node.val);

        return height;
    }
}
