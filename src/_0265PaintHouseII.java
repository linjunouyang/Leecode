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
     * 1. Recursion with memorization
     *
     * Time: O(nk^2)
     * Space: O(nk)
     */
    public int minCostII1(int[][] costs) {
        int houses = costs.length;
        int colors = costs[0].length;
        // if cost too big, use long
        int[][] cache = new int[houses][colors];
        int min = Integer.MAX_VALUE;
        for (int color = 0; color < colors; color++) {
            min = Math.min(min, paintUntil(costs, houses - 1, color, cache));
        }
        return min;
    }

    private int paintUntil(int[][] costs, int house, int color, int[][] cache) {
        if (house == 0) {
            cache[0][color] = costs[0][color];
            return cache[0][color];
        }

        if (cache[house][color] != 0) {
            // bc The problem says costs entry > 0 -> non 0 means computed before
            return cache[house][color];
        }

        int colors = costs[0].length;
        int min = Integer.MAX_VALUE;
        for (int prevColor = 0; prevColor < colors; prevColor++) {
            if (prevColor == color) {
                continue;
            }
            min = Math.min(min, paintUntil(costs, house - 1, prevColor, cache));
        }

        cache[house][color] = costs[house][color] + min;

        return cache[house][color];
    }

    /**
     * 2. Bottom-up DP
     *
     * Time: O(nk^2)
     * Space: O(nk)
     */
    public int minCostII2(int[][] costs) {
        int houses = costs.length;
        int colors = costs[0].length;
        int[][] cache = new int[houses][colors];

        // init
        for (int color = 0; color < colors; color++) {
            cache[0][color] = costs[0][color];
        }

        for (int house = 1; house < houses; house++) {
            for (int color = 0; color < colors; color++) {
                cache[house][color] = Integer.MAX_VALUE;
                for (int prevColor = 0; prevColor < colors; prevColor++) {
                    if (prevColor == color) {
                        continue;
                    }
                    cache[house][color] = Math.min(cache[house][color],
                            cache[house - 1][prevColor]);
                }
                cache[house][color] += costs[house][color];
            }
        }

        int min = Integer.MAX_VALUE;
        for (int color = 0; color < colors; color++) {
            min = Math.min(min, cache[houses - 1][color]);
        }

        return min;
    }

    /**
     * 2. Bottom-up DP (Space optimized)
     *
     * n houses, k colors
     * Time complexity: O(n k^2)
     * Space complexity: O(k)
     */
    public int minCostII3(int[][] costs) {
        int houses = costs.length;
        int colors = costs[0].length;
        int[][] cache = new int[2][colors];

        // init
        for (int color = 0; color < colors; color++) {
            cache[0][color] = costs[0][color];
        }

        for (int house = 1; house < houses; house++) {
            int row = house % 2;
            int prevRow = (house - 1) % 2;
            for (int color = 0; color < colors; color++) {
                cache[row][color] = Integer.MAX_VALUE;
                for (int prevColor = 0; prevColor < colors; prevColor++) {
                    if (prevColor == color) {
                        continue;
                    }
                    cache[row][color] = Math.min(cache[row][color],
                            cache[prevRow][prevColor]);
                }
                cache[row][color] += costs[house][color];
            }
        }

        int min = Integer.MAX_VALUE;
        int lastRow = (houses - 1) % 2;
        for (int color = 0; color < colors; color++) {
            min = Math.min(min, cache[lastRow][color]);
        }

        return min;
    }


    /**
     * 4. Bottom-up Dynamic Programming: Optimized Time and Space
     *
     * keep track of minCost, minColor, secondMinCost
     *
     * Time: O(nk)
     * Space: O(nk)
     */
    public int minCostII4(int[][] costs) {
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
