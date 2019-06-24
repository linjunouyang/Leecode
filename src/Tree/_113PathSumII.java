package Tree;


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
public class _113PathSumII {
    /**
     * 1. Recursion, DFS (pre-order), backtracking
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
     * Time complexity: O(n)
     * Space complexity:
     * Recursion call stack:
     * O(n) for unbalanced trees
     * O(logn) for balanced trees
     *
     * Result:
     * O(nlgn)
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Path Sum II.
     * Memory Usage: 37.5 MB, less than 99.98% of Java online submissions for Path Sum II.
     * @param root
     * @param sum
     * @return
     */
    public List<List<Integer>> pathSum(TreeNode root, int sum) {
        List<List<Integer>> paths = new ArrayList<>();
        List<Integer> path = new ArrayList<>();
        pathHelper(root, sum, path, paths);
        return paths;
    }

    public void pathHelper(TreeNode root, int sum, List<Integer> path, List<List<Integer>> paths) {
        if (root == null) {
            return;
        }

        path.add(root.val);

        if (root.left == null && root.right == null && sum == root.val) {
            paths.add(new ArrayList<>(path)); // make sure the inserted list won't be changed later on
        } else {
            pathHelper(root.left, sum - root.val, path, paths);
            pathHelper(root.right, sum - root.val, path, paths);
        }

        path.remove(path.size() - 1); //
    }

    /**
     * 2. Iteration, DFS (post-order)
     */
    public static List<List<Integer>> pathSum2(TreeNode root, int sum) {
        List<List<Integer>> list = new ArrayList<>();

        if (root == null) {
            return list;
        }

        List<Integer> path = new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        int pathSum = 0;
        // to determine whether the right tree has been visited. prefer name it lastVisited
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

            if (curr.right != null & curr.right != prev) {
                curr = curr.right;
                continue;
            }

            // check leaf
            if (curr.left == null && curr.right == null && pathSum == sum) {
                list.add(new ArrayList<>(path));
            }

            stack.pop();
            prev = curr;
            pathSum -= curr.val;
            path.remove(path.size() - 1);
            // so check the next item from the stack
            curr = null;
        }

        return list;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(4);
        root.right = new TreeNode(8);
        root.left.left = new TreeNode(11);
        root.left.left.left = new TreeNode(7);
        root.left.left.right = new TreeNode(2);
        root.right.left = new TreeNode(13);
        root.right.right = new TreeNode(4);
        root.right.right.left = new TreeNode(5);
        root.right.right.right = new TreeNode(1);

        pathSum2(root, 22);
    }
}
