import java.util.*;



/*
1. 9.28 Figured out recursion but not iteration.

 */
public class _590NaryTreePostorderTraversal {
    class Node {
        public int val;
        public List<Node> children;

        public Node() {

        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    }
    /*
    1. Recursion

    Avoid global variable and repeatedly created local variables

    Time complexity: O(n) number of nodes
    Space complexity: O(h) height of the tree.
    For skewed trees, h = n;
     */
    public List<Integer> postorder(Node root) {
        List<Integer> list = new ArrayList<>();
        return order(root, list);
    }

    public List<Integer> order(Node root, List<Integer> list) {
        if (root == null) {
            return list;
        }

        for (Node child: root.children) {
            order(child, list);
        }

        list.add(root.val);

        return list;
    }

    /*
    2. Iteration

    Remember Collections.reverse method
     */

    public List<Integer> postorder2(Node root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) {
            return list;
        }

        Stack<Node> stack = new Stack<>();
        stack.add(root);

        while(!stack.isEmpty()) {
            root = stack.pop();
            list.add(root.val);
            for (Node node: root.children) {
                stack.add(node);
            }
        }
        Collections.reverse(list);
        return list;
    }

}
