public class _0265PaintHouseII {
    /**
     * 1. Dynamic Programming
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
}
