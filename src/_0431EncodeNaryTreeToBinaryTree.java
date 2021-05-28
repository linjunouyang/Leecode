import java.util.*;

public class _0431EncodeNaryTreeToBinaryTree {
    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };


    /**
     * 1. BFS
     *
     * BFS from nTree's perspective
     *
     * https://leetcode.com/problems/encode-n-ary-tree-to-binary-tree/
     *
     * Sibling - right pointer
     * Child - left pointer
     *
     * Time: O(n)
     * Space: O(n)
     * At any given moment, the queue contains nodes that are at most spread into two levels.
     */
    class NodePair {
        TreeNode bNode;
        Node nNode;

        public NodePair(TreeNode bNode, Node nNode) {
            this.bNode = bNode;
            this.nNode = nNode;
        }
    }


    // Encodes an n-ary tree to a binary tree.
    public TreeNode encode(Node root) {
        if (root == null) {
            return null;
        }

        TreeNode bRoot = new TreeNode(root.val);
        Deque<NodePair> queue = new ArrayDeque<>();
        queue.offer(new NodePair(bRoot, root));

        while (!queue.isEmpty()) {
            NodePair pair = queue.poll();
            TreeNode bNode = pair.bNode;
            Node nNode = pair.nNode;

            // The pointer is from parent to child,
            // so we construct the relationship before offer nodes into queue
            // (otherwise we need to know the parent each node's associated to)
            TreeNode firstChild = null;
            TreeNode prev = null;
            for (Node nChild : nNode.children) {
                TreeNode bChild = new TreeNode(nChild.val);
                if (firstChild == null) {
                    firstChild = bChild;
                } else {
                    prev.right = bChild;
                }
                prev = bChild;
                queue.offer(new NodePair(bChild, nChild));
            }

            bNode.left = firstChild;
        }

        return bRoot;
    }

    // Decodes your binary tree to an n-ary tree.
    public Node decode(TreeNode root) {
        if (root == null) {
            return null;
        }
        Node nRoot = new Node(root.val, new ArrayList<>());
        Deque<NodePair> queue = new ArrayDeque<>();
        queue.offer(new NodePair(root, nRoot));

        while (!queue.isEmpty()) {
            NodePair pair = queue.poll();
            TreeNode bNode = pair.bNode;
            Node nNode = pair.nNode;

            TreeNode curr = bNode.left;
            while (curr != null) {
                Node nChild = new Node(curr.val, new ArrayList<>());
                nNode.children.add(nChild);
                queue.offer(new NodePair(curr, nChild));
                curr = curr.right;
            }
        }

        return nRoot;
    }

    /**
     * 2. DFS
     *
     * Time: O(n)
     * Space: O(n)
     */
    // Encodes an n-ary tree to a binary tree.
    public TreeNode encode2(Node root) {
        if (root == null) {
            return null;
        }

        TreeNode newRoot = new TreeNode(root.val);

        // Encode the first child of n-ary node to the left node of binary tree.
        if (root.children.size() > 0) {
            Node firstChild = root.children.get(0);
            newRoot.left = this.encode(firstChild);
        }

        // Encoding the rest of the sibling nodes.
        TreeNode sibling = newRoot.left;
        for (int i = 1; i < root.children.size(); ++i) {
            sibling.right = this.encode(root.children.get(i));
            sibling = sibling.right;
        }

        return newRoot;
    }

    // Decodes your binary tree to an n-ary tree.
    public Node decode2(TreeNode root) {
        if (root == null) {
            return null;
        }

        Node newRoot = new Node(root.val, new ArrayList<Node>());

        // Decoding all the children nodes
        TreeNode sibling = root.left;
        while (sibling != null) {
            newRoot.children.add(decode(sibling));
            sibling = sibling.right;
        }

        return newRoot;
    }
}
