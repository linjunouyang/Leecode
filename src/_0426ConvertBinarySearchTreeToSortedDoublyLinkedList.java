import java.util.ArrayDeque;
import java.util.Deque;

/**
 *  in-place means mutating the input and not returning newly allocated data. That is you can use extra space but in the end the returned value should not be something newly created but instead all changes done in the given input only.
 */
public class _0426ConvertBinarySearchTreeToSortedDoublyLinkedList {
    class Node {
        public int val;
        public Node left;
        public Node right;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val,Node _left,Node _right) {
            val = _val;
            left = _left;
            right = _right;
        }
    }

    /**
     * 1. Recursive In-order traversal with dummy node
     *
     * Time complexity: O(n)
     * Space complexity: O(h)
     */
    public Node treeToDoublyList(Node root) {
        if (root == null) {
            return null;
        }
        Node dummy = new Node(0, null, null);

        Node last = inorder(root, dummy);

        last.right = dummy.right;
        dummy.right.left = last;

        return dummy.right;
    }

    private Node inorder(Node node, Node prev) {
        if (node == null) {
            return prev;
        }
        Node leftLast = inorder(node.left, prev);

        leftLast.right = node;
        node.left = leftLast;

        Node rightLast = inorder(node.right, node);

        return rightLast;
    }

    /**
     * 2. Iterative in-order traversal with dummy node
     *
     * Time complexity: O(n)
     * Space complexity: O(h)
     */
    public Node treeToDoublyList2(Node root) {
        if (root == null) {
            return null;
        }

        Deque<Node> stack = new ArrayDeque<>();
        Node curr = root;
        Node dummy = new Node(0, null, null);
        Node prev = dummy;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.pop();

            prev.right = curr;
            curr.left = prev;
            prev = curr;

            curr = curr.right;
        }

        dummy.right.left = prev;
        prev.right = dummy.right;

        return dummy.right;
    }

    /**
     * 2.1 in-order traversal without dummy node
     *
     * We need to maintain two references: first and last
     * https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/solution/
     */
    private Node treeToDoublyList21(Node root) {
        if (root == null) {
            return null;
        }

        Deque<Node> nodes = new ArrayDeque<>();
        Node curr = root;
        Node prev = null;
        Node first = null;

        while (!nodes.isEmpty() || curr != null) {
            while (curr != null) {
                nodes.push(curr);
                curr = curr.left;
            }

            curr = nodes.pop();
            if (prev != null) {
                prev.right = curr;
                curr.left = prev;
            } else {
                first = curr;
            }
            prev = curr;

            curr = curr.right;
        }

        first.left = prev;
        prev.right = first;
        return prev.right;
    }

    /**
     * 4. Divide and Conquer
     * https://leetcode.com/problems/convert-binary-search-tree-to-sorted-doubly-linked-list/discuss/154659/Divide-and-Conquer-without-Dummy-Node-Java-Solution
     */
    public Node treeToDoublyList3(Node root) {
        if (root == null) {
            return null;
        }

        Node leftHead = treeToDoublyList3(root.left);
        Node rightHead = treeToDoublyList3(root.right);
        root.left = root;
        root.right = root;
        return connect(connect(leftHead, root), rightHead);
    }

    // Used to connect two circular doubly linked lists. n1 is the head of circular DLL as well as n2.
    private Node connect(Node n1, Node n2) {
        if (n1 == null) {
            return n2;
        }
        if (n2 == null) {
            return n1;
        }

        Node tail1 = n1.left;
        Node tail2 = n2.left;

        tail1.right = n2;
        n2.left = tail1;
        tail2.right = n1;
        n1.left = tail2;

        return n1;
    }
}
