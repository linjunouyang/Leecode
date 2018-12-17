package Tree;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;

public class _226InvertBinaryTree {
    /*
    1. Recursion

    Treat left and right child as a tree.

    Time complexity: O(n) n: number of nodes
    Space complexity:
    O(h) function calls will be places on the stack. h: height of tree.
    If the tree is skewed, then O(h) = O(n)
    Not really scalable
     */
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode tempRight = root.right;
        root.right = invertTree(root.left);
        root.left = invertTree(tempRight);
        return root;
    }

    /*
    2. Iteration
    ---------------------------------------
    Similar to BFS. -> Level order traversal

    We need to swap the left and right child of all nodes.

    **The queue is to store nodes whose children have not been swapped yet.**
    Initially, only the root is in the queue.

    As long as the queue is not empty, remove the next node from the queue,
    swap its children, and add its children to the queue.
    Null nodes are not added to the queue.

    Eventually, the queue will be empty and all the children swapped.
    We return the original root.
    ---------------------------------------
    Time complexity: O(n)
    Space complexity: O(n)
    Worst case: the queue contain all nodes in one level of the binary tree.
    For a full binary tree, the leaf level has n/2 -> O(n) leaves
    ---------------------------------------
    Deque and Queue are both queue interfaces. FIFO. -> BFS

    Queues typically, but not necessarily , order elements in a FIFO manner.
    Its implementations generally don't allow null insertion. (Exception: LinkedList)

    Deque extends Queue interface. FIFO / LIFO
    This interface should be used in preference to the legacy Stack class.
    Better not to insert null.

    Stack class extends class Vector.
    It has a way of accessing an element by position. (bad)

    class LinkedList implements List and Deque interfaces.
     */
    public TreeNode invertTree2(TreeNode root) {
        if (root == null) {
            return null;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();
            TreeNode temp = current.left;
            current.left = current.right;
            current.right = temp;

            if (current.left != null) {
                queue.add(current.left);
            }
            if (current.right != null) {
                queue.add(current.right);
            }
        }
        return root;
    }

    /*
    3. Iteration.

    DFS.

    Using stack to store all the nodes (not-null) whose children have not been swapped.

    Advantage of stack: ease of logging / debugging.
    At any point of execution, you can log the size of your stack, do something

    Time complexity: O(n), n = number of nodes
    Space complexity:
    Best case: completely skewed tree. O(1)
    Typical case: balanced tree. O(h + 1) = O(h)
    Worst case: skewed tree below. O(h) = O(n/2) = O(n)
            1
           / \
          [2] 3
             / \
            [4] 5
               / \
              [6] 7
                 / \
                [8] [9]
     */
    public TreeNode invertTree3(TreeNode root) {
        if (root == null) {
            return null;
        }

        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(root);

        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            TreeNode left = node.left;
            node.left = node.right;
            node.right = left;

            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }

        return root;
    }
}
