public class _0935KnightDialer {
    /**
     * 0. Brute Force
     *
     * Start from every num, and explore all neighbors (at least two)
     * -> Choices grow exponentially
     *
     * Draw the function call tree -> repetitive same sub-problems
     */

    /**
     * 1. DFS + Memo
     *
     * If we explore based on MOVES -> TLE (https://leetcode.com/submissions/detail/519412795/)
     * To speed up, simply store the valid next numeric cell for every cell
     * The idea: based on board and valid moves, construct adj lists
     *
     * Be clear about recursive function definition
     *
     * Time: O(n)
     * cache init: O(n)
     * explore: O(n)
     *
     * Space: O(n)
     * recursive stack: O(n)
     * cache: O(n)
     */
    private static final int[][] NEXT_DIGITS = new int[][]{{4, 6}, {8, 6}, {7, 9}, {4, 8},
            {3, 9, 0}, {}, {1, 7, 0}, {2, 6},
            {1, 3}, {2, 4}};

    private static final int DIGITS = 10;
    private static final int MOD = (int)(Math.pow(10, 9) + 7);

    public int knightDialer(int n) {

        int[][] cache = new int[n + 1][10];
        for (int row = 0; row < n + 1; row++) {
            for (int col = 0; col < DIGITS; col++) {
                cache[row][col] = -1;
            }
        }

        int ans = 0;
        for (int digit = 0; digit < DIGITS; digit++) {
            ans += explore(digit, n - 1, cache) % MOD;
            ans %= MOD;
        }

        return ans;
    }

    // given last step 'digit', and leftSteps...
    private int explore(int digit, int leftSteps, int[][] cache) {
        if (leftSteps == 0) {
            return 1;
        }

        if (cache[leftSteps][digit] != -1) {
            return cache[leftSteps][digit];
        }

        int combinations = 0;

        for (int nextDigit : NEXT_DIGITS[digit]) {
            combinations += explore(nextDigit, leftSteps - 1, cache) % MOD; // nextDigit instead of digit
            combinations %= MOD;
        }

        cache[leftSteps][digit] = combinations;
        return combinations;
    }

    /**
     * 2.1 Bottom-up DP
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int knightDialer2(int n) {
        int[][] dp = new int[n + 1][10];

        for (int digit = 0; digit < DIGITS; digit++) {
            dp[1][digit] = 1;
        }

        for (int prevJump = 1; prevJump < n; prevJump++) {
            int currJump = prevJump + 1;
            for (int digit = 0; digit < DIGITS; digit++) {
                for (int nextDigit : NEXT_DIGITS[digit]) {
                    dp[currJump][nextDigit] += dp[prevJump][digit];
                    dp[currJump][nextDigit] %= MOD;
                }
            }
        }

        int res = 0;
        for (int digit = 0; digit < DIGITS; digit++) {
            res += dp[n][digit];
            res %= MOD;
        }

        return res;
    }

    /**
     * 2. Bottom-up DP
     *
     * Time: O(n)
     * Space: O(n) -> O(1)
     */
    public int knightDialer21 (int n) {
        int[] dp = new int[10];

        for (int digit = 0; digit < DIGITS; digit++) {
            dp[digit] = 1;
        }

        for (int prevJump = 1; prevJump < n; prevJump++) {
            int[] dp1 = new int[10];
            for (int digit = 0; digit < DIGITS; digit++) {
                for (int nextDigit : NEXT_DIGITS[digit]) {
                    dp1[nextDigit] += dp[digit];
                    dp1[nextDigit] %= MOD;
                }
            }
            dp = dp1;
        }

        int res = 0;
        for (int digit = 0; digit < DIGITS; digit++) {
            res += dp[digit];
            res %= MOD;
        }

        return res;
    }

    public int knightDialer22(int n) {
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
