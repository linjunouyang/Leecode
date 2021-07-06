import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

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
                    if (isValid(nextRow, rows, nextCol, cols)
                            && !visited[nextRow][nextCol]) {
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
    public int shortestBridge2(int[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        HashSet<Integer> first = new HashSet<>();
        HashSet<Integer> second = new HashSet<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int pos = row * cols + col;
                if (grid[row][col] == 1) {
                    if (first.size() == 0) {
                        exploreIsland(grid, row, col, first);
                    }
                    if (!first.contains(pos)) {
                        exploreIsland(grid, row, col, second);
                    }
                }
            }
        }

        return buildBridge(grid, first, second);
    }

    private void exploreIsland(int[][] grid, int row, int col,
                               HashSet<Integer> island) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        Deque<Integer> queue = new ArrayDeque<>();

        int pos = row * cols + col;
        queue.offer(pos);
        island.add(pos);

        while (!queue.isEmpty()) {
            pos = queue.poll();
            int x = pos / cols;
            int y = pos % cols;
            for (int[] direction : directions) {
                int x1 = x + direction[0];
                int y1 = y + direction[1];
                int pos1 = x1 * cols + y1;
                if (0 <= x1 && x1 < rows && 0 <= y1 && y1 < cols
                        && grid[x1][y1] == 1 && !island.contains(pos1)) {
                    queue.offer(pos1);
                    island.add(pos1);
                }
            }
        }
    }

    private int buildBridge(int[][] grid, HashSet<Integer> first, HashSet<Integer> second) {
        int rows = grid.length;
        int cols = grid[0].length;
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        if (first.size() > second.size()) {
            HashSet<Integer> temp = first;
            first = second;
            second = temp;
        }

        HashSet<Integer> visited = new HashSet<>();
        int steps = 0;

        while (true) {
            HashSet<Integer> next = new HashSet<>(); // can't use next.clear();
            for (int pos : first) {
                int x = pos / cols;
                int y = pos % cols;
                for (int[] dir : directions) {
                    int x1 = x + dir[0];
                    int y1 = y + dir[1];
                    int pos1 = x1 * cols + y1;
                    if (0 <= x1 && x1 < rows && 0 <= y1 && y1 < cols) {
                        if (second.contains(pos1)) {
                            return steps;
                        }
                        if (!visited.contains(pos1)) {
                            visited.add(pos1);
                            next.add(pos1);
                        }
                    }
                }
            }

            if (next.size() > second.size()) {
                first = second;
                second = next;
            } else {
                first = next;
            }

            steps++;
        }
    }

    /**
     * Possible follow-up
     * https://leetcode.com/problems/shortest-bridge/discuss/272996/what-if-there're-more-than-2-islands
     */
}
