import java.util.*;

public class _0323NumberOfConnectedComponentsInAnUndirectedGraph {
    /**
     * 1. DFS or BFS
     *
     * Time complexity:
     * graph construction: O(E)?
     * bfs/dfs: O(V + E)
     *
     * Space:
     * graph: O(V + E)
     * visited: O(V)
     * queue/stack: O(V)
     *
     */
    public int countComponents(int n, int[][] edges) {
        Map<Integer, List<Integer>> adjList = new HashMap<>();
        for (int[] edge : edges) {
            adjList.putIfAbsent(edge[0], new ArrayList<>());
            adjList.get(edge[0]).add(edge[1]);
            adjList.putIfAbsent(edge[1], new ArrayList<>());
            adjList.get(edge[1]).add(edge[0]);
        }

        boolean[] visited = new boolean[n + 1];
        int components = 0;
        for (int i = 0; i < n; i++) {
            if (!visited[i]) {
                //bfs(adjList, visited, i);
                dfs(adjList, visited, i);
                components++;
            }
        }

        return components;
    }

    private void bfs(Map<Integer, List<Integer>> adjList, boolean[] visited, int start) {
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(start);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            visited[node] = true;
            for (int next : adjList.getOrDefault(node, new ArrayList<>())) {
                if (!visited[next]) {
                    queue.offer(next);
                }
            }
        }
    }

    private void dfs(Map<Integer, List<Integer>> adjList, boolean[] visited, int start) {
        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(start);

        while (!stack.isEmpty()) {
            int node = stack.pop();
            visited[node] = true;
            for (int next : adjList.getOrDefault(node, new ArrayList<>())) {
                if (!visited[next]) {
                    stack.push(next);
                }
            }
        }
    }

    /**
     * 2. Union Find
     *
     * Time:
     * union on edges: O(E) (path compression, union by rank used)
     *
     * Space:
     * uf: O(n)
     * find call stack: O(n)
     */
    class UnionFind {
        int size;
        int[] parents;
        int[] ranks;

        public UnionFind(int n) {
            size = n;
            parents = new int[n];
            for (int i = 0; i < n; i++) {
                parents[i] = i;
            }
            ranks = new int[n];
        }

        public int find(int i) {
            if (i != parents[i]) {
                parents[i] = find(parents[i]);
            }
            return parents[i];
        }

        public int union(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);
            if (xRoot ==  yRoot) {
                return 0;
            }

            if (ranks[xRoot] < ranks[yRoot]) {
                parents[xRoot] = yRoot;
            } else if (ranks[yRoot] < ranks[xRoot]){
                parents[yRoot] = xRoot;
            } else {
                parents[yRoot] = xRoot;
                ranks[xRoot]++;
            }
            return 1;
        }
    }

    public int countComponents2(int n, int[][] edges) {
        UnionFind uf = new UnionFind(n);
        int components = n;
        for (int[] edge : edges) {
            components = components - uf.union(edge[0], edge[1]);
        }
        return components;
    }
}
