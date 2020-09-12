import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class _0979DistributeCoinsInBinaryTree {
    int moves = 0;

    /**
     * 1. DFS (post order)
     *
     * For each node, we use an int array to record the information
     * [# of nodes in the subtree (include itself), # of total coins in the subtree (include itself)].
     * Then we know that for a certain node,
     * 1) if there are more coins than nodes in the subtree, the node must "contribute" the redundant coins to its parent.
     * 2) Else, if there are more nodes than coins in the subtree, then the node must take some coins from the parent.
     *
     * Both of these two operations will create moves in the tree.
     * And we just need to add the absolute value of the (# nodes - # coins) to the final result
     * because the move can be "contribute" or "take".
     *
     * Time: O(n)
     * Space: O(n)
     *
     *
     */
    private int steps = 0;

    public int distributeCoins(TreeNode root) {
        postorder(root);
        return steps;
    }

    // return coins of this level
    private int postorder(TreeNode node) {
        if(node == null) {
            return 0;
        }

        // coins from children -- post-order traversal
        int coin = postorder(node.left) + postorder(node.right);

        // current node coin
        if(node.val == 0) {
            coin += -1; // current node need one coin
        } else {
            coin += node.val - 1; // keep one coin for current node
        }

        steps += Math.abs(coin); // each coin move (from node to node's parent) or the other way need 1 step
        return coin;
    }

    /**
     * 2. Post-order Iteration
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public int distributeCoins2(TreeNode root) {
        int moves = 0;

        Deque<TreeNode> stack = new ArrayDeque<>();
        Map<TreeNode, Integer> map = new HashMap<>();
        TreeNode prev = null;
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.peek();

            if (curr.right == null || prev == curr.right) {
                stack.pop();

                int coins = map.getOrDefault(curr.left, 0) + map.getOrDefault(curr.right, 0);
                coins = coins + curr.val - 1;
                moves += Math.abs(coins);
                map.put(curr, coins);

                prev = curr;
                curr = null;
            } else {
                curr = curr.right;
            }
        }

        return moves;
    }
}
