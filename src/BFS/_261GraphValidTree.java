package BFS;

import java.util.*;

public class _261GraphValidTree {
    /**
     * 1. BFS
     *
     * How does a graph qualify as a tree?
     * 1. If n nodes, then n - 1 edges
     * 2. All nodes are connected
     *
     * Map: put, get
     * Queue: offer, poll
     * Set: add, contains
     *
     * @param n
     * @param edges
     * @return
     */
    public boolean validTree(int n, int[][] edges) {
        if (n == 0) {
            // This should return true I think
            return false;
        }

        if (edges.length != n - 1) {
            return false;
        }

        Map<Integer, Set<Integer>> graph = initializeGraph(n, edges);

        Queue<Integer> queue = new LinkedList<>();
        Set<Integer> hash = new HashSet<>();

        queue.offer(0);
        hash.add(0);

        while (!queue.isEmpty()) {
            int node = queue.poll();

            for (Integer neighbor : graph.get(node)) {

                if (hash.contains(neighbor)) {
                    // 这里不能return false;
                    // 因为无向边在两个地方都存在
                    continue;
                }

                hash.add(neighbor);
                queue.offer(neighbor);
            }
        }

        return (hash.size() == n);
    }

    private Map<Integer, Set<Integer>> initializeGraph(int n, int[][] edges) {
        // Map<Node Number 0..n-1, Connected Node Num Set>
        Map<Integer, Set<Integer>> graph = new HashMap<>();

        for (int i = 0; i < n; i++) {
            graph.put(i, new HashSet<Integer>());
        }

        for (int i = 0; i < edges.length; i++) {
            int u = edges[i][0];
            int v = edges[i][1];
            graph.get(u).add(v);
            graph.get(v).add(u);
        }

        return graph;
    }
}
