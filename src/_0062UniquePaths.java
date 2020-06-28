/**
 * https://www.jiuzhang.com/solution/unique-paths/#tag-highlight-lang-java
 * There is also a math solution, but I don't like it.
 */
public class _0062UniquePaths {
    /**
     * 1. Dynamic Programming
     *
     * Time complexity: O(mn)
     * Space complexity: O(mn)
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths(int m, int n) {
        if (m <= 1 || n <= 1) {
            return 1;
        }

        int[][] dp = new int[n][m];
        for (int i = 0; i < m; i++) {
            dp[0][i] = 1;
        }

        for (int i = 0; i < n; i++) {
            dp[i][0] = 1;
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                // or put initialization here
                // if (i == 0 || j == 0) dp[i][j] = 1
                dp[j][i] = dp[j - 1][i] + dp[j][i - 1];
            }
        }

        return dp[n - 1][m - 1];
    }

    /**
     * 2. Dynamic Programming - Optimization by reusing 1D array
     *
     * Time complexity: O(mn)
     * Space complexity: O(n)
     *
     * @param m
     * @param n
     * @return
     */
    public int uniquePaths2(int m, int n) {
        if (m <= 1 || n <= 1) {
            return 1;
        }

        int[] dp = new int[m];
        dp[0] = 1;

        for (int i = 0; i < n; i++) {
            for (int j = 1; j < m; j++) {
                dp[j] = dp[j] + dp[j - 1];
            }
        }

        return dp[m - 1];
    }

}
