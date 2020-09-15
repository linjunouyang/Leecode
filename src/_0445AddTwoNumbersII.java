import java.util.ArrayDeque;
import java.util.Deque;

public class _0445AddTwoNumbersII {
    public class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    /**
     * 1. Stack
     *
     * Time: O(l1 + l2)
     * Space: O(l1 + l2)
     */
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // base cases
        Deque<Integer> stack1 = new ArrayDeque<>();
        ListNode curr1 = l1;
        while (curr1 != null) {
            stack1.push(curr1.val);
            curr1 = curr1.next;
        }

        Deque<Integer> stack2 = new ArrayDeque<>();
        ListNode curr2 = l2;
        while (curr2 != null) {
            stack2.push(curr2.val);
            curr2 = curr2.next;
        }

        ListNode dummy = new ListNode();
        int carry = 0;

        while (!stack1.isEmpty() || !stack2.isEmpty()) {
            int num1 = stack1.isEmpty() ? 0 : stack1.pop();
            int num2 = stack2.isEmpty() ? 0 : stack2.pop();
            int sum = num1 + num2 + carry;
            ListNode digit = new ListNode(sum % 10);
            carry = sum / 10;
            ListNode oldHead = dummy.next;
            dummy.next = digit;
            digit.next = oldHead;
        }

        if (carry == 1) {
            ListNode oldHead = dummy.next;
            dummy.next = new ListNode(1);
            dummy.next.next = oldHead;
        }

        return dummy.next;
    }

    /**
     * 2. Recursion
     * Time: O(l1 + l2)
     * Space: O(l1 + l2)
     */
    public ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        int size1 = getLength(l1);
        int size2 = getLength(l2);
        ListNode head = new ListNode(1);
        // Make sure l1.length >= l2.length
        head.next = size1 < size2 ? helper(l2, l1, size2 - size1) : helper(l1, l2, size1 - size2);
        // Handle the first digit
        if (head.next.val > 9) {
            head.next.val = head.next.val % 10;
            return head;
        }
        return head.next;
    }
    // get length of the list
    public int getLength(ListNode l) {
        int count = 0;
        while(l != null) {
            l = l.next;
            count++;
        }
        return count;
    }
    // offset is the difference (l1 - l2)
    public ListNode helper(ListNode l1, ListNode l2, int offset) {
        if (l1 == null) {
            return null;
        }
        // check whether l1 becomes the same length as l2
        ListNode result = offset == 0 ? new ListNode(l1.val + l2.val) : new ListNode(l1.val);
        ListNode post = offset == 0 ? helper(l1.next, l2.next, 0) : helper(l1.next, l2, offset - 1);
        // handle carry
        if (post != null && post.val > 9) {
            result.val += 1;
            post.val = post.val % 10;
        }
        // combine nodes
        result.next = post;
        return result;
    }

    /**
     * 3. No recursion, no stack
     *
     * https://leetcode.com/problems/add-two-numbers-ii/discuss/687339/Java-O(N)-solution-with-follow-up-question-no-recursion-no-stacks
     */
}
