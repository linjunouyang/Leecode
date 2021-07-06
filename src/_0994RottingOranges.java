import java.util.ArrayDeque;
import java.util.Deque;

public class _0994RottingOranges {
    /**
     * 1. BFS
     *
     * direction array
     * visited array or modify on input
     * self-defined Position class or two queues (one for x, the other for y) or ArrayDeque<int[]>
     * isValid checks coordinates
     *
     * Time: O(row * cols)
     * Space: O(row * cols)
     */
    public int orangesRotting(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return -1;
        }

        int[][] directions = new int[][] {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int rows = grid.length;
        int cols = grid[0].length;

        boolean[][] visited = new boolean[rows][cols];
        Deque<Position> queue = new ArrayDeque<>();
        int numFresh = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 2) {
                    visited[row][col] = true;
                    queue.offer(new Position(row, col));
                } else if (grid[row][col] == 1) {
                    numFresh++;
                }
            }
        }

        if (numFresh == 0) {
            // or put numFresh > 0 as a condition for while loop
            return 0;
        }

        int minutes = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                Position pos = queue.poll();
                for (int[] direction : directions) {
                    int nextRow = pos.row + direction[0];
                    int nextCol = pos.col + direction[1];
                    if (isValid(rows, cols, nextRow, nextCol)
                            && !visited[nextRow][nextCol]
                            && grid[nextRow][nextCol] == 1) {
                        visited[nextRow][nextCol] = true;
                        queue.offer(new Position(nextRow, nextCol));
                        numFresh--;
                    }
                }
            }
            minutes++;
        }

        // minutes - 1:
        // we mark last batch of rotten oranges, but we still offer them to the queue
        // even though next iteration (last one) won't find any new positions to push
        // we still incorrectly increment minutes
        return numFresh == 0 ? minutes - 1 : -1;
    }

    class Position {
        int row;
        int col;
        public Position(int r, int c) {
            row = r;
            col = c;
        }
    }

    private boolean areAllRotten(int[][] grid, boolean[][] visited) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[0].length; col++) {
                if (grid[row][col] == 1 && !visited[row][col]) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(int rows, int cols, int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        return true;
    }
}
