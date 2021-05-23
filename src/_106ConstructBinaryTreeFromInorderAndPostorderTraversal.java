import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class _106ConstructBinaryTreeFromInorderAndPostorderTraversal {
    /**
     * 1. Recursion, DFS (pre-order)
     *
     * Take the last element in postorder array as the root, find the position of the root in the inorder array;
     * then locate the range for left sub-tree and right sub-tree and do recursion.
     * Use a HashMap to record the index of root in the inorder array.
     *
     * Time complexity:
     * hashmap building: O(n)
     * visit each node once: O(n)
     *
     * Space complexity:
     * hashmap storage: O(n)
     * recursion call stack: O(n) for skewed trees, O(lgn) for balanced trees
     *
     * Runtime: 2 ms, faster than 92.14% of Java online submissions for Construct Binary Tree from Inorder and Postorder Traversal.
     * Memory Usage: 36.4 MB, less than 99.84% of Java online submissions for Construct Binary Tree from Inorder and Postorder Traversal.
     *
     * @param inorder
     * @param postorder
     * @return
     */
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        if (inorder == null || postorder == null || inorder.length != postorder.length
                || inorder.length == 0) {
            return null;
        }

        Map<Integer, Integer> inmap = new HashMap<>();
        for (int i = 0; i < inorder.length; i++) {
            inmap.put(inorder[i], i);
        }

        return buildHelper(inmap, 0, inorder.length - 1, postorder, postorder.length - 1);
    }


    private TreeNode buildHelper(Map<Integer, Integer> inmap, int inStart, int inEnd, int[] postOrder, int postEnd) {
        if (inStart > inEnd) {
            return null;
        }

        TreeNode root = new TreeNode(postOrder[postEnd]);

        int inRoot = inmap.get(root.val);
        int rightLen = inEnd - inRoot;

        root.left = buildHelper(inmap, inStart, inRoot - 1, postOrder,postEnd - rightLen - 1);
        root.right = buildHelper(inmap, inRoot + 1, inEnd, postOrder, postEnd - 1);
        return root;
    }

    /**
     * 2. Iteration
     *
     * stack: only nodes whose left side hasn't been handled will be pushed into stack
     *
     *
     *      3
     *       \
     *    9   20
     *         \
     *       15 7
     * inorder:  (inorder left) root (inorder right)
     * postorder: (postorder left) (postorder right) root
     *
     * ---
     * inorder =   9, 3, 15, 20, [7]
     * postorder = 9, [15], 7, 20,3
     *
     * prev =
     * root =
     * stack = 7 20 3
     *
     * stack.peek() = inorder[ip] (reaches the rightest)
     *
     * -----
     * prev = 7
     * inorder =   9, 3, 15, [20], 7
     * stack = 20 3
     *
     * stack.peek() = inorder[ip]
     * ---
     *
     * prev = 20
     * inorder = 9, 3, [15], 20, 7
     * stack = 3
     *
     * 1
     *  \
     *  2
     *   \
     *   3
     *    \
     *    4
     *    \
     *     5
     * inorder: 1 2 3 4 [5]
     * postorder: [5] 4 3 2 1
     *
     * stack: 1 2 3 4 5
     *
     *       1
     *      /
     *     2
     *    3
     *   4
     *  5
     * inorder: 5 4 [3] 2 1
     * postorder: 5 4 [3] 2 1
     *
     *
     *
     * Starting from the last element of the postorder and inorder array,
     * we put elements from postorder array to a stack and each one is the right child of the last one
     * until an element in postorder array is equal to the element on the inorder array.
     *
     * Then, we pop as many as elements we can from the stack and decrease the mark in inorder array
     * until the peek() element is not equal to the mark value or the stack is empty.
     *
     * Then, the new element that we are gonna scan from postorder array is the left child of the last element we have popped out from the stack.
     *
     * Time complexity: O(n)
     *
     * Space complexity:
     * O(n) for right skewed trees.
     * O(1) for left skewed trees
     * O(lgn) for balanced trees
     *
     *
     */

    public TreeNode buildTree2(int[] inorder, int[] postorder) {
        if (inorder.length == 0 || postorder.length == 0) {
            return null;
        }

        int ip = inorder.length - 1;
        int pp = postorder.length - 1;

        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode prev = null;
        TreeNode root = new TreeNode(postorder[pp]);
        stack.push(root);
        pp--;

        while (pp >= 0) {
            while (!stack.isEmpty() && stack.peek().val == inorder[ip]) {
                // stack.peek().val == inorder[ip] reach to the most right
                prev = stack.pop();
                ip--;
            }

            TreeNode newNode = new TreeNode(postorder[pp]);

            if (prev != null) {
                prev.left = newNode;
            } else if (!stack.isEmpty()) {
                TreeNode currTop = stack.peek();
                currTop.right = newNode;
            }

            stack.push(newNode);
            prev = null;
            pp--;
        }

        return root;
    }
}
