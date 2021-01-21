import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Similar: LC 100, 101
 */
public class _0572SubtreeOfAnotherTree {
    /**
     * 1. Recursive PreOrder
     *
     * Time complexity:
     * O(m*n)
     * worst case: for each node in the 1st tree, we need to check if isSame(Node s, Node t). Total m nodes, isSame(...) takes O(n) worst case
     *
     * Space complexity:
     * O(height of 1str tree)(Or you can say: O(m) for worst case, O(logm) for average case)
     */
    public boolean isSubtree(TreeNode s, TreeNode t) {
        if (t == null) {
            return true;
        }
        if (s == null) {
            return false;
        }
        if (isSame(s, t)) {
            return true;
        }
        return isSubtree(s.left, t) || isSubtree(s.right, t);
    }

    private boolean isSame(TreeNode s, TreeNode t) {
        if (s == null && t == null) {
            return true;
        }
        if (s == null || t == null) {
            return false;
        }

        if (s.val != t.val) {
            return false;
        }

        return isSame(s.left, t.left) && isSame(s.right, t.right);
    }

    /**
     * 1.1 Iterative Pre-order
     */
    public boolean isSubtree11(TreeNode s, TreeNode t) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(s);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (node.val == t.val) {
                if (isSame(node, t)) {
                    return true;
                }
            }

            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }

        return false;
    }

    private boolean isSame11(TreeNode n1, TreeNode n2) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(n1);
        stack.push(n2);

        while (!stack.isEmpty()) {
            TreeNode node1 = stack.pop();
            TreeNode node2 = stack.pop();
            if (node1.val != node2.val) {
                return false;
            }

            if (node1.right != null && node2.right != null) {
                stack.push(node1.right);
                stack.push(node2.right);
            } else if (node1.right != node2.right) {
                return false;
            }

            if (node1.left != null && node2.left != null) {
                stack.push(node1.left);
                stack.push(node2.left);
            } else if (node1.left != node2.left) {
                return false;
            }
        }

        return true;
    }

    /**
     * 2. BFS
     */
    public boolean isSubtree2(TreeNode s, TreeNode t) {
        // base cases
        if (t == null) {
            return true;
        }
        if (s == null) {
            return false;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(s);

        while (!queue.isEmpty()) {
            TreeNode node = queue.poll();
            if (node.val == t.val) {
                if (compareTree(node, t)) {
                    return true;
                }
            }
            if (node.left != null) {
                queue.offer(node.left);
            }
            if (node.right != null) {
                queue.offer(node.right);
            }
        }

        return false;
    }

    // pre-condition: t1 != null && t2 != null && t1.val == t2.val
    private boolean compareTree(TreeNode t1, TreeNode t2) {
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(t1);
        queue.offer(t2);

        while (!queue.isEmpty()) {
            TreeNode n1 = queue.poll();
            TreeNode n2 = queue.poll();

            if (n1.left != null && n2.left != null) {
                if (n1.left.val == n2.left.val) {
                    queue.offer(n1.left);
                    queue.offer(n2.left);
                } else {
                    return false;
                }
            } else if (n1.left != n2.left) {
                return false;
            }

            if (n1.right != null && n2.right != null) {
                if (n1.right.val == n2.right.val) {
                    queue.offer(n1.right);
                    queue.offer(n2.right);
                } else {
                    return false;
                }
            } else if (n1.right != n2.right) {
                return false;
            }
        }

        return true;
    }
}
