/**
 * TO-DO:
 * Need more understanding.
 *
 */
public class _0123BestTimeToBuyAndSellStockIII {

    /**
     * 1. Dynamic Programming
     *
     * Suppose in real life, you have bought and sold a stock and made $100 dollar profit.
     * When you want to purchase a stock which costs you $300 dollars, you can say I have made $100 profit,
     * so I think this $300 dollar stock is worth $200 FOR ME since I have hold $100 for free.
     * This is how we calculate twoBuy! just minimize the cost again! The twoBuyTwoSell is just making as much profit as possible.
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param prices
     * @return
     */
    public int maxProfit(int[] prices) {
        int oneBuyOneSell = 0;
        int twoBuyTwoSell = 0;
        int oneBuy = Integer.MAX_VALUE;
        int twoBuy = Integer.MAX_VALUE;

        for (int price : prices) {
            oneBuy = Math.min(oneBuy, price);
            oneBuyOneSell = Math.max(oneBuyOneSell, price - oneBuy);
            twoBuy = Math.min(twoBuy, price - oneBuyOneSell);
            twoBuyTwoSell = Math.max(twoBuyTwoSell, price - twoBuy);
        }

        return twoBuyTwoSell;
    }
}
