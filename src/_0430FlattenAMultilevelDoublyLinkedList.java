import java.util.ArrayDeque;
import java.util.Deque;

public class _0430FlattenAMultilevelDoublyLinkedList {
    class Node {
        public int val;
        public Node prev;
        public Node next;
        public Node child;
    }

    /**
     * 1. Recursion (bottom-up)
     *
     * DFS
     *
     * concatenate three list
     * [...., head] [head.child, childEnd] [nextSibling, ...]
     *
     * Time: O(n)
     * Space: O(layers)
     */
    public Node flatten(Node head) {
        // base case
        if (head == null) {
            return head;
        }

        Node nextSibling = flatten(head.next);
        flatten(head.child);

        if (head.child != null) {
            head.child.prev = head;
            head.next = head.child;

            Node childEnd = head.child;
            while (childEnd.next != null) {
                childEnd = childEnd.next;
            }

            head.child = null;

            childEnd.next = nextSibling;
            if (nextSibling != null) {
                nextSibling.prev = childEnd;
            }
        }

        return head;
    }

    /**
     * 2. Iteration / Post-order
     *
     * Time: O(n)
     * Space: O(layers)
     *
     */
    public Node flatten2(Node head) {
        if (head == null) {
            return null;
        }

        Deque<Node> stack = new ArrayDeque<>();
        Node curr = head;
        Node prev = null;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.child;
            }

            curr = stack.peek();

            if (curr.next == null || prev == curr.next) {
                curr = stack.pop();

                Node nextSibling = curr.next;
                if (curr.child != null) {
                    curr.child.prev = curr;
                    curr.next = curr.child;

                    Node childEnd = curr.child;
                    while (childEnd.next != null) {
                        childEnd = childEnd.next;
                    }

                    curr.child = null;

                    childEnd.next = nextSibling;
                    if (nextSibling != null) {
                        nextSibling.prev = childEnd;
                    }
                }

                prev = curr;
                curr = null;
            } else {
                curr = curr.next;
            }
        }

        return head;
    }

    /**
     * 3. Iteration (top-down)
     */
    public Node flatten3(Node head) {
        if( head == null) {
            return head;
        }

        Node p = head;

        while (p!= null) {
            /* CASE 1: if no child, proceed */
            if (p.child == null) {
                p = p.next;
                continue;
            }

            /* CASE 2: got child, find the tail of the child and link it to p.next */
            Node temp = p.child;
            // Find the tail of the child
            while (temp.next != null) {
                temp = temp.next;
            }

            // Connect tail with p.next, if it is not null
            temp.next = p.next;
            if( p.next != null )  {
                p.next.prev = temp;
            }

            // Connect p with p.child, and remove p.child
            p.next = p.child;
            p.child.prev = p;
            p.child = null;
        }
        return head;
    }
}
