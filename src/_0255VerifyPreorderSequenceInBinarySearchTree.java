import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given that the array was a preorder traversal. I could expect the root node to be the first element and the left child to be the second element. The tricky part is finding the right child. If the tree that was traversed was a BST then the right child would be the first element to the right of the root node that had a val > node.val.
 */
public class _0255VerifyPreorderSequenceInBinarySearchTree {
    /**
     * 1. Recursive DFS search
     *
     * For a given range [start, end], start is the root.
     * Without pruning, the left subtree could end in [start, end]
     *
     * Because it's a BST, all the nodes in left subtree should be smaller
     * all the nodes in right subtree should be bigger
     * -> validate this before diving into sub-problems
     *
     * Time: O(n^2)
     * average: T(n) = 2T(n/2) + O(n)
     * -> O(nlogn)
     *
     * Space: O(n)
     */
    public boolean verifyPreorder(int[] preorder) {
        return verifyHelper(preorder, 0, preorder.length - 1);
    }

    private boolean verifyHelper(int[] preorder, int start, int end) {
        if (start >= end) {
            return true;
        }

        int rootVal = preorder[start];

        boolean hasBigger = false;
        int rightStart = end + 1;
        for (int i = start + 1; i <= end; i++) {
            if (preorder[i] > rootVal && !hasBigger) {
                hasBigger = true;
                rightStart = i;
            } else if (preorder[i] < rootVal && hasBigger) {
                return false;
            }
        }

        boolean isLeftValid = verifyHelper(preorder, start + 1, rightStart - 1);
        boolean isRightValid = verifyHelper(preorder, rightStart, end);
        if (isLeftValid && isRightValid) {
            return true;
        }

        return false;
    }

    /**
     * 2. Iterative Pre-order
     *
     * Simulate the pre-order traversal, keeping a stack of nodes of which we're still in the left subtree.
     * If the next number is smaller than the last stack value, then we're still in the left subtree of all stack nodes, so just push the new one onto the stack.
     *
     * But before that, pop all smaller ancestor values, as we must now be in their right subtrees (or even further, in the right subtree of an ancestor).
     *
     * Also, use the popped values as a lower bound, since being in their right subtree means we must never come across a smaller number anymore.
     *
     * Time: O(n)
     * Space: O(n)
     */
    public boolean verifyPreorder2(int[] preorder) {
        int low = Integer.MIN_VALUE;
        Deque<Integer> path = new ArrayDeque();
        for (int val : preorder) {
            if (val < low) {
                return false;
            }
            while (!path.isEmpty() && val > path.peek()) {
                low = path.pop();
            }
            path.push(val);
        }
        return true;
    }

    public boolean verifyPreorder22(int[] preorder) {
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{0, preorder.length - 1});
        while (!stack.isEmpty()) {
            int[] range = stack.pop();
            int rootVal = preorder[range[0]];

            int firstBiggerIdx = range[1] + 1;
            for (int i = range[0]; i <= range[1]; i++) {
                if (firstBiggerIdx == range[1] + 1 && preorder[i] > rootVal) {
                    firstBiggerIdx = i;
                } else if (firstBiggerIdx != range[1] + 1 && preorder[i] < rootVal) {
                    return false;
                }
            }

            if (range[0] + 1 < firstBiggerIdx - 1) {
                stack.push(new int[]{range[0] + 1, firstBiggerIdx - 1});
            }

            if (firstBiggerIdx < range[1]) {
                stack.push(new int[]{firstBiggerIdx, range[1]});
            }

        }
        return true;
    }
}
