import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Cartesian Tree
 *
 * in-order: get the array back
 */
public class _0654MaximumBinaryTree {
    /**
     * 1. Recursion (Pre-order)
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     *
     * @param nums
     * @return
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        if (nums == null) {
            return null;
        }

        return helper(nums, 0, nums.length - 1);
    }

    private TreeNode helper(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        // Find the maximum
        int maxPos = start;
        for (int i = start; i <= end; i++) {
            if (nums[i] > nums[maxPos]) {
                maxPos = i;
            }
        }

        TreeNode root = new TreeNode(nums[maxPos]);

        // Left subtree
        root.left = helper(nums, start, maxPos - 1);

        // Right subtree
        root.right = helper(nums, maxPos + 1, end);

        return root;
    }

    /**
     * 2. Iteration (pre-order)
     *
     * How to convey more information for each iteration:
     * 1) same type info -> use an array
     * 2) different type info -> use another stack, parallel operation
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     *
     */
    public TreeNode constructMaximumBinaryTree2(int[] nums) {
        // base case
        if (nums == null) {
            return null;
        }

        ArrayDeque<int[]> stack = new ArrayDeque<>();
        ArrayDeque<TreeNode> parentStack = new ArrayDeque<>();

        TreeNode root = null;
        stack.push(new int[] {0, nums.length - 1, 0});
        parentStack.push(new TreeNode(0));

        while (!stack.isEmpty()) {
            int[] range = stack.pop();
            TreeNode parent = parentStack.pop();

            if (range[0] <= range[1]) {
                // Find the max
                int maxPos = range[0];
                for (int i = range[0]; i <= range[1]; i++) {
                    if (nums[i] > nums[maxPos]) {
                        maxPos = i;
                    }
                }

                TreeNode node = new TreeNode(nums[maxPos]);
                if (range[2] == -1) {
                    parent.right = node;
                } else if (range[2] == 1) {
                    parent.left = node;
                } else {
                    root = node;
                }

                // right subtree
                stack.push(new int[]{maxPos + 1, range[1], -1});
                parentStack.push(node);

                // left subtree
                stack.push(new int[]{range[0], maxPos - 1, 1});
                parentStack.push(node);
            }
        }

        return root;
    }

    /**
     * 3. Improved Iteration
     *
     * --------
     *
     * Intuition:
     * Basically, if we need O(n) solution, we can only have constant operation during the traverse of the list.
     * Therefore, we need something the keep track of the traversing memory.
     * We then brainstorm several data structures that can help us keep track of this kind of memory.
     * What memory do we need?
     * We need to remember the max node rather than trying to find them each time.
     * So we need something that has an order and hierarchy.
     * Stack comes in very naturally for this need given its characteristics. All we need to do is to force an order by ourselves.
     *
     * -------
     *
     * Let me try to explain the algorithm.
     * If we have built the max binary tree for nums[0] ~ nums[i - 1], how can we insert nums[i] to the binary tree?
     * Say the max binary tree for nums[0] ~ nums[i - 1] looks like:
     *
     *       A
     *      / \
     *   ...   B
     *        / \
     *     ...   C
     *          / \
     *       ...   ...
     * Say the node for nums[i] is D.
     * If D.val > A.val, then because A.val is at the left of D.val, we can just move the tree rooted at A to the left child of D.
     *
     *         D
     *        /
     *       A
     *      / \
     *   ...   B
     *        / \
     *     ...   C
     *          / \
     *       ...   ...
     * If D.val < A.val, then because D.val is at the right of A.val, D must be put into the right subtree of A.
     * Similarly, if D.val < B.val, then D must be put into the right subtree of B.
     * Say B.val > D.val > C.val, then D should be the right child of B. (because D.val is at the right of B.val, and D.val is the biggest among the numbers at the right of B.val.)
     * Because C.val < D.val, and C.val is at the left of D.val, C should become left child of D.
     *
     *       A
     *      / \
     *   ...   B
     *        / \
     *      ...  D
     *          /
     *         C
     *        / \
     *     ...   ...
     * So to update the max binary tree for nums[0] ~ nums[i - 1], we need to know the nodes on the right path of the tree. (A, B, C, ...)
     * How to maintain the path?
     * Let's look at the property of the nodes.
     * A is the biggest among nums[0] ~ nums[i - 1].
     * B is the biggest for the numbers between A and nums[i] (exclusive).
     * C is the biggest for the numbers between B and nums[i] (exclusive).
     * Let's use a stack, and assume that the content of the stack contains the "right path" of nodes before the node for the current number.
     * For the node of the new number, we should remove the nodes in the stack which are smaller than the current number.
     * So we pop the stack until the top element of the stack is greater than the current number.
     * Then, add the node for the current number to the stack.
     *
     * ------
     *
     * traverse the array once and create the node one by one. and use stack to store a decreasing sequence.
     * each step, we create a new curNode. compare to the peek of stack,
     * 2.a. keep popping the stack while (stack.peek().val < curNode.val), and set the last popping node to be curNode.left.
     * Because the last one fulfilling the criteria is the largest number among curNode's left children. => curNode.left = last pop node
     * 2.b. after popping up all nodes that fulfill (stack.peek().val < curNode.val),
     * thus (stack.peek().val > curNode.val), the stack.peek() is curNode's root => peek.right = curNode
     * return the bottom node of stack.
     *
     * ------
     * If stack is empty, we push the node into stack and continue
     * If new value is smaller than the node value on top of the stack, we append TreeNode as the right node of top of stack.
     * If new value is larger, we keep poping from the stack until the stack is empty OR top of stack node value is greater than the new value. During the pop, we keep track of the last node being poped.
     * After step 2, we either in the situation of 0, or 1, either way, we append last node as left node of the new node.
     * After traversing, the bottom of stack is the root node because the bottom is always the largest value we have seen so far (during the traversing of list).
     *
     * -----
     * We scan numbers from left to right, build the tree one node by one step;
     * We use a stack to keep some (not all) tree nodes and ensure a decreasing order;
     * For each number, we keep pop the stack until empty or a bigger number;
     * The bigger number (if exist, it will be still in stack) is current number's root, a
     * nd the last popped number (if exist) is current number's left child (temporarily, this relationship may change in the future);
     * Then we push current number into the stack.
     *
     * Time complexity: O(n)
     * Space complexity: o(n)
     *
     */
    public TreeNode constructMaximumBinaryTree3(int[] nums) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        for(int i = 0; i < nums.length; i++) {
            TreeNode curr = new TreeNode(nums[i]);
            while(!stack.isEmpty() && stack.peek().val < nums[i]) { // filter first node
                curr.left = stack.pop();
            }

            // empty or not

            if(!stack.isEmpty()) { // also filter first node
                stack.peek().right = curr;
            }
            stack.push(curr);
        }

        return stack.isEmpty() ? null : stack.removeLast();
    }

    /**
     * 4. nlogn method (TD)
     *
     * https://leetcode.com/problems/maximum-binary-tree/discuss/106147/C%2B%2B-8-lines-O(n-log-n)-map-plus-stack-with-binary-search
     *
     */

}
