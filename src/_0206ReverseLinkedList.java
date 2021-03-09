public class _0206ReverseLinkedList {
    class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) {
            this.val = val;
        }
        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
    /**
     * 1. Iteration
     *
     * We can't access prev, so need to store prev along the way
     *
     * When we set curr.next to prev, we lost the original next element.
     * so need to store
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null) {
            ListNode oldNext = curr.next;
            curr.next = prev;
            prev = curr;
            curr = oldNext;
        }
        return prev;
    }

    /**
     * 2. Recursion
     *
     * Why head.next = null
     *
     * 1 -> 2 -> 3
     * 1 -> 2 <-> 3
     * 1 <-> 2 <- 3
     *
     * a cycle:
     * 1 -> 2
     * 1 <-> 2
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public ListNode reverseList2(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode reversedHead = reverseList(head.next);
        // head.next actually becomes reversedTail
        ListNode reversedTail = head.next;
        // (in sub-problems, we didn't change the cur head's next pointer)
        // this will make 'head'  as the new reversedTail
        reversedTail.next = head;
        head.next = null;
        return reversedHead;
    }
}
