import java.util.ArrayDeque;
import java.util.Queue;

public class _1162AsFarFromLandAsPossible {
    /**
     * 1. BFS
     *
     * Intuitively, start from every water, find out it's nearest land
     * traverse starting water spot: O(n^2)
     * find out nearest land: O(n^2)
     * total: O(n^4)
     *
     * and exploring water
     * Put all lands into queue and exploring water
     * Why all lands?
     * because we are finding the distance between water and it [nearest] land
     *
     * Time: O(n^n)
     * Space: O(n)
     *
     */
    public int maxDistance(int[][] grid) {
        int n = grid.length;
        boolean[][] visited = new boolean[n][n];
        Queue<int[]> q = new ArrayDeque<>();
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (grid[row][col] == 1) {
                    visited[row][col] = true;
                    q.offer(new int[]{row, col});
                }
            }
        }

        int[][] dirs = new int[][]{{1, 0}, {0, 1}, {-1, 0}, {0, -1}};
        int result = -1;
        int level = 0;
        while (!q.isEmpty()) {
            int size = q.size();
            level++;
            for (int i = 0; i < size; i++) {
                int[] cur = q.poll();
                for (int[] dir : dirs) {
                    int x = cur[0] + dir[0];
                    int y = cur[1] + dir[1];
                    if (x >= 0 && x < n && y >= 0 && y < n && !visited[x][y]) {
                        visited[x][y] = true;
                        result = level;
                        q.offer(new int[]{x, y});
                    }
                }
            }
        }

        return result;
    }

    /**
     * 2. DP
     *
     * https://leetcode.com/problems/as-far-from-land-as-possible/discuss/422691/7ms-DP-solution-with-example-beats-100
     *
     * Time: O(n^2)
     * Space: O(1)
     */
    public int maxDistance2(int[][] grid) {
        int n = grid.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] == 1) {
                    continue;
                }
                grid[i][j] = 201; //201 here cuz as the despription, the size won't exceed 100.
                if (i > 0) {
                    grid[i][j] = Math.min(grid[i][j], grid[i-1][j] + 1);
                }
                if (j > 0) {
                    grid[i][j] = Math.min(grid[i][j], grid[i][j-1] + 1);
                }
            }
        }

        for (int i = n-1; i > -1; i--) {
            for (int j = n-1; j > -1; j--) {
                if (grid[i][j] == 1) {
                    continue;
                }
                if (i < n-1) {
                    grid[i][j] = Math.min(grid[i][j], grid[i+1][j] + 1);
                }
                if (j < n-1) {
                    grid[i][j] = Math.min(grid[i][j], grid[i][j+1] + 1);
                }
                res = Math.max(res, grid[i][j]); //update the maximum
            }
        }

        return res == 201 ? -1 : res - 1;
    }
}
