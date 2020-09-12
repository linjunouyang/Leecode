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
     * 1. Using previously established next pointer + dummy node
     *
     * Treat each level as linked list
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public Node connect(Node root) {
        if (root == null) {
            return null;
        }
        Node dummy = new Node(0); // dummy start for next level
        dummy.next = root;
        while (dummy.next != null) {
            Node curr = dummy.next; // curr node on curr level

            dummy.next = null; // initialize next level (empty)
            Node prev = dummy; // prev node on next level
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
