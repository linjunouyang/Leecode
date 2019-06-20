package Tree;

import sun.tools.tree.ArrayAccessExpression;

import java.util.*;

/**
 * 6.20 Failed. Stuck on the reversing order.
 */
public class _103BinaryTreeZigzagLevelOrderTraversal {

    /**
     * 1. Iteration. BFS. Queues
     *
     * There are two places we can reverse the order
     * 1) the level result list
     * 2) the level node list
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Notice: add(0, val) & LinkedList
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);
        boolean zigzag = false;

        while (!queue.isEmpty()) {
            List<Integer> level = new LinkedList<>();

            int size = queue.size();

            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();

                if (zigzag) {
                    level.add(0, node.val);
                } else {
                    level.add(node.val);
                }

                if (node.left != null) {
                    queue.add(node.left);
                }

                if (node.right != null) {
                    queue.add(node.right);
                }
            }

            res.add(level);
            zigzag = !zigzag;
        }
        return res;
    }


    /**
     * 2. Recursion. DFS(Pre/In/Post Order).
     *
     * By changing the order we process the root, left, and right.
     * we can achieve pre-order / in-order / post-order traversal
     *
     *
     * @param root
     * @return
     */
    public List<List<Integer>> zigzagLevelOrder2(TreeNode root)
    {
        List<List<Integer>> sol = new ArrayList<>();
        travel(root, sol, 0);
        return sol;
    }

    private void travel(TreeNode curr, List<List<Integer>> sol, int level)
    {
        if(curr == null) return;

        if(sol.size() <= level)
        {
            List<Integer> newLevel = new LinkedList<>();
            sol.add(newLevel);
        }

        List<Integer> collection  = sol.get(level);
        if(level % 2 == 0) {
            collection.add(curr.val);
        } else {
            collection.add(0, curr.val);
        }

        travel(curr.left, sol, level + 1);
        travel(curr.right, sol, level + 1);
    }

    /**
     * 3. Iteration. BFS. Two Stacks
     *
     * Time complexity: O(n)
     *
     * Space complexity: O(n)
     *
     * Runtime: 1 ms, faster than 81.42% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     * Memory Usage: 36.2 MB, less than 99.97% of Java online submissions for Binary Tree Zigzag Level Order Traversal.
     */

    public List<List<Integer>> zigzagLevelOrder3(TreeNode root) {
        List<List<Integer>> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        Deque<TreeNode> s1 = new ArrayDeque<>();
        Deque<TreeNode> s2 = new ArrayDeque<>();
        s1.push(root);

        while (!s1.isEmpty() || !s2.isEmpty()) {
            List<Integer> level = new ArrayList<>();

            while (!s1.isEmpty()) {
                TreeNode node = s1.pop();
                level.add(node.val);
                if (node.left != null) {
                    s2.push(node.left);
                }
                if (node.right != null) {
                    s2.push(node.right);
                }
            }

            res.add(level);

            level = new ArrayList<>();

            while (!s2.isEmpty()) {
                TreeNode node = s2.pop();
                level.add(node.val);
                if (node.right != null) {
                    s1.push(node.right);
                }
                if (node.left != null) {
                    s2.push(node.left);
                }
            }

            if (!level.isEmpty()) {
                res.add(level);
            }
        }

        return res;
    }
}
