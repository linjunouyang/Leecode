public class _0121BestTimeToBuyAndSellStock {
    /**
     * 1. Brute Force
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int max = 0;
        for (int i = 0; i < prices.length; i++) {
            for (int j = i + 1; j < prices.length; j++) {
                max = Math.max(max, prices[j] - prices[i]);
            }
        }

        return max;
    }

    /**
     * 2. Dynamic Programming
     *
     * This is an optimized dynamic programming problem.
     * It doesn't need to have a tabulation to be considered a dynamic programming problem.
     *
     * In this problem what we're doing in each step is to throw out the previous max profits and minimum
     * and just use the recent one exactly like other dynamic programming problems that have O(1) complexity.
     *
     * dp[0][i] = min(dp[0][i-1], prices[i])
     * dp[1][i] = max(dp[1][i-1], prices[i] - dp[0][i])
     * dp[0] tracks the minimum and dp[1] tracks the max profit.
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Best Time to Buy and Sell Stock.
     * Memory Usage: 37.2 MB, less than 100.00% of Java online submissions for Best Time to Buy and Sell Stock.
     *
     * @param prices
     * @return
     */
    public int maxProfit2(int[] prices) {
        int minPrice = Integer.MAX_VALUE;
        int maxProfit = 0;
        for (int i = 0; i < prices.length; i++) {
            minPrice = Math.min(minPrice, prices[i]);
            maxProfit = Math.max(maxProfit, prices[i] - minPrice);
        }
        return maxProfit;
    }

    /**
     * 3. Kadane's Algorithm (solution for modified question)
     *
     *  giving the difference array of prices, Ex: for {1, 7, 4, 11}, the given array is {0, 6, -3, 7}
     *
     *  maxCur: max profit you will get if you sell the stock at day i
     *
     *
     *  i = 1
     *  maxCur = Math.max(0, 0 + 6 - 0) = 6
     *  maxSoFar = Math.max(6, 0) = 6
     *
     *  i = 2
     *  maxCur = Math.max(0, 6 - 3 - 6) = 0
     *  maxSoFar = Math.max(0, 6) = 6
     *
     *  i = 3
     *  maxCur = Math.max(0, 0 + 7 + 3) = 10
     *  maxSoFar = Math.max(10, 6) = 10
     *
     *  Explanation:
     *  https://leetcode.com/problems/best-time-to-buy-and-sell-stock/discuss/39038/Kadane's-Algorithm-Since-no-one-has-mentioned-about-this-so-far-%3A)-(In-case-if-interviewer-twists-the-input)
     *
     *
     * @param prices
     * @return
     */
    public int maxProfit3(int[] prices) {
        // current maximum value
        int maxCur = 0;

        // maximum value found so far
        int maxSoFar = 0;

        for (int i = 1; i < prices.length; i++) {
            // By reseting maxCur to 0, it means that we have found a point i
            // where the price[i] is lower than the time we bought,
            // and that we should then try to buy at point i to see if we can achieve a bigger gain.
            // a[i] - a[i - 1] - a[i - 1] + a[i - 2]
            maxCur = Math.max(0, maxCur + prices[i] - prices[i - 1]);
            maxSoFar = Math.max(maxCur, maxSoFar);
        }

        return maxSoFar;
    }
}
