/**
 * [-129, -129]
 * We're not look at 129129 and 921921
 * instead, treat a node as a single unit
 * from left to right: -129 -129
 * from right to left: -129 -129
 *
 */
public class _0234PalindromeLinkedList {
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
     }

    /**
     * 1. Copy into ArrayList + Two Pointers
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */

    /**
     * 2. Reverse Second half in-place and restore it
     *
     * Downside:
     * In a concurrent environment (multiple threads and processes accessing the same data),
     * access to the Linked List by other threads or processes would have to be locked
     * while this function is running, because the Linked List is temporarily broken.
     * This is a limitation of many in-place algorithms.
     *
     * Time complexity: O(n)
     * Space complexity:
     */
    public boolean isPalindrome(ListNode head) {

        if (head == null) {
            return true;
        }

        // Find the end of first half and reverse second half.
        ListNode firstHalfEnd = endOfFirstHalf(head);
        ListNode secondHalfStart = reverseList(firstHalfEnd.next);

        // Check whether or not there is a palindrome.
        ListNode p1 = head;
        ListNode p2 = secondHalfStart;
        boolean isSame = true;
        while (isSame && p2 != null) {
            if (p1.val != p2.val) isSame = false;
            p1 = p1.next;
            p2 = p2.next;
        }

        // Restore the list and return the result.
        firstHalfEnd.next = reverseList(secondHalfStart);
        return isSame;
    }

    private ListNode endOfFirstHalf(ListNode head) {
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    // Taken from https://leetcode.com/problems/reverse-linked-list/solution/
    private ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode nextTemp = curr.next;
            curr.next = prev;
            prev = curr;
            curr = nextTemp;
        }
        return prev;
    }


}
