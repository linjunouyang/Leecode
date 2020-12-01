import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Unbounded (unlimited coins) Knapsack problem
 * https://leetcode.com/problems/coin-change-2/discuss/141076/Unbounded-Knapsack
 * https://leetcode.com/problems/coin-change-2/discuss/176706/Beginner-Mistake%3A-Why-an-inner-loop-for-coins-doensn't-work-Java-Soln
 *
 * The main idea is to define our DP properly for our requirement. Then ordering of loop and all will be dictated based on DP definition.
 *
 * If ordering is important ( set of values with same sum, but different permutation are considered different)
 *
 * DP[i][j] would be, number of ways to get sum j, considering ALL the element AND having i as last element. ( Finally we just need to sum all values of DP[i][target])
 * We find solution for a cur sum, using smaller sum solutions. ( This defines the ordering of loops )
 * If ordering is not important
 *
 * DP[i][j] would be, number of ways to get sum j, considering only element UNTIL i. The element i can be present or not.
 * We find solution for a cur set of numbers, using a solution for smaller set of numbers. ( This defines the ordering of loops now )
 * still not clear? think the implication of definition of DP for counting permutations:
 *
 * in case 1, for final answer we do sum of all values of DP[i][target]. i,e we consider all ordered set of values that give same sum of target, but have different last element. This happens even at the smaller sum result we will now reuse, and hence we get all permutations.
 * in case 2, the the final answer is simply combination of (a) number of sets having last element (b) number of sets not having last element. We only consider presence or absence, we are not considering ordering as separate counts. Hence this definition does not give permutations count.
 */
public class _0518CoinChange2 {
    /**
     * 1. DFS + Memoization (top-down dp)
     *
     * Time:
     * Space:
     */
    public int change(int amount, int[] coins) {
        Arrays.sort(coins);
        Map<Integer, Map<Integer, Integer>> map = new HashMap<>();
        // or Integer[][] map = new Integer[amount + 1][coins.length + 1];
        return backtrack(amount, coins, 0, map);
    }

    private int backtrack(int amount, int[] coins, int curIdx, Map<Integer, Map<Integer, Integer>> map) {
        if (amount != 0 && (curIdx == coins.length || coins[curIdx] > amount)) {
            return 0;
        } else if (amount == 0) {
            return 1;
        }

        if (map.containsKey(amount) && map.get(amount).containsKey(curIdx)) {
            return map.get(amount).get(curIdx);
        }

        int ans =  backtrack(amount - coins[curIdx], coins, curIdx, map) + backtrack(amount, coins, curIdx + 1, map);
        map.putIfAbsent(amount, new HashMap<>());
        map.get(amount).put(curIdx, ans);

        return ans;
    }

    /**
     * 2. Bottom-up DP
     *
     * dp[i][j] :
     * the number of combinations to make up amount j by using the first i types of coins
     *
     * dp[i][j] =
     * 1. not using the ith coin, only using the first i-1 coins to make up amount j, then we have dp[i-1][j] ways.
     * 2. using the ith coin: dp[i][j-coins[i-1]]
     * (after considering using first i-th coins, the number of combinations that sum up j - coins[i - 1])
     *
     * Initialization: dp[i][0] = 1
     * how many ways we have to use first i coins to make up the amount of 0 , so it's clear that we can have 1 way that we use none of them.
     *
     * m: amount, n: types of coins
     * Time: O(mn)
     * Space: O(mn)
     */
    public int change2(int amount, int[] coins) {
        int[][] dp = new int[coins.length+1][amount+1];
        dp[0][0] = 1;

        for (int i = 1; i <= coins.length; i++) {
            dp[i][0] = 1;
            for (int j = 1; j <= amount; j++) {
                dp[i][j] = dp[i-1][j] + (j >= coins[i-1] ? dp[i][j-coins[i-1]] : 0);
            }
        }
        return dp[coins.length][amount];
    }

    /**
     * Well, the real reason why the answer changes because of loops is because of the change in dp definitio when you try to reduce the space complexity.
     * If we define dp[i][j] as "number of ways to get sum 'j' using 'first i' coins", then the answer doesn't change because of loop arrangement.
     *
     * So why does the answer change only when we try to reduce the space complexity?
     * To get the correct answer, the correct dp definition should be dp[i][j]="number of ways to get sum 'j' using 'first i' coins".
     * a) Now when we try to traverse the 2-d array row-wise by keeping only previous row array(to reduce space complexity),
     * we preserve the above dp definition as dp[j]="number of ways to get sum 'j' using 'previous /first i coins' "
     * b) but when we try to traverse the 2-d array column-wise by keeping only the previous column,
     * the meaning of dp array changes to dp[j]="number of ways to get sum 'j' using 'all' coins".
     *
     * In the below code you can see that if we are not interested in reducing the space complexity, both the loop arrangements yield the same answer.
     * -------
     * When looping coins first, we are always appending a new coin type (or not) to previous combination of coins(for a smaller target number),
     * since previous combination is always unique, appending a new type of coins will make the combination still unique.
     * We are always considering the different coin type in next loop.
     *
     * When looping amount first, it will happen the following scenario:
     * If we want to get amount of 8, we can append 3 and append 5, also we can append 5 and append 3 to achieve it.
     * In this way, we use the two coin(3 and 5) in different way, but the same number (one for each).
     * We will keep considering the previous type of coin when the amount is increasing.
     * -------
     * If outer loop is amount, you are considering every coin at every stage.
     * Let's start with amount 2: it can be made from 2 and 1 + 1, so 2 combinations. For amount 3 you would consider every coin again, which would mean that you're trying dp[amount - 1] and dp[amount - 2], which is: 2 (as there are 2 combinations for amount 2) and 1 (1 combination for amount 1).
     * So in this case you have 3 combinations for amount 3:
     * 1 + 2 - taken from dp[amount - 2]
     * 2 + 1, 1 + 1 + 1 - taken from dp[amount - 1]
     * You can see is one duplicate: 1 + 2 and 2 + 1
     *
     * If outer loop is coins, you are NOT considering every coing at every stage
     * Let's assume you've already calculated all dps for coin with value 1. So for every amount there is just one combination, dp array looks like that: [1, 1, 1, 1, 1...]
     * Now we are doing all calculations with value 2. We are at amount 2, so again, amount 2 has 2 combinations: 1 + 1 and 2. Makes sense, no duplicates.
     * For amount 3 you are NOT considering every coin again - you are just considering ending every combination with 2, so ONLY dp[amount - 2].
     * That would make only two combinations for amount 3:
     * 1 + 1 + 1 - taken as previous value of dp[3], calculated for coin value 1
     * 1 + 2 - taken from dp[amount - 2]
     *
     * Time: O(mn)
     * Space: O(m)
     */
    public int change3(int amount, int[] coins) {
        int[] dp = new int[amount + 1];
        dp[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amount; i++) {
                dp[i] += dp[i-coin];
            }
        }
        return dp[amount];
    }



}
