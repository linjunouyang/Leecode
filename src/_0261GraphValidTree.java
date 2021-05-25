import java.util.*;

/**
 * a graph, G, is a tree iff the following two conditions are met:
 *
 * 1. G is fully connected.
 * In other words, for every pair of nodes in G, there is a path between them.
 * 2. G contains no cycles.
 * In other words, there is exactly one path between each pair of nodes in G.
 *
 * ---
 *
 * Graph Representation:
 * 1. adjacency list
 * 2. adjacency matrix (for graphs where edge num >> node num)
 * 3. linked representation using node objects
 */
public class _0261GraphValidTree {
    /**
     * 1. BFS/DFS
     *
     * DFS/BFS requires being able to look up the adjacent (immediate neighbours) of a given node.
     * -> adj list
     *
     * Detect cycles in graph during DFS/BFS
     * 1. directed graphs: seen.contains(next) -> return false;
     * 2. undirected graphs:
     * General idea:
     * DFS should only go along each edge once, and therefore only in one direction.
     * when we go along an edge, we  ensure that we don't then later go back along it in the opposite direction.
     * a. delete the opposite direction edges from the adjacency list.
     * b. maintain node -> prev/parent,
     * when we iterate through the neighbours of a node, we ignore the "parent" node
     *
     * Optimization:
     * Check whether or not there are n - 1 edges. (filter out cycles and some disconnected graphs)
     * Check connectivity.
     *
     * Time: O(n + e)
     * creating adj lists: O(e + n)
     * DFS: O(e)
     *
     * Space: O(n + e)
     * adj lists: O(n + e)
     * queue: O(n)
     */
    public boolean validTree(int n, int[][] edges) {
        int numEdges = edges.length;
        if (numEdges != n - 1) {
            // filter out cyclic graphs, and some disconnected graphs
            return false;
        }

        HashMap<Integer, List<Integer>> adjLists = new HashMap<>();
        buildAdjLists(edges, adjLists);

        HashMap<Integer, Integer> nodeToParent = new HashMap<>();
        nodeToParent.put(0, -1);
        Deque<Integer> queue = new ArrayDeque<>();
        queue.offer(0);

        while (!queue.isEmpty()) {
            int node = queue.poll();
            List<Integer> adjList = adjLists.getOrDefault(node, new ArrayList<>());
            for (int next : adjList) {
                if (nodeToParent.get(node) == next) {
                    continue;
                }

                // check cycles
                if (nodeToParent.containsKey(next)) {
                    return false;
                }

                queue.offer(next);
                nodeToParent.put(next, node);
            }
        }

        // check connectivity
        return nodeToParent.size() == n;
    }

    private void buildAdjLists(int[][] edges, HashMap<Integer, List<Integer>> adjLists) {
        for (int[] edge : edges) {
            int node1 = edge[0];
            int node2 = edge[1];
            adjLists.putIfAbsent(node1, new ArrayList<>());
            adjLists.get(node1).add(node2);
            adjLists.putIfAbsent(node2, new ArrayList<>());
            adjLists.get(node2).add(node1);
        }
    }

    /**
     * 2. Union Find
     *
     *  For every edge, if was no merge to happen, i
     *  t was because we were adding an edge between two nodes that were already connected via a path.
     *  This means there is now an additional path between them -> a cycle.
     *
     *  Time: O(n)
     *  Space: O(n)
     */
    class UnionFind {
        int size;
        int[] parent;
        int[] weight;

        public UnionFind(int n) {
            this.size = n;
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            weight = new int[n];
        }

        // path compression (recursion, iteration)
        // path splitting, path halving
        public int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(parent[x]);
            }
            return parent[x];
        }

        public void union(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);
            if (xRoot == yRoot) {
                return;
            }
            // union by rank
            if (weight[xRoot] >= weight[yRoot]) {
                parent[yRoot] = xRoot;
            } else {
                parent[xRoot] = yRoot;
            }
            size--;
        }
    }

    public boolean validTree2(int n, int[][] edges) {
        int edgeNum = edges.length;
        if (n - 1 != edgeNum) {
            return false;
        }

        UnionFind uf = new UnionFind(n);
        for (int[] edge : edges) {
            uf.union(edge[0], edge[1]);
        }

        return uf.size == 1;
    }

}
