public class _0256PaintHouse {
    /**
     * 1. Dynamic Programming
     *
     * p[i][j] = min{dp[i-1][k] +costs[i][j]} (k != j)
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param costs: n x 3 cost matrix
     * @return: An integer, the minimum cost to paint all houses
     */
    public int minCost(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int n = costs.length;

        //dp[i][j]表示第i幢房子涂j的颜色最小的总和
        //初始化状态dp[0][i]=costs[0][i]
        int[][] dp = new int[2][3];

        for (int i= 0; i < 3; i++) {
            dp[0][i] = costs[0][i];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < 3; j++) {
                dp[i&1][j] = Integer.MAX_VALUE;
                for (int k = 0; k < 3; k++) {
                    if (k != j) {
                        dp[i&1][j] = Math.min(dp[i&1][j], dp[i&1 ^ 1][k] + costs[i][j]);
                    }
                }
            }
        }

        return Math.min(dp[n&1^1][0], Math.min(dp[n&1^1][1], dp[n&1^1][2]) );

    }
}
