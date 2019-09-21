package LinkedList;

public class _143ReorderList {
    /**
     * 1. Find middle & reverse second half & inter-wine
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Reorder List.
     * Memory Usage: 37.7 MB, less than 100.00% of Java online submissions for Reorder List.
     *
     *
     * @param head
     */
    public void reorderList(ListNode head) {
        if (head == null) {
            return;
        }

        // find the middle node
        // 1 2(s) 3 null(f) -> fast = null
        // 1 2(s) 3 4(f)  -> fast.next = null
        // 1 2 3(s) 4 5(f) -> fast.next = null
        ListNode slow = head;
        ListNode fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        // reverse the second half
        ListNode head2 = reverse(slow.next);
        // set first split part's last item -> next = null
        slow.next = null;

        // Interwine the two halves
        merge(head, head2);
        merge2(head, head2);
    }

    private ListNode reverse(ListNode n) {
        ListNode prev = null;
        ListNode cur = n;

        while (cur != null) {
            ListNode next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        return prev;
    }

    private void merge(ListNode head1, ListNode head2) {
        // 1) according to initial division, head2 might be null at start;
        // 2) head2 = next, which means head2 is more further away.
        // h1 can't be null if h2 is not null (because of head1 = head2)
        while (head2 != null) {
            // h1 -> next ... h2
            // h1 -> h2 -> next
            ListNode next = head1.next;
            head1.next = head2;
            head1 = head2;
            head2 = next;
        }
    }

    private void merge2(ListNode l1, ListNode l2) {
        // according to previous division
        // l1.length = l2.length or l1.length + 1 = l2.length

        while (l1 != null && l2 != null) {
            ListNode n1 = l1.next;
            ListNode n2 = l2.next;

            l1.next = l2;
            l2.next = n1;

            l1 = n1;
            l2 = n2;
        }
    }
}
