import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

/**
 * 6.22 Failed
 *
 * Notice the definition in the question:
 *
 * a binary tree in which the depth of the two subtrees of ** every node ** never differ by more than 1.
 * Which means it's not sufficient to just compare the heights of left and right subtrees.
 * We have to check whether the left and right subtree is balanced as well.
 *
 * https://stackoverflow.com/questions/2603692/what-is-the-difference-between-tree-depth-and-height
 * Be aware of height and depth definition
 */

public class _0110BalancedBinaryTree {
    /**
     * 1. Recursion, top-down
     *
     * Check strictly according to the definition:
     * 1) difference between the heights of the two sub trees are not bigger than 1
     * 2) both the left sub tree and right sub tree are also balanced
     *
     * Time complexity: O(nlgn)
     *
     * Time complexity for depth:
     * T(n) = 2T(n/2) + O(1)
     * O(n ^ logba) = O(n)
     *
     * Time complexity for isBalanced:
     * for balanced trees: T(n) = 2(T/2) + O(n) (calculating the depth is O(n))
     * O(nlogn)
     * for unbalanced trees: O(n) early termination because of Math.abs(left - right) <= 1
     * ** one possible mistake : T(n) = T(n-1) + O(n) -> O(n^2) **
     *
     * Space complexity:
     * O(lgn) for balanced trees.
     * O(n) for skewed trees.
     *
     * Runtime: 1 ms, faster than 88.13% of Java online submissions for Balanced Binary Tree.
     * Memory Usage: 40.7 MB, less than 51.19% of Java online submissions for Balanced Binary Tree.
     *
     * @param root
     * @return
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        int left = depth(root.left);
        int right = depth(root.right);

        return Math.abs(left - right) <= 1 && isBalanced(root.left) && isBalanced(root.right);
    }

    private int depth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        return Math.max(depth(root.left), depth(root.right)) + 1;
    }

    /**
     * 2. Recursion, Bottom-up, DFS
     *
     * 上面这种Brute Froce的方法，整棵树有很多冗余无意义的遍历，
     * 其实我们在处理完get_height这个高度的时候，我们完全可以在检查每个节点高度并且返回的同时，
     * 记录左右差是否已经超过1，只要有一个节点超过1，那么直接返回False即可，
     *
     * Instead of calling depth() explicitly for each child node,
     * we return the height of the current node in DFS recursion.
     *
     * When the sub tree of the current node (inclusive) is balanced,
     * the function dfsHeight() returns a non-negative value as the height.
     * Otherwise -1 is returned.
     *
     * According to the leftHeight and rightHeight of the two children,
     * the parent node could check if the sub tree is balanced, and decides its return value.
     *
     * Time complexity: O(n)
     * each node is visited once
     *
     * Space complexity:
     * O(n) for skewed trees
     * O(lgn) for balanced trees
     *
     * Runtime: 1 ms, faster than 88.13% of Java online submissions for Balanced Binary Tree.
     * Memory Usage: 36.6 MB, less than 100.00% of Java online submissions for Balanced Binary Tree.
     */
    public boolean isBalanced2(TreeNode root) {
        return dfsHeight(root) != -1;
    }

    private int dfsHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = dfsHeight(root.left);
        if (leftHeight == -1) {
            // why checking leftHeight and rightHeight:
            // once unbalanced, propagate the result back to top
            return -1;
        }

        int rightHeight = dfsHeight(root.right);
        if (rightHeight == -1) {
            // why checking leftHeight and rightHeight:
            // once unbalanced, propagate the result back to top
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            // only check current node
            return -1;
        }

        return Math.max(leftHeight, rightHeight) + 1;
    }

    /**
     * Same idea, but counting depth
     */
    public boolean isBalanced22(TreeNode root) {
        return isBalancedHelper(root, new int[]{0});
    }

    private boolean isBalancedHelper(TreeNode root, int[] depth) {
        if (root == null) {
            return true;
        }
        depth[0]++;

        int[] leftDepth = new int[]{depth[0]};
        if(!isBalancedHelper(root.left, leftDepth)) {
            return false;
        }

        int[] rightDepth = new int[]{depth[0]};
        if(!isBalancedHelper(root.right, rightDepth)) {
            return false;
        }

        if (Math.abs(leftDepth[0] - rightDepth[0]) > 1) {
            return false;
        }

        depth[0] = Math.max(leftDepth[0], rightDepth[0]);

        return true;
    }

    /**
     * 3. Iterative:
     *
     * Time: O(n)
     * Space: O(n)
     *
     */
    public boolean isBalanced3(TreeNode root) {
        if (root == null) {
            return true;
        }
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        TreeNode prev = null;
        HashMap<TreeNode, Integer> map = new HashMap<>(); // TreeNode -> height

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.peek();

            if (curr.right == null || curr.right == prev) {
                stack.pop();

                int leftHeight = map.getOrDefault(curr.left,  0);
                int rightHeight = map.getOrDefault(curr.right, 0);
                if (Math.abs(leftHeight - rightHeight) > 1) {
                    return false;
                }
                map.put(curr, Math.max(leftHeight, rightHeight) + 1);

                prev = curr;
                curr = null;
            } else {
                curr = curr.right;
            }

        }

        return true;

    }
}
