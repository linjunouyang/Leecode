import java.util.ArrayDeque;
import java.util.Deque;

public class _0934ShortestBridge {
    /**
     * 1. BFS / DFS
     *
     * BFS/DFS find all 1st island points
     * BFS from all 1st island points to 2nd island points
     *
     * Time: O(mn)
     * Space: O(mn)
     */
    public int shortestBridge(int[][] A) {
        int rows = A.length;
        int cols = A[0].length;

        // find first island points
        boolean foundFirstIsland = false;
        boolean[][] visited = new boolean[rows][cols];
        Deque<int[]> queue = new ArrayDeque<>();

        for (int row = 0; row < rows && !foundFirstIsland; row++) {
            for (int col = 0; col < cols && !foundFirstIsland; col++) {
                if (A[row][col] == 1) {
                    findIslandPoints(A, row, col, visited, queue);
                    foundFirstIsland = true;
                }
            }
        }

        // start from all 1st island points, bfs 2nd island points
        int distance = 0;
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                int[] point = queue.poll(); // visit

                for (int[] direction : directions) {
                    int nextRow = point[0] + direction[0];
                    int nextCol = point[1] + direction[1];
                    if (isValid(nextRow, rows, nextCol, cols) && !visited[nextRow][nextCol]) {
                        if (A[nextRow][nextCol] == 1) {
                            return distance;
                        }

                        queue.offer(new int[]{nextRow, nextCol});
                        visited[nextRow][nextCol] = true;
                    }
                }
            }

            distance++;
        }

        return -1;
    }

    private void findIslandPoints(int[][] A, int row, int col,
                                  boolean[][] visited,
                                  Deque<int[]> queue) {
        int rows = A.length;
        int cols = A[0].length;
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};


        Deque<int[]> queue2 = new ArrayDeque<>();
        // in-sync
        queue2.offer(new int[]{row, col});
        visited[row][col] = true;

        while (!queue2.isEmpty()) {
            int[] point = queue2.poll();
            // visit
            queue.offer(point);

            for (int[] direction : directions) {
                int nextRow = point[0] + direction[0];
                int nextCol = point[1] + direction[1];
                if (isValid(nextRow, rows, nextCol, cols) && A[nextRow][nextCol] == 1
                        && !visited[nextRow][nextCol]) {
                    // in-sync
                    queue2.offer(new int[]{nextRow, nextCol});
                    visited[nextRow][nextCol] = true;
                }
            }
        }
    }

    private boolean isValid(int row, int rows, int col, int cols) {
        return 0 <= row && row < rows && 0 <= col && col < cols;
    }


    /**
     * 1.2 Bi-direction BFS
     * https://leetcode.com/problems/shortest-bridge/discuss/189235/Java-Bidirectional-BFS
     */

    /**
     * Possible follow-up
     * https://leetcode.com/problems/shortest-bridge/discuss/272996/what-if-there're-more-than-2-islands
     */
}
