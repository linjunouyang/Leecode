package LinkedList;

import java.util.HashMap;
import java.util.Map;

class Node {
    public int val;
    public Node next;
    public Node random;

    public Node() {}

    public Node(int _val,Node _next,Node _random) {
        val = _val;
        next = _next;
        random = _random;
    }
};

public class _138CopyListWithRandomPointer {

    /**
     * 1. Store mapping between new nodes and old news in original linked list
     *
     * An intuitive solution is to keep a hash table for each node in the list,
     * via which we just need to iterate the list in 2 rounds respectively
     * to create nodes and assign the values for their random pointers.
     * As a result, the space complexity of this solution is O(N), although with a linear time complexity.
     *
     * Note: if we do not consider the space reversed for the output,
     * then we could say that the algorithm does not consume any additional memory during the processing,
     * i.e. O(1) space complexity
     *
     * As an optimised solution, we could reduce the space complexity into constant.
     * The idea is to associate the original node with its copy node in a single linked list.
     * In this way, we don't need extra space to keep track of the new nodes.
     *
     * The algorithm is composed of the follow three steps which are also 3 iteration rounds.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Copy List with Random Pointer.
     * Memory Usage: 35.5 MB, less than 21.49% of Java online submissions for Copy List with Random Pointer.
     *
     * https://leetcode.com/problems/copy-list-with-random-pointer/discuss/43491/A-solution-with-constant-space-complexity-O(1)-and-linear-time-complexity-O(N)
     *
     * @param head
     * @return
     */

    public Node copyRandomList(Node head) {
        // Node copyHead = head.next;
        if (head == null) {
            return null;
        }


        // first iteration: copy each node, and link together in the original list
       Node cur = head;

       while (cur != null) {
           // cur != null, so no need to check cur.next
           Node next = cur.next;
           cur.next = new Node(cur.val, next, null);
           cur = next;
       }

       // second iteration: assign random pointers for the copy nodes

       cur = head;
       while (cur != null) {
           // since each node is copied, cur.next won't be null
           // but cur.random can be null
           if (cur.random != null) {
               cur.next.random = cur.random.next;
           }
           cur = cur.next.next;
       }

       // third iteration: restore the original list, and extract the copy list

       cur = head;
       Node copyHead = head.next;

       while (cur != null) {
           Node next = cur.next.next;
           Node copy = cur.next;

           // restore original list
           cur.next = next;

           // extract the copy list
           // cur is not null, so no need to check cur.next
           // but next can be null
           if (next != null) {
               copy.next = next.next;
           }

           cur = next;
       }

       return copyHead;
    }

    /**
     * 2. Hashmap
     *
     *
     * Typically, hashCode() just returns the object's address in memory if you don't override it"
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 1 ms, faster than 73.78% of Java online submissions for Copy List with Random Pointer.
     * Memory Usage: 35.2 MB, less than 38.32% of Java online submissions for Copy List with Random Pointer.
     *
     * @param head
     * @return
     */
    public Node copyRandomList2(Node head) {

        Map<Node, Node> map = new HashMap<>();

        Node curr = head;
        while (curr != null) {
            map.put(curr, new Node(curr.val, null, null));
            curr = curr.next;
        }

        curr = head;
        while (curr != null) {
            Node copy = map.get(curr);
            copy.next = map.get(curr.next);
            copy.random = map.get(curr.random);

            curr = curr.next;
        }

        return map.get(head);
    }
}
