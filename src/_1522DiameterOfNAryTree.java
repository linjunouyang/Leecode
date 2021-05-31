import java.util.ArrayList;
import java.util.List;

/**
 * Longest path can only happen:
 * 1. between two leaves nodes (might or might not through root node,
 * but the intersection of two paths is a root node of a subtree)
 * 2. between a leaf node and the root node
 *
 * Longest path bridged by a non-leaf node:
 * If we pick two longest sub-paths from a non-leaf node to its descendant leaves nodes, and combine them together.
 * Case 2 can be integrated into this as well (other subtree is empty)
 *
 * With the above insights, to find the diameter of the tree,
 * we enumerate all non-leaf nodes and select the top two longest sub-paths bridged by each non-leaf node.
 */
public class _1522DiameterOfNAryTree {
    class Node {
        public int val;
        public List<Node> children;

        public Node() {
            children = new ArrayList<Node>();
        }

        public Node(int _val) {
            val = _val;
            children = new ArrayList<Node>();
        }

        public Node(int _val, ArrayList<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    /**
     * 1. DFS + Tree Height
     *
     * Height: the [longest] length of path to a leaf node from that node.
     *
     * height(node)=max(height(child))+1, ∀child∈node.children
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int diameter(Node root) {
        int[] res = new int[]{0};
        height(root, res);
        return res[0];
    }

    protected int height(Node node, int[] res) {
        if (node.children.size() == 0) {
            return 0;
        }

        // select the top two largest heights
        int maxHeight1 = 0;
        int maxHeight2 = 0;
        for (Node child : node.children) {
            int parentHeight = height(child, res) + 1;
            if (parentHeight > maxHeight1) {
                maxHeight2 = maxHeight1;
                maxHeight1 = parentHeight;
            } else if (parentHeight > maxHeight2) {
                maxHeight2 = parentHeight;
            }
        }
        res[0] = Math.max(res[0], maxHeight1 + maxHeight2);

        return maxHeight1;
    }

    /**
     * 2. DFS + Depth
     *
     * depth: the path length to the root.
     *
     * longest path
     * max depth 1 + max depth 2 - 2 * cur node depth
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int diameter2(Node root) {
        int[] res = new int[]{0};
        maxDepth(root, 0, res);
        return res[0];
    }

    /**
     * return the maximum depth of leaves nodes descending from the given node
     */
    protected int maxDepth(Node node, int currDepth, int[] res) {
        if (node.children.size() == 0) {
            return currDepth;
        }

        // select the top two largest depths
        int maxDepth1 = 0;
        int maxDepth2 = 0;
        for (Node child : node.children) {
            int depth = maxDepth(child, currDepth + 1, res);
            if (depth > maxDepth1) {
                maxDepth2 = maxDepth1;
                maxDepth1 = depth;
            } else if (depth > maxDepth2) {
                maxDepth2 = depth;
            }
            // calculate the distance between the two farthest leaves nodes.
            int distance = maxDepth1 + maxDepth2 - 2 * currDepth;
            res[0] = Math.max(res[0], distance);
        }

        return maxDepth1;
    }
}
