package LinkedList;

public class _21MergeTwoSortedLists {
    /**
     * 1. Recursion
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Merge Two Sorted Lists.
     * Memory Usage: 39.9 MB, less than 15.83% of Java online submissions for Merge Two Sorted Lists.
     *
     * in real life, the length of a linked list could be much longer than we expected,
     * in which case the recursive approach is likely to introduce a stack overflow. (Imagine a file system)
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists(ListNode l1, ListNode l2){
        if(l1 == null) {
            return l2;
        }

        if(l2 == null) {
            return l1;
        }

        if(l1.val < l2.val){
            l1.next = mergeTwoLists(l1.next, l2);
            return l1;
        } else{
            l2.next = mergeTwoLists(l1, l2.next);
            return l2;
        }
    }

    /**
     * 2. Iteration
     *
     * One thing deserves discussion is whether we should create a new ListNode as a convenient way to hold the list.
     * Sometimes, in industrial projects, sometimes it's not trivial to create a ListNode
     * which might require many resource allocations or inaccessible dependencies (we need to mock them).
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Merge Two Sorted Lists.
     * Memory Usage: 35.8 MB, less than 100.00% of Java online submissions for Merge Two Sorted Lists.
     *
     * @param l1
     * @param l2
     * @return
     */
    public ListNode mergeTwoLists2(ListNode l1, ListNode l2){
        ListNode dummy = new ListNode(0);
        ListNode lastNode = dummy;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                lastNode.next = l1;
                l1 = l1.next;
            } else {
                lastNode.next = l2;
                l2 = l2.next;
            }
            lastNode = lastNode.next;
        }

        if (l1 != null) {
            lastNode.next = l1;
        } else {
            lastNode.next = l2;
        }

        return dummy.next;
    }
}
