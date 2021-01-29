import java.util.ArrayDeque;
import java.util.Deque;

public class _0547NumberOfProvinces {
    /**
     * 1. BFS/DFS(Iteration)
     *
     * Time: O(n ^ 2)
     * Space: O(n) (provinces array and queue)
     */
    public int findCircleNum(int[][] isConnected) {
        int cities = isConnected.length;
        int[] provinces = new int[cities];
        int provinceNum = 1;
        for (int city = 0; city < cities; city++) {
            if (provinces[city] == 0) {
                bfs(isConnected, provinces, provinceNum, city);
                // dfs(is
                provinceNum++; // mistake 1: put this outside of if block
            }
        }
        return provinceNum - 1; // remember to minus 1
    }

    /**
     * bfs -> dfs (pre-order): queue -> stack, offer -> push, poll -> pop
     *
     * No explicit definition of in-order
     */
    private void bfs(int[][] isConnected, int[] provinces, int provinceNum, int start) {
        int cities = isConnected.length;
        Deque<Integer> queue = new ArrayDeque();
        queue.offer(start);
        provinces[start] = provinceNum;

        while (!queue.isEmpty()) {
            int city = queue.poll();
            for (int other = 0; other < cities; other++) {
                if (provinces[other] == 0 && isConnected[city][other] == 1) {
                    // no need to check other != city because when other = city, provinces[other = city] != 0
                    // Think clearly about all conditions need to put in this if block
                    provinces[other] = provinceNum;
                    queue.offer(other);
                }
            }
        }
    }

    /**
     * 2. Union Find
     *
     * Time: O(n ^ 2) considering path compression and union by rank is used
     * Space: O(n)
     */
    class UnionFind {
        int size;
        int[] weights;
        int[] roots;
        int groups;

        public UnionFind(int n) {
            groups = n;
            size = n;
            weights = new int[n];
            roots = new int[n];
            for (int i = 0; i < n; i++) {
                roots[i] = i;
            }
        }

        public int find(int i) {
            if (roots[i] != i) {
                roots[i] = find(roots[i]);
            }
            return roots[i];
        }

        public void union(int x, int y) {
            int xRoot = find(x);
            int yRoot = find(y);
            if (xRoot != yRoot) {
                if (weights[xRoot] < weights[yRoot]) {
                    roots[xRoot] = yRoot;
                } else if (weights[yRoot] < weights[xRoot]) {
                    roots[yRoot] = xRoot;
                } else {
                    roots[yRoot] = xRoot;
                    weights[xRoot]++;
                }
                groups--;
            }
        }

    }

    public int findCircleNum2(int[][] isConnected) {
        int cities = isConnected.length;
        UnionFind uf = new UnionFind(cities);
        for (int i = 0; i < cities; i++) {
            for (int j = 0; j < cities; j++) {
                if (isConnected[i][j] == 1) {
                    uf.union(i, j);
                }
            }
        }
        return uf.groups;
    }


}
