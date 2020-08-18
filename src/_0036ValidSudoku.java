/**
 * Be careful with subBox number calculation:
 * box_index = (row / 3) * 3 + col / 3;
 */
public class _0036ValidSudoku {
    /**
     * 1. HashTable
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     * @param board
     * @return
     */
    public boolean isValidSudoku(char[][] board) {
        boolean[] rowUsage = new boolean[9];
        boolean[][] colUsage = new boolean[9][9];
        boolean[][] boxUsage = new boolean[3][9];

        for (int row = 0; row < 9; row++) {
            rowUsage = new boolean[9];
            if (row % 3 == 0) {
                boxUsage = new boolean[3][9];
            }

            for (int col = 0; col < 9; col++) {
                if (board[row][col] != '.') {
                    int num = board[row][col] - '0';
                    int boxIdx = (col / 3);
                    if (rowUsage[num - 1] || colUsage[col][num - 1] || boxUsage[boxIdx][num - 1]) {
                        return false;
                    } else {
                        rowUsage[num - 1] = true;
                        colUsage[col][num - 1] = true;
                        boxUsage[boxIdx][num - 1] = true;
                    }
                }
            }
        }

        return true;
    }
}
