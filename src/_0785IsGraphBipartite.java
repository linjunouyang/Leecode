import java.util.ArrayDeque;
import java.util.Deque;

public class _0785IsGraphBipartite {
    /**
     * 1. BFS
     *
     * Time: O(V + E)
     * Space:
     * colors: O(n)
     * Queue: O(E) or O(n)?
     */
    public boolean isBipartite(int[][] graph) {
        if (graph == null) {
            return false;
        }

        int nodes = graph.length;
        int[] colors = new int[nodes];

        for (int i = 0; i < nodes; i++) {
            if (colors[i] == 0) {
                if (!bfs(i, graph, colors)) {
                    return false;
                }
            }
        }

        return true;
    }

    private boolean bfs(int start, int[][] graph, int[] colors) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(start);
        int color = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int curr = queue.poll();
                colors[curr] = color;
                for (int neighbor : graph[curr]) {
                    if (colors[neighbor] == 0) {
                        queue.offer(neighbor);
                    } else if (colors[neighbor] != -colors[curr]) {
                        return false;
                    }
                }
            }
            color = -color;
        }
        return true;
    }

    /**
     * 2. Recursive DFS
     *
     * Time: O(V + E)
     * Space:
     * colors: O(V)
     * recursion call stack: O(E) or O(n)
     */
    public boolean isBipartite2(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];

        for (int i = 0; i < n; i++) {              //This graph might be a disconnected graph. So check each unvisited node.
            if (colors[i] == 0 && !validColor(graph, colors, 1, i)) {
                return false;
            }
        }
        return true;
    }

    public boolean validColor(int[][] graph, int[] colors, int color, int node) {
        if (colors[node] != 0) {
            return colors[node] == color;
        }
        colors[node] = color;
        for (int next : graph[node]) {
            if (!validColor(graph, colors, -color, next)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 3. Iterative DFS
     *
     * Time: O(V + E)
     * Space: O(V)
     */
    public boolean isBipartite3(int[][] graph) {
        int n = graph.length;
        int[] colors = new int[n];
        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < n; i++) {              //This graph might be a disconnected graph. So check each unvisited node.
            if (colors[i] == 0) {
                stack.push(i);
                int color = 1;
                colors[i] = color;

                while (!stack.isEmpty()) {
                    int node = stack.pop();
                    for (int next : graph[node]) {
                        if (colors[next] == 0) {
                            stack.push(next);
                            colors[next] = -colors[node];
                        } else if (colors[next] == colors[node]) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
