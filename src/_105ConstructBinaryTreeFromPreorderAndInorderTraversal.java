import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * 6.21 Failed. Have a rough idea, but can't not implement it.
 *
 */
public class _105ConstructBinaryTreeFromPreorderAndInorderTraversal {
    /**
     * 1. Recursion
     *
     * Time complexity:
     * O(n^2) for left-skewed trees and balanced trees
     * O(n) for right-skewed trees.
     *
     * Space complexity:
     * O(n) for skewed trees.
     * O(lgn) for balanced trees.
     *
     * 1
     *  2
     *   3
     *
     * pre: 1 2 3
     * in 1 2 3
     *
     *     1
     *    2
     *   3
     *  4
     * 5
     * pre: 1 2 3 4 5
     * in: 5 4 3 2 1
     *
     * Runtime: 11 ms, faster than 30.83% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
     * Memory Usage: 43.2 MB, less than 8.71% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
     *
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if (preorder == null || inorder == null || preorder.length != inorder.length
            || preorder.length == 0) {
            return null;
        }

        return build(preorder, 0, preorder.length - 1,
                inorder, 0, inorder.length - 1);
    }

    private TreeNode build(int[] preorder, int preLow, int preHigh, int[] inorder, int inLow, int inHigh) {
        // no need to check inLow > inHigh
        if (preLow > preHigh) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[preLow]);

        int inorderRoot = inLow;

        for (int i = inLow; i <= inHigh; i++) {
            if (inorder[i] == root.val) {
                inorderRoot = i;
                break;
            }
        }

        int leftTreeLen = inorderRoot - inLow;
        root.left = build(preorder, preLow + 1, preLow + leftTreeLen, inorder, inLow, inorderRoot - 1);
        root.right = build(preorder, preLow + leftTreeLen + 1, preHigh, inorder, inorderRoot + 1, inHigh);
        return root;
    }

    /**
     * 2. Recursion
     *
     * Using hashmap to save time for searching index
     *
     * Time complexity:
     * O(n) construct the map + visit each node once
     *
     * Space complexity:
     * O(n) hashtable + [depth of the tree] recursion calls
     *
     * Runtime: 2 ms, faster than 96.93% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
     * Memory Usage: 40.5 MB, less than 14.10% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
     */
    public TreeNode buildTree2(int[] preorder, int[] inorder) {
        Map<Integer, Integer> inMap = new HashMap<Integer, Integer>();

        for (int i = 0; i < inorder.length; i++) {
            inMap.put(inorder[i], i);
        }

        return build2(preorder, 0, 0, inorder.length - 1, inMap);
    }

    private TreeNode build2(int[] preorder, int preStart, int inStart, int inEnd, Map<Integer, Integer> inMap) {
        if (inStart > inEnd) {
            return null;
        }

        TreeNode root = new TreeNode(preorder[preStart]);
        int inRoot = inMap.get(root.val);
        int leftNum = inRoot - inStart;

        root.left = build2(preorder, preStart + 1, inStart, inRoot - 1, inMap);
        root.right = build2(preorder, preStart + leftNum + 1, inRoot + 1, inEnd, inMap);

        return root;
    }

    /**
     * 3. Iteration (Pre-order)
     *
     *       1
     *      / \
     *    2    3
     *   / \  / \
     *  4  5  6 7
     *
     * pre: 1 2 4 5 3 6 7
     * in: 4 2 5 1 6 3 7
     *
     * stack: 7
     * value:
     * parent: null
     *
     * Time complexity:
     * O(n) HashMap building + visit every node once
     *
     * Space complexity:
     * O(n) for hashmap
     * O(n) for left-skewed trees' stack
     * O(1) for right-skewed trees' stack
     * O(lgn) for balanced trees
     *
     * Runtime: 4 ms, faster than 59.73% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
     * Memory Usage: 36 MB, less than 99.90% of Java online submissions for Construct Binary Tree from Preorder and Inorder Traversal.
     */
    public TreeNode buildTree3(int[] preorder, int[] inorder) {
        // deal with edge cases
        if (preorder.length == 0) {
            return null;
        }

        // build a map of the indices of the values as they appear in the inorder array
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            map.put(inorder[i], i);
        }

        // initialize the stack of tree nodes
        Deque<TreeNode> stack = new ArrayDeque<>();
        int value = preorder[0];
        TreeNode root = new TreeNode(value);
        stack.push(root);

        // for all remaining values
        for (int i = 1; i < preorder.length; i++) {
            // create a node
            value = preorder[i];
            TreeNode node = new TreeNode(value);

            if (map.get(value) < map.get(stack.peek().val)) {
                // the new node is on the left of the last node (inorder)
                // so it must be its left child
                stack.peek().left = node;
            } else {
                // the new node is on the right of the last node
                // so it must be the right child of either the last node
                // or one of the last node's ancestors.
                // pop the stack until we either run out of ancestors
                // or the node at the top of the stack is to the right of the new node
                TreeNode parent = null;
                while (!stack.isEmpty() && map.get(value) > map.get(stack.peek().val)) {
                    parent = stack.pop();
                }
                parent.right = node;
            }
            stack.push(node);
        }

        return root;
    }
}
