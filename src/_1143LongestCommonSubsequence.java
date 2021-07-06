public class _1143LongestCommonSubsequence {
    /**
     * 1. DFS + Memoization
     *
     * Time: O(m * n)
     * Space: O(m * n)
     */
    public int longestCommonSubsequence(String text1, String text2) {
        int[][] cache = new int[text1.length()][text2.length()];
        for (int i = 0; i < text1.length(); i++) {
            for (int j = 0; j < text2.length(); j++) {
                cache[i][j] = -1;
            }
        }
        return match(text1, 0, text2, 0, cache);
    }

    private int match(String text1, int i, String text2, int j, int[][] cache) {
        if (i == text1.length() || j == text2.length()) {
            return 0;
        }
        if (cache[i][j] != -1) {
            return cache[i][j];
        }

        int max = 0;
        if (text1.charAt(i) == text2.charAt(j)) {
            max = 1 + match(text1, i + 1, text2, j + 1, cache);
        } else {
            max = Math.max(match(text1, i + 1, text2, j, cache),
                    match(text1, i, text2, j + 1, cache));
        }
        cache[i][j] = max;
        return max;
    }

    /**
     * 2. Bottom-up DP
     *
     * Time: O(m * n)
     * Space: O(m * n)
     */
    public int longestCommonSubsequence2(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();
        int[][] dp = new int[len1][len2];

        for (int j = 0; j < len2; j++) {
            if (text2.charAt(j) == text1.charAt(0)
                    || (j > 0 && dp[0][j - 1] == 1)) {
                dp[0][j] = 1;
            }
        }

        for (int i = 0; i < len1; i++) {
            if (text1.charAt(i) == text2.charAt(0)
                    || (i > 0 && dp[i - 1][0] == 1)) {
                dp[i][0] = 1;
            }
        }

        for (int i = 1; i < len1; i++) {
            for (int j = 1; j < len2; j++) {
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        return dp[len1 - 1][len2 - 1];
    }

    /**
     * 3. Bottom-up DP (space optimized)
     *
     * Time: O(mn)
     * Space: O(n)
     */
    public int longestCommonSubsequence3(String text1, String text2) {
        int len1 = text1.length();
        int len2 = text2.length();
        int[] dp = new int[len2];

        for (int j = 0; j < len2; j++) {
            if (text2.charAt(j) == text1.charAt(0)
                    || (j > 0 && dp[j - 1] == 1)) {
                dp[j] = 1;
            }
        }

        for (int i = 1; i < len1; i++) {
            int topLeft = dp[0];
            if (text1.charAt(i) == text2.charAt(0)) {
                dp[0] = 1;
            }
            for (int j = 1; j < len2; j++) {
                int prev = dp[j];
                if (text1.charAt(i) == text2.charAt(j)) {
                    dp[j] = topLeft + 1;
                } else {
                    dp[j] = Math.max(dp[j], dp[j - 1]);
                }
                topLeft = prev;
            }
        }

        return dp[len2 - 1];
    }


}
