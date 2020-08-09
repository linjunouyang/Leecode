/**
 * The problem states that given n will always be valid
 */
public class _0019RemoveNthNodeFromEndOfList {
    class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /**
     * 1. One Pointer + Dummy Node
     * <p>
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode curr = dummy;
        int len = 0;
        while (curr.next != null) {
            curr = curr.next;
            len++;
        }

        // len - n + 1
        curr = dummy;
        for (int i = 0; i < len - n; i++) {
            curr = curr.next;
        }
        curr.next = curr.next.next;

        return dummy.next;
    }

    /**
     * 2. Two Pointers + Dummy Node
     *
     * List: dummy -> 1
     * n: 2
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public ListNode removeNthFromEnd2(ListNode head, int n) {
        if (n <= 0) {
            // not tested by leetcode
            return head;
        }

        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode slow = dummy;
        ListNode fast = dummy;
        for (int i = 0; i < n && fast != null; i++) {
            fast = fast.next;
        }

        if (fast == null) {
            // fast is supposed to be at nth element. If fast == null, then n > length
            // not tested by leetcode
            return head;
        }

        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;

        return dummy.next;
    }


}
