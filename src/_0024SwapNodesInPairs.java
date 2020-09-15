public class _0024SwapNodesInPairs {
    /**
     * 1. Iteration
     *
     * Time: O(n)
     * Space: O(1)
     */
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode before = dummy;
        ListNode first = head;

        while (first != null && first.next != null) {
            ListNode second = first.next;
            ListNode after = second.next;

            second.next = first;
            first.next = after;
            before.next = second;

            before = first;
            first = after;
        }

        return dummy.next;
    }

    /**
     * 2. Recursion
     *
     * Time: O(n)
     * Space: O(n)
     */
    public ListNode swapPairs2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode first = head;
        ListNode second = head.next;
        ListNode after = second.next;

        first.next = swapPairs(after);
        second.next = first;

        return second;
    }
}
