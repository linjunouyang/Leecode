import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * --
 * 9.12 Failed. Came up with rough idea (recursion, slow, fast)
 * <p>
 * If we don't use prev
 * head -> ... -> slow -> slow.next -> ... -> fast
 * head -> ... -> slow    slow.next -> ... -> fast
 * <p>
 * slow.left = sortedListToBST(head);
 * slow.right = sortedListToBST(slow.next);
 * <p>
 * The result will contain duplicate elements because we forgot to separate slow from left
 * <p>
 * So, We must use three pointers prev, slow, fast.
 * head -> ... -> prev -> slow -> slow.next -> ... -> fast
 * head -> ... -> prev    slow -> slow.next -> ... -> fast
 * <p>
 * If we forgot to set prev.next = null, stackoverflow
 * we don't have to set slow.next = null, because in singly linked list, we can't go back;
 */



public class _0109ConvertSortedListToBinarySearchTree {
    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 1. Recursive pre-order, D&C, slow & fast pointers
     * helper function that takes range parameters [head, tail)
     *
     * Such helper function design is very common:
     * Arrays.binarySearch
     * String.subString
     *
     * Time complexity:
     * T(n) = 2T(n/2) + T(1)
     * O(nlgn)
     *
     * Space complexity:
     * O(lgn)
     * because we always divide in the middle
     * but if there is only two elements: 1->2
     * toBST(1, null)
     * toBST(1, 2)
     * toBST(1, 1)
     */
    public TreeNode sortedListToBST(ListNode head) {
        return toBST(head, null);
    }

    // convert the linked list [head -> ... -> tail) to a binary search tree
    private TreeNode toBST(ListNode head, ListNode tail) {
        // no need to check head == null, because tail is behind head
        // if head = null, then tail = null, so head = tail
        if (head == tail) {
            return null;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != tail && fast.next != tail) {
            slow = slow.next;
            fast = fast.next.next;
        }

        TreeNode root = new TreeNode(slow.val);
        root.left = toBST(head, slow);
        root.right = toBST(slow.next, tail);
        return root;
    }

    /**
     * 2. Mid point as root, Pre-order traversal
     *
     * Time: O(n)
     * Space: O(n)
     */
    public TreeNode sortedListToBST3(ListNode head) {
        List<Integer> list = new ArrayList<>();
        ListNode curr = head;
        while (curr != null){
            list.add(curr.val);
            curr = curr.next;
        }
        return buildTree(0, list.size() - 1, list);
    }

    private TreeNode buildTree(int left, int right, List<Integer> list){
        if (left > right) {
            return null;
        }
        int mid = left + (right - left + 1) / 2;
        TreeNode root = new TreeNode(list.get(mid));
        root.left = buildTree(left, mid - 1, list);
        root.right = buildTree(mid + 1, right, list);
        return root;
    }

    private TreeNode buildTree2(List<Integer> list){
        if (list.size() == 0) {
            // consistent with operations before pushing left and right
            return null;
        }

        TreeNode root = null;

        Deque<int[]> rangeStack = new ArrayDeque<>();
        rangeStack.push(new int[]{0, list.size() - 1});

        Deque<TreeNode> parentStack = new ArrayDeque<>();

        while (!rangeStack.isEmpty()) {
            int[] range = rangeStack.pop();
            int mid = range[0] + (range[1] - range[0]) / 2;
            TreeNode parent = parentStack.isEmpty() ? null : parentStack.pop();
            TreeNode node = new TreeNode(list.get(mid));
            if (root == null) {
                root = node;
            }
            if (parent != null) {
                if (parent.val > node.val) {
                    parent.left = node;
                } else {
                    parent.right = node;
                }
            }

            if (mid + 1 <= range[1]) {
                rangeStack.push(new int[]{mid + 1, range[1]});
                parentStack.push(node);
            }

            if (range[0] <= mid - 1) {
                rangeStack.push(new int[]{range[0], mid - 1});
                parentStack.push(node);
            }
        }

        return root;
    }

    /**
     * 3. In-order
     *
     * Time: O(n)
     * Space: O(logn)
     */
    public TreeNode sortedListToBST4(ListNode head) {
        int length = getLength(head);
        return buildTree(0, length - 1, new ListNode[]{head});
    }

    public int getLength(ListNode head) {
        int ret = 0;
        while (head != null) {
            ++ret;
            head = head.next;
        }
        return ret;
    }

    public TreeNode buildTree(int left, int right, ListNode[] head) {
        if (left > right) {
            return null;
        }
        int mid = left + (right - left) / 2;
        TreeNode root = new TreeNode(0);

        root.left = buildTree(left, mid - 1, head);

        root.val = head[0].val;
        head[0] = head[0].next;

        root.right = buildTree(mid + 1, right, head);
        return root;
    }
}
