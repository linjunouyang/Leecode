public class _0361BombEnemy {
    /**
     * 1. DP-like solution
     *
     * Brute Force: redundant calculations
     *
     * Reduce redundant calculations.
     * Only need to recount if there's a wall
     *
     * Time: O(rows * cols)
     * Space: O(cols)
     */
    public int maxKilledEnemies(char[][] grid) {
        int rows = grid.length;
        int cols = grid[0].length;

        int rowHits = 0;
        int[] colHits = new int[cols];
        int maxHits = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {

                // ||, not &&
                if (col == 0 || grid[row][col - 1] == 'W') {
                    rowHits = 0;
                    for (int k = col; k < cols; k++) {
                        if (grid[row][k] == 'W') {
                            break;
                        } else if (grid[row][k] == 'E') {
                            rowHits++;
                        }
                    }
                }

                // row == 0: initialize counting process
                if (row == 0 || grid[row - 1][col] == 'W') {
                    colHits[col] = 0;
                    for (int k = row; k < rows; k++) {
                        if (grid[k][col] == 'W') {
                            break;
                        } else if (grid[k][col] == 'E') {
                            colHits[col]++;
                        }
                    }
                }

                if (grid[row][col] == '0') {
                    maxHits = Math.max(maxHits, rowHits + colHits[col]);
                }
            }
        }

        return maxHits;
    }
}
