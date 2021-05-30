public class _0546RemoveBoxes {
    /**
     * 1. Brute Force : backtracking
     *
     * try remove every same-color sequence for current configuration
     *
     * Time: O(n!)
     * Space: O(n ^ 2)
     */

    /**
     * https://leetcode.com/problems/remove-boxes/discuss/101310/Java-top-down-and-bottom-up-DP-solutions
     *
     * Natural thought:
     * T(i, j) as the maximum points one can get by removing boxes of the subarray boxes[i, j] (both inclusive)
     * Recurrence relation exploration:
     * Take the first box boxes[i] as an example. What are the possible ways of removing it?
     * 1. 1 + T(i + 1, j)
     * 2. you might get more points if this box (boxes[i]) can be removed together with other boxes of the same color.
     *
     * Let's say boxes[i] = boxes[m], and we got T(i + 1, m - 1) so far
     * the boxes we left: the one at index i (boxes[i]) and those of the subarray boxes[m, j].
     * there is no way applying the definition of the sub-problem to the subarray boxes[m, j],
     * since we have some extra piece of information that is not included in the definition.
     * The definition of the sub-problem is not self-contained and its solution relies on information external.
     *
     * Problems without self-contained sub-problems usually don't have well-defined recurrence relations,
     * which renders it impossible to be solved recursively.
     * Solution: modify the definition of the problem to absorb the external information so that the new one is self-contained.
     *
     *
     * [t0,... ......., t1, 0, 1, ....... k, boxed[i], boxed[i+1]
     * ------------------  ----------- ---- --------  ----------
     * boxed[i+1]'s color  boxed[i]'s color
     * This couldn't happen
     * because T(i, j, k) definitions restricts that we only care about the scenarios
     * "where all the boxes to the left of boxes[i] can only have the same color as boxes[i]".
     *
     * Because for the subarray boxes[i, j], boxes[i] is its leftmost box.
     * There cannot be any other boxes to its left except for those of the same color attached in previous steps.
     * When boxes[i] is removed, all the attached boxes to its left will also get removed.
     * Therefore the rest boxes are boxes[i+1, j], with NO boxes on the left -> dp[i+1][j][0].
     *
     */

    /**
     * 2. Top-down dp
     *
     * (note that dfs is a particular algorithm that is normally relevant for trees/graphs)
     * dfs with memorization is an example of Top-down dp.
     * There are examples of Top-down dp that do not use dfs
     * e.g. climbing chairs
     *
     * Time: O(n ^ 4)
     * Space: O(n ^ 3)
     */
    public int removeBoxes(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];
        return removeHelper(boxes, 0, n - 1, 0, dp);
    }

    private int removeHelper(int[] boxes, int start, int end, int leftSameNum, int[][][] dp) {
        if (start > end) {
            return 0;
        }

        if (dp[start][end][leftSameNum] > 0) {
            return dp[start][end][leftSameNum];
        }

        int lastSame = start;
        int sameNum = leftSameNum + 1;
        while (lastSame + 1 <= end && boxes[lastSame + 1] == boxes[start]) {
            // // optimization: all boxes of the same color counted continuously from the ith box should be grouped together
            //            // some sub-problems (nextI in this range) can be ignored
            lastSame++;
            sameNum++;
        }

        int maxPoints = sameNum * sameNum + removeHelper(boxes, lastSame + 1, end, 0, dp);

        for (int i = lastSame + 2; i <= end; i++) {
            if (boxes[i] == boxes[start]) {
                maxPoints = Math.max(maxPoints,
                        removeHelper(boxes, lastSame + 1, i - 1, 0, dp)
                                + removeHelper(boxes, i, end, sameNum, dp));
            }
        }

        dp[start][end][leftSameNum] = maxPoints;
        return maxPoints;
    }

    /**
     * 3. Bottom-up:
     *
     * Though theoretically the time complexities of the top-down and bottom-up DP solutions are the same (O(n^4)), in practice the top-down approach would usually perform better as it can skip some intermediate cases that would otherwise require explicit computation for the bottom-up case.
     *
     * Time: O(n ^ 4)
     * Space: O(n ^ 3)
     */
    public int removeBoxes3(int[] boxes) {
        int n = boxes.length;
        int[][][] dp = new int[n][n][n];

        for (int start = 0; start < n; start++) {
            for (int leftSameNum = 0; leftSameNum <= start; leftSameNum++) {
                dp[start][start][leftSameNum] = (leftSameNum + 1) * (leftSameNum + 1);
            }
        }

        for (int len = 2; len <= n; len++) {
            for (int start = 0; start + len -  1 < n; start++) {
                int end = start + len - 1;

                for (int leftSameNum = 0; leftSameNum <= start; leftSameNum++) {
                    int res = (leftSameNum + 1) * (leftSameNum + 1)
                            + dp[start + 1][end][0];

                    for (int i = start + 1; i <= end; i++) {
                        if (boxes[i] == boxes[start]) {
                            res = Math.max(res,
                                    dp[start + 1][i - 1][0]
                                            + dp[i][end][leftSameNum + 1]);
                        }
                    }

                    dp[start][end][leftSameNum] = res;
                }
            }
        }

        return dp[0][n - 1][0];
    }
}
