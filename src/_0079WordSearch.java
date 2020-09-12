import javafx.stage.DirectoryChooserBuilder;

/**
 * Avoid using same cell twice:
 * remembering previous step is not enough, you might go back to a old spot by a circle loop
 *
 * Think clear about the main job of back
 *
 * Similar questions:
 * 489 Robot Room Cleaner
 *
 * Template:
 * https://leetcode.com/explore/learn/card/recursion-ii/472/backtracking/
 *
 *
 */
public class _0079WordSearch {
    /**
     * 1. Backtracking
     *
     * explore board in DFS manner
     *
     * Time:
     * n: number off cells
     *
     * I think the overall time complexity is closer to O(N*3^min(L, N)).
     * Each cell has only 3 directions to be potentially explored
     * because one direction has been already visited by its parent.
     * So, the worst case can be expressed by N * 4 * 3^min(L - 1, N - 1) (4 means the beginning point)
     * and by big O, -> O(N * 3^min(L, N))
     *
     * More accurate: O(N * min(3^L, N))
     *
     *
     * Space:
     * visited: O(size of board)
     * recursion call stack: O(len of word)
     *
     */
    private final static int[][] DIRECTION = {{-1,0}, {0, -1}, {1, 0}, {0, 1}};

    public boolean exist(char[][] board, String word) {

        int rows = board.length;
        int cols = board[0].length;

        boolean[][] visited = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (explore(board, visited, word, 0, row, col)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean explore(char[][] board, boolean[][] visited, String word, int idx, int row, int col) {
        // OR we can check length boundary and grid boundary here
//        if (idx == word.length()) {
//            return true;
//        }
//
//        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) {
//            return false;
//        }

        if (!visited[row][col] && board[row][col] == word.charAt(idx)) {
            visited[row][col] = true;

            if (idx == word.length() - 1) {
                return true;
            }

            for (int i = 0; i < DIRECTION.length; i++) {
                int[] nextDirection = DIRECTION[i];
                int nextRow = row + nextDirection[0];
                int nextCol = col + nextDirection[1];
                if (0 <= nextRow && nextRow < board.length && 0 <= nextCol && nextCol < board[0].length) {
                    if (explore(board, visited, word, idx + 1, nextRow, nextCol)) {
                        return true;
                    }
                }
            }

            visited[row][col] = false;
        }

        return false;
    }
}
