package LinkedList;

import java.util.HashSet;
import java.util.Set;

public class _142LinkedListCycleII {

    /**
     * 1. HashSet
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 6 ms, faster than 20.35% of Java online submissions for Linked List Cycle II.
     * Memory Usage: 35 MB, less than 46.32% of Java online submissions for Linked List Cycle II.
     *
     * @param head
     * @return
     */
    public ListNode detectCycle(ListNode head) {
        Set<ListNode> hash = new HashSet<>();

        ListNode curr = head;

        while (curr != null) {
            if (hash.contains(curr)) {
                return curr;
            } else {
                hash.add(curr);
                curr = curr.next;
            }
        }

        return null;
    }

    /**
     * 2. Slow and Fast
     *
     * L1: non-loop part lenth
     * L2: start of the loop -> meet point
     * c: length of the circle
     *
     * slow: L1 + L2 + n1c
     * fast: L1 + L2 + n2c
     *
     * slow * 2 = fast
     *
     * L1 = (n2 - 2*n1)C - L2
     * L1 = n3C - L2
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Linked List Cycle II.
     * Memory Usage: 35.9 MB, less than 6.32% of Java online submissions for Linked List Cycle II.
     *
     * @param head
     * @return
     */
    public ListNode detectCycle2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        boolean hasCycle = false;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                hasCycle = true;
                break;
            }
        }

        if (!hasCycle) {
            return null;
        }

        ListNode slow2 = head;
        while (slow != slow2) {
            slow = slow.next;
            slow2 = slow2.next;
        }

        return slow2;
    }


}
