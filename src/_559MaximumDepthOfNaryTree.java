import java.util.*;

class NodeN {
    public int val;
    public List<NodeN> children;

    public NodeN() {}

    public NodeN(int _val, List<NodeN> _children) {

    }
}

public class _559MaximumDepthOfNaryTree {
    /*
    1. Recursion

    Depth First Search

    Time complexity: O(N) visit each node once.
    Space complexity: O(logN)
    Worst case: completely unbalanced. Recursion calls occur N times (the height of the tree)
    Storage for call stack: O(N).
    Best case: completely balanced. Height: log(N). O(logN)

    Note:
    1. List is an child interface of Collection. It cannot be instantiated directly.
    2. List<Integer> instead of List<int>
    3. java.util.Collections class consists exclusively of static methods that operate on or return collections.
    4. isEmpty(), add() is specified in interface Collection<E>
    5. (NodeN item : root children)
     */

    public int maxDepth(NodeN root) {
        if (root == null) {
            return 0;
        } else if (root.children.isEmpty()) {
            return 1;
        } else {
            List<Integer> heights = new LinkedList<>();
            for (NodeN item : root.children) {
                heights.add(maxDepth(item));
            }
            return Collections.max(heights) + 1;
        }
    }

    /*
    2. Iteration

    Breadth First Search
    Using a for loop removing nodes of the same level at once.

    Link: https://leetcode.com/problems/maximum-depth-of-n-ary-tree/discuss/183060/Java-BFS-Iterative-Solution


    Time complexity: O(N) number of nodes
    Space complexity:
    O(N) for balanced tree.
    O(1) for skewed tree.

    Note:
    1. Queue is an sub-interface of Collection. It can not be instantiated directly.
    LinkedList<E> implements Lists<E>, Deque<E>
    2. add() remove() and offer() poll() are available in the Queue interface.
    add(), remove() is specified by Collection<E>.
    offer(), poll() is specified by Queue<E>.
    Why prefer offer() and poll() ?
    add() throws exception when queue if full, whereas offer() return false
    remove() throws exception when queue is empty, whereas poll() returns null

     */
    public int maxDepth2(NodeN root) {
        if (root == null) {
            return 0;
        }

        Queue<NodeN> queue = new LinkedList<>();
        queue.offer(root);

        int depth = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                NodeN current = queue.poll();
                for (NodeN child: current.children) {
                    queue.offer(child);
                }

            }

            depth++;
        }

        return depth;
    }
}
