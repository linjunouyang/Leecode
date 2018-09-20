package Tree;

import java.util.*;

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}

/**
 * 9.20 First. Not Solved in 20 minutes.
 * Notice that the array size is [1, 10000] (no 0)
 */

public class _654MaximumBinaryTree {

    /*
    Solution 1: Recursive Solution

    Detailed Analysis: Solution https://leetcode.com/problems/maximum-binary-tree/solution/

    a Cartesian Tree: in-order traversal -> array

    Time complexity:
    Worst: O(n^2)
    Average: O(nlogn)  T(n) = T(m) + T(n-m) + O(n) (Best:T(n) = 2T(n/2) + O(n))
    Space complexity:
    Worst: O(n)
    Average: O(logn)
     */
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        // nums's size is at least 1. No need for boundary checking.
        return construct(nums, 0, nums.length);
    }

    public TreeNode construct(int[] nums, int l, int r) {
        // construct MBT from nums[l ... r-1] and return the root
        if (l == r) {
            return null;
        }
        int max_i = max(nums, l, r);
        TreeNode root = new TreeNode(nums[max_i);
        root.left = construct(nums, l, max_i);
        root.right = construct(nums, max_i + 1, r);
        return root;
    }

    public int max(int [] nums, int l, int r) {
        // find max index in nums[l ... r-1]
        int max_i = l;
        for (int i = l + 1; i < r; i++) {
            if (nums[i] > nums[max_i]) {
                max_i = i;
            }
        }
        return max_i;
    }

    /*
    Solution 2: Stack

    https://leetcode.com/problems/maximum-binary-tree/discuss/106156/Java-worst-case-O(N)-solution

    Let's use a stack, and assume that the content of the stack contains the "right path" of nodes before the node for the current number.
    For the node of the new number, we should remove the nodes in the stack which are smaller than the current number.
    So we pop the stack until the top element of the stack is greater than the current number.
    Then, add the node for the current number to the stack.

    1. traverse the array and create nodes one by one.
    Use stack to store "the right path"
    2. each step, we compare curNode to stack.peek(), and set the last popping node to be curNode.left
    a. keep popping the stack while curNode.val is bigger set curNode.left = stack.pop()
    b. stack.peek.right = curr.
    3. return the bottom node of the stack.

    Time complexity: O(n)
    Space complexity: O(n) (when input array is decreasing)
     */
    public TreeNode constructMaximumBinaryTree2(int[] nums) {
        // Deque is likely to be aster than stack when used as a stack
        // & faster than LinkedList when used as a queue.
        Deque<TreeNode> stack = new LinkedList<>();
        for (int i = 0; i < nums.length; i++) {
            TreeNode curr = new TreeNode(nums[i]);
            while (!stack.isEmpty() && nums[i] > stack.peek().val) {
                curr.left = stack.pop();
            }
            while (!stack.isEmpty()) {
                stack.peek().right = curr;
            }
            stack.push(curr);
        }
        return stack.peekLast();

    }
}
