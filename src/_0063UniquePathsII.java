public class _0063UniquePathsII {
    /**
     * 1. Dynamic Programming
     *
     * Time complexity: O(mn)
     * Space complexity: O(mn)
     *
     * @param obstacleGrid
     * @return
     */
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }

        int rows = obstacleGrid.length;
        int cols = obstacleGrid[0].length;

        int[][] paths = new int[rows][cols];

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (obstacleGrid[row][col] == 1) {
                    paths[row][col] = 0;
                } else if (row == 0) {
                    paths[row][col] = col - 1 >= 0 ? paths[row][col - 1] : 1;
                } else if (col == 0) {
                    paths[row][col] = row - 1 >= 0 ? paths[row - 1][col] : 1;
                } else {
                    paths[row][col] = paths[row - 1][col] + paths[row][col -1];
                }
            }
        }

        return paths[rows - 1][cols - 1];
    }

    /**
     * 2. Dynamic Programming with Optimization
     *
     * Time complexity: O(mn)
     * Space complexity: O(n)
     *
     * @param obstacleGrid
     * @return
     */

    public int uniquePathsWithObstacles3(int[][] obstacleGrid) {
        if (obstacleGrid == null || obstacleGrid.length == 0 || obstacleGrid[0].length == 0) {
            return 0;
        }
        int width = obstacleGrid[0].length;
        int[] dp = new int[width];
        dp[0] = 1;
        for (int[] row : obstacleGrid) {
            for (int j = 0; j < width; j++) {
                if (row[j] == 1)
                    dp[j] = 0;
                else if (j > 0)
                    dp[j] += dp[j - 1];
            }
        }
        return dp[width - 1];
    }


}
