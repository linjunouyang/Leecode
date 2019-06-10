package Tree;

import java.util.*;

public class _100SameTree {
    /**
     * 1. Recursion, pre-order traversal (DFS), divide and conquer?
     *
     * Time complexity: O(N)
     * Space complexity: O(logN) for completely balanced tree
     * O(N) for completely unbalanced tree.
     *
     * @param p
     * @param q
     * @return
     */
    public static boolean isSameTree1(TreeNode p, TreeNode q) {
        // this is brilliant!
        if (p == null || q == null) {
            return (p == q);
        }

        if (p.val == q.val) {
            return isSameTree1(p.left, q.left) && isSameTree1(p.right, q.right);
        }

        return false;
    }

    /**
     * 2. Iteration, pre-order traversal (DFS)
     *
     * Time complexity: O(min(m, n))
     * Space complexity: O(lgn) for completely balanced tree
     * O(n) for completely unbalanced tree.
     *
     * ArrayDeque:
     * implements Deque, no capacity restrictions, null prohibited
     * push: NullPointerException - if the specified element is null
     * pop: NoSuchElementException - if the deque is empty
     *
     * @param args
     */
    public static boolean isSameTree2(TreeNode p, TreeNode q) {
        Deque<TreeNode> stack = new ArrayDeque<>();
        if (p != null) {
            stack.push(p);
        }
        if (q != null) {
            stack.push(q);
        }

        while (!stack.isEmpty()) {
            TreeNode qNode = stack.pop();
            TreeNode pNode = null;
            if (!stack.isEmpty()) {
                pNode = stack.pop();
            }

            if (pNode == null || qNode == null) {
                // while guarantees that stack is not empty
                // pNode, qNode can't be null at the same time.
                return false;
            }

            if (pNode.val == qNode.val) {
                if (pNode.right == null ^ qNode.right == null) {
                    return false;
                }
                if (pNode.right != null && qNode.right != null) {
                    stack.push(pNode.right);
                    stack.push(qNode.right);
                }

                if (pNode.left == null ^ qNode.left == null) {
                    return false;
                }
                if (pNode.left != null && qNode.left != null) {
                    stack.push(pNode.left);
                    stack.push(qNode.left);
                }
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 2.1
     *
     * LinkedList allows null, less checking code.
     */

    public static boolean isSameTree3(TreeNode p, TreeNode q) {
        Deque<TreeNode> stack = new LinkedList<>();
        stack.push(p);
        stack.push(q);

        while (!stack.isEmpty()) {
            TreeNode qNode = stack.pop();
            TreeNode pNode = stack.pop();

            if (pNode == null && qNode == null) {
                continue;
            }
            if (pNode == null || qNode == null) {
                return false;
            }

            if (pNode.val == qNode.val) {
                stack.push(pNode.right);
                stack.push(qNode.right);
                stack.push(pNode.left);
                stack.push(qNode.left);
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * 2.2 Two stacks
     */
    public static boolean isSameTree4(TreeNode p, TreeNode q) {
        Stack<TreeNode> stack_p = new Stack<>();
        Stack<TreeNode> stack_q = new Stack<>();
        if (p != null) {
            stack_p.push(p);
        }
        if (q != null) {
            stack_q.push(q);
        }

        while (!stack_p.isEmpty() && !stack_q.isEmpty()) {
            TreeNode pn = stack_p.pop();
            TreeNode qn = stack_q.pop();
            if (pn.val != qn.val) {
                return false;
            }

            if (pn.right != null) {
                stack_p.push(pn.right);
            }
            if (qn.right != null) {
                stack_q.push(qn.right);
            }
            if (stack_p.size() != stack_q.size()) {
                return false;
            }

            if (pn.left != null) {
                stack_p.push(pn.left);
            }
            if (qn.left != null) {
                stack_q.push(qn.left);
            }
            if (stack_p.size() != stack_q.size()) {
                return false;
            }
        }

        return stack_p.size() == stack_q.size();
    }

    /**
     * 3. Iteration, LinkedList as Queue, BFS, Level-order Traversal
     *
     * Time complexity: O(n)
     * Space complexity: O(n) for completely balanced trees when storing last level nodes
     *
     * _______________
     * Queue interface:
     * Typically FIFO, exceptions: priority queues, LIFO queues (or Stacks)
     * Throws exception: add(e), remove(), element
     * Returns null: offer(e), poll(), peek();
     *
     * remove, poll uses the head of the queue
     *
     * generally prohibit null, exceptions: LinkedList
     *
     * implementations: ArrayDeque (null prohibited), LinkedList, PriorityQueue
     * _______________
     * Deque interface:
     * extends queue
     *
     * https://docs.oracle.com/javase/7/docs/api/java/util/Deque.html
     *
     * @param args
     */
    public static boolean isSameTree5(TreeNode p, TreeNode q) {
        Queue<TreeNode> queue = new LinkedList<>();
        queue.offer(p);
        queue.offer(q);

        while (!queue.isEmpty()) {
            TreeNode f = queue.poll();
            TreeNode s = queue.poll();
            if(f == null && s == null){
                // f and s can be null at the same time
                // because we don't check null before offer
                continue;
            } else if(f == null || s == null || f.val != s.val){
                return false;
            }
            queue.offer(f.right);
            queue.offer(s.right);
            queue.offer(f.left);
            queue.offer(s.left);
        }

        return true;
    }


    public static void main(String[] args) {
        // p
        TreeNode p = new TreeNode(1);
        p.left = new TreeNode(2);

        // q
        TreeNode q = new TreeNode(1);
        q.right = new TreeNode(2);

        isSameTree2(p, q);

    }
}
