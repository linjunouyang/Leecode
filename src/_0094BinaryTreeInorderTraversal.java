import java.util.*;

public class _0094BinaryTreeInorderTraversal {
    /*
    Approach 1: Recursive Approach

    Note:
    1.
    Interface List<E>: An ordered collection. Typically allow duplicate elements.
    Implementing Classes: ArrayList, LinkedList, Stack, Vector
    LinkedList has more memory overhead than ArrayList

    2.
    Returning null is a bad design as client code will have to handle this.
    Instead, return a empty ArrayList;

    3.
    Helper methods usually have additional parameters that describes how far
    the recursion has already proceeded or how far it still has to proceed.
    This only makes sense when helper methods are private.

    --------------------------
    Time complexity:
    O(n), n: number of nodes
    T(n) = 2 * T(n/2) + 1

    Space complexity:
    Average: O(log(n))
    Worst: O(log n) for left skewed tree

    Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Inorder Traversal.
    Memory Usage: 37.9 MB, less than 5.11% of Java online submissions for Binary Tree Inorder Traversal.
    --------------------------
     */
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> res = new ArrayList<>();
        helper(root, res);
        return res;
    }

    private void helper(TreeNode root, List<Integer> res){
        if (root != null) {
            if (root.left != null) {
                helper(root.left, res);
            }
            res.add(root.val);
            if (root.right != null) {
                helper(root.right, res);
            }
        }
    }

    /*
    Approach 2: Iterating method using Stack

    stack stores all the unvisited roots of subtrees

    Note:
    1.
    Outside while loop:
    curr != null for initial
    !stack.isEmpty() for subsequent
    2.
    A more complete and consistent set of LIFO stack operations
    is provided by Deque interface and its implementations,
    which should be used in preference to this class.

    Time complexity:
    O(n)

    Space complexity:
    worst : O(n) for left-skewed tree.
    average: o(log n)

    Runtime: 0 ms, faster than 100.00% of Java online submissions for Binary Tree Inorder Traversal.
    Memory Usage: 38.2 MB, less than 5.11% of Java online submissions for Binary Tree Inorder Traversal.


    */
    public List<Integer> inorderTraversal2(TreeNode root) {
        List<Integer> res= new ArrayList<>();
        Deque<TreeNode> stack = new ArrayDeque<>();
        TreeNode curr = root;

        while (curr != null || !stack.isEmpty()) {
            while (curr != null) {
                stack.push(curr);
                curr = curr.left;
            }
            curr = stack.pop();
            res.add(curr.val);
            curr = curr.right;
        }

        return res;
    }
}
