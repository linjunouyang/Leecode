package LinkedList;

class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }
}

/**
 *
 * Haven't checked discussion solutions
 */
public class _25ReverseNodesInKGroup {
    /**
     * 1. Nine chapter
     * <p>
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Nodes in k-Group.
     * Memory Usage: 38.4 MB, less than 24.14% of Java online submissions for Reverse Nodes in k-Group.
     * <p>
     * Similar Idea:
     * https://leetcode.com/problems/reverse-nodes-in-k-group/discuss/11440/Non-recursive-Java-solution-and-idea
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        dummy.next = head;

        ListNode curr = dummy;
        while (curr != null) {
            curr = reverseNextK(head, k);
        }

        return dummy.next;
    }

    // head -> n1 -> n2 ... nk -> nk+1
    // ->
    // head -> nk -> nk-1 .. n1 -> nk+1
    // return n1
    private ListNode reverseNextK(ListNode head, int k) {
        ListNode nk = head;
        for (int i = 0; i < k; i++) {
            nk = nk.next;
            if (nk == null) {
                return null;
            }
        }

        // reverse
        ListNode n1 = head.next;
        ListNode nkplus = nk.next;

        ListNode prev = null;
        ListNode curt = n1;
        while (curt != nkplus) {
            ListNode temp = curt.next;
            curt.next = prev;
            prev = curt;
            curt = temp;
        }

        // connect
        head.next = nk;
        n1.next = nkplus;
        return n1;
    }

    /**
     * 2. Indirect transfer
     *
     * list = 1 -> 2 -> 3 -> 4 -> 5, k = 3
     * 1. Use a dummy head to simplify operations.
     * Dummy -> 1 -> 2 -> 3 -> 4 -> 5
     * 2. Use three pointers. The operation is similar to Leetcode#92 Reverse Linked List II.
     * a. The pointer n will go k steps further.
     * (If there are no k nodes further, it means we don't have to reverse these k nodes. ==> Stop. )
     * b. The pointer p is always at the fixed position in this for-loop.
     * c. The pointer c = p.next, which means the current node we want to move.
     * d. The pointer start means the starting node for the next loop.
     *
     * Dummy -> 1 -> 2 -> 3 -> 4 -> 5
     *    p     c         n
     *          start
     *
     * Dummy -> 2 -> 3 -> 1 -> 4 -> 5
     *    p     c    n    start
     *
     * Dummy -> 3 -> 2 -> 1 -> 4 -> 5
     *    p     c         start
     *          n
     *
     * https://leetcode.com/problems/reverse-nodes-in-k-group/discuss/11413/Share-my-Java-Solution-with-comments-in-line
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup2(ListNode head, int k) {
        ListNode dummy = new ListNode(0);
        ListNode start = dummy; // the starting node for the next loop
        dummy.next = head;
        while(true) {
            ListNode p = start; // fixed position
            ListNode c = p; // current node we want to move
            ListNode n = p; // go k steps further
            start = p.next;
            for(int i = 0; i < k && n != null; i++) n = n.next;
            if(n == null) break;
            for(int i = 0; i < k-1; i++) {
                c = p.next;
                p.next = c.next;
                c.next = n.next;
                n.next = c;
            }
        }
        return dummy.next;
    }

    /**
     * 3. Recursion
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Reverse Nodes in k-Group.
     * Memory Usage: 38.7 MB, less than 24.14% of Java online submissions for Reverse Nodes in k-Group.
     *
     */
    public ListNode reverseKGroup3(ListNode head, int k) {
        // 1. test whether we have more than k nodes left
        ListNode node = head;
        int count = 0;
        while (count < k) {
            if (node == null) {
                return head;
            }
            node = node.next;
            count++;
        }

        // 2. reverse k node at current level

        // 1 -> 2 -> 3 -> 4 -> 5 -> 6
        // h         n

        // reverse(3 -> 4 -> 5 -> 6, 2);
        // 3 -> 4 -> 5 -> 6
        // h         n

        // reverse(5 -> 6, 2);
        // 5 -> 6
        // h       n

        // reverse(null, 2)
        // return null

        // reverse(5 -> 6, 2);
        // pre = null;
        // 5(h) -> 6(n)
        // 5(h) -> null
        // pre = 5, head = 6, count = 1
        // next = null
        // 6 -> 5
        // pre = 6, head = null, count = 0
        // 6 -> 5


        ListNode pre = reverseKGroup3(node, k);
        while (count > 0) {
            ListNode next = head.next;
            head.next = pre;
            pre = head;
            head = next;
            count = count - 1;
        }

        return pre;
    }

}
