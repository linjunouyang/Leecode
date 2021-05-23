

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * Why design iterators:
 *
 * an iterator can be used to iterate over any container object.
 * For our purpose, the container object is a binary search tree.
 * If such an iterator is defined, then the traversal logic can be abstracted out
 * and we can simply make use of the iterator to process the elements in a certain order.
 *
 *
 *
 */
public class _0173BinarySearchTreeIterator {
    private List<TreeNode> sorted;

    /**
     * Time complexity: O(n)
     * Space complexity: O(n), recursive stack O(h)
     *
     * @param root
     */
    public _0173BinarySearchTreeIterator(TreeNode root) {
        sorted = new LinkedList<>();
        inOrder(root, sorted);
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     *
     * ? the new array is used for both the function calls
     * ? and hence the space complexity for both the calls is O(N)O(N).
     * @return
     */
    public int next() {
        return sorted.remove(0).val;
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     *
     *
     * @return
     */
    public boolean hasNext() {
        return !sorted.isEmpty();
    }

    private void inOrder(TreeNode root, List<TreeNode> res) {
        if (root == null) {
            return;
        }
        inOrder(root.left, res);
        res.add(root);
        inOrder(root.right, res);
    }

    /**
     * if we could simulate a controlled recursion for an inorder traversal,
     * we wouldn't really need to use any additional space other than the space used by the stack
     * for our recursion simulation.
     */

    private Deque<TreeNode> stack;

    /**
     * Time complexity: O(h)
     * Space complexity: O(h)
     *
     * @param root
     */
//    public _0173BinarySearchTreeIterator(TreeNode root) {
//        stack = new ArrayDeque<>();
//
//        pushLeft(root);
//    }

    /**
     * Time complexity: O(h)
     * Space complexity: O(h)
     *
     * @param root
     */
    private void pushLeft(TreeNode root) {
        while (root != null) {
            stack.push(root);
            root = root.left;
        }
    }

    /**
     * Time complexity: O(h)
     * Worst case: O(n)
     * Amortize case: O(1)
     *
     * I find it easiest to reason that each node gets pushed and popped exactly once in next() when iterating over all N nodes.
     * That comes out to 2N * O(1) over N calls to next(), making it O(1) on average, or O(1) amortized.
     *
     * Notice:
     * 1) we only push left for nodes which have a right child. Otherwise, we simply return.
     * 2) if we end up calling the helper function, it won't always process N nodes.
     * Only if we have a skewed tree would there be N nodes for the root.
     * But that is the only node for which we would call the helper function.
     *
     * Space complexity: O(h)
     *
     * Runtime: 57 ms, faster than 90.74% of Java online submissions for Binary Search Tree Iterator.
     * Memory Usage: 50.6 MB, less than 92.59% of Java online submissions for Binary Search Tree Iterator.
     *
     * @return
     */
    public int next2() {
        TreeNode node = stack.pop();

        if (node.right != null) {
            pushLeft(node.right);
        }

        return node.val;
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1), O(h)
     *
     * @return
     */
    public boolean hasNext2() {
        return !stack.isEmpty();
    }

}
