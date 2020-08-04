import java.util.ArrayDeque;
import java.util.Deque;

public class _1008ConstructBinarySearchTreeFromPreorderTraversal {
    /**
     * 1. Recursive pre-order traversal
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param preorder
     * @return
     */
    public TreeNode bstFromPreorder(int[] preorder) {
        if (preorder == null) {
            return null;
        }
        return helper(preorder, 0, preorder.length - 1);
    }

    // build bst from preorder [start, end]
    private TreeNode helper(int[] preorder, int start, int end) {
        if (start > end) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[start]);

        int rightStart = start + 1;
        while (rightStart <= end && preorder[rightStart] < preorder[start]) {
            rightStart++;
        }

        root.left = helper(preorder, start + 1, rightStart - 1);
        root.right = helper(preorder, rightStart, end);
        return root;
    }

    /**
     * Iterative Helper
     *
     * Summary:
     * Need to store different type of info along the way -> another stack
     * recursion base case -> its opposite would be the pre-condition for entering stack
     */
    private TreeNode helper(int[] preorder) {

        Deque<TreeNode> parentStack = new ArrayDeque<>();
        TreeNode root = new TreeNode(preorder[0]);
        parentStack.push(root);

        Deque<int[]> rangeStack = new ArrayDeque<>();
        rangeStack.push(new int[]{1, preorder.length - 1});

        while (!rangeStack.isEmpty()) {
            TreeNode parent = parentStack.pop();
            int[] range = rangeStack.pop();

            int rightStart = range[0];
            while (rightStart <= range[1] && preorder[rightStart] < parent.val) {
                rightStart++;
            }

            if (rightStart <= range[1]) {

                parent.right = new TreeNode(preorder[rightStart]);
                parentStack.push(parent.right);
                rangeStack.push(new int[]{rightStart + 1, range[1]});
            }

            if (range[0] <= rightStart - 1) {
                parent.left = new TreeNode(preorder[range[0]]);
                parentStack.push(parent.left);
                rangeStack.push(new int[]{range[0] + 1, rightStart - 1});

            }

        }

        return root;

    }
}
