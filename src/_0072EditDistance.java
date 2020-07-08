public class _0072EditDistance {
    /**
     * 1. Dynamic Programming
     *
     * Time complexity: O(mn)
     * Space complexity: O(mn)
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        } else if (word2 == null) {
            return word1.length();
        } else if (word1 == null) {
            return word2.length();
        }

        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n+1][m+1];
        for (int i = 0; i < m + 1; i++) {
            dp[0][i] = i;
        }

        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    // replacement, deletion and insertion
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], Math.min(dp[i - 1][j], dp[i][j - 1]));
                }
            }
        }
        return dp[n][m];
    }

    /**
     * 2. Dynamic Programming with Space Optimization
     *
     * x x x x x x
     * x 0 1 2 3 4
     * x 1 ?
     * x 2
     * x 3
     * x 4
     *
     * Time complexity: O(mn)
     * Space complexity: O(m) or O(n)
     *
     * @param word1
     * @param word2
     * @return
     */
    public int minDistance2(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        } else if (word2 == null) {
            return word1.length();
        } else if (word1 == null) {
            return word2.length();
        }

        int[] dp = new int[word2.length() + 1];

        for (int j = 1; j <= word2.length(); j++) {
            dp[j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            int topLeft = dp[0]; // top-left
            dp[0] = i;        // left
            for (int j = 1; j <= word2.length(); j++) {
                int top = dp[j];
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[j] = topLeft;
                } else {
                    // top-left, left, top
                    dp[j] = Math.min(topLeft, Math.min(dp[j - 1], dp[j])) + 1;
                }

                topLeft = top;
            }
        }

        return dp[word2.length()];
    }
}
