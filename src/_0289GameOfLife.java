public class _0289GameOfLife {
    /**
     * 1. Typical Array Iteration
     *
     * Time: O(rows * cols)
     * Space: O(rows * cols)
     */
    public void gameOfLife(int[][] board) {
        if (board == null || board[0] == null) {
            return;
        }
        int rows = board.length;
        int cols = board[0].length;
        int[][] next = new int[rows][cols];

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                int numLiveNeighbors = countLiveNeighbors(board, row, col);
                if (numLiveNeighbors == 3 || (numLiveNeighbors == 2 && board[row][col] == 1)) {
                    next[row][col] = 1;
                } else if (numLiveNeighbors < 2 || numLiveNeighbors > 3) {
                    next[row][col] = 0;
                }
            }
        }

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                board[row][col] = next[row][col];
            }
        }

    }

    private int countLiveNeighbors(int[][] board, int row, int col) {
        int count = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = col - 1; j <= col + 1; j++) {
                if (i == row && j == col) {
                    continue;
                }
                if (0 <= i && i < board.length && 0 <= j && j < board[0].length && board[i][j] == 1) {
                    count++;
                }
            }
        }
        return count;
    }
}
