package Tree;

import java.util.HashMap;
import java.util.Map;

/**
 * TO-DO:
 *
 * Time and space complexity for 3 solutions
 *
 */

public class _337HouseRobberIII {
    /**
     * 1. Simply recursion ? Not dynamic programming.
     *
     * "opt substructure": if we want to rob max money from current rooted binary tree,
     * we ope that we can do the same to its left and right subtrees.
     *
     * the key : construct the solution to the original problem from solutions to its subproblems,
     * i.e., how to get rob(root) from rob(root.left), rob(root.right), ... etc.
     *
     * Apparently the analyses above suggest a recursive solution.
     * And for recursion, it's always worthwhile figuring out the following two properties:
     * 1. Termination condition: the tree is empty --- nothing to rob so the amount of money is zero.
     * 2. Recurrence relation: only two scenarios at the end: root is robbed or is not.
     *
     * a. root robbed. the next level of subtrees that are available would be the four "grandchild-subtrees"
     * (root.left.left, root.left.right, root.right.left, root.right.right).
     *
     * b. root not robbed. the next level of available subtrees would just be the two "child-subtrees"
     * (root.left, root.right).
     *
     * We only need to choose the scenario which yields the larger amount of money.
     *
     *
     * Time complexity:
     * exponential ?
     * For each tree node tn at depth d, let T(d) be the number of times the rob function will be called on it.
     * Then we have T(d) = T(d - 1) + T(d - 2).
     * rob will be called on tn either from its parent (at depth d - 1) or its grandparent (at depth d - 2),
     *  Note T(0) = T(1) = 1, i.e., rob will be called only once for the tree root and its two child nodes.
     *
     * Therefore T(d) will essentially be the (d+1)-th Fibonacci number (starting from 1),
     * which grows exponentially (more info can be found here).
     *
     * Space complexity:
     * Recursion call stack: O(lgn) for balanced trees, O(n) for skewed tres.
     *
     *
     */
    public int rob1(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int val = 0;

        if (root.left != null) {
            val += rob1(root.left.left) + rob1(root.left.right);
        }

        if (root.right != null) {
            val += rob1(root.right.left) + rob1(root.right.right);
        }

        return Math.max(val + root.val, rob1(root.left) + rob1(root.right));
    }

    /**
     * 2. Dynamic Programming
     *
     * In step I, we only considered the aspect of "optimal substructure",
     * but think little about the possibilities of overlapping of the subproblems.
     *
     * For example, to obtain rob(root), we need rob(root.left), rob(root.right),
     * rob(root.left.left), rob(root.left.right), rob(root.right.left), rob(root.right.right);
     * but to get rob(root.left), we also need rob(root.left.left), rob(root.left.right),
     * similarly for rob(root.right)
     *
     * The naive solution above computed these subproblems repeatedly, which resulted in bad time performance.
     *
     * recall the two conditions for dynamic programming: "optimal substructure" + "overlapping of subproblems",
     * we actually have a DP problem.
     *
     * A naive way to implement DP here is to use a hash map to record the results for visited subtrees.
     *
     * Time complexity:
     * n entries in the map
     * O(n)
     *
     * Space complexity:
     * HashMap: O(n)
     * Recursion call stack: O(lgn) for balanced trees, O(n) for skewed trees;
     *
     *
     */
    public int rob2(TreeNode root) {
        return robSub(root, new HashMap<>());
    }

    private int robSub(TreeNode root, Map<TreeNode, Integer> map) {
        if (root == null) {
            return 0;
        }

        if (map.containsKey(root)) {
            return map.get(root);
        }

        int val = 0;

        if (root.left != null) {
            val += robSub(root.left.left, map) + robSub(root.left.right, map);
        }

        if (root.right != null) {
            val += robSub(root.right.left, map) + robSub(root.right.right, map);
        }

        val = Math.max(val + root.val, robSub(root.left, map) + robSub(root.right, map));
        map.put(root, val);

        return val;
    }

    /**
     * 3 Greedy, DFS
     *
     * Solution 1: optimal substructure
     * rob(root) = maximum amount of money that can be robbed of the binary tree rooted at root.
     *
     * Solution 2: optimal substructure + overlapping problems
     * dynamic programming
     * using map to store computed results
     *
     * Why we have overlapping sub-problems?
     * for each tree root, there are two scenarios: it is robbed or is not.
     * rob(root) does not distinguish between these two cases,
     * so "information is lost as the recursion goes deeper and deeper",
     * which results in repeated subproblems
     *
     * If we were able to maintain the information about the two scenarios for each tree root,
     * let's see how it plays out.
     * Redefine rob(root) as a new function which will return an array of two elements,
     * the first element of which denotes the maximum amount of money that can be robbed if root is not robbed,
     * while the second element signifies the maximum amount of money robbed if it is robbed.
     *
     *
     * 1st element of rob(root), sum up the larger elements of rob(root.left) and rob(root.right), respectively,
     * since root is not robbed and we are free to rob its left and right subtrees.
     *
     * 2nd element of rob(root), add up the 1st elements of rob(root.left) and rob(root.right), respectively,
     * plus the value robbed from root itself,
     * it's guaranteed that we cannot rob the nodes of root.left and root.right.
     *
     * by keeping track of the information of both scenarios,
     * we decoupled the subproblems and the solution essentially boiled down to a greedy one.
     *
     * Time complexity (not sure):
     * O(n)
     *
     * Space complexity:
     * O(lgn) for balanced trees
     * O(n) for skewed trees
     *
     *
     */
    public int rob3(TreeNode root) {
        int [] res = robSub3(root);
        return Math.max(res[0], res[1]);
    }

    private int[] robSub3(TreeNode root) {
        int[] res = new int[2];

        if (root == null) {
            return res;
        }

        int[] left = robSub3(root.left);
        int[] right = robSub3(root.right);

        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        res[1] = root.val + left[0] + right[0];

        return res;
    }
}
