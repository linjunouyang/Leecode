/**
 * During Interview, you need to discuss invalid inputs with interviewer
 *
 * e.g.
 *
 * For example, could you get a test case such as [[],[],[],[]]?
 * This would be k = 0 and n = 4.
 * 1) Either you'd be told it could never happen
 * 2) or that needed to do something special for it, such as returning -1.
 *
 * Invalid scenarios:
 * 1) k = 1, n > 1
 * 2) k = 0, n > 0
 */



public class _0265PaintHouseII {
    /**
     * 1. Intuition - Recursion with memorization
     * paint(1, red)
     * painting house 1 red, along with the cost of painting the houses after it
     * (taking into account restrictions caused by painting house 1 red).
     *
     * Disadvantage: re-computation
     *
     */

    /**
     * 2. Dynamic Programming
     *
     *
     * dp[i][j] = min{dp[i-1][h]} + costs[i][j] (h != j)
     *
     * n houses, k colors
     * Time complexity: O(nk)
     * Space complexity: O(k)
     *
     * @param costs
     * @return
     */
    public int minCostII(int[][] costs) {
        if (costs == null || costs.length == 0 || costs[0].length == 0) {
            return 0;
        }

        int houses = costs.length;
        int colors = costs[0].length;

        int[] lastHouse = costs[0];

        for (int i = 1; i < houses; i++) {
            int[] currHouse = new int[colors];

            // find min and second min
            int min = Integer.MAX_VALUE;
            int second_min = Integer.MAX_VALUE;
            for (int p = 0; p < colors; p++) {
                if (lastHouse[p] <= min) {
                    second_min = min;
                    min = lastHouse[p];
                } else if (lastHouse[p] < second_min) {
                    second_min = lastHouse[p];
                }
            }

            // compute paint costs for current house
            for (int j = 0; j < colors; j++) {
                if (lastHouse[j] == min) {
                    currHouse[j] = second_min + costs[i][j];
                } else {
                    currHouse[j] = min + costs[i][j];
                }
            }

            lastHouse = currHouse;
        }

        int ans = Integer.MAX_VALUE;
        for (int i = 0; i < colors; i++) {
            ans = Math.min(ans, lastHouse[i]);
        }

        return ans;
    }


    /**
     * 2. Dynamic Programming: Optimized Time and Space
     * @param costs
     * @return
     */
    public int minCostII2(int[][] costs) {
        if (costs.length == 0) return 0;
        int k = costs[0].length;
        int n = costs.length;


        /* Firstly, we need to determine the 2 lowest costs of
         * the first row. We also need to remember the color of
         * the lowest. */
        int prevMin = -1; int prevSecondMin = -1; int prevMinColor = -1;
        for (int color = 0; color < k; color++) {
            int cost = costs[0][color];
            if (prevMin == -1 || cost < prevMin) {
                prevSecondMin = prevMin;
                prevMinColor = color;
                prevMin = cost;
            } else if (prevSecondMin == -1 || cost < prevSecondMin) {
                prevSecondMin = cost;
            }
        }

        // And now, we need to work our way down, keeping track of the minimums.
        for (int house = 1; house < n; house++) {
            int min = -1; int secondMin = -1; int minColor = -1;
            for (int color = 0; color < k; color++) {
                // Determine the cost for this cell (without writing it in).
                int cost = costs[house][color];
                if (color == prevMinColor) {
                    cost += prevSecondMin;
                } else {
                    cost += prevMin;
                }
                // Determine whether or not this current cost is also a minimum.
                if (min == -1 || cost <= min) {
                    secondMin = min;
                    minColor = color;
                    min = cost;
                } else if (secondMin == -1 || cost < secondMin) {
                    secondMin = cost;
                }
            }
            // Transfer current mins to be previous mins.
            prevMin = min;
            prevSecondMin = secondMin;
            prevMinColor = minColor;
        }

        return prevMin;
    }



}
