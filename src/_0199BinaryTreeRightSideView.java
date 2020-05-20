import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class _0199BinaryTreeRightSideView {
    /**
     * 1 BFS, Level order traversal, Queue
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Learn: queue.peakLast();
     *
     * @param root
     * @return
     */
    public static List<Integer> rightSideView1(TreeNode root) {
        List<Integer> res = new ArrayList<>();

        if (root == null) {
            return res;
        }

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            res.add(queue.peekLast().val);
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                if (node.left != null) {
                    queue.offer(node.left);
                }
                if (node.right != null) {
                    queue.offer(node.right);
                }
            }
        }

        return res;
    }

    /**
     * 2. DFS, Recursion
     *
     * Note:
     * (1) NOT standard pre-order traverse. It checks the RIGHT node first and then the LEFT
     * (2) currDepth == result.size() makes sure the 1st element of a level will be added
     * (3) reverse the visit order. it will return the left view of the tree.
     *
     * Time complexity: O(n)
     * Space complexity: O(lgn) -> O(n)
     *
     * @param args
     */
    public static List<Integer> rightSideView2(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        rightView(root, result, 0);
        return result;
    }

    public static void rightView(TreeNode curr, List<Integer> result, int currDepth) {
        if (curr == null) {
            return;
        }

        if (currDepth == result.size()) {
            result.add(curr.val);
        }

        rightView(curr.right, result, currDepth + 1);
        rightView(curr.left, result, currDepth + 1);
    }

    /**
     * 3. DFS, Iteration
     *
     * Time complexity: O(n)
     * Space complexity: O(lgn) for balanced trees, O(n) for skewed tres.
     * @param args
     */
    public static List<Integer> rightSideView3(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        if (root == null) {
            return res;
        }

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        Deque<Integer> levelStack = new ArrayDeque<>();

        nodeStack.push(root);
        levelStack.push(0);

        while (!nodeStack.isEmpty()) {
            TreeNode node = nodeStack.pop();
            int level = levelStack.pop();

            if (res.size() == level) {
                res.add(node.val);
            }

            if (node.left != null) {
                nodeStack.push(node.left);
                levelStack.push(level + 1);
            }

            if (node.right != null) {
                nodeStack.push(node.right);
                levelStack.push(level + 1);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.right = new TreeNode(5);
        root.right.right = new TreeNode(4);
        rightSideView3(root);

    }
}
