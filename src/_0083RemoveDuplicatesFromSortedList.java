public class _0083RemoveDuplicatesFromSortedList {
    /**
     * 1. Iteration
     *
     * Time: O(n)
     * Space: O(1)
     */
    public ListNode deleteDuplicates(ListNode head) {
        if (head == null) {
            return head;
        }
        ListNode current = head;
        while (current.next != null) {
            if (current.next.val == current.val) {
                current.next = current.next.next;
            } else {
                current = current.next;
            }
        }
        return head;
    }

    /**
     * 2. Recursion
     *
     * Time: O(n)
     * Space: O(1)
     */
    public ListNode deleteDuplicates2(ListNode head) {
        if (head == null) {
            return null;
        }

        head.next = deleteDuplicates2(head.next);

        if (head.next != null && head.next.val == head.val) {
            return head.next;
        } else {
            return head;
        }
    }

}
