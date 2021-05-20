import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class _0695MaxAreaOfIsland {
    /**
     * 1. BFS
     * <p>
     * BFS is used for finding the shortest path in a graph, because it starts from a vertex and "radiates" outside from it, like a wave. DFS can find a path which may not necessarily be the shortest.
     * <p>
     * In a directed acyclic graph (DAG), DFS finds the timestamp of vertex discovery and can be used to order vertices (example - topological sort).
     * For a DAG like a tree, assuming the tree is fairly balanced, DFS is preferred because it requires less space (O(lgn)) than BFS (O(n)).
     * <p>
     * Time complexity: O(rows * cols)
     * Space complexity: O(rows * cols)
     *
     * @param grid
     * @return
     */
    public int maxAreaOfIsland(int[][] grid) {
        if (grid == null || grid[0] == null) {
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        boolean[][] visited = new boolean[rows][cols];
        int maxArea = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 1 && !visited[row][col]) {
                    maxArea = Math.max(maxArea, bfs(grid, row, col, visited));
                }
            }
        }

        return maxArea;
    }

    private int bfs(int[][] grid, int row, int col, boolean[][] visited) {
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int rows = grid.length;
        int cols = grid[0].length;

        Deque<int[]> queue = new ArrayDeque<>();
        visited[row][col] = true;
        int area = 1;
        queue.offer(new int[]{row, col});

        while (!queue.isEmpty()) {
            int[] pos = queue.poll();
            for (int[] direction : directions) {
                int nextX = pos[0] + direction[0];
                int nextY = pos[1] + direction[1];
                if (0 <= nextX && nextX < rows && 0 <= nextY && nextY < cols
                        && grid[nextX][nextY] == 1 && !visited[nextX][nextY]) {
                    visited[nextX][nextY] = true;
                    area++;
                    queue.offer(new int[]{nextX, nextY});
                }
            }
        }

        return area;
    }

    /**
     * 2. DFS
     */

    /**
     * 3. Union and Find
     *
     * Time: O(rows * cols)
     * Space: O(rows * cols)
     */
    public int maxAreaOfIsland3(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }
        int rows = grid.length;
        int cols = grid[0].length;
        int max = 0;
        UnionFind uf = new UnionFind(rows * cols);
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 1) {
                    max = Math.max(max, link(grid, row, col, uf));
                }
            }
        }
        return max;
    }


    private int link(int[][] grid, int row, int col, UnionFind uf) {
        int rows = grid.length;
        int cols = grid[0].length;
        int pre = row * cols + col;
        int res = 1;
        int[][] DIRECTIONS = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] dir: DIRECTIONS) {
            int x = row + dir[0];
            int y = col + dir[1];
            if (x < 0 || y < 0 || x >= rows || y >= cols || grid[x][y] != 1) {
                continue;
            }
            res = uf.union(pre, x * cols + y);
        }
        return res;
    }

    class UnionFind {
        int[] parent;
        int[] size;
        int[] rank;

        UnionFind(int n) {
            parent = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
            }
            rank = new int[n];
            size = new int[n];
            Arrays.fill(this.size, 1);
        }

        int find(int x) {
            if (parent[x] != x) {
                parent[x] = find(this.parent[x]);
            }
            return parent[x];
        }

        int union(int x, int y) {
            int px = find(x);
            int py = find(y);

            if (px == py) {
                return this.size[px];
            }
            if (rank[px] > rank[py]) {
                parent[py] = px;
                size[px] += this.size[py];
                return size[px];
            } else if (rank[px] < rank[py]) {
                parent[px] = py;
                size[py] = size[px] + size[py];
                return size[py];
            } else {
                parent[px] = py;
                rank[py]++;
                size[py] = size[px] + size[py];
                return size[py];
            }
        }

        int getSize(int x) {
            return size[find(x)];
        }
    }
}

