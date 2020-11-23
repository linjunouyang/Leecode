import java.util.ArrayDeque;
import java.util.Deque;

/**
 * How is this different from Number oof Islands?
 * https://leetcode.com/problems/friend-circles/discuss/101431/Stupid-question%3A-How-is-this-question-different-from-Number-of-Islands
 */
public class _0547FriendCircles {
    /**
     * 1. DFS
     *
     * The given matrix can be viewed as the Adjacency Matrix of a graph.
     * This problem = finding the number of connected components in an undirected graph.
     *
     * Time: O(n^2)
     * If you take a step back and think about the number of times the dfs function is called,
     * you will see that the dfs function is called once per student (as it is called only when a student has not been visited/seen yet).
     * Now, we look at what happens inside the dfs function and see that the for loop runs 0...M.length (=N).
     * So, the dfs function viewed by itself is O(N) and the dfs function is called N times, so O(N*N).
     *
     * Could you kindly explain your answer? For me, as stated in previous comments, a
     * nother way to view the solution is that every element in the matrix is being inspected hence N*N (=size of the matrix).
     * If this was an adjacency list rather than a matrix, I could see how the complexity could be tightened to O(N+R) where R=number of relationships (can also be viewed as number of edges),
     * but not in the case of this matrix.
     *
     * Space:
     * visited: O(n)
     * call stack: O(n)
     */
    public int findCircleNum1(int[][] M) {
        int[] visited = new int[M.length];
        int count = 0;
        for (int i = 0; i < M.length; i++) {
            if (visited[i] == 0) {
                dfs(M, visited, i);
                count++;
            }
        }
        return count;
    }

    public void dfs(int[][] M, int[] visited, int i) {
        for (int j = 0; j < M.length; j++) {
            if (M[i][j] == 1 && visited[j] == 0) {
                visited[j] = 1;
                dfs(M, visited, j);
            }
        }
    }

    /**
     * 2. BFS
     *
     * Altenative:
     * https://leetcode.com/problems/friend-circles/discuss/101344/Java-BFS-Equivalent-to-Finding-Connected-Components-in-a-Graph
     *
     * Time: O(n^2)
     * Space: O(n)
     */
    public int findCircleNum2(int[][] M) {
        int[] visited = new int[M.length];
        int count = 0;
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < M.length; i++) {
            if (visited[i] == 0) {
                queue.add(i);
                while (!queue.isEmpty()) {
                    int s = queue.remove();
                    visited[s] = 1;
                    for (int j = 0; j < M.length; j++) {
                        if (M[s][j] == 1 && visited[j] == 0)
                            queue.add(j);
                    }
                }
                count++;
            }
        }
        return count;
    }

    /**
     * 3. Union Find
     *
     *  have seen path compression in Sedgewick's book where he states find operation requires Log(ast)N,
     *  where asterix - means if N is number of atoms in universe then Log*N will be 5~6.
     *  So, if we take this into consideration complexity of the solution will be NNLog*N -> 5 * N * N -> N^2.
     *  Also it may be faster than DFS - because it uses recursion stack, and BFS because of usage dynamic memory in terms of Queue,
     *  UF initializes array and has a lot of sequential reads, so it wouldn't be surprise for me if during benchmark UF show best result,
     *  although in terms of Big O notation it has slightly worse complexity.
     *
     * Time: O(n ^ 2)
     * Space: O(n)
     */
    public int findCircleNum3(int[][] M) {
        int n = M.length;
        UnionFind uf = new UnionFind(n);
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (M[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.count();
    }

    class UnionFind {
        private int count = 0;
        private int[] parent, rank;

        public UnionFind(int n) {
            count = n;
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
        }

        public int find(int p) {
            while (p != parent[p]) {
                parent[p] = parent[parent[p]];    // path compression by halving
                p = parent[p];
            }
            return p;
        }

        public void union(int p, int q) {
            int rootP = find(p);
            int rootQ = find(q);
            if (rootP == rootQ) return;
            if (rank[rootQ] > rank[rootP]) {
                parent[rootP] = rootQ;
            }
            else {
                parent[rootQ] = rootP;
                if (rank[rootP] == rank[rootQ]) {
                    rank[rootP]++;
                }
            }
            count--;
        }

        public int count() {
            return count;
        }
    }
}
