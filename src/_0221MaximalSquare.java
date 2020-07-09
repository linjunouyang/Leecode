public class _0221MaximalSquare {

    // 1 0 1 0 0
    // 1 0 1 1 1
    // 1 1 1 1 1

    // 1 1 0 1 0

    // -> 1 0 1 0 0
    // -> 1 0

    /**
     * 1. Dynamic Programming
     *
     * dp(i,j): the side length of the maximum square
     * whose **bottom right corner is (i,j)**
     *
     * how to get rid of branch (row == 0 || col == 0)
     * dp = new int[rows + 1][cols + 1] and start iteration with i = 1, j = 1.
     *
     * Time complexity: O(mn)
     * Space complexity: O(mn)
     *
     * @param matrix
     * @return
     */
    public int maximalSquare(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];
        int maxLength = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (row == 0 || col == 0) {
                    // char -> int
                    dp[row][col] = matrix[row][col] - '0';
                } else if (matrix[row][col] == '1') { // char '1'
                    int min = Math.min(dp[row - 1][col - 1], dp[row - 1][col]);
                    min = Math.min(min, dp[row][col - 1]);
                    dp[row][col] = min + 1;
                }
                maxLength = Math.max(maxLength, dp[row][col]);
            }
        }

        return maxLength * maxLength;
    }


    /**
     * 2. Dynamic Programming with optimized space
     *
     * Time complexity: O(mn)
     * Space complexity: O(n)
     *
     * @param matrix
     * @return
     */
    public int maximalSquare2(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int[] dp = new int[cols + 1];
        int maxsqlen = 0;
        for (int i = 1; i <= rows; i++) {
            int prev = 0;
            for (int j = 1; j <= cols; j++) {
                int temp = dp[j];
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j - 1], prev), dp[j]) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp;
            }
        }
        return maxsqlen * maxsqlen;
    }
}
