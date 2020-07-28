import java.util.ArrayDeque;
import java.util.Deque;

public class _0617MergeTwoBinaryTrees {
    /**
     * 1. Recursion (Pre order)
     *
     * Time complexity: O(m + n)
     * Space complexity: O(max height)
     *
     * @param t1
     * @param t2
     * @return
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        // Write your code here
        if (t1 == null && t2 == null) {
            return null;
        }

        TreeNode root = new TreeNode(0);
        if(t1 != null) {
            root.val += t1.val;
        }

        if(t2 != null) {
            root.val += t2.val;
        }

        root.left = mergeTrees(t1.left, t2.left);
        root.right = mergeTrees(t1.right, t2.right);
        return root;
    }

    /**
     * 2. Iteration Pre-order
     *
     * Time complexity: O(m + n)
     * Space complexity: O(max height)
     */
    public TreeNode mergeTrees2(TreeNode t1, TreeNode t2) {
        if (t1 == null && t2 == null) {
            return null;
        }

        TreeNode root = new TreeNode(0);
        TreeNode[] triplet = new TreeNode[3];
        triplet[0] = t1;
        triplet[1] = t2;
        triplet[2] = root;

        Deque<TreeNode[]> stack = new ArrayDeque<>();
        stack.push(triplet);

        while (!stack.isEmpty()) {
            triplet = stack.pop();

            if (triplet[0] == null && triplet[1] == null) {
                continue;
            }

            TreeNode[] right = new TreeNode[3];
            TreeNode[] left = new TreeNode[3];

            if (triplet[0] != null) {
                triplet[2].val += triplet[0].val;

                right[0] = triplet[0].right;
                if (triplet[0].right != null) {
                    right[2] = new TreeNode(0);
                }

                left[0] = triplet[0].left;
                if (triplet[0].left != null) {
                    left[2] = new TreeNode(0);
                }
            }


            if (triplet[1] != null) {
                triplet[2].val += triplet[1].val;

                right[1] = triplet[1].right;
                if (triplet[1].right != null && right[2] == null) {
                    right[2] = new TreeNode(0);
                }

                left[1] = triplet[1].left;
                if (triplet[1].left != null && left[2] == null) {
                    left[2] = new TreeNode(0);
                }
            }

            triplet[2].left = left[2];
            triplet[2].right = right[2];

            stack.push(right);
            stack.push(left);
        }

        return root;
    }
}
