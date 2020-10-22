import java.util.*;

public class _0310MinimumHeightTrees {
    /**
     * 1. Brute Force
     *
     * Starting from each node in the graph, we treat it as a root to build a tree.
     * Furthermore, we would like to know the distance between this root node and the rest of the nodes.
     * The maximum of the distance would be the height of this tree.
     * (559 Maximum Depth of N-ary Tree)
     *
     * Then according to the definition of Minimum Height Tree (MHT),
     * we simply filter out the roots that have the minimal height among all the trees.
     *
     * However, this solution is not efficient, whose time complexity would be O(N^2)
     * where N is the number of nodes in the tree.
     * As one can imagine, it will result in Time Limit Exceeded exception in the online judge.
     *
     */


    /**
     * 2. Topological Sort (Sort of)
     *
     * Reference:
     * https://leetcode.com/problems/minimum-height-trees/discuss/76055/Share-some-thoughts
     * https://leetcode.com/problems/minimum-height-trees/solution/
     *
     * ? why is it topological-sort-like
     * generates the order of objects based on their dependencies.
     *
     * --
     * (1) A tree is an undirected graph in which any two vertices are
     * connected by exactly one path.
     *
     * (2) Any connected graph who has n nodes with n-1 edges is a tree.
     *
     * (3) The degree of a vertex of a graph is the number of
     * edges incident to the vertex.
     *
     * (4) A leaf is a vertex of degree 1. An internal vertex is a vertex of
     * degree at least 2.
     *
     * (5) A path graph is a tree with two or more vertices that is not
     * branched at all.
     *
     * (6) A tree is called a rooted tree if one vertex has been designated
     * the root.
     *
     * (7) The height of a rooted tree is the number of edges on the longest
     * downward path between root and a leaf.
     * ---
     *
     * Calling iterator() is constant time.
     * It is a method call that returns an Iterator instance on the collection you are calling on.
     *
     * -----
     * For the tree-alike graph, the number of centroids is no more than 2.
     *
     * 1. If the nodes form a chain, it is intuitive to see that the above statement holds,
     * which can be broken into the following two cases:
     * a) If the number of nodes is even, then there would be two centroids.
     * b) If the number of nodes is odd, then there would be only one centroid.
     *
     * 2. Other cases: not a chain, proof by contradiction:
     * Suppose that we have 3 centroids in the graph,
     * if we remove all the non-centroid nodes in the graph, then the 3 centroids nodes must form a triangle shape
     * Because these centroids are equally important to each other, and they should equally close to each other as well.
     * If any of the edges that is missing from the triangle, then the 3 centroids would be reduced down to a single centroid.
     * However, the triangle shape forms a cycle which is contradicted to the condition
     * that there is no cycle in our tree-alike graph.
     * -----
     *
     * Any node that has already been a leaf cannot be the root of a MHT,
     * because its adjacent non-leaf node will always be a better candidate.
     *
     * Time:
     * graph construction: O(|V| - 1)
     * get initial leaves: O(|V|)
     * trimming almost all edges and nodes: O(V + V - 1)
     * Total: O(V)
     *
     * Space:
     * adjacency list: O(V + V - 1)
     * Leaves: worst case: one centroid, rest are leaves, O(V - 1)
     * total: O(V)
     */
    public List<Integer> findMinHeightTrees(int n, int[][] edges) {
        List<Integer> res = new ArrayList<Integer>();
        if (n == 1) {
            res.add(0);
            return res;
        }

        List<List<Integer>> adjList = new ArrayList<List<Integer>>();
        for (int i=0; i<n; i++) {
            adjList.add(new ArrayList<Integer>());
        }
        int[] degree = new int[n];
        for (int i=0; i<edges.length; i++) {
            adjList.get(edges[i][0]).add(edges[i][1]);
            adjList.get(edges[i][1]).add(edges[i][0]);
            degree[edges[i][0]]++;
            degree[edges[i][1]]++;
        }

        Queue<Integer> queue = new ArrayDeque<Integer>();
        for (int i = 0; i < n; i++)  {
            if (degree[i] == 1) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            List leaves = new ArrayList<Integer>();
            int size = queue.size();
            for (int i = 0; i < size; i++){
                int curr = queue.poll();
                leaves.add(curr);
                for(int next : adjList.get(curr)) {
                    degree[next]--;
                    if (degree[next]==1) {
                        queue.offer(next);
                    }
                }
            }
            res = leaves;
        }
        return res;
    }

    /**
     * Other two solutions:
     * https://leetcode.com/problems/minimum-height-trees/discuss/76052/Two-O(n)-solutions
     */


}
