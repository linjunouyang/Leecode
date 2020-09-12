/**
 * Non-empty, non-negative l1 l2
 */
public class _0002AddTwoNumbers {
    class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 1. Dummy Node
     *
     * Special cases:
     * extra carry at the end
     * l1 = [9, 9] l2 = [1]
     *
     * Time complexity: O(max(m, n))
     * Space complexity: O(max(m, n) + 1)
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode c1 = l1;
        ListNode c2 = l2;
        ListNode sentinel = new ListNode(0);
        ListNode d = sentinel;
        int sum = 0;
        while (c1 != null || c2 != null) {
            sum /= 10;
            if (c1 != null) {
                sum += c1.val;
                c1 = c1.next;
            }
            if (c2 != null) {
                sum += c2.val;
                c2 = c2.next;
            }
            d.next = new ListNode(sum % 10);
            d = d.next;
        }
        if (sum / 10 == 1)
            d.next = new ListNode(1);
        return sentinel.next;
    }

    /**
     * Follow-up:
     * What if digits are stored in non-reversed order?
     * (3→4→2)+(4→6→5)=8→0→7
     */
}
