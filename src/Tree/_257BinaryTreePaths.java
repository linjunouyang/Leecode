package Tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * 6.17 First, come up with recursion
 *
 * ? https://leetcode.com/problems/binary-tree-paths/discuss/68258/Accepted-Java-simple-solution-in-8-lines
 * What's the time complexity and space complexity?
 */
public class _257BinaryTreePaths {
    /**
     * 1. Recursion
     *
     * Time complexity: O(nlgn) for balanced trees, root is visited #leaves number of times
     * O(n^2) for unbalanced trees
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Binary Tree Paths.
     * Memory Usage: 36.5 MB, less than 100.00% of Java online submissions for Binary Tree Paths.
     */
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();

        if (root == null) {
            return paths;
        }

        pathHelper1(paths, root, "");

        return paths;
    }

    private void pathHelper1(List<String> paths, TreeNode root, String pathSoFar) {
        if (root.left != null || root.right != null) {
            pathSoFar += root.val + "->";
            if (root.left != null) {
                pathHelper1(paths, root.left, pathSoFar);
            }
            if (root.right != null) {
                pathHelper1(paths, root.right, pathSoFar);
            }
        } else {
            pathSoFar += root.val;
            paths.add(pathSoFar);
        }
    }

    /**
     * 1.1 Recursion, clearer
     *
     * Time complexity: O(nlgn) for balanced trees, O(n^2) for unbalanced trees/
     * Space complexity: O(lgn) for balanced trees, O(n) for unbalanced trees
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Binary Tree Paths.
     * Memory Usage: 36.5 MB, less than 100.00% of Java online submissions for Binary Tree Paths.
     *
     * String concatenation is costly ????????????????????
     * Optimization: String Builder
     * "StringBuilder" is a mutable object, it will hold its value after returning.
     * String creates a copy in every recursion, no need to worry about the "side-effect" when backtrack.
     */
    public List<String> binaryTreePaths11(TreeNode root) {
        List<String> paths = new ArrayList<>();

        if (root != null) {
            pathHelper11(paths, root, "" + root.val);
        }

        return paths;
    }

    private void pathHelper11(List<String> paths, TreeNode root, String pathSoFar) {
        if (root.left == null && root.right == null) {
            paths.add(pathSoFar);
        }

        if (root.left != null) {
            pathHelper11(paths, root.left, pathSoFar + "->" + root.left.val);
        }

        if (root.right != null) {
            pathHelper11(paths, root.right, pathSoFar + "->" + root.right.val);
        }
    }

    /**
     * 1.2 Recursion with String Builder
     *
     * because the call of Path(the helper) function will change the object of sb.
     * So we should change sb object back after calling Path function.
     *
     * Time complexity: O(N) for unbalanced trees, O(nlgn) for balanced trees.
     *
     * For this kinds of problem that uses DFS to find all the paths,
     * time complexity is usually O(max_length_of_each_path * max_number_of_valid_paths)
     *
     * each time you find a valid path, you need to copy the char array first before storing it in the result.
     * Even using StringBuilder, toString creates a copy of the wrapped char array and is not an O(1) operation.
     *
     * Space complexity: O(n) for unbalanced trees. O(nlgn) for balanced trees.
     */
    public List<String> binaryTreePaths12(TreeNode root) {
        List<String> paths = new ArrayList<>();

        if (root != null) {
            StringBuilder sb = new StringBuilder();
            pathHelper12(paths, root, sb);
        }

        return paths;
    }

    private void pathHelper12(List<String> paths, TreeNode root, StringBuilder sb) {
        int len = sb.length();
        sb.append(root.val);

        if (root.left == null && root.right == null) {
            paths.add(sb.toString());
        }

        sb.append("->");

        if (root.left != null) {
            pathHelper12(paths, root.left, sb);
        }

        if (root.right != null) {
            pathHelper12(paths, root.right, sb);
        }

        sb.setLength(len);
    }

    /**
     * 2. Iteration, BFS, Level-order Traversal, Queue
     *
     * Runtime: 2 ms, faster than 32.28% of Java online submissions for Binary Tree Paths.
     * Memory Usage: 36.4 MB, less than 100.00% of Java online submissions for Binary Tree Paths.
     */
    public List<String> binaryTreePaths2(TreeNode root) {
        List<String> paths = new ArrayList<>();

        if (root == null) {
            return paths;
        }

        Deque<TreeNode> nodeQueue = new ArrayDeque<>();
        Deque<String> pathQueue = new ArrayDeque<>();
        nodeQueue.offer(root);
        pathQueue.offer("" + root.val);

        while (!nodeQueue.isEmpty()) {
            TreeNode node = nodeQueue.poll();
            String path = pathQueue.poll();

            if (node.left == null && node.right == null) {
                paths.add(path);
            }

            if (node.left != null) {
                nodeQueue.offer(node.left);
                pathQueue.offer(path + "->" + node.left.val);
            }
            if (node.right != null) {
                nodeQueue.offer(node.right);
                pathQueue.offer(path + "->" + node.right.val);
            }
        }

        return paths;
    }

    /**
     * 3. Iteration, DFS, Pre-order Traversal
     *
     * Runtime: 2 ms, faster than 32.28% of Java online submissions for Binary Tree Paths.
     * Memory Usage: 36.8 MB, less than 100.00% of Java online submissions for Binary Tree Paths.
     */
    public List<String> binaryTreePaths3(TreeNode root) {
        List<String> paths = new ArrayList<>();

        if (root == null) {
            return paths;
        }

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<String> pathStack = new ArrayDeque<>();
        nodeStack.push(root);
        pathStack.push("" + root.val); // notice the empty string

        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            String path = pathStack.pop();

            if (node.left == null && node.right == null) {
                paths.add(path);
            }

            if (node.right != null) {
                nodeStack.push(node.right);
                pathStack.push(path + "->" + node.right.val);
            }

            if (node.left != null) {
                nodeStack.push(node.left);
                pathStack.push(path + "->" + node.left.val);
            }
        }

        return paths;

    }
}
