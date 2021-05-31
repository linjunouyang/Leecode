import java.util.HashMap;

public class _1278PalindromePartitioningIII {
    /**
     * 1. Top-down DP
     *
     * Time:
     * cache initialization: O(s len * s len * k)
     * calculation: O(len * len * k) (number of problems) * O(len * len) (time for each problem)
     *
     * Space: O(s len * s len * k)
     * cache: O(s len * s len * k)
     * recursion call stack: O(k)
     */
    public int palindromePartition(String s, int k) {
        int len = s.length();
        int[][][] cache = new int[len][len][k + 1];

        for (int start = 0; start < len; start++){
            for (int end = 0; end < len; end++){
                for (int parts = 0; parts < k + 1; parts++) {
                    cache[start][end][parts] = -1;
                }
            }
        }

        return divide(s, 0, s.length() - 1, k, cache);
    }

    private int divide(String s, int start, int end,
                       int parts, int[][][] cache) {
        if (cache[start][end][parts] != -1) {
            return cache[start][end][parts];
        }

        if (parts == 1) {
            return getCost(s, start, end);
        }

        int lastPos = end - parts + 1;
        int minCost = Integer.MAX_VALUE;
        for (int i = start; i <= lastPos; i++) {
            int cost = getCost(s, start, i);
            cost += divide(s, i + 1, end, parts - 1, cache);
            minCost = Math.min(minCost, cost);
        }
        cache[start][end][parts] = minCost;
        return minCost;
    }

    private int getCost(String s, int start, int end) {
        int cost = 0;
        while (start < end) {
            if (s.charAt(start) != s.charAt(end)) {
                cost++;
            }
            start++;
            end--;
        }
        return cost;
    }

    /**
     * 1.1 3D -> 2D:
     */

    /**
     * 2. Bottom-up DP
     *
     *
     */
    public int palindromePartition2(String s, int k) {
        int len = s.length();
        int[][][] dp = new int[len][len][k + 1];

        for (int l = 2; l <= len; l++) {
            for (int start = 0; start + l - 1 < len; start++) {
                int end = start + l - 1;
                dp[start][end][1] = getCost(s, start, end);
            }
        }


        for (int l = 2; l <= len; l++) {
            for (int start = 0; start + l - 1 < len; start++) {
                int end = start + l - 1;

                for (int parts = 2; parts <= k; parts++) {
                    dp[start][end][parts] = Integer.MAX_VALUE;

                    for (int part1End = start;
                         part1End + parts - 1 <= end;
                         part1End++) {
                        int cost = getCost(s, start, part1End);
                        cost += dp[part1End + 1][end][parts - 1];
                        dp[start][end][parts] = Math.min(dp[start][end][parts],
                                cost);
                    }
                }
            }
        }

        return dp[0][len - 1][k];
    }

    /**
     * 2.1 Optimized bottom-up
     *
     * DP: 3D-> 2D
     * dp[i][j]: min cost of breaking first i chars in S into j palindromes (i >= j)
     * dp[i][j] = min(dp[i0][j - 1] + cost(S, i0 + 1, i)) (i0 >= j - 1)
     *
     * Notice that getCost(s, start, end) will be called O(n^2 * k) times
     * but cost only has O(n^2) sets of parameters -> re-computation -> cache
     *
     * Time: O(n^2 * k)
     * cost pre-computation: O(n ^ 2)
     * dp: enumerate i, j and i0 -> O(n^2 * k)
     *
     * Space: O(n ^ 2)
     * cost: O(n ^ 2)
     * dp: O(n * k) (k <= n)
     */
    public int palindromePartition21(String s, int k) {
        int len = s.length();
        int[][] changes = new int[len][len];
        int[][] dp = new int[len][k + 1];

        for (int span = 2; span <= len; span++) {
            for (int start = 0; start <= len - span; start++) {
                int end = start + span - 1;
                int cost = (s.charAt(start) == s.charAt(end)) ? 0 : 1;
                changes[start][end] = changes[start + 1][end - 1] + cost;
            }
        }

        for (int end = 0; end < s.length(); end++) {
            dp[end][1] = changes[0][end];
        }

        for (int parts = 2; parts <= k; parts++) {
            for (int lastPartEnd = parts - 1; lastPartEnd < s.length(); lastPartEnd++) {
                dp[lastPartEnd][parts] = Integer.MAX_VALUE;
                for (int end = lastPartEnd - 1; end >= parts - 2; end--) {
                    dp[lastPartEnd][parts] = Math.min(dp[lastPartEnd][parts],
                            dp[end][parts - 1] + changes[end + 1][lastPartEnd]);
                }
            }
        }

        return dp[len - 1][k];
    }

}

