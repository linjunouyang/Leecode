import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 6.23 First. Almost got it. Failed. Didn't understand iterative version
 *
 * 1. Wrong condition:
 * if (root.val > sum) {
 *     return;
 * }
 *
 * Notice that nodes' value can be negative
 * [-2, null, -3] sum = -5
 * output: []
 * expected: [[-2, -3]]
 *
 * 2. backtracking
 */
public class _0113PathSumII {
    /**
     * 1. Recursion, DFS (pre-order), backtracking
     *
     * can be changed into in/post order
     *
     * 1.
     * when you use add function of List, it just add a copy of reference of the object into the List
     * So if you don't create a new copy, all the reference you add to your result refer to the same object.
     *
     * 2. backtrack
     * path.remove(path.size() - 1))
     * 1. Just added the path, backtrack
     * 2. Not a valid path, backtrack
     *
     * Time complexity: O(n * h) -> could be O(n ^ 2)
     * copying a list take O(h) time
     *
     * We can construct an unbalanced tree: the root has left and right children;
     * the left child of any node is always a leaf; the right child of any node always has its own left and right children except the last right child.
     * If the tree depth is n, the tree has 2n+1 nodes, n+1 leaves. The leaves have depth 1,2,3,....n.
     * If we have to output all the paths from root to leaves, we have to output 1+2+..n=O(n^2) numbers.
     * So the time complexity for the worst cases is O(n^2).
     *
     *           A
     *          / \
     *         B   C
     *            / \
     *           D   E
     *              / \
     *         and so on...
     *
     * Space complexity:
     * Recursion call stack:
     * O(n) for unbalanced trees
     * O(logn) for balanced trees
     *
     * Result space: O(nlgn)
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        pathHelper(root, sum, path, paths);
        return paths;
    }

    public void pathHelper(TreeNode root, int sum, List<Integer> path, List<List<Integer>> paths) {
        if (root == null) {
            // if we check sum here, we will add duplicate paths
            return;
        }

        path.add(root.val);

        if (root.left == null && root.right == null && sum == root.val) {
            // make sure the inserted list won't be changed later on
            paths.add(new ArrayList<>(path));
        } else {
            pathHelper(root.left, sum - root.val, path, paths);
            pathHelper(root.right, sum - root.val, path, paths);
        }

        // we finished exploring in this subtree, we will go back to parent, so remove this num
        path.remove(path.size() - 1);
    }

    /**
     * 2. Iteration, DFS (post-order)
     *
     * pre/in-order should also work
     *
     * Time: O(n * h) -> could be O(n ^ 2)
     * Copying the list takes O(h)
     * Space: O(h)
     */
    public List<List<Integer>> pathSum2(TreeNode root, int targetSum) {
        List<List<Integer>> paths = new ArrayList<>();

        if (root == null) {
            return paths;
        }

        List<Integer> path = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        int pathSum = 0;
        TreeNode prev = null;
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                path.add(curr.val);
                pathSum += curr.val;
                curr = curr.left;
            }

            // check left leaf node's right subtree
            // or if it is from the right subtree
            // Why peek? If it has right subtree, no need to push it back
            curr = stack.peek();

            if (curr.right == null || prev == curr.right) {
                curr = stack.pop();

                if (curr.left == null && curr.right == null && pathSum == targetSum) {
                    paths.add(new ArrayList<>(path));
                }

                pathSum -= curr.val;
                path.remove(path.size() - 1);
                prev = curr;
                curr = null;
            } else {
                curr = curr.right;
            }
        }

        return paths;
    }

}
