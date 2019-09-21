package LinkedList;

import java.util.HashSet;
import java.util.Set;

public class _160IntersectionOfTwoLinkedLists {
    /**
     * 1. HashSet
     *
     * Time complexity: O(n1 + n2)
     * Space complexity: O(n1 + n2)
     *
     * Runtime: 7 ms, faster than 21.97% of Java online submissions for Intersection of Two Linked Lists.
     * Memory Usage: 41.2 MB, less than 5.71% of Java online submissions for Intersection of Two Linked Lists.
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        Set<ListNode> hash = new HashSet<>();

        ListNode curr = headA;

        while (curr != null) {
            // no cycle guaranteed
            hash.add(curr);
            curr = curr.next;
        }

        curr = headB;

        while (curr != null) {
            if (hash.contains(curr)) {
                return curr;
            }
            curr = curr.next;
        }

        return null;
    }

    /**
     * 2. Form a Cycle -> Slow and Fast pointer
     *
     * References # 142 Linked List Cycle II
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 1 ms, faster than 98.77% of Java online submissions for Intersection of Two Linked Lists.
     * Memory Usage: 37.7 MB, less than 52.86% of Java online submissions for Intersection of Two Linked Lists.
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode2(ListNode headA, ListNode headB) {
        if (headA == null || headB == null) {
            return null;
        }

        // 1. find the tail of list b, and link it with the head of list b
        ListNode curr = headB;

        while (curr.next != null) {
            curr = curr.next;
        }

        curr.next = headB;

        // 2. slow and fast start from headA, determine whether two lists are intersected
        ListNode slow = headA;
        ListNode fast = headA;
        boolean isIntersected = false;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;

            if (slow == fast) {
                isIntersected = true;
                break;
            }
        }

        if (!isIntersected) {
            // question's requirement: structure unchanged
            curr.next = null;
            return null;
        }

        // 3. find the intersection node
        ListNode slow2 = headA;

        while (slow != slow2) {
            slow = slow.next;
            slow2 = slow2.next;
        }

        // question's requirement: structure unchanged
        curr.next = null;
        return slow;
    }

    /**
     * 3. Synchronize two lists iteration by extending iteration to the other list
     *
     * https://leetcode.com/problems/intersection-of-two-linked-lists/discuss/49785/Java-solution-without-knowing-the-difference-in-len!
     *
     * You can prove that: say A length = a + c, B length = b + c, after switching pointer, pointer A will move another b + c steps, pointer B will move a + c more steps, since a + c + b + c = b + c + a + c, it does not matter what value c is. Pointer A and B must meet after a + c + b (b + c + a) steps. If c == 0, they meet at NULL.
     * Thanks, hpplayer. This solution is very smart.
     *
     * ----
     *
     * Visualization of this solution:
     * Case 1 (Have Intersection & Same Len):
     *
     *        a
     * A:     a1 → a2 → a3
     *                    ↘
     *                      c1 → c2 → c3 → null
     *                    ↗
     * B:     b1 → b2 → b3
     *        b
     *             a
     * A:     a1 → a2 → a3
     *                    ↘
     *                      c1 → c2 → c3 → null
     *                    ↗
     * B:     b1 → b2 → b3
     *             b
     *                  a
     * A:     a1 → a2 → a3
     *                    ↘
     *                      c1 → c2 → c3 → null
     *                    ↗
     * B:     b1 → b2 → b3
     *                  b
     * A:     a1 → a2 → a3
     *                    ↘ a
     *                      c1 → c2 → c3 → null
     *                    ↗ b
     * B:     b1 → b2 → b3
     * Since a == b is true, end loop while(a != b), return the intersection node a = c1.
     *
     * Case 2 (Have Intersection & Different Len):
     *
     *             a
     * A:          a1 → a2
     *                    ↘
     *                      c1 → c2 → c3 → null
     *                    ↗
     * B:     b1 → b2 → b3
     *        b
     *                  a
     * A:          a1 → a2
     *                    ↘
     *                      c1 → c2 → c3 → null
     *                    ↗
     * B:     b1 → b2 → b3
     *             b
     * A:          a1 → a2
     *                    ↘ a
     *                      c1 → c2 → c3 → null
     *                    ↗
     * B:     b1 → b2 → b3
     *                  b
     * A:          a1 → a2
     *                    ↘      a
     *                      c1 → c2 → c3 → null
     *                    ↗ b
     * B:     b1 → b2 → b3
     * A:          a1 → a2
     *                    ↘           a
     *                      c1 → c2 → c3 → null
     *                    ↗      b
     * B:     b1 → b2 → b3
     * A:          a1 → a2
     *                    ↘                a = null, then a = b1
     *                      c1 → c2 → c3 → null
     *                    ↗           b
     * B:     b1 → b2 → b3
     * A:          a1 → a2
     *                    ↘
     *                      c1 → c2 → c3 → null
     *                    ↗                b = null, then b = a1
     * B:     b1 → b2 → b3
     *        a
     *             b
     * A:          a1 → a2
     *                    ↘
     *                      c1 → c2 → c3 → null
     *                    ↗
     * B:     b1 → b2 → b3
     *             a
     *                  b
     * A:          a1 → a2
     *                    ↘
     *                      c1 → c2 → c3 → null
     *                    ↗
     * B:     b1 → b2 → b3
     *                  a
     * A:          a1 → a2
     *                    ↘ b
     *                      c1 → c2 → c3 → null
     *                    ↗ a
     * B:     b1 → b2 → b3
     * Since a == b is true, end loop while(a != b), return the intersection node a = c1.
     *
     * Case 3 (Have No Intersection & Same Len):
     *
     *        a
     * A:     a1 → a2 → a3 → null
     * B:     b1 → b2 → b3 → null
     *        b
     *             a
     * A:     a1 → a2 → a3 → null
     * B:     b1 → b2 → b3 → null
     *             b
     *                  a
     * A:     a1 → a2 → a3 → null
     * B:     b1 → b2 → b3 → null
     *                  b
     *                       a = null
     * A:     a1 → a2 → a3 → null
     * B:     b1 → b2 → b3 → null
     *                       b = null
     * Since a == b is true (both refer to null), end loop while(a != b), return a = null.
     *
     * Case 4 (Have No Intersection & Different Len):
     *
     *        a
     * A:     a1 → a2 → a3 → a4 → null
     * B:     b1 → b2 → b3 → null
     *        b
     *             a
     * A:     a1 → a2 → a3 → a4 → null
     * B:     b1 → b2 → b3 → null
     *             b
     *                  a
     * A:     a1 → a2 → a3 → a4 → null
     * B:     b1 → b2 → b3 → null
     *                  b
     *                       a
     * A:     a1 → a2 → a3 → a4 → null
     * B:     b1 → b2 → b3 → null
     *                       b = null, then b = a1
     *        b                   a = null, then a = b1
     * A:     a1 → a2 → a3 → a4 → null
     * B:     b1 → b2 → b3 → null
     *             b
     * A:     a1 → a2 → a3 → a4 → null
     * B:     b1 → b2 → b3 → null
     *        a
     *                  b
     * A:     a1 → a2 → a3 → a4 → null
     * B:     b1 → b2 → b3 → null
     *             a
     *                       b
     * A:     a1 → a2 → a3 → a4 → null
     * B:     b1 → b2 → b3 → null
     *                  a
     *                            b = null
     * A:     a1 → a2 → a3 → a4 → null
     * B:     b1 → b2 → b3 → null
     *                       a = null
     * Since a == b is true (both refer to null), end loop while(a != b), return a = null.
     *
     * Notice that if list A and list B have the same length, this solution will terminate in no more than 1 traversal; if both lists have different lengths, this solution will terminate in no more than 2 traversals -- in the second traversal, swapping a and b synchronizes a and b before the end of the second traversal. By synchronizing a and b I mean both have the same remaining steps in the second traversal so that it's guaranteed for them to reach the first intersection node, or reach null at the same time (technically speaking, in the same iteration) -- see Case 2 (Have Intersection & Different Len) and Case 4 (Have No Intersection & Different Len).
     *
     * PS: There are many great explanations of this solution for various cases, I believe to visualize it can resolve most of the doubts posted previously.
     *
     * Runtime: 1 ms, faster than 98.77% of Java online submissions for Intersection of Two Linked Lists.
     * Memory Usage: 39.9 MB, less than 10.71% of Java online submissions for Intersection of Two Linked Lists.
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode3(ListNode headA, ListNode headB) {
        ListNode a = headA;
        ListNode b = headB;

        //if a & b have different len, then we will stop the loop after second iteration
        while (a != b) {
            //if a & b have different len, then we will stop the loop after second iteration
            a = a == null ? headB : a.next;
            b = b == null ? headA : b.next;
        }

        return a;
    }

    /**
     * 4. Get length, start at *same* start point
     *
     *
     * 1, Get the length of the two lists.
     *
     * 2, Align them to the same start point.
     *
     * 3, Move them together until finding the intersection point, or the end null
     *
     * @param headA
     * @param headB
     * @return
     */
    public ListNode getIntersectionNode4(ListNode headA, ListNode headB) {
        int lenA = length(headA);
        int lenB = length(headB);

        // move headA and headB to the sam start point
        while (lenA > lenB) {
            headA = headA.next;
            lenA--;
        }

        while (lenA > lenB) {
            headA = headA.next;
            lenA--;
        }

        while (lenA < lenB) {
            headB = headB.next;
            lenB--;
        }

        // find the intersection until end
        while (headA != headB) {
            headA = headA.next;
            headB = headB.next;
        }

        return headA;
    }

    private int length(ListNode node) {
        int length = 0;
        while (node != null) {
            node = node.next;
            length++;
        }
        return length;
    }
}
