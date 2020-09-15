public class _0203RemoveLinkedListElements {
    /**
     * Time: O(n)
     * Space: O(1)
     */
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode();
        dummy.next = head;

        ListNode prev = dummy;
        ListNode curr = dummy.next;

        while (curr != null) {
            if (curr.val == val) {
                prev.next = curr.next;
                // notice we don't update prev here
                // prev representing the last element in the new list
            } else {
                prev = curr;
            }

            curr = curr.next;
        }

        return dummy.next;
    }

    /**
     * 2. Recursion
     *
     * Time: O(n)
     * Space: O(n)
     */
    public ListNode removeElements2(ListNode head, int val) {
        if (head == null) {
            return head;
        }

        // can't return, because we didn't examine the part after current head
//        if (head.val == val) {
//            return head.next;
//        }

        head.next = removeElements(head.next, val);

        if (head.val == val) {
            return head.next;
        } else {
            return head;
        }
    }


}
