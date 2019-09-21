package LinkedList;

import java.util.HashSet;
import java.util.Set;

public class _141LinkedListCycle {
    /**
     * 1. HashSet
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 4 ms, faster than 20.25% of Java online submissions for Linked List Cycle.
     * Memory Usage: 37.2 MB, less than 100.00% of Java online submissions for Linked List Cycle.
     *
     * @param head
     * @return
     */
    public boolean hasCycle(ListNode head) {
        Set<ListNode> hash = new HashSet<>();

        ListNode curr = head;

        while (curr != null) {
            if (hash.contains(curr)) {
                return true;
            } else {
                hash.add(curr);
                curr = curr.next;
            }
        }

        return false;
    }

    /**
     * 2. Slow and Fast pointer
     *
     * ----
     * Intuition
     *
     * If we think slow is still, then fast goes 1 step at a time.
     * So,the problem is just like a Chasing problem.
     * There is a time when fast catches slow
     * ----
     *
     * Explanation 1:
     *
     * General definitions:
     * (1) divide the list into two parts: a line segment and a cycle.
     * (2) it takes m step(s) to walk from one end to the other in the line segment
     * (3) it takes n step(s) to walk back to the starting point in the cycle.
     *
     * Let m = 0 if the cycled list contains no line segment; n = 0 if the list has no cycle;m = 0, n = 0 for an empty list.
     *
     * Since runner 's speed doubles that of walker, when walker reaches the intersection of line segment and the cycle,
     * walker moves m step(s) and runner moves 2*m steps.
     * Then, runner needs to move n - m % n step(s) more to catch up with walker in the cycle.
     * It takes another (n - m % n) / 1, considering the difference of velocity between walker and runner is 1.
     * So, it takes a total of m + (n - m % n) step(s) before walker and runner meet.
     *
     * ----
     * Explanation 2:
     *
     * Since the runner's speed is faster than walker, then it is guaranteed that runner will pass walker in some time.
     * The only thing we need to prove is that the runner never jumps over walker so never meet.
     * Suppose the runner jumps over walker in one unit of time, then we need to guarantee that 2 > distance + 1.
     * So distance < 1 so distance = 0. This implies that runner already meets walker, contradiction.
     * So runner will never jumps over walker.
     * Runner will pass walker + runner never jumps over = runner will meet walker.
     *
     * ----
     * Explanation 3:
     * https://codingfreak.blogspot.com/2012/09/detecting-loop-in-singly-linked-list_22.html
     * last three paragraphs
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Linked List Cycle.
     * Memory Usage: 38 MB, less than 84.29% of Java online submissions for Linked List Cycle.
     *
     * @param head
     * @return
     */
    public boolean hasCycle2(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}
