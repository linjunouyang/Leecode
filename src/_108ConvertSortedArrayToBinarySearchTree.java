import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class _108ConvertSortedArrayToBinarySearchTree {
    /**
     * 1. Recursion, Binary Search
     *
     * Time complexity: O(n)
     * T(n) = 2T(n/2) + O(1)
     *
     * Space complexity: O(lgn)
     *
     */
    public TreeNode sortedArrayToBST1(int[] nums) {
        return sortedArrayToBSTHelper(nums, 0, nums.length - 1);
    }

    private TreeNode sortedArrayToBSTHelper(int[] nums, int start, int end) {
        if (start > end) {
            return null;
        }

        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = sortedArrayToBSTHelper(nums, start, mid - 1);
        root.right = sortedArrayToBSTHelper(nums, mid + 1, end);

        return root;
    }

    /**
     * 2 Iteration, pre-order iteration (in order????)
     *
     * nodeStack : nodes that will be processed next
     * leftIndexStack, rightIndexStack: the range
     *
     */
    public TreeNode sortedArrayToBST2(int [] nums) {
        int len = nums.length;

        if (len == 0) {
            return null;
        }

        // 0 as a placeholder
        TreeNode head = new TreeNode(0);

        Deque<TreeNode> nodeStack = new LinkedList<>();
        Deque<Integer> leftIndexStack = new LinkedList<>();
        Deque<Integer> rightIndexStack = new LinkedList<>();

        nodeStack.push(head);
        leftIndexStack.push(0);
        rightIndexStack.push(len - 1);

        while (!nodeStack.isEmpty()) {
            TreeNode currNode = nodeStack.pop();
            int left = leftIndexStack.pop();
            int right = rightIndexStack.pop();
            int mid = left + (right - left) / 2;
            currNode.val = nums[mid];
            if (left <= mid-1 ) {
                currNode.left = new TreeNode(0);
                nodeStack.push(currNode.left);
                leftIndexStack.push(left);
                rightIndexStack.push(mid - 1);
            }
            if (mid + 1 <= right) {
                currNode.right = new TreeNode(0);
                nodeStack.push(currNode.right);
                leftIndexStack.push(mid + 1);
                rightIndexStack.push(right);
            }
        }

        return head;
    }

    /**
     * 3 Iteration, BFS
     *
     * Time complexity: O(n)
     *
     * Space complexity: O(n)
     *
     * Side note:
     * DFS: Pre-order, In-order, Out-order, Post-order
     * BFS: level-order
     *
     * about nested class:
     * https://www.geeksforgeeks.org/nested-classes-java/
     *
     *
     */

    private static class MyNode {
        TreeNode node;
        int lb;
        int rb;

        public MyNode(TreeNode n, int theLeft, int theRight) {
            this.node = n;
            this.lb = theLeft;
            this.rb = theRight;
        }
    }

    public TreeNode sortedArrayToBST3(int [] nums) {
        if (nums == null || nums.length == 0) {
            return null;
        }

        Queue<MyNode> queue = new LinkedList<>();
        int left = 0;
        int right = nums.length - 1;
        int val = nums[left + (right - left) / 2];
        TreeNode root = new TreeNode(val);
        queue.offer(new MyNode(root, left, right));

        while (!queue.isEmpty()) {
            MyNode cur = queue.poll();

            int mid = cur.lb + (cur.rb - cur.lb) / 2;

            if (mid != cur.lb) {
                TreeNode leftChild = new TreeNode(nums[cur.lb + (mid - 1 - cur.lb) / 2]);
                cur.node.left = leftChild;
                queue.offer(new MyNode(leftChild, cur.lb, mid - 1 ));
            }

            if (mid != cur.rb) {
                TreeNode rightChild = new TreeNode(nums[mid + 1 + (cur.rb - mid - 1) / 2]);
                cur.node.right = rightChild;
                queue.offer(new MyNode(rightChild, mid + 1, cur.rb));
            }
        }

        return root;
    }
}
