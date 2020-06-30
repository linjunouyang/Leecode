public class _0332CoinChange {

    /**
     * 1. Dynamic Programming - top down
     *
     * https://leetcode.com/problems/coin-change/solution/
     *
     * n: number of coins
     *
     * Time complexity: O(S * n)
     *
     * Prune impossible branches
     * cache calculated subproblems
     *
     * Space complexity: O(n)
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange(int[] coins, int amount) {
        if(amount<1) return 0;
        return helper(coins, amount, new int[amount]);
    }

    private int helper(int[] coins, int rem, int[] count) {
        // rem: remaining coins after the last step;
        // count[rem]: minimum number of coins to sum up to rem
        if(rem<0) return -1; // not valid
        if(rem==0) return 0; // completed
        if(count[rem-1] != 0) return count[rem-1]; // already computed, so reuse
        int min = Integer.MAX_VALUE;
        for(int coin : coins) {
            int res = helper(coins, rem-coin, count);
            if(res>=0 && res < min)
                min = 1+res;
        }
        count[rem-1] = (min==Integer.MAX_VALUE) ? -1 : min;
        return count[rem-1];
    }

    /**
     * 2. Dynamic Programming - bottom up
     *
     * think of the last step we take
     *
     * In this method, we don't re-compute same problems over and over again
     *
     * potential improvement: sort coins arrayy
     *
     *
     * n: amount, m: number of coin value
     * Time complexity: O(mn)
     * Space complexity: O(n)
     *
     * @param coins
     * @param amount
     * @return
     */
    public int coinChange2(int[] coins, int amount) {

        int[] dp = new int[amount + 1];

        for (int i = 1; i <= amount; i++) {

            dp[i] = amount + 1;

            for (int j = 0; j < coins.length; j++) {

                if (i >= coins[j]) {

                    dp[i] = Integer.min(dp[i], dp[i - coins[j]] + 1);

                }

            }

        }

        return dp[amount] == amount + 1 ? -1 : dp[amount];
    }
}
