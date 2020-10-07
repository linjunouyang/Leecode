import java.util.HashMap;

public class _0437PathSumIII {
    /**
     * 1. Backtracking
     *
     * Time:
     * Space:
     */
    public int pathSum(TreeNode root, int sum) {
        return backtrack(root, sum, 0, false);
    }

    // starting with sum and root, explore possible paths whose sum is target
    private int backtrack(TreeNode root, int target, int sum, boolean hasPrev) {
        if (root == null) {
            return 0;
        }

        sum += root.val;
        int pathsWithRoot = sum == target ? 1 : 0;
        pathsWithRoot += backtrack(root.left, target, sum, true) + backtrack(root.right, target, sum, true);

        sum -= root.val;
        int pathWithoutRoot = 0;
        if (!hasPrev) {
            pathWithoutRoot = backtrack(root.left, target, sum, false) + backtrack(root.right, target, sum, false);
        }

        return pathsWithRoot + pathWithoutRoot;
    }

    /**
     * 2. Prefix Sum, DFS,
     *
     * how you can keep track of a sequence of sums in a 1D hash table, when a tree can have multiple branches,
     * how do you keep track of duplicate sums on different branches of the tree?
     *
     * The answer is that this method only keeps track of 1 branch at a time.
     * Because we're doing DFS, we will iterate all the way to the end of a single branch before coming back up.
     * However, as we're coming back up, we remove the nodes at the bottom of the branch using line map.put(sum, map.get(sum) - 1);, before ending the function.
     *
     * the hash table is only keeping track of a portion of a single branch at any given time, from the root node to the current node only.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int pathSum2(TreeNode root, int sum) {
        HashMap<Integer, Integer> preSum = new HashMap(); // <PrefixSum, Frequency>
        preSum.put(0,1); // Without this line, not able to count paths starting from root
        return helper(root, 0, sum, preSum);
    }

    /**
     * find all paths ended with current node, whose sum equal to target.
     */
    public int helper(TreeNode root, int currSum, int target, HashMap<Integer, Integer> preSum) {
        if (root == null) {
            return 0;
        }

        currSum += root.val;

        /**
         * [1], 0
         * correct: currSum = 1, res = 0
         * wrong: preSum.put(1, 1), res = 1
         */
        int res = preSum.getOrDefault(currSum - target, 0);
        preSum.put(currSum, preSum.getOrDefault(currSum, 0) + 1);

        res += helper(root.left, currSum, target, preSum) + helper(root.right, currSum, target, preSum);
        /**
         *  if left subtree has been scanned, preSum has to remove this path,
         *  because this path is not the prefix path of the right subtree.
         *  Same as the left subtree, when right subtree is scanned, the path should be removed too.
         */
        preSum.put(currSum, preSum.get(currSum) - 1);
        return res;
    }

}
