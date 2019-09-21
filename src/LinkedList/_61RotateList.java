package LinkedList;

/**
 *
 * What's the point of dummy node?
 * --
 * 9.5 Multiple failure, finally passed
 *
 *
 */
public class _61RotateList {


    /**
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate List.
     * Memory Usage: 37.8 MB, less than 79.31% of Java online submissions for Rotate List.
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null) {
            // k % len, len can't be 0
            return head;
        }

        int len = 1;
        ListNode fast = head;
        ListNode slow = head;

        // Get the total length, move fast to the end
        while (fast.next != null) {
            fast = fast.next;
            len++;
        }

        // Get the last rotated node
        for (int j = 1; j < len - k % len; j++) {
            slow = slow.next;
        }

        fast.next = head;
        head = slow.next;
        slow.next = null;

        return head;
    }

    /**
     * 2.
     *
     * First Iteration: Count the length, find the tail, form a cycle
     * Second Iteration (to len - k % len): Set new head and new tail
     *
     * Time complexity: O(n)
     * Space complexity: O
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate List.
     * Memory Usage: 37 MB, less than 94.83% of Java online submissions for Rotate List.
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode rotateRight2(ListNode head, int k) {
        if (head == null) {
            return head;
        }

        ListNode p = head;
        int len = 1;

        // if there is just one element, and len's initial value is 0
        while (p.next != null) {
            p = p.next;
            len++;
        }

        p.next = head;

        for (int i = 0; i < len - k % len; i++) {
            p = p.next;
        }

        head = p.next;
        p.next = null;

        return head;
    }

}
