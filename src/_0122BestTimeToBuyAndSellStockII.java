public class _0122BestTimeToBuyAndSellStockII {
    /**
     * 1. One Pass: Buy at valley, sell at peak
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Best Time to Buy and Sell Stock II.
     * Memory Usage: 37 MB, less than 100.00% of Java online submissions for Best Time to Buy and Sell Stock II.
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }

        int i = 0;
        int valley = prices[0];
        int peak = prices[0];
        int maxProfit = 0;

        while (i < prices.length - 1) {
            while (i < prices.length - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            valley = prices[i];

            while (i < prices.length - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            peak = prices[i];

            maxProfit += peak - valley;
        }

        return maxProfit;
    }

    /**
     * 2. One Pass: Keep adding difference between consecutive numbers if increasing
     *
     * no need for tracking peeks and valleys
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     */
    public int maxProfit2(int[] prices) {
        int maxProfit = 0;

        for (int i = 0; i < prices.length - 1; i++) {
            if (prices[i + 1] > prices[i]) {
                maxProfit += prices[i + 1] - prices[i];
            }
        }

        return maxProfit;
    }


}
