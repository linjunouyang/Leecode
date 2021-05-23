import java.util.*;

public class _617MergeTwoBinaryTrees {
    /*
    Recursive Method:
    https://leetcode.com/problems/merge-two-binary-trees/solution/

    Traverse two trees in a preorder fashion.
    At every step, check if the current nodes exist. If so, add the values and update.
    Also, mergetTrees() with the let and the right children.
    If any of these children is nul, return the child of the other tree to be added as a child subtree to the calling parent node in the first tree.
    At the end, the first tree will be the required tree.

    Time complexity: O(m). m:  the MIN number of nodes from the two given trees
    Space complexity: O(m). The depth of the recursion tree can go upto m in the case of a skewed tree
    Average: O(logm).
     */
    public TreeNode mergeTrees(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }
        t1.val += t2.val;
        t1.left = mergeTrees(t1.left, t2.left);
        t1.right = mergeTrees(t1.right, t2.right);
        return t1;
    }

    /*
    Iterative Method:
    SAME URL

    NO recursion -> YES Stack
    calling and execute method with references to the children -> push and pop references to the children
    Stack is FILO -> Put right before left (pre-order)

    Starting by pushing roots onto the stack.
    At every step, remove a node pair.
    For every pair removed, add the values and update the value in the first tree.
    If first trees's l child exists, push the l children onto the stack.
    If not, append the l child of 2nd tree to the first tree.
    Same for the right child pair as weill.

    Time complexity: O(n) n: smaller of the number of nodes in the two trees.
    Space complexity: O(n) The depth of stack can grow upto n in case of a skewed tree.
     */
    public TreeNode mergeTrees2(TreeNode t1, TreeNode t2) {
        if (t1 == null) {
            return t2;
        }
        if (t2 == null) {
            return t1;
        }

        Stack <TreeNode[]> stack = new Stack<>();
        stack.push(new TreeNode[] {t1, t2});
        while (!stack.isEmpty()) {
            TreeNode[] t = stack.pop();
            if (t[1] == null) {
                continue;
            }
            t[0].val += t[1].val;

            if (t[0].right == null) {
                t[0].right = t[1].right;
            } else {
                stack.push(new TreeNode[] {t[0].right, t[1].right});
            }

            if (t[0].left == null) {
                t[0].left = t[1].left;
            } else {
                stack.push(new TreeNode[]{t[0].left, t[1].left});
            }
        }
        return t1;
    }
}
