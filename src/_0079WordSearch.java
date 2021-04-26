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
 */
public class _0079WordSearch {
    /**
     * 1. Backtracking
     *
     * Time: n: number off cells
     *
     * I think the overall time complexity is closer to O(N*3^min(L, N)).
     * Each cell has only 3 directions to be potentially explored
     * because one direction has been already visited by its parent.
     * So, the worst case can be expressed by N * 4 * 3^min(L - 1, N - 1) (4 means the beginning point)
     * and by big O, -> O(N * 3^min(L, N))
     *
     * More accurate: O(n * min(3^L, n))
     *
     * Space:
     * visited: O(size of board)
     * recursion call stack: O(len of word)
     */
    public boolean exist(char[][] board, String word) {
        int rows = board.length;
        int cols = board[0].length;
        boolean[][] visited = new boolean[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (search(board, visited, row, col, word, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean search(char[][] board, boolean[][] visited, int row, int col,
                           String word, int idx) {
        if (word.charAt(idx) != board[row][col]) {
            return false;
        }

        if (idx == word.length() - 1) {
            return true;
        }

        visited[row][col] = true;

        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        int rows = board.length;
        int cols = board[0].length;

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (isValid(newRow, rows, newCol, cols)
                    && !visited[newRow][newCol]
                    && search(board, visited, newRow, newCol, word, idx + 1)) {
                return true;
            }
        }

        visited[row][col] = false;

        return false;
    }

    private boolean isValid(int row, int rows, int col, int cols) {
        return 0 <= row && row < rows && 0 <= col && col < cols;
    }
}
