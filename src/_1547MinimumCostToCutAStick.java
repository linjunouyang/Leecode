import java.util.*;

public class _1547MinimumCostToCutAStick {
    /**
     * 1. Top-down DP
     *
     * DFS with memoization
     *
     * References:
     * https://leetcode.com/problems/minimum-cost-to-cut-a-stick/discuss/780900/Java-16-lines-straightforward-dfs%2Bmemo-with-line-by-line-explanation-easy-to-understand
     *
     * n: number of cuts
     * Time: O(n ^ 3)
     * Sorting cuts: O(n * logn)
     * O(n ^ 2) cases * O(n) for each case = O(n ^ 3)
     *
     * Space: O(n ^ 2)
     * Memoization: O(n ^ 2)
     * Recursive call stack: O(n)
     */
    public int minCost(int n, int[] cuts) {
        Arrays.sort(cuts);
        return dfs(0, n, cuts, 0, cuts.length, new HashMap<>());
    }

    // min cost to perform all the cuts [cuts[start], cuts[end]), which are within range stick [l, r]
    public int dfs(int l, int r, int[] cuts, int start, int end,
                   HashMap<String, Integer> stickToMinCost){
        if (start == end) {
            // no cuts
            return 0;
        }
        String stick = l + " " + r;
        if (stickToMinCost.containsKey(stick)) {
            return stickToMinCost.get(stick);
        }

        int minCost = Integer.MAX_VALUE;
        for (int i = start; i < end; i++){
            int left = dfs(l, cuts[i], cuts, start, i, stickToMinCost);
            int right = dfs(cuts[i], r, cuts, i + 1, end, stickToMinCost);
            minCost = Math.min(minCost, r - l + left + right);
        }
        stickToMinCost.put(stick, minCost);
        return minCost;
    }

    /**
     * 2. Bottom-up DP
     *
     * Time: O(n ^ 3)
     * Space: O(n ^ 2)
     */
    public int minCost2(int n, int[] cuts) {
        List<Integer> posList = new ArrayList<Integer>();
        for (int cut : cuts) {
            posList.add(cut);
        }
        posList.add(0);
        posList.add(n);
        Collections.sort(posList);

        int[][] dp = new int[posList.size()][posList.size()];

        // Notice our stick label starts with 0, ends with n
        // [start, start + len] is the range representation
        for (int len = 2; len < n + 2; len++) {
            for (int start = 0; start + len < posList.size(); start++) {
                int end = start + len;
                dp[start][end] = Integer.MAX_VALUE;
                for (int i = start + 1; i < end; i++) {
                    dp[start][end] = Math.min(dp[start][end],
                            posList.get(end) - posList.get(start) + dp[start][i] + dp[i][end]);
                }
            }
        }

        return dp[0][posList.size() - 1];
    }
}
