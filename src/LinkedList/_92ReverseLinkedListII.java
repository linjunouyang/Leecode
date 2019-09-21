package LinkedList;

public class _92ReverseLinkedListII {
    public ListNode reverseBetween(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode lastBefore = dummy;

        for (int i = 0; i < m - 1; i++) {
            lastBefore = lastBefore.next;
        }

        ListNode curr = lastBefore.next;
        ListNode prev = null;
        ListNode mNode = curr;

        for (int i = 0; i < n - m + 1; i++) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        lastBefore.next = prev;
        mNode.next = curr;

        return dummy.next;
    }

    /**
     * 2.
     *
     * inserting then between pre and pre.next.
     * keep moving forward by 1 until you reach the difference, m - n,
     * & you keep making start.next point to then.next
     * to ensure it's always pointing to the tail part of the list.
     * Pretty simple lol.
     *
     * @param head
     * @param m
     * @param n
     * @return
     */
    public ListNode reverseBetween2(ListNode head, int m, int n) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // the node before reversing
        ListNode pre = dummy;

        for (int i = 0; i < m - 1; i++) {
            pre = pre.next;
        }

        // the start of a list that will be reversed
        ListNode start = pre.next;
        // the node that will be reversed
        ListNode then = start.next;

        // dummy -> 1 (pre) -> 2 (start, m) -> 3(then) -> 4(n) -> 5


        // insert then between pre and pre.next
        // i = 0;
        // dummy -> 1 (pre) -> 2 (start, m) -> 3(then) -> 4(n) -> 5
        // dummy -> 1 (pre) -> 3 -> 2(start, m) -> 4(then, n) -> 5

        // i = 1;
        // dummy -> 1 -> 4 -> 3 -> 2 -> 5
        for (int i = 0; i < n - m; i++)  {
            start.next = then.next;
            then.next = pre.next;
            pre.next = then;
            then = start.next;
        }

        return dummy.next;
    }




}
