import java.util.ArrayDeque;
import java.util.Queue;

public class _0097InterleavingString {
    /**
     * 1. Recursion with Memoization (DFS)
     *
     * When thinking in recursion, just focus on current single step
     * put all the remaining work to subsequent recursive calls
     *
     * Without Memoization, time complexity: O(2 ^ (m + n))
     * Recursion with memo is kind of top-down dp?
     *
     * To solve this problem, let's look at if s1[0 ~ i] s2[0 ~ j] can be interleaved to s3[0 ~ k].
     *
     * Start from indices0, 0, 0 and compare s1[i] == s3[k] or s2[j] == s3[k]
     * Return valid only if either i or j match k and the remaining is also valid
     * Caching is the key to performance. This is very similar to top down dp
     * Only need to cache invalid[i][j] since most of the case s1[0 ~ i] and s2[0 ~ j] does not form s3[0 ~ k]. Also tested caching valid[i][j] the run time is also 1ms
     * Many guys use substring but it's duplicate code since substring itself is checking char by char. We are already doing so
     *
     * Time complexity: O(mn) every (i, j) computed once
     * Space complexity: O(mn)
     */
    public boolean isInterleave5(String s1, String s2, String s3) {
        int m = s1.length();
        int n = s2.length();
        if(m + n != s3.length()) {
            return false;
        }
        return dfs(s1, 0,  s2, 0, s3, new boolean[m + 1][n + 1]);
    }

    public boolean dfs(String s1, int i, String s2, int j, String s3, boolean[][] invalid) {
        if (invalid[i][j]) {
            return false;
        }
        int k = i + j;
        if (k == s3.length()) {
            return true;
        }
        boolean valid =
                i < s1.length() && s1.charAt(i) == s3.charAt(k) && dfs(s1, i + 1, s2, j, s3, invalid) ||
                        j < s2.length() && s2.charAt(j) == s3.charAt(k) && dfs(s1, i, s2, j + 1, s3, invalid);
        if (!valid) {
            invalid[i][j] = true;
        }
        return valid;
    }

    /**
     * 3. DP (bottom up)
     *
     * dp[i][j]: whether s3's first (i + j) chars is the interleaving of s1's first i chars and s2's first j chars
     * dp[i][j] = (dp[i - 1][j] && s3[i + j - 1] == s1[i-1]) || (dp[i][j - 1] && s2[j - 1] == s3[i + j - 1])
     *
     * Time complexity: O(s1 len * s2 len)
     * Space complexity: O(s1 len * s2 len)
     */
    public boolean isInterleave3(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        int n1 = s1.length();
        int n2 = s2.length();
        boolean[][] dp = new boolean[n1 + 1][n2 + 1];
        for (int i = 0; i <= n1; i++) {
            for (int j = 0; j <= n2; j++) {
                if (i == 0 && j == 0) {
                    // s1 empty, s2 empty
                    dp[i][j] = true;
                } else {
                    dp[i][j] = (i > 0 && dp[i - 1][j] && s1.charAt(i - 1) == s3.charAt(i + j - 1))
                            || (j > 0 && dp[i][j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
                }
            }
        }
        return dp[n1][n2];
    }

    /**
     * Space optimization
     *
     * Further: choose the min between s1 and s2
     */
    public boolean isInterleave31(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) return false;
        int n1 = s1.length(), n2 = s2.length();
        boolean[] dp = new boolean[n2 + 1];
        for (int i = 0; i <= n1; i++) {
            for (int j = 0; j <= n2; j++) {
                if (i == 0 && j == 0) { // s1 empty, s2 empty
                    dp[j] = true;
                } else {
                    dp[j] = (i > 0 && dp[j] && s1.charAt(i - 1) == s3.charAt(i + j - 1)) || (j > 0 && dp[j - 1] && s2.charAt(j - 1) == s3.charAt(i + j - 1));
                }
            }
        }
        return dp[n2];
    }


    /**
     * My initial idea
     */
    public boolean isInterleave32(String s1, String s2, String s3) {
        // 2 * len1
        // 2 * len2
        // dp[i][j]: whether we can form s3 first ith chars by using s1 first jth chars
        // dp[i][j] = dp[i - 1][j - 1] && s1.charAt(j) = s3.charAt(i)
        // || dp[i - 1][j] && s2.charAt(i - j) = s3.charAt(i);
        // dp[n][s1.length()] = true;
        // i - j
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }

        boolean[][] dp = new boolean[s3.length() + 1][s1.length() + 1];
        dp[0][0] = true;

        for (int i = 1; i <= s3.length() && i <= s2.length(); i++) {
            dp[i][0] = s2.charAt(i - 1) == s3.charAt(i - 1);
        }

        for (int i = 1; i <= s3.length(); i++) {
            for (int j = 1; j <= i && j <= s1.length(); j++) {
                boolean fromS1 = fromS1 = dp[i - 1][j - 1] && s1.charAt(j - 1) == s3.charAt(i - 1);

                boolean fromS2 = false;
                if (i - j - 1 < s2.length()) {
                    fromS2 = dp[i - 1][j] && s2.charAt(i - j - 1) == s3.charAt(i - 1);
                }
                dp[i][j] = fromS1 || fromS2;
            }
        }

        return dp[s3.length()][s1.length()];
    }

    /**
     * 4. BFS
     *
     * Say s1 = "aab" and s2 = "abc". s3 = "aaabcb". Then the board looks like
     *
     * o--a--o--b--o--c--o
     * |     |     |     |
     * a     a     a     a
     * |     |     |     |
     * o--a--o--b--o--c--o
     * |     |     |     |
     * a     a     a     a
     * |     |     |     |
     * o--a--o--b--o--c--o
     * |     |     |     |
     * b     b     b     b
     * |     |     |     |
     * o--a--o--b--o--c--o
     *
     * Each "o" is a cell in the board. We start from the top-left corner, and try to move right or down.
     * If the next char in s3 matches the edge connecting the next cell, then we're able to move.
     * When we hit the bottom-right corner, this means s3 can be represented by interleaving s1 and s2.
     * One possible path for this example is indicated with "x"es below:
     *
     * x--a--x--b--o--c--o
     * |     |     |     |
     * a     a     a     a
     * |     |     |     |
     * o--a--x--b--o--c--o
     * |     |     |     |
     * a     a     a     a
     * |     |     |     |
     * o--a--x--b--x--c--x
     * |     |     |     |
     * b     b     b     b
     * |     |     |     |
     * o--a--o--b--o--c--x
     *
     *
     * Time complexity: O(mn?)
     * Space complexity: O(mn)
     */
    public boolean isInterleave4(String s1, String s2, String s3) {
        if (s1.length() + s2.length() != s3.length()) {
            return false;
        }
        boolean[][] visited = new boolean[s1.length() + 1][s2.length() + 1];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.offer(new int[]{0, 0});
        while (!queue.isEmpty()) {
            int[] p = queue.poll();
            if (visited[p[0]][p[1]]) {
                continue;
            }
            if (p[0] == s1.length() && p[1] == s2.length()) {
                return true;
            }
            if (p[0] < s1.length() && s1.charAt(p[0]) == s3.charAt(p[0] + p[1]))  {
                queue.offer(new int[]{p[0] + 1, p[1]});
            }
            if (p[1] < s2.length() && s2.charAt(p[1]) == s3.charAt(p[0] + p[1])) {
                queue.offer(new int[]{p[0], p[1] + 1});
            }
            visited[p[0]][p[1]] = true;
        }
        return false;
    }
}
