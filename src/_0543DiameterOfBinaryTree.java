import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class _0543DiameterOfBinaryTree {
    /**
     * 1. Recursion (Post-order traversal)
     *
     * pre-order traversal:
     * in-order traversal: output sorted binary search tree result
     * post-order traversal is good for counting height,
     *
     * Although the longest path doesn't have to go through the root node,
     * it has to pass the root node of some subtree of the tree
     * (because it has to be from one leaf node to another leaf node, otherwise we can extend it for free).
     * 'The longest path that passes a given node as the ROOT node is T = left_height+right_height.
     * So you just calculate T for all nodes and output the max T.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public int DiameterOfBinaryTree(TreeNode root) {
        return DFS(root)[0];
    }

    // int[2] = [best, height + 1 (number of nodes from this node to deepest leaf) ]
    private int[] DFS(TreeNode node)
    {
        if (node == null) return new int[] { 0, 0 };
        int[] left = DFS(node.left);
        int[] right = DFS(node.right);

        int best = Math.max(left[1] + right[1], Math.max(left[0], right[0]));
        int numNodes = 1 + Math.max(left[1], right[1]);
        return new int[] { best, numNodes };
    }

    /**
     * 2. Iteration (Post-order)
     *
     * The *depth* of a *node* is the number of edges from the root to the the node
     * A root node will have a depth of 0.
     *
     * The *height* of a *node* is the number of edges from the node to the deepest leaf
     * A leaf node will have a height of 0.
     *
     * The *height* of a *tree* is the *height* of its root node,
     * The *depth* of a *tree* is depth of deepest node
     * The diameter of a binary tree is the length of the longest path between any two nodes in a tree. This path may or may not pass through the root.
     *
     * Generally we don't talk about the depth of a tree
     *
     * make sure the node is there till the left and right childs are processed (so we use peek instead of pop)
     *
     * ____
     * I feel like we can't use traditional iterative post-order (which only uses a stack)
     * because we don't have the info of left child and right child
     *
     * but this one could be another iterative post-order traversal (using hashmap to indicating visit status)
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public int DiameterOfBinaryTree2(TreeNode root) {
        if(root == null){
            return 0;
        }
        int maxLength = 0;

        ArrayDeque<TreeNode> nodeStack = new ArrayDeque<>();
        // node -> number of nodes from this node to the deepest leaf
        Map<TreeNode,Integer> nodePathCountMap = new HashMap<>();
        nodeStack.push(root);
        while(!nodeStack.isEmpty()){
            TreeNode node = nodeStack.peek();
            if(node.left != null && !nodePathCountMap.containsKey(node.left)){
                nodeStack.push(node.left);
            }else if(node.right!=null && !nodePathCountMap.containsKey(node.right)){
                nodeStack.push(node.right);
            }else {
                TreeNode rootNodeEndofPostOrder = nodeStack.pop();
                int leftMax = nodePathCountMap.getOrDefault(rootNodeEndofPostOrder.left,0);
                int rightMax = nodePathCountMap.getOrDefault(rootNodeEndofPostOrder.right,0);
                int nodeMax = 1 + Math.max(leftMax, rightMax);
                nodePathCountMap.put(rootNodeEndofPostOrder,nodeMax);
                maxLength = Math.max(maxLength, leftMax + rightMax );
            }

        }
        return maxLength;
    }

}
