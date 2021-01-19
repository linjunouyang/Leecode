import java.util.HashMap;
import java.util.Map;


public class _0138CopyListWithRandomPointer {
    class Node {
        int val;
        Node next;
        Node random;

        public Node(int val) {
            this.val = val;
            this.next = null;
            this.random = null;
        }
    }

    /**
     * 1. HashMap
     *
     * Time: O(n)
     * Space: O(n)
     */
    public Node copyRandomList(Node head) {
        HashMap<Node, Node> originToCopy = new HashMap<>();
        Node originCurr = head;
        while (originCurr != null) {
            originToCopy.put(originCurr, new Node(originCurr.val));
            originCurr = originCurr.next;
        }

        originCurr = head;
        while (originCurr != null) {
            Node copyCurr = originToCopy.get(originCurr);
            copyCurr.next = originToCopy.get(originCurr.next);
            copyCurr.random = originToCopy.get(originCurr.random);
            originCurr = originCurr.next;
        }
        return originToCopy.get(head);
    }

    /**
     * 2. Shadow copy
     *
     * Original Node 1 (random1) - Original Node 2 (random2)
     * ->
     * Original Node 1 (random1) - Copy Node 1 (null) - Original Node 2 (random 2) - Copy Node 2 (null)
     *
     * cur->next->random = cur->random->next
     * cur: original node
     * cur->next: copy node, cur->next->random: copy nodes' random pointer
     * cur->random: original node's random node
     * cur->random->next: original node's random node in the copy list
     *
     *
     * Time: O(n)
     * Space: O(1)
     */
    public Node copyRandomList2(Node head) {
        if (head == null) {
            return null;
        }

        Node originCurr = head;
        while (originCurr != null) {
            Node copyCurr = new Node(originCurr.val);
            copyCurr.next = originCurr.next;
            originCurr.next = copyCurr;
            originCurr = copyCurr.next;
        }

        originCurr = head;
        while (originCurr != null) {
            if (originCurr.random != null) {
                originCurr.next.random = originCurr.random.next;
            }
            originCurr = originCurr.next.next;
        }

        originCurr = head;
        Node copyHead = head.next;
        Node copyCurr = head.next;
        while (originCurr != null) {
            originCurr.next = copyCurr.next;
            originCurr = originCurr.next;
            if (copyCurr.next != null) {
                copyCurr.next = copyCurr.next.next;

                copyCurr = copyCurr.next;
            }

        }

        return copyHead;
    }

    /**
     * 3. Recursion
     */
    public Node copyRandomList3(Node head) {
        Map<Node, Node> map = new HashMap<>();
        return helper(head, map);
    }

    private Node helper(Node node, Map<Node, Node> map) {
        if (node == null){
            return null;
        }

        if (map.containsKey(node)) {
            return map.get(node);
        }

        Node newNode = new Node(node.val);
        map.put(node, newNode);
        newNode.next = helper(node.next, map);
        newNode.random = helper(node.random, map);
        return newNode;
    }
}
