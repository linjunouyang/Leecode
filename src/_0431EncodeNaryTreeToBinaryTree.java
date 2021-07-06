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
        if (root == null)  {
            return null;
        }

        TreeNode bRoot = new TreeNode(root.val);

        Deque<TreeNode> bStack = new ArrayDeque<>();
        bStack.push(bRoot);
        Deque<Node> nStack = new ArrayDeque<>();
        nStack.push(root);

        while (!nStack.isEmpty()) {
            TreeNode bNode = bStack.pop();
            Node nNode = nStack.pop();

            if (nNode.children.size() > 0) {
                Node firstChild = nNode.children.get(0);
                TreeNode left = new TreeNode(firstChild.val);

                bNode.left = left;

                bStack.push(left);
                nStack.push(firstChild);
            }

            TreeNode curr = bNode.left;
            for (int i = 1; i < nNode.children.size(); i++) {
                Node nChild = nNode.children.get(i);
                TreeNode bChild = new TreeNode(nChild.val);

                curr.right = bChild;
                curr = curr.right; // don't forget

                bStack.push(bChild);
                nStack.push(nChild);
            }
        }

        return bRoot;
    }

    // Decodes your binary tree to an n-ary tree.
    public Node decode(TreeNode root) {
        if (root == null) {
            return null;
        }

        Node nRoot = new Node(root.val, new ArrayList<>());

        Deque<TreeNode> bStack = new ArrayDeque<>();
        bStack.push(root);
        Deque<Node> nStack = new ArrayDeque<>();
        nStack.push(nRoot);

        while (!bStack.isEmpty()) {
            TreeNode bNode = bStack.pop();
            Node nNode = nStack.pop();

            TreeNode bChild = bNode.left;
            while (bChild != null) {
                Node nChild = new Node(bChild.val, new ArrayList<>());
                nNode.children.add(nChild);

                bStack.push(bChild);
                nStack.push(nChild);

                bChild = bChild.right;
            }
        }

        return nRoot;
    }

    /**
     * 2. DFS (pre-order)
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

        Node nRoot = new Node(root.val, new ArrayList<Node>());

        // Decoding all the children nodes
        TreeNode bChild = root.left;
        while (bChild != null) {
            Node nChild = decode(bChild);
            nRoot.children.add(nChild);
            bChild = bChild.right;
        }

        return nRoot;
    }
}
