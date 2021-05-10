import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * https://www.1point3acres.com/bbs/thread-754519-1-1.html
 *
 * shortest path in an undirected unweighted graph: BFS
 *
 * Follow up:
 * Suppose every key has a weight, how to get all keys with min sum of weights?
 * -> shortest path in an undirected weighted graph: Dijkstra
 *
 */
public class _0864ShortestPathToGetAllKeys {
    class State {
        int keys;
        int row;
        int col;

        public State(int keys, int row, int col) {
            this.keys = keys;
            this.row = row;
            this.col = col;
        }
    }

    // O(rows * cols * 2^keys)
    public int shortestPathAllKeys(String[] grid) {
        int[] res = scanGrid(grid);
        return bfs(grid, res[0], res[1], res[2]);
    }

    private int[] scanGrid(String[] grid) {
        int rows = grid.length;
        int cols = grid[0].length();
        int[] res = new int[3];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char c = grid[row].charAt(col);
                if (c == '@') {
                    res[1] = row;
                    res[2] = col;
                } else if (Character.isLowerCase(c)) {
                    res[0] = Math.max(c - 'a' + 1, res[0]);
                }
            }
        }

        return res;
    }

    private int bfs(String[] grid, int totalKeys, int startRow, int startCol) {
        int rows = grid.length;
        int cols = grid[0].length();

        State start = new State(0, startRow, startCol);

        Deque<State> queue = new ArrayDeque<>();
        queue.offer(start);

        HashSet<String> visited = new HashSet<>();
        visited.add(0 + "," + startRow + "," + startCol);

        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();

            for (int i = 0; i < size; i++) {
                State state = queue.poll();

                if (state.keys == (1 << totalKeys) - 1) {
                    return steps;
                }


                for (int[] direction : directions) {
                    int keys = state.keys;
                    int row = state.row + direction[0];
                    int col = state.col + direction[1];
                    if (row < 0 || row >= rows || col < 0 || col >= cols) {
                        continue;
                    }

                    char nextChar = grid[row].charAt(col);

                    if (nextChar == '#') {
                        continue;
                    }
                    if (Character.isUpperCase(nextChar) && (keys & (1 << (nextChar - 'a'))) == 0 ) {
                        continue;
                    }
                    if (Character.isLowerCase(nextChar)) {
                        keys |= 1 << (nextChar - 'a');
                    }

                    String stateStr = keys + "," + row + "," + col;
                    if (!visited.contains(stateStr)) {
                        visited.add(stateStr);
                        queue.offer(new State(keys, row, col));
                    }
                }
            }

            steps++;
        }

        return -1;
    }
}
