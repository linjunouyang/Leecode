import java.util.ArrayDeque;
import java.util.Deque;

public class _0117PopulatingNextRightPointersInEachNodeII {
    class Node {
        public int val;
        public Node left;
        public Node right;
        public Node next;

        public Node() {
        }

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, Node _left, Node _right, Node _next) {
            val = _val;
            left = _left;
            right = _right;
            next = _next;
        }
    }
    /**
     * 1. BFS
     *
     * Time: O(n)
     * Space: O(n)
     */
    public Node connect1(Node root) {
        if (root == null) {
            return null;
        }

        Deque<Node> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Node curr = queue.poll();
                if (i < size - 1) {
                    curr.next = queue.peek();
                }
                if (curr.left != null) {
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    queue.offer(curr.right);
                }
            }
        }

        return root;
    }

    /**
     * 2. Using previously established next pointer + dummy node
     *
     * Treat each level as linked list
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public Node connect2(Node root) {
        if (root == null) {
            return null;
        }
        Node dummy = new Node(0); // dummy start for next level
        dummy.next = root;
        while (dummy.next != null) { // while we still have a list
            Node curr = dummy.next; // curr node on curr level

            dummy.next = null; // detach dummy from current level
            Node prev = dummy; // prev node on next level, for linking nodes in the next level
            while (curr != null) {
                if (curr.left != null) {
                    prev.next = curr.left;
                    prev = prev.next;
                }
                if (curr.right != null) {
                    prev.next = curr.right;
                    prev = prev.next;
                }
                curr = curr.next;
            }
        }
        return root;
    }
}
