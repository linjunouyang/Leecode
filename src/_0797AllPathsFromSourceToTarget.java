import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class _0797AllPathsFromSourceToTarget {
    /**
     * 1. Bactracking / DFS
     *
     * How many paths there are at maximum to travel from the Node 0 to the Node N-1 for a graph with N nodes:
     * 2: 1
     * 3: 2
     * 4: 4
     * 5: 8
     *
     * 2^(n - 2) paths
     * each path could have at most n - 2 intermediate nodes, O(n) to build a path
     * // another way of thinking:
     * path of length k corresponds to some choice of (k - 1) nodes between 0 and (n-1).
     * How many paths of length k are there? The answer is (n-2 choose k-1) because we pick k - 1 nodes between 0 and (n - 1).
     * which is O(2 ^ n)
     * and each path takes O(n) to build
     *
     * Time: O(n * 2^n)
     *
     * Space:
     * current path: O(n)
     * recursion call stack: O(n)
     *
     * @param graph
     * @return
     */
    public List<List<Integer>> allPathsSourceTarget(int[][] graph) {
        List<List<Integer>> paths = new ArrayList<>();
        dfs(graph, 0, new ArrayList<>(), paths);
        return paths;
    }

    /**
     * or we could call 1st dfs with list containing 0
     * and
     * path.add(next);
     * dfs
     * path.remove
     */
    private void dfs(int[][] graph, int cur, List<Integer> path, List<List<Integer>> paths) {
        path.add(cur);
        if (cur == graph.length - 1) {
            paths.add(new ArrayList<>(path));
            path.remove(path.size() - 1);
            return;
        }

        for (int next : graph[cur]) {
            dfs(graph, next, path, paths);
        }
        path.remove(path.size() - 1);
    }

    /**
     * 2. Top-down Dynamic Programming:
     *
     * ∀nextNode∈neighbors(currNode),
     * allPathsToTarget(currNode)={currNode+allPathsToTarget(nextNode)}
     *
     * we cache allPathsToTarget(node)
     *
     * Time: asymptotically same, but needs to copy intermediate paths
     *
     * Space: not efficient, we have to store intermediate paths
     *
     */

    /**
     * 3. Iterative Version
     */
    public List<List<Integer>> allPathsSourceTarget3(int[][] graph) {
        Integer end = graph.length - 1;
        List<List<Integer>> result = new ArrayList<>();

        Deque<Integer> stack = new ArrayDeque<>(); // keep track of nodes to process
        Deque<Integer> path  = new ArrayDeque<>(); // path generated so far

        // prime the stack with the start node
        stack.push(0);

        while (!stack.isEmpty()) {
            /* 'hack' alert
            we need an Integer object and not an int because path.peekLast()
            will return null the very first time it is called.
            you cannot compare null with an int but you can with an Integer
			*/
            Integer current = stack.peek(); /* peek, don't pop */
            if (current == path.peekLast()) {
                /*
				   when the top of the stack and the top of queue match
                   it means we have processes all this node's connections
                   there are no more paths to explore so remove the node
                   from the list of nodes to process, and from the path
                   We are backtracking to the previous point in the path, and
                   the list of nodes to process
				*/
                stack.pop();       // remove the end from the path
                path.removeLast(); // remove the end from the path
                continue;
            }
            path.offer(current);
            if (current == end) {
                /* We've found a path */
                result.add(new ArrayList(path)); /* add the path Queue as a List to the result */
                path.removeLast(); /* go back one step in the path */
                stack.pop();       /* remove the from the nodes to process */
                continue;
            }
			/*
			   we haven't seen this node before.
               add all of this node's neighbors to the stack of nodes to process
			*/
            for (int neighbor : graph[current]) {
                stack.push(neighbor);
            }
        }
        return result;
    }
}
