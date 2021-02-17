import java.util.ArrayDeque;
import java.util.Deque;

public class _0286WallsAndGates {
    /**
     * 1. BFS from start
     *
     * BFS from every empty room
     *
     * Time: O(m^2 n^2)
     * Space: O(mn)
     */

    /**
     * 2. BFS from ends
     *
     * Start BFS from ALL gates at the same time
     * distance update from Integer.MAX_VALUE to other values act as marking visited
     *
     * Time complexity : O(mn)O(mn).
     *
     * If you are having difficulty to derive the time complexity, start simple.
     *
     * Let us start with the case with only one gate. The breadth-first search takes at most m×n steps to reach all rooms,
     * therefore the time complexity is O(mn).
     * But what if you are doing breadth-first search from k gates?
     *
     * Once we set a room's distance, we are basically marking it as visited,
     * which means each room is visited at most once.
     * Therefore, the time complexity does not depend on the number of gates and is O(mn)O.
     *
     * Space complexity : O(mn).
     * The space complexity depends on the queue's size. We insert at most m×n points into the queue.
     */
    public void wallsAndGates(int[][] rooms) {
        int rows = rooms.length;
        int cols = rooms[0].length;

        Deque<int[]> queue = new ArrayDeque<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (rooms[row][col] == 0) {
                    queue.offer(new int[]{row, col});
                }
            }
        }

        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int distance = 1;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] pos = queue.poll();
                // if we update distance here, we need to choose min(rooms, distance)
                // because on the same level
                // a far gate (e.g. distance =3) might add this pos to queue at a early time,
                // and a closer updates its value at current level (e.g. distance = 2)
                int row = pos[0];
                int col = pos[1];
                for (int[] direction : directions) {
                    int newRow = row + direction[0];
                    int newCol = col + direction[1];
                    if (isValid(newRow, rows, newCol, cols)
                            && rooms[newRow][newCol] == Integer.MAX_VALUE) {
                        rooms[newRow][newCol] = distance;
                        queue.offer(new int[]{newRow, newCol});
                    }
                }
            }
            distance++;
        }
    }

    private boolean isValid(int row, int rows, int col, int cols) {
        return 0 <= row && row < rows && 0 <= col && col < cols;
    }

    /**
     * 3.  DFS
     *
     */
}
