/**
 * https://www.jiuzhang.com/solution/design-tic-tac-toe/
 * class XXXException extends Exception // a constructor calls super("msg");
 * method signature throws xxxException, xxxException
 * inside a method: throw new XXXException()
 */
public class _0348DesignTicTacToe {
    /**
     * 1. Brute Force
     *
     * Store board as 2D array
     * after each move, check row, col, diagonal, anti-diagonal
     *
     * Time: O(n)
     * Space: O(n^2)
     */

    /**
     * 2.
     *
     * This works under the given assumption: every move() is valid
     */
    public class TicTacToe {
        private int[] rows;
        private int[] cols;
        private int diagonal;
        private int antiDiagonal;

        public TicTacToe(int n) {
            rows = new int[n];
            cols = new int[n];
        }

        public int move(int row, int col, int player) {
            int toAdd = player == 1 ? 1 : -1;

            rows[row] += toAdd;
            cols[col] += toAdd;
            if (row == col) {
                diagonal += toAdd;
            }

            if (col == (cols.length - row - 1)) {
                antiDiagonal += toAdd;
            }

            int size = rows.length;
            if (Math.abs(rows[row]) == size ||
                    Math.abs(cols[col]) == size ||
                    Math.abs(diagonal) == size ||
                    Math.abs(antiDiagonal) == size) {
                return player;
            }

            return 0;
        }
    }
}
