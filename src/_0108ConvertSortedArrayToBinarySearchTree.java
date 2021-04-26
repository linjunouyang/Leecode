import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class _0108ConvertSortedArrayToBinarySearchTree {
    /**
     * 1. Pre-order traversal
     *
     * Time: O(nlogn)
     * Arrays.sort: O(nlogn)
     * Construct nodes: O(n)
     *
     * Space:
     * Recursive version: O(logn) used by recursive stack
     * Iterative version: O(logn) use by ArrayDeque
     */
    public TreeNode constructBST(int[] arr) {
        if (arr == null) {
            return null;
        }

        Arrays.sort(arr);

        // Other implementations: recursiveConstruct, iterativeConstruct2
        return iterativeConstruct(arr, 0, arr.length - 1);
    }

    // Iterative version 1 (My own idea)
    private TreeNode iterativeConstruct(int[] arr, int left, int right) {
        if (left > right) {
            return null;
        }

        Deque<TreeNode> parentStack = new ArrayDeque<>();
        Deque<int[]> rangeStack = new ArrayDeque<>();
        Deque<Boolean> isLeftStack = new ArrayDeque<>();

        int mid = (arr.length - 1) / 2;
        TreeNode root = new TreeNode(arr[mid]);

        if (0 <= mid - 1) {
            rangeStack.push(new int[]{0, mid - 1});
            parentStack.push(root);
            isLeftStack.push(true);
        }

        if (mid + 1 <= arr.length - 1) {
            rangeStack.push(new int[]{mid + 1, arr.length - 1});
            parentStack.push(root);
            isLeftStack.push(false);
        }

        while (!rangeStack.isEmpty()) {
            int[] range = rangeStack.pop();
            TreeNode parent = parentStack.pop();
            boolean isLeft = isLeftStack.pop();

            mid = range[0] + (range[1] - range[0]) / 2;
            TreeNode node = new TreeNode(arr[mid]);
            if (isLeft) {
                parent.left = node;
            } else {
                parent.right = node;
            }

            if (range[0] <= mid - 1) {
                parentStack.push(node);
                rangeStack.push(new int[]{range[0], mid - 1});
                isLeftStack.push(true);
            }

            if (mid + 1 <= range[1]) {
                parentStack.push(node);
                rangeStack.push(new int[]{mid + 1, range[1]});
                isLeftStack.push(false);
            }
        }

        return root;
    }

    // Iterative version 2.
    // Reference: https://leetcode.com/problems/convert-sorted-array-to-binary-search-tree/discuss/35218/Java-Iterative-Solution
    private TreeNode iterativeConstruct2(int[] arr, int left, int right) {
        if (left > right) {
            return null;
        }

        TreeNode root = new TreeNode(0);

        Deque<TreeNode> nodeStack = new ArrayDeque<>();
        nodeStack.push(root);

        Deque<int[]> rangeStack = new ArrayDeque<>();
        rangeStack.push(new int[]{0, arr.length - 1});

        while (!rangeStack.isEmpty()) {
            int[] range = rangeStack.pop();
            TreeNode node = nodeStack.pop();

            int mid = range[0] + (range[1] - range[0]) / 2;
            node.val = arr[mid];

            if (range[0] <= mid - 1) {
                node.left = new TreeNode(0);
                nodeStack.push(node.left);
                rangeStack.push(new int[]{range[0], mid - 1});
            }

            if (mid + 1 <= range[1]) {
                node.right = new TreeNode(0);
                nodeStack.push(node.right);
                rangeStack.push(new int[]{mid + 1, range[1]});
            }
        }

        return root;
    }

    private TreeNode recursiveConstruct(int[] arr, int left, int right) {
        if (left > right) {
            return null;
        }

        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(arr[mid]);

        root.left = recursiveConstruct(arr, left, mid - 1);
        root.right = recursiveConstruct(arr, mid + 1, right);

        return root;
    }
}
