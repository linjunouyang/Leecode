import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class _1120MaximumAverageSubtree {
    /**
     * 1. DFS
     *
     * the value of Double.MIN_VALUE is actually positive?
     * Use -Double.MAX_VALUE to represent the min
     *
     * Or we can extract a class:
     * 1) nodeCount 2) valueSum 3)maxAve found in this subtree
     *
     * Time: O(n)
     * Space: O(n)
     */
    public double maximumAverageSubtree(TreeNode root) {
        double[] max = {-Double.MAX_VALUE};
        maxAvgHelper(root, new HashMap<>(), max);
        return max[0];
    }

    private void maxAvgHelper(TreeNode root, Map<TreeNode, int[]> map, double[] max) {
        if (root == null) {
            return;
        }

        maxAvgHelper(root.left, map, max);
        maxAvgHelper(root.right, map, max);

        int[] left = map.getOrDefault(root.left, new int[]{0, 0});
        int[] right = map.getOrDefault(root.right, new int[]{0, 0});
        int num = left[0] + right[0] + 1;
        int sum = left[1] + right[1] + root.val;
        map.put(root, new int[]{num, sum});
        double avg = sum / (double) num;
        if (avg > max[0]) {
            max[0] = avg;
        }
    }

    /**
     * 2. Postorder Iteration
     *
     * Time: O(n)
     * Space: O(n)
     */
    public double maximumAverageSubtree2(TreeNode root) {
        double maxAvg = -Double.MAX_VALUE;
        Map<TreeNode, int[]> map = new HashMap<>();
        TreeNode cur = root;
        TreeNode pre = null;
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (cur != null || !stack.isEmpty()) {
            while (cur != null) {
                stack.push(cur);
                cur = cur.left;
            }

            cur = stack.peek();

            if (cur.right == null || pre == cur.right) {
                stack.pop();

                int[] left = map.getOrDefault(cur.left, new int[]{0, 0});
                int[] right = map.getOrDefault(cur.right, new int[]{0, 0});
                int num = 1 + left[0] + right[0];
                int sum = cur.val + left[1] + right[1];
                map.put(cur, new int[]{num, sum});
                maxAvg = Math.max(maxAvg, sum / (double)num);

                pre = cur;
                cur = null;

            } else {
                cur = cur.right;
            }

        }
        return maxAvg;
    }
}
