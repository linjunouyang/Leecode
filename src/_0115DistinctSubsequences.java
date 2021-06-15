import java.util.HashMap;

/**
 * When you see string problem that is about subsequence or matching,
 * dynamic programming method should come to mind naturally.
 *
 * Thinking strategy:
 * 1. Simplified: if there's ONE subsequence in S that equals T
 * Two pointers
 * 2. Back to original: 2^N combinations
 * Recursion + Memoization
 * 3. Iteration
 * 4. Space-optimized Iteration
 *
 * https://leetcode.com/problems/distinct-subsequences/solution/
 */
public class _0115DistinctSubsequences {
    /**
     * 1. Recursion + Memorization
     *
     * https://leetcode.com/problems/distinct-subsequences/solution/
     *
     * Whenever we have choices in a problem, it could be a good idea to fall back on a recursive approach.
     * A recursive solution makes the most sense when a problem can be broken down into subproblems
     * and solutions to subproblems can be used to solve the top level problem.
     * Well, for our problem, a substring is our subproblem because i represents that we have already processed 0...i−1 characters in string S.
     * Similarly, j represents that we have processed 0...j−1 characters in string T.
     *
     * [State / Input]
     * Every recursive approach needs some variables that help define the state of the recursion.
     * In our case, we have been talking about these two indices that will help us iterate over our strings one character at a time.
     * Hence, i and j together will define the state of our recursion.
     *
     * [Return value]
     * The return value is not that hard to figure out really.
     * Given two indices i and j, our function would return the number of distinct subsequences in the substring
     * s[i...M] that equal the substring t[j⋯N] where M and N represent the lengths of the two string respectively.
     *
     * [Base Case]
     * 1) If we exhausted the string S, but there are still characters to be considered in string T,
     * that means we ended up rejecting far too many characters and eventually ran out!
     * Here, we return a 0 because now, there's no possibility of a match.
     * 2) However, if we exhausted the string T, then it means we found a subsequence in S that matches T and hence, we return a 1.
     *
     * [Memoization, caching]
     *
     * Time:
     * Time on a single call: O(1)
     * Number of recursive calls defined by state variables: O(M * N)
     *
     * Space:
     * Dictionary: O(M * N)
     * Recursion Stack: O(M)
     */
    public int numDistinct(String s, String t) {
        HashMap<String, Integer> map = new HashMap<>();
        return numDistinctHelper(map, s, 0, t, 0);
    }

    private int numDistinctHelper(HashMap<String, Integer> map, String s, int sIdx, String t, int tIdx) {
        if (tIdx == t.length()) {
            return 1;
        }
        if (sIdx == s.length() || s.length() - sIdx < t.length() - tIdx) {
            return 0;
        }
        // Or we could use Pair
        String key = sIdx + "," + tIdx;
        if (map.containsKey(key)) {
            return map.get(key);
        }

        int ans = numDistinctHelper(map, s, sIdx + 1, t, tIdx);
        if (s.charAt(sIdx) == t.charAt(tIdx)) {
            ans += numDistinctHelper(map, s, sIdx + 1, t, tIdx + 1);
        }

        map.put(sIdx + "," + tIdx, ans);
        return ans;
    }


    /**
     * 2. DP
     * dp[i][j]:
     * number of ways aligning s[0, i - 1] and t[0, j - 1]
     *
     * Status Transfer:
     * a)
     * When s[i - 1] != t[j - 1],
     * dp[i][j] = dp[i - 1][j]
     *
     * b)
     * When s[i - 1] = t[j - 1], we can choose to match s[i - 1] with t[j - 1] or not
     * 1) Match -> dp[i - 1][j - 1]
     * 2) Ignore s[i - 1] -> dp[i - 1][j]
     * dp[i][j] = dp[i - 1][j] + dp[i - 1][j - 1]
     *
     * Time: O(nm)
     * Space: O(nm) -> could optimized to O(s)
     */
    public int numDistinct2(String s, String t) {
        int sLen = s.length();
        int tLen = t.length();
        int[][] dp = new int[sLen + 1][tLen + 1];

        for (int sIdx = 0; sIdx < sLen; sIdx++) {
            dp[sIdx][0] = 1;
        }

        for (int sIdx = 1; sIdx <= sLen; sIdx++) {
            // originally tIdx <= sIdx
            for (int tIdx = 1; tIdx <= Math.min(sIdx, tLen); tIdx++) {
                dp[sIdx][tIdx] = dp[sIdx - 1][tIdx];
                if (s.charAt(sIdx - 1) == t.charAt(tIdx - 1)) {
                    dp[sIdx][tIdx] += dp[sIdx - 1][tIdx - 1];
                }
            }
        }

        return dp[sLen][tLen];
    }

    /**
     * 3. Space Optimized
     */
    public int numDistinct3(String s, String t) {
        int sLen = s.length(); //3
        int tLen = t.length(); //4
        int[] dp = new int[tLen + 1];//5
        dp[0] = 1;

        for (int sIdx = 1; sIdx <= sLen; sIdx++) {
            int[] dp2 = new int[tLen + 1];
            dp2[0] = 1;
            for (int tIdx = 1; tIdx <= Math.min(sIdx, tLen); tIdx++) {
                dp2[tIdx] = dp[tIdx];
                if (s.charAt(sIdx - 1) == t.charAt(tIdx - 1)) {
                    dp2[tIdx] += dp[tIdx - 1];
                }
            }
            dp = dp2;
        }

        return dp[tLen];
    }

    public int numDistinct4(String s, String t) {
        int sLen = s.length(); //3
        int tLen = t.length(); //4
        int[] dp = new int[tLen + 1];//5
        dp[0] = 1;

        for (int sIdx = 1; sIdx <= sLen; sIdx++) {
            for (int tIdx = Math.min(sIdx, tLen); tIdx >= 1; tIdx--) {
                dp[tIdx] = dp[tIdx];
                if (s.charAt(sIdx - 1) == t.charAt(tIdx - 1)) {
                    dp[tIdx] += dp[tIdx - 1];
                }
            }
        }

        return dp[tLen];
    }
}
