public class _0064MinimumPathSum {
    /**
     * 1. Dynamic Programming with Space optimization (2D -> 1D)
     *
     * Another way to define DP array:
     * dp(i, j): the min sum of the path from (i,j) to the bottom rightmost.
     *
     * m * n grid
     * Time complexity: O(mn)
     * Space complexity: O(n)
     *
     * @param grid
     * @return
     */
    public int minPathSum(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0].length == 0) {
            return 0;
        }

        int rows = grid.length;
        int cols = grid[0].length;
        int[] sums = new int[cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row != 0 && col != 0) {
                    sums[col] = Math.min(sums[col], sums[col - 1])
                            + grid[row][col];
                } else if (row == 0 && col != 0) {
                    sums[col] = sums[col - 1] + grid[row][col];
                } else if (row != 0 && col == 0) {
                    sums[col] = sums[col] + grid[row][col];
                } else {
                    sums[col] = grid[0][0];
                }

            }
        }

        return sums[cols - 1];
    }

    /**
     * 2. dp with printing path
     * https://www.jiuzhang.com/solution/minimum-path-sum/
     */
}
