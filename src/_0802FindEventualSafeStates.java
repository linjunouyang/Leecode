import java.util.*;

/**
 * A node is eventually safe if ALL paths from the node ends at a terminal node
 */
public class _0802FindEventualSafeStates {
    /**
     * 1. Reverse Graph + Topological Sort
     *
     * 对于一个节点 u，如果我们从 u 开始任意行走能够走到一个环里，那么 u 就不是一个安全的节点。
     * 换句话说，u 是一个安全的节点，当且仅当 u 直接相连的节点（u 的出边相连的那些节点）都是安全的节点。
     *
     * 因此我们可以首先考虑没有任何出边的节点，它们一定都是安全的。
     * 随后我们再考虑仅与这些节点直接相连的节点，它们也一定是安全的，
     * 以此类推。这样我们可以将所有的边全部反向，首先所有没有任何入边的节点都是安全的，我们把这些节点全部移除。随后新的图中没有任何入边的节点都是安全的，以此类推。
     * 我们发现这种做法实际上就是对图进行拓扑排序。
     *
     * Time:
     * build reversed Adj Lists: O(E + V)
     * topological sort: O(E + V)
     * build answer list: O(V)
     *
     * Space:
     * degree: O(V)
     * reversed adj lists: O(E + V)
     * queue: O(V)
     */
    public List<Integer> eventualSafeNodes(int[][] graph) {
        int n = graph.length;
        int[] degree = new int[n]; // in-degree in reversed map
        HashMap<Integer, List<Integer>> reversedAdjLists = new HashMap<>();

        for (int node = 0; node < n; node++) {
            for (int neighbor : graph[node]) {
                if (!reversedAdjLists.containsKey(neighbor)) {
                    reversedAdjLists.put(neighbor, new ArrayList<>());
                }
                reversedAdjLists.get(neighbor).add(node);
                degree[node]++;
            }
        }

        // Set<Integer> res = new HashSet<>();
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            if (degree[i] == 0) {
                queue.add(i);
            }
        }

        while (!queue.isEmpty()) {
            int node = queue.poll();
            if (reversedAdjLists.containsKey(node)) {
                for (int neighbor : reversedAdjLists.get(node)) {
                    degree[neighbor]--;
                    if (degree[neighbor] == 0) {
                        queue.offer(neighbor);
                    }
                }
            }
        }

        List<Integer> safeNodes = new ArrayList<Integer>();
        for (int node = 0; node < n; node++) {
            if (degree[node] == 0) {
                safeNodes.add(node);
            }
        }

        return safeNodes;
    }

    /**
     * 2. DFS
     *
     * nodes in cycle, nodes that lead to a cycle -> not safe
     *
     * Time: O(V + E)
     *
     * Space:
     * color: O(V)
     * dfs call stack: O(V)
     */

    /**
     * An enum is a special "class" that represents a group of constants
     * An enum can, just like a class, have attributes and methods.
     * The only difference is that enum constants are public, static and final (unchangeable - cannot be overridden).
     */
    enum State {
        UNVISITED,
        VISITED, // visiting
        SAFE, // visited

        // All enum types get a static values() method automatically by the Java compiler.
    }

    public List<Integer> eventualSafeNodes2(int[][] graph) {
        int n = graph.length;
        State[] state = new State[n];
        List<Integer> ans = new ArrayList();

        for (int node = 0; node < n; node++) {
            if (dfs(node, state, graph)) {
                ans.add(node);
            }
        }

        return ans;
    }

    // colors: WHITE 0, GRAY 1, BLACK 2;
    public boolean dfs(int node, State[] state, int[][] graph) {
        if (state[node] == State.SAFE) {
            return true;
        }
        if (state[node] == State.VISITED) {
            return false;
        }

        state[node] = State.VISITED;

        for (int next: graph[node]) {
            if (!dfs(next, state, graph)) {
                return false;
            }
        }

        state[node] = State.SAFE;

        return true;
    }

}
