package LinkedList;

/**
 * Dummy Node
 * ---
 * 9.3 Failed
 */
public class _86PartitionList {
    /**
     * 1. Dummy Node
     * separate the list into 2 parts and link them afterwords
     *
     * Time complexity: O(1)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Partition List.
     * Memory Usage: 35.8 MB, less than 100.00% of Java online submissions for Partition List.
     *
     * @param head
     * @param x
     * @return
     */
    public ListNode partition(ListNode head, int x) {
        ListNode smallerHead = new ListNode(0);
        ListNode biggerHead = new ListNode(0);
        ListNode smallerLast = smallerHead;
        ListNode biggerLast = biggerHead;

        while (head != null) {
            if (head.val < x) {
                smallerLast.next = head;
                smallerLast = smallerLast.next;
            } else {
                biggerLast.next = head;
                biggerLast = biggerLast.next;
            }
            // don't forget proceed
            head = head.next;
        }

        smallerLast.next = biggerHead.next;
        // avoid cycle
        biggerLast.next = null;
        return smallerHead.next;
    }

}
