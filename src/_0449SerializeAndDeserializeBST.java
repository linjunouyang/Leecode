import java.util.*;

/**
 * Similar:
 * 297 Serialize and Deserialize Binary Tree
 *
 * To construct a binary tree
 * pre-order / post-order / level order + in-order
 * (because pre-order and post-order only tells you root,
 * doesn't give you boundary between left and right)
 *
 * To construct a binary search tree
 * you only need one of pre-order / post-order / level-order
 * because in-order info is already contained in the string
 *
 * Further Improvement:
 * Make deserialize iterative
 * Make deserialize without stack
 * Using level order
 *
 */
public class _0449SerializeAndDeserializeBST {
    /**
     * 1. Pre-order Traversal
     *
     * , is delimiter between different node's value
     * (avoid treating (21,34) as 2 1 3 4)
     *
     * In #297, we use # to decide whether we should add null
     * here, we use lower and upper boundary to do this.
     *
     * There is no need to use "#" or "null" in BST which makes it more compact!
     * The reason is that we can reconstruct BST by only using preorder(/postorder/levelorder) traversal.
     * However, in the binary tree situation, we need to use preorder(/postorder/levelorder) + inorder to reconstruct the tree.
     * If we want to directly construct BT, we have to use "#" or "null".
     *
     * One of the ways a BST tree is different from a general binary tree is
     * its structure is wholly dependent on the order in which the values are inserted.
     * (not really, 213 can be 2->1->3 or 2->3->1)
     * A string created from a preorder traversal of a BST will tell you the order in which the values were inserted into the tree.
     * Since you just need the order the values were inserted,
     * you do not need to account for null nodes in the string with "#" or "null".
     * Hence, the final string contains only the values and separators, which makes it the most compact possible.
     *
     * Similar idea but different implementation:
     * https://leetcode.com/problems/serialize-and-deserialize-bst/discuss/93180/Using-lower-bound-and-upper-bound-to-deserialize-BST
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public String serialize(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Deque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            sb.append(node.val).append(',');
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }

        }
        return sb.toString();
    }


    public TreeNode deserialize(String data) {
        if (data.isEmpty()) {
            return null;
        }
        Queue<String> q = new LinkedList<>(Arrays.asList(data.split(",")));
        return deserialize(q, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public TreeNode deserialize(Queue<String> q, int lower, int upper) {
        if (q.isEmpty()) {
            return null;
        }
        String s = q.peek();
        int val = Integer.parseInt(s);
        if (val < lower || val > upper) {
            return null;
        }
        q.poll();
        TreeNode root = new TreeNode(val);
        root.left = deserialize(q, lower, val);
        root.right = deserialize(q, val, upper);
        return root;
    }

    /**
     * 2. Post order
     */
    public String serialize2(TreeNode root) {
        if (root == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;
        TreeNode prev = null;

        while(curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }

            curr = stack.peek();

            if (curr.right == null || prev == curr.right) {
                curr = stack.pop();
                sb.append(curr.val).append(",");
                prev = curr;
                curr = null;
            } else {
                curr = curr.right;
            }
        }
        return sb.toString();
    }


    public TreeNode deserialize2(String data) {
        if (data.isEmpty()) {
            return null;
        }
        String[] vals = data.split(",");
        Deque<String> stack = new ArrayDeque<>();
        for (String val : vals) {
            stack.push(val);
        }
        return deserialize(stack, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public TreeNode deserialize(Deque<String> q, int lower, int upper) {
        if (q.isEmpty()) {
            return null;
        }
        String s = q.peek();
        int val = Integer.parseInt(s);
        if (val < lower || val > upper) {
            return null;
        }
        q.pop();
        TreeNode root = new TreeNode(val);
        root.right = deserialize(q, val, upper);
        root.left = deserialize(q, lower, val);
        return root;
    }


}
