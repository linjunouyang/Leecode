public class _0935KnightDialer {
    /**
     * 0. Brute Force
     *
     * Start from every num, and explore all neighbors (at least two)
     * -> Choices grow exponentially
     *
     * Draw the function call tree -> repetitive same subproblems
     */

    /**
     * 1. Bottom-up DP
     *
     * Time: O(n)
     * Space: O(n) -> O(1)
     */
    public int knightDialer(int n) {
        int[][] adjLists = new int[][]{{4,6},{6,8},{7,9},{4,8},{3,9,0},
                {},{1,7,0},{2,6},{1,3},{2,4}};

        long[][] dp = new long[10][2];
        for (int num = 0; num <= 9; num++) {
            dp[num][0] = 1;
        }

        for (int len = 2; len <= n; len++) {
            int prevCol = len & 1;
            int currCol = ~len & 1;
            for (int num = 0; num <= 9; num++) {
                dp[num][currCol] = 0;
            }
            for (int num = 0; num <= 9; num++) {
                for (int nextNum : adjLists[num]) {
                    dp[nextNum][currCol] += dp[num][prevCol];
                    dp[nextNum][currCol] %= (1e9 + 7);
                }
            }
        }

        long res = 0;
        int col = ~n & 1;
        for (int num = 0; num <= 9; num++) {
            res += dp[num][col];
        }

        return (int) (res % (1e9 + 7));
    }
}
