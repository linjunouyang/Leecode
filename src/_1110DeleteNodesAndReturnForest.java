import java.util.*;

/**
 * [Google] [eBay]
 *
 * For a tree problem, solve it with recursion first
 *
 * The question is composed of two requirements:
 *
 * To remove a node, the node need to notify its parent about its' existence.
 * To determine whether a node is a root node in the final forest, we need to know [1] whether the node is removed (which is trivial), and [2] whether its parent is removed (which requires the parent to notify the child)
 * It is very obvious that a tree problem is likely to be solved by recursion. The two components above are actually examining interviewees' understanding to the two key points of recursion:
 *
 * passing info downwards -- by arguments -- deleted passed to child to indicate whether a child is a root
 * passing info upwards -- by return value -- deleted node return null to parent, others return themselves
 */
public class _1110DeleteNodesAndReturnForest {
    /**
     * 1. Recursive pre-order
     *
     * If a parent node is about to be deleted, its two child becomes root node
     *
     * When to add to res list:
     * When a node is a root (has no parent) and isn't deleted
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public List<TreeNode> delNodes(TreeNode root, int[] to_delete) {
        Set<Integer> set = new HashSet<>();
        for (int i : to_delete) {
            set.add(i);
        }
        List<TreeNode> res = new ArrayList<>();

        helper(root, true, set, res);
        return res;
    }

    private TreeNode helper(TreeNode node, boolean is_root, Set<Integer> set, List<TreeNode> res) {
        if (node == null) {
            return null;
        }
        boolean deleted = set.contains(node.val);
        if (is_root && !deleted) {
            res.add(node);
        }
        node.left = helper(node.left, deleted, set, res);
        node.right =  helper(node.right, deleted, set, res);
        return deleted ? null : node;
    }

    /**
     * 2. Iterative pre-order
     */
    public List<TreeNode> delNodes2(TreeNode root, int[] to_delete) {
        Set<Integer> set = new HashSet<>();
        for (int i : to_delete) {
            set.add(i);
        }
        List<TreeNode> res = new ArrayList<>();

        Deque<TreeNode> stack = new ArrayDeque<>();
        Deque<Boolean> stack2 = new ArrayDeque<>();
        stack.push(root);
        stack2.push(true);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            boolean isRoot = stack2.pop();
            boolean isDeleted = set.contains(node.val);

            if (isRoot && !isDeleted) {
                res.add(node);
            }

            if (node.right != null) {
                stack.push(node.right);
                stack2.push(isDeleted);
                if (set.contains(node.right.val)) {
                    node.right = null;
                }
            }

            if (node.left != null) {
                stack.push(node.left);
                stack2.push(isDeleted);
                if (set.contains(node.left.val)) {
                    node.left = null;
                }
            }
        }
        return res;
    }

    /**
     * 3. Iterative Pre-order
     *
     */
    public List<TreeNode> delNodes3(TreeNode root, int[] to_delete) {
        Set<Integer> set = new HashSet<>();
        for (int i : to_delete) {
            set.add(i);
        }
        List<TreeNode> res = new ArrayList<>();
        // Special treatment for root, as nodes get added to the answer
        // when their parents get deleted and root has no parent.
        if (!set.contains(root.val)) {
            res.add(root);
        }

        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            boolean isDeleted = set.contains(node.val);

            if (node.right != null) {
                stack.push(node.right);
                if (set.contains(node.right.val)) {
                    // in the recursive method, we pass the child deletion message to parent through return value
                    // in iteration, we simply check this at parent level
                    node.right = null;
                } else if (isDeleted) {
                    res.add(node.right);
                }
            }

            if (node.left != null) {
                stack.push(node.left);
                if (set.contains(node.left.val)) {
                    node.left = null;
                } else if (isDeleted) {
                    //如果left node不需要删除，我们只在当它的父节点需要删除时才将它加入res
                    res.add(node.left);
                }
            }

            // if we don't use the innermost else if in previous block
//            if (isDeleted) {
//                if (node.left != null) {
//                    res.add(node.left);
//                }
//
//                if (node.right != null) {
//                    res.add(node.right);
//                }
//            }
        }
        return res;
    }

    /**
     * 4. Iterative BFS
     * https://leetcode.com/problems/delete-nodes-and-return-forest/discuss/629417/Java-Iterative-Solution
     * Similar code as above
     */

}
