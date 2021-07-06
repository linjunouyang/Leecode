public class _0256PaintHouse {
    /**
     * 0. Brute Force
     *
     * generate every valid permutation of house colors
     * (or all permutations and then remove all the invalid ones, e.g. ones that have 2 red houses side-by-side)
     * and score them
     *
     * t's not worth worrying about how you'd implement the brute force solution—it's completely infeasible and useless in practice.
     * Additionally, the latter approaches move in a different direction, and the permutation code actually takes some effort to understand
     * You wouldn't be writing code for it in an interview either, instead you'd simply describe a possible approach and move onto optimizing,
     * and then write code for a more optimal algorithm.
     *
     * Time complexity : O(2^n) or O(3^n)
     *
     * Without writing code, we can get a good idea of the cost. We know that at the very least, we'd have to process every valid permutation. The number of valid permutations doubles with every house added. With 4 houses, there were 24 permutations. If we add another house, then all of our permutations for 4 houses could be extended with 2 different colors for the 5th house, giving 48 permutations. Because it doubles every time, this is O(n^2)O(n
     * 2
     * It'd be even worse if we generated all permutations of 0, 1, and 2 and then pruned out the invalid ones. There are O(n^3)O(n
     * 3
     *  ) such permutations in total.
     *
     * Space complexity : Anywhere from O(n) to O(n * 3^n)
     *
     * This would depend entirely on the implementation.
     * If you generated all the permutations at the same time and put them in a massive list,
     * then you'd be using O(n * 2^n)O(n∗2 n) or O(n * 3^n)O(n∗3 ) space.
     * If you generated one, processed it, generated the next, processed it, etc, without keeping the long list,
     * it'd require O(n) space.
     *
     *
     * More explanation: https://leetcode.com/problems/paint-house/solution/
     */

    /**
     * 1. Recursion with Memoization
     */
    private static final int COLORS = 3;
    private static final int RED = 0;
    private static final int BLUE = 1;
    private static final int GREEN = 2;

    public int minCost(int[][] costs) {
        int houses = costs.length;
        // the min total cost to paint all houses starting from 'house' (with 'house' painted as 'color')
        int[][] cache = new int[houses][COLORS];

        return Math.min(paint(0, RED, costs, cache),
                Math.min(paint(0, GREEN, costs, cache),
                        paint(0, BLUE, costs, cache)));
    }

    // paint 'house' with 'color', what's the min total cost for painting houses starting from 'house'
    private int paint(int houseIdx, int color, int[][] costs, int[][] cache) {
        int houses = costs.length;
        if (houseIdx == houses) {
            return 0;
        }
        if (cache[houseIdx][color] != 0) {
            return cache[houseIdx][color];
        }

        int minCost = Integer.MAX_VALUE;
        for (int nextColor = RED; nextColor <= GREEN; nextColor++) {
            if (color == nextColor) {
                continue;
            }
            int cost = costs[houseIdx][color] + paint(houseIdx + 1, nextColor, costs, cache);
            minCost = Math.min(minCost, cost);
        }

        cache[houseIdx][color] = minCost;

        return minCost;
    }

    /**
     * 2. Dynamic Programming
     *
     * p[i][j] = min{dp[i-1][k] +costs[i][j]} (k != j)
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public int minCost21(int[][] costs) {
        int houses = costs.length;
        int[][] dp = new int[houses][COLORS];

        for (int color = RED; color <= GREEN; color++) {
            dp[houses - 1][color] = costs[houses - 1][color];
        }

        for (int house = houses - 2; house >= 0; house--) {
            for (int color = RED; color <= GREEN; color++) {
                dp[house][color] = Integer.MAX_VALUE;
                for (int nextColor = RED; nextColor <= GREEN; nextColor++) {
                    if (nextColor == color) {
                        continue;
                    }
                    dp[house][color] = Math.min(dp[house][color], dp[house + 1][nextColor]);
                }
                dp[house][color] += costs[house][color];
            }
        }

        return Math.min(dp[0][RED],
                Math.min(dp[0][GREEN],
                        dp[0][BLUE]));
    }




    public int minCost2(int[][] costs) {
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
                dp[i & 1][j] = Integer.MAX_VALUE;
                for (int k = 0; k < 3; k++) {
                    if (k != j) {
                        dp[i & 1][j] = Math.min(dp[i & 1][j],
                                dp[i & 1 ^ 1][k] + costs[i][j]);
                    }
                }
            }
        }

        return Math.min(dp[n & 1 ^ 1][0],
                Math.min(dp[n & 1 ^ 1][1],
                        dp[n & 1 ^ 1][2]));

//        int[] previousRow = costs[costs.length -1];
//
//        for (int n = costs.length - 2; n >= 0; n--) {
//
//            int[] currentRow = costs[n].clone();
//            // Total cost of painting the nth house red.
//            currentRow[0] += Math.min(previousRow[1], previousRow[2]);
//            // Total cost of painting the nth house green.
//            currentRow[1] += Math.min(previousRow[0], previousRow[2]);
//            // Total cost of painting the nth house blue.
//            currentRow[2] += Math.min(previousRow[0], previousRow[1]);
//            previousRow = currentRow;
//        }
//
//        return Math.min(Math.min(previousRow[0], previousRow[1]), previousRow[2]);

    }
}
