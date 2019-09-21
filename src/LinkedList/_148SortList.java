package LinkedList;

/**
 *
 *
 * ---
 * 9.6 Third pass. Can't believe I successfully wrote merge sort lol. congrats!
 *
 */
public class _148SortList {
    /**
     * 1. Merge Sort
     *
     * Use [slow, fast] two pointers to get the middle and end node (one less iteration)
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(lgn) for recursion call stacks
     *
     * @param head
     * @return
     */
    public ListNode sortList(ListNode head) {
        // base case: 1) fast != null && fast.next != null
        // 2) intuition
        if (head == null || head.next == null) {
            return head;
        }

        // step 1. cut the list to two halves
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        prev.next = null;

        // sort the first half
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);

        // link two parts
        return merge(l1, l2);
    }

    private ListNode merge(ListNode head1, ListNode head2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;

        ListNode curr1 = head1;
        ListNode curr2 = head2;

        while (curr1 != null && curr2 != null) {
            if (curr1.val <= curr2.val) {
                curr.next = curr1;
                curr1 = curr1.next;
            } else {
                curr.next = curr2;
                curr2 = curr2.next;
            }
            curr = curr.next;
        }

        if (curr1 != null) {
            curr.next = curr1;
        }

        if (curr2 != null) {
            curr.next = curr2;
        }

        return dummy.next;
    }

    /**
     * 2. Bottom-up merge sort
     *
     * Image illustration
     * https://algs4.cs.princeton.edu/22mergesort/
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(1)
     *
     * Runtime: 5 ms, faster than 35.38% of Java online submissions for Sort List.
     * Memory Usage: 40.8 MB, less than 68.42% of Java online submissions for Sort List.
     *
     *
     * @param head
     * @return
     */
    public ListNode sortList2(ListNode head) {
        // Why dummy? front node might change
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        // get the length
        int len = 0;
        while (head != null) {
            len++;
            head = head.next;
        }

        //  We start by doing a pass of 1-by-1 merges (considering individual items as subarrays of size 1),
        //  then a pass of 2-by-2 merges (merge subarrays of size 2 to make subarrays of size 4),
        //  then 4-by-4 merges, and so forth
        for (int i = 1; i < len; i *= 2) {
            ListNode prev = dummy;
            ListNode curr = dummy.next;

            // [head] -> ... -> [prev] [left] [right] [curr] -> ... ->
            // head->prev : sorted part
            // left, right: sublist to merge
            // curr: head of remaining list
            while (curr != null) {
                ListNode left = curr;
                ListNode right = split(left, i);
                curr = split(right, i);
                prev = merge(left, right, prev);
            }
        }

        return dummy.next;
    }

    // split list to [1, step] and [step + 1 ...]
    // return the head of second part
    private ListNode split(ListNode head, int step) {
        //  because of head.next in the for loop
        if (head == null) {
            return null;
        }

        // NOTICE: head.next != null
        for (int i = 1; head.next != null && i < step; i++) {
            head = head.next;
        }

        ListNode right = head.next;
        head.next = null;
        return right;
    }

    // merge left and right into one list and pin it after prev
    // return the end node of the merged list
    private ListNode merge(ListNode left, ListNode right, ListNode prev) {
        // here, we can guarantee that prev won't be null
        ListNode cur = prev;

        while (left != null & right != null) {
            if (left.val < right.val) {
                cur.next = left;
                left = left.next;
            } else {
                cur.next = right;
                right = right.next;
            }
            // don't forget to proceed cur
            cur = cur.next;
        }

        if (left != null) {
            cur.next = left;
        }

        if (right != null) {
            cur.next = right;
        }

        while (cur.next != null) {
            cur = cur.next;
        }

        return cur;
    }
}
