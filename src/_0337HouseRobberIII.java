import java.util.HashMap;
import java.util.Map;

/**
 * A very comprehensive analysis on LeetCode:
 * https://leetcode.com/problems/house-robber-iii/discuss/79330/Step-by-step-tackling-of-the-problem
 *
 */
public class _0337HouseRobberIII {
    /**
     * 1. Dynamic Programming:
     *
     * Only consider optimal substructure:
     *
     * Time complexity:
     * The time complexity for the naive recursion in step I is indeed exponential.
     * For each tree node tn at depth d, let T(d) be the number of times the rob function will be called on it.
     * Then we have T(d) = T(d - 1) + T(d - 2).
     * This is because rob will be called on tn either from its parent (at depth d - 1) or its grandparent (at depth d - 2),
     * Note T(0) = T(1) = 1,
     * i.e., rob will be called only once for the tree root and its two child nodes.
     * Therefore T(d) will essentially be the (d+1)-th Fibonacci number (starting from 1),
     *
     *  In the worst case scenario where the binary tree degenerates to a linked list,
     *  the depth d is essentially the same as n.
     *  Therefore, T(d) will be the same as T(n) in the worst case.
     *
     *  𝑇(𝑛)=𝑇(𝑛−1)+𝑇(𝑛−2)<2𝑇(𝑛−1)
     *  2𝑇(𝑛−1)=2(2𝑇(𝑛−2))=2(2(2𝑇(𝑛−3)))=⋯=2𝑘𝑇(𝑛−𝑘)=⋯=2𝑛𝑇(0)=𝑂(2𝑛)
     *
     *
     * Space complexity:
     *
     * @param root
     * @return
     */
    public int rob(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int val = 0;

        if (root.left != null) {
            val += rob(root.left.left) + rob(root.left.right);
        }

        if (root.right != null) {
            val += rob(root.right.left) + rob(root.right.right);
        }

        return Math.max(val + root.val, rob(root.left) + rob(root.right));
    }

    /**
     * 2. Dynamic Programming
     *
     * Time complexity: exponential
     * Space complexity: O(n) for HashMap, O(h) for stack cost
     *
     * @param root
     * @return
     */
    public int rob2(TreeNode root) {
        return robSub(root, new HashMap<>());
    }

    private int robSub(TreeNode root, Map<TreeNode, Integer> map) {
        if (root == null) return 0;
        if (map.containsKey(root)) return map.get(root);

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
     * 3. Greedy
     *
     * Time complexity: O(n)
     * Space complexity: O(h) for stack cost
     *
     * @param root
     * @return
     */
    public int rob3(TreeNode root) {
        int[] max = robHelper(root);
        return Math.max(max[0], max[1]);
    }

    // Return: int[0] max when root not robbed, int[1] max when root is robbed
    private int[] robHelper(TreeNode root) {
        if (root == null) {
            return new int[2];
        }

        int[] left = robHelper(root.left);
        int[] right = robHelper(root.right);

        int[] res = new int[2];
        res[0] = Math.max(left[0], left[1]) + Math.max(right[0], right[1]);
        res[1] = root.val + left[0] + right[0];
        return res;
    }

}
