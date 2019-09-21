package LinkedList;

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

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    TreeNode(int x) {
        val = x;
    }
}

public class _109ConvertSortedListToBinarySearchTree {

    /**
     * 1. Recursion, helper function that takes range parameters [head, tail)
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
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Convert Sorted List to Binary Search Tree.
     * Memory Usage: 37.3 MB, less than 100.00% of Java online submissions for Convert Sorted List to Binary Search Tree.
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST(ListNode head) {
        return toBST(head, null);
    }

    // convert singly linked list [head -> ... -> tail)
    // (head inclusive, tail exclusive)
    // to a binary search tree
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
     * 2. Recursion, three pointers: prev, slow, fast
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Convert Sorted List to Binary Search Tree.
     * Memory Usage: 37.3 MB, less than 100.00% of Java online submissions for Convert Sorted List to Binary Search Tree.
     *
     * @param head
     * @return
     */
    public TreeNode sortedListToBST2(ListNode head) {
        // if fast = head = null, loop never executes
        // prev is null, prev.next will be illegal
        if (head == null) {
            return null;
        }

        // if fast = head, but head.next = null, loop never executes
        // prev is null, prev.next will be illegal
        if (head.next == null) {
            return new TreeNode(head.val);
        }

        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = null;

        TreeNode root = new TreeNode(slow.val);
        root.left = sortedListToBST2(head);
        root.right = sortedListToBST2(slow.next);

        return root;
    }
}
