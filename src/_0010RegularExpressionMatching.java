import java.util.HashMap;

/**
 * By RE convention, the star applies before a match so .* can match any sequence of characters.
 *
 * [TO-READ]
 * https://leetcode.com/problems/regular-expression-matching/discuss/5847/Evolve-from-brute-force-to-dp
 * From brute force to dp
 */
public class _0010RegularExpressionMatching {
    /**
     * 1. Top-down DP
     *
     * Time: O(mn)
     * Space: O(mn)
     */
    public boolean isMatch1(String s, String p) {
        HashMap<String, Boolean> cache = new HashMap<>();
        return matchHelper(s, 0, p, 0, cache);
    }

    private boolean matchHelper(String s, int i, String p, int j,
                                HashMap<String, Boolean> cache) {
        if (j == p.length()) {
            return i == s.length();
        }

        String key = i + "," + j;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        boolean firstMatch = i < s.length() &&
                (p.charAt(j) == '.' || s.charAt(i) == p.charAt(j));

        boolean res = false;
        if (j + 1 < p.length() && p.charAt(j + 1) == '*') {
            // second char is '*'
            res = matchHelper(s, i, p, j + 2, cache) || // not match
                    (firstMatch && matchHelper(s, i + 1, p, j, cache)); // match
        } else {
            // second char is not '*'
            res = firstMatch && matchHelper(s, i + 1, p, j + 1, cache); // 1-1 match
        }

        cache.put(key, res);
        return res;
    }

    /**
     * 2. DP
     *
     * boolean[][] dp = new boolean[m + 1][n + 1];
     * dp[i][j]: if the first i characters in s match the first j characters in p.
     *
     * Initialization:
     *
     * M[0][0] = true, since empty string matches empty pattern
     * M[i][0] = false (which is default value) since empty pattern cannot match non-empty string
     * M[0][j]: what pattern matches empty string ""? It should be #* #* #* #*...
     * As we can see, the length of pattern should be even && the char at the odd position(1, 3, ...) should be *
     *
     * Induction rule (very similar to edit distance, which we also consider from the end position)
     * 3 cases for the last character in the pattern: [letter . *]:
     *
     * 1) a letter that can match the last character in s, then M[i][j] = M[i - 1][j - 1]
     * If it cannot match the last character in s, then M[i][j] = false
     * 2) a dot ., then it can definitely match the last character in s, so M[i][j] = M[i - 1][j - 1]
     * 3) If it is a star * , then it can means 0 time, or at least 1 time(>= 1):
     * 3.1. 0 time, then we are using the 1st j - 2 characters in pattern p to match 1st i chars in s, so M[i][j] = M[i][j - 2]
     * 3.2. >= 1 time, then a* can be treated like a*a where the last a is just a virtual/dummy character who will be responsible for matching the last character in s,
     * so M[i][j] can be divided into 2 parts:
     * a)The dummy character(the 2nd last character in p) matches last character in s, i.e. p[j - 2] = s[i] or p[j - 2] = '.'. For example:
     * ######a
     * ###a*a
     * b) The first j characters in p match the previous i - 1 characters in s, i.e. M[i - 1][j].
     *
     * -> M[i][j] depends on M[i - 1][j - 1], M[i][j - 2], M[i - 1][j],
     * i.e. get M[i][j], we need to know previous elements in M which determines in our for loops, i goes from 1 to m - 1, j goes from 1 to n + 1
     *
     * Time complexity: O(p len * s len)
     * Space complexity: O(p len * s len)
     *
     */
    public boolean isMatch2(String s, String p) {
        if (s == null || p == null) {
            return false;
        }

        int m = s.length();
        int n = p.length();

        boolean[][] M = new boolean[m + 1][n + 1];
        M[0][0] = true;
        for (int j = 1; j < n; j +=2){
            M[0][j + 1] = p.charAt(j) == '*' && M[0][j - 1];
        }

        for (int i = 1; i < m + 1; i++) {
            for (int j = 1; j < n + 1; j++) {
                char curS = s.charAt(i - 1);
                char curP = p.charAt(j - 1);

                if (curS == curP || curP == '.'){
                    M[i][j] = M[i - 1][j - 1];
                } else if (curP == '*') {
                    // if * represents 0 time
                    boolean b1 = M[i][j - 2];

                    // if * represents 1 or more times
                    char starBase = p.charAt(j - 2);
                    boolean b2 = (M[i - 1][j] && (starBase == '.' || starBase == curS));

                    M[i][j] = b1 || b2;
                }
            }
        }

        return M[m][n];
    }
}
