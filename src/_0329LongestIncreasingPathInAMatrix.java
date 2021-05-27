import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Matrix -> Map
 * Each cell is a vertex
 * Edge: If two adjacent cells have value a < b, i.e. increasing then we have a directed edge (a, b).
 *
 * Usually, in DFS or BFS, we can employ a set visited to prevent the cells from duplicate visits.
 * -> here because the path is increasing, we will never visit a node with smaller value.
 *
 * for most of this kind of questions that we could not add memorization upon a DFS.
 * Normally when you could move to 4 directions, there would be cycle so you could not memorize the result.
 * However since this question is strictly increasing, thus it is a DAG.
 */
public class _0329LongestIncreasingPathInAMatrix {
    /**
     * 1. Naive DFS
     *
     * Time: O(mn * 2^mn)
     * Space: O(mn)
     */

    /**
     * 2. DFS with memoization
     *
     * Time: O(mn)
     * Space: O(mn)
     */
    public int longestIncreasingPath(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] cache = new int[rows][cols];
        int max = 1;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int res = dfs(matrix, row, col, cache);
                max = Math.max(max, res);
            }
        }

        return max;
    }

    private int dfs(int[][] matrix, int row, int col, int[][] cache) {
        if (cache[row][col] != 0) {
            return cache[row][col];
        }

        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int[] direction : directions) {
            int row1 = row + direction[0];
            int col1 = col + direction[1];
            if (0 <= row1 && row1 < rows && 0 <= col1 && col1 < cols
                    && matrix[row][col] < matrix[row1][col1]) {
                cache[row][col] = Math.max(cache[row][col], dfs(matrix, row1, col1, cache));
            }
        }

        cache[row][col]++;

        return cache[row][col];
    }

    /**
     * 3. BFS / Topological Sort
     *
     * Time: O(mn)
     * Space: O(mn)
     */
    public int longestIncreasingPath2(int[][] matrix) {
        if (matrix.length == 0) {
            return 0;
        }

        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int rows = matrix.length;
        int cols = matrix[0].length;

        // in-degree[i][j] indicates thee number of adjacent cells smaller than matrix[i][j]
        int[][] indegree = new int[rows][cols];
        getInDegree(matrix, indegree);

        // Add each cell with in-degree zero to the queue
        Deque<int[]> queue = new ArrayDeque<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (indegree[row][col] == 0) {
                    queue.offer(new int[]{row, col});
                }
            }
        }

        // BFS implements the Topological Sort
        int length = 0; // The longest path so far
        while(!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                int row = pos[0];
                int col = pos[1];
                for (int[] dir: dirs) {
                    int row1 = row + dir[0];
                    int col1 = col + dir[1];
                    if (row1 >= 0 && row1 < rows && col1 >= 0 && col1 < cols
                            && matrix[row1][col1] > matrix[row][col]
                            && --indegree[row1][col1] == 0) {
                        queue.offer(new int[]{row1, col1});
                    }
                }
            }
            length++;
        }

        return length;
    }

    private void getInDegree(int[][] matrix, int[][] indegree) {
        int[][] dirs = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
        int rows = matrix.length;
        int cols = matrix[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                for (int[] dir: dirs) {
                    int row1 = row + dir[0];
                    int col1 = col + dir[1];
                    if (row1 >= 0 && row1 < rows && col1 >= 0 && col1 < cols
                            && matrix[row1][col1] > matrix[row][col]) {
                        indegree[row1][col1]++;
                    }
                }
            }
        }
    }
}
