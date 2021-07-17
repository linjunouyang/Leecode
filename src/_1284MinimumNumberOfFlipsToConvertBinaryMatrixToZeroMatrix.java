import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Queue;
import java.util.Set;

/**
 * https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/discuss/446342/JavaPython-3-Convert-matrix-to-int%3A-BFS-and-DFS-codes-w-explanation-comments-and-analysis.
 */
public class _1284MinimumNumberOfFlipsToConvertBinaryMatrixToZeroMatrix {

    /**
     * 1. BFS
     *
     * Time: O(rows * cols * 2^(rows * cols))
     * Space: O(rows * cols * 2^(rows * cols))
     */
    public int minFlips(int[][] mat) {
        int rows = mat.length;
        int cols = mat[0].length;

        int start = 0;

        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                start |= mat[row][col] << (row * cols + col);
            }
        }

        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(start);

        Set<Integer> states = new HashSet<>();
        states.add(start);

        final int[][] directions = new int[][]{{0, 0}, {-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int step = 0; !queue.isEmpty(); ++step) {
            for (int size = queue.size(); size > 0; --size) {
                int cur = queue.poll();
                if (cur == 0) {
                    return step;
                }

                for (int row = 0; row < rows; ++row) {
                    for (int col = 0; col < cols; ++col) {
                        int next = cur;

                        // flip
                        for (int[] direction : directions) {
                            int row1 = row + direction[0];
                            int col1 = col + direction[1];
                            if (row1 >= 0 && row1 < rows && col1 >= 0 && col1 < cols) {
                                next ^= 1 << (row1 * cols + col1); // exclusive or
                            }
                        }

                        if (states.add(next))  {
                            queue.offer(next);
                        }
                    }
                }
            }
        }

        return -1;
    }

    /**
     * 2. DFS:
     * https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/discuss/446371/Java-Recursion-%2B-Memoization-Explained
     * HashMap remember answers (applicable to a certain board configuration regardless where we start)
     * HashSet remember CURRENT recursive path (Avoid revisiting, not GLOBAL visited)
     *
     * https://leetcode.com/problems/minimum-number-of-flips-to-convert-binary-matrix-to-zero-matrix/discuss/446342/JavaPython-3-Convert-matrix-to-int%3A-BFS-and-DFS-codes-w-explanation-comments-and-analysis.
     */
}
