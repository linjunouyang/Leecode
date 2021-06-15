public class _0072EditDistance {
    /**
     * 0.
     * https://leetcode-cn.com/problems/edit-distance/solution/bao-li-dfs-ji-yi-you-hua-dfs-zhuang-tai-i9rut/
     * https://leetcode.com/problems/edit-distance/discuss/159295/Python-solutions-and-intuition
     *
     * 对于给定的单词 A 和 B，从左向右逐个匹配：
     * 对于 A 中当前遍历到的字符 A[i]，如果和 B[j] 相同，则不需要任何操作，继续向右遍历 i+1, j+1
     * 如果 A[i] != B[j]，则可以删除 A[i]，或者替换 A[i]，或者插入和 B[j] 相同的字符，因此存在三种选项
     * 所以每次遍历匹配时，都存在 4 种可能的逻辑分支，因此可以确定 DFS 能解决问题。先把 DFS 代码写出
     */

    /**
     * 1. Top-down DP
     *
     * Time: O(mn)
     * Space: O(mn)
     */
    public int minDistance(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        } else if (word1 == null) {
            return word2.length();
        } else if (word2 == null) {
            return word1.length();
        }

        int n = word1.length();
        int m = word2.length();
        int[][] memo = new int[n + 1][m + 1];

        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                memo[i][j] = -1;
            }
        }

        return dfs(word1, 0, word2, 0, memo);
    }

    private int dfs(String word1, int i, String word2, int j,
                    int[][] memo) {
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        if (i == word1.length()) {
            memo[i][j] = word2.length() - j;
            return memo[i][j];
        }

        if (j == word2.length()) {
            memo[i][j] = word1.length() - i;
            return memo[i][j];
        }

        if (word1.charAt(i) == word2.charAt(j)) {
            memo[i][j] = dfs(word1, i + 1, word2, j + 1, memo);
            return memo[i][j];
        }

        int replace = 1 + dfs(word1, i + 1, word2, j + 1, memo);
        int insert = 1 + dfs(word1, i + 1, word2, j, memo);
        int delete = 1 + dfs(word1, i, word2, j + 1, memo);

        memo[i][j] = Math.min(replace, Math.min(insert, delete));

        return memo[i][j];
    }

    /**
     * 2. Bottom-up DP
     *
     * intuition:
     * reduce problem to simple ones.
     * One could notice that it seems to be more simple for short words
     * and so it would be logical to relate an edit distance D[n][m] with the lengths n and m of input words.
     *
     * If dp = new int[n][m]
     * it's hard to initialize dp[i][0] and dp[0][j]
     * - no smaller sub-problem solution -> can't use recursion relation
     * - hard to initialize manually as well
     *
     * Time complexity: O(mn)
     * Space complexity: O(mn)
     */
    public int minDistance2(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        }

        int n = word1.length();
        int m = word2.length();

        int[][] dp = new int[n + 1][m + 1];

        // init
        for (int i = 0; i < m + 1; i++) {
            dp[0][i] = i;
        }
        for (int i = 0; i < n + 1; i++) {
            dp[i][0] = i;
        }

        for (int i = 1; i < n + 1; i++) {
            for (int j = 1; j < m + 1; j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) { // remember to minus 1
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(dp[i - 1][j - 1], // replacement
                            Math.min(dp[i - 1][j], // insertion
                                    dp[i][j - 1])); // deletion
                }
            }
        }

        return dp[n][m];
    }

    /**
     * 2.1 Dynamic Programming with Space Optimization
     *
     * x x x x x x
     * x 0 1 2 3 4
     * x 1 ?
     * x 2
     * x 3
     * x 4
     *
     * Notice that for (j-1) col, we need prev and also curr value
     *
     * Time complexity: O(mn)
     * Space complexity: O(m) or O(n)
     *
     */
    public int minDistance21(String word1, String word2) {
        if (word1 == null && word2 == null) {
            return 0;
        }

        int[] dp = new int[word2.length() + 1];

        for (int j = 1; j <= word2.length(); j++) {
            dp[j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            int topLeft = dp[0]; // top-left
            dp[0] = i;        //  init
            for (int j = 1; j <= word2.length(); j++) {
                int top = dp[j];
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[j] = topLeft;
                } else {
                    dp[j] = Math.min(topLeft, // replace
                            Math.min(dp[j - 1], // delete
                                    dp[j])) // insert
                            + 1;
                }

                // top becomes topLeft for next value
                topLeft = top;
            }
        }

        return dp[word2.length()];
    }
}
