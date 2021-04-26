public class _0044WildcardMatching {
    /**
     * 1. Memoized Search, DFS, backtracking
     *
     * source:
     * https://www.jiuzhang.com/solution/wildcard-matching/
     *
     * 记忆化的作用是当我们重复进入同样一种状态的时候，就不要算两次。
     * 比如你算过了 is_match("abc", "acc") 下次再碰到的时候就直接return 你之前算过的值。
     *
     * [my own understanding]
     * backtracking is demonstrated at:
     * match = isMatchHelper(s, sIndex, p, pIndex + 1, memo, visited) ||
     *                     isMatchHelper(s, sIndex + 1, p, pIndex, memo, visited);
     * we first explore possible matching by moving pIndex forward,
     * if false, we revert pIndex, and move sIndex instead
     *
     * Time: O(sLen * pLen)
     * 对于我们要记忆的东西（也就是搜索函数里的参数组合）一共就 O(nm) 种可能性。每种参数组合的可能性往下递归的时候，是 O(1) 的处理时间。
     *
     * Space: O(sLen * pLen)
     */
    public boolean isMatch(String s, String p) {
        if (s == null || p == null) {
            return false;
        }

        boolean[][] memo = new boolean[s.length()][p.length()];
        boolean[][] visited = new boolean[s.length()][p.length()];
        return isMatchHelper(s, 0, p, 0, memo, visited);
    }

    private boolean isMatchHelper(String s, int sIndex,
                                  String p, int pIndex,
                                  boolean[][] memo,
                                  boolean[][] visited) {
        // 如果 p 从pIdex开始是空字符串了，那么 s 也必须从 sIndex 是空才能匹配上
        if (pIndex == p.length()) {
            return sIndex == s.length();
        }

        // 如果 s 从 sIndex 是空，那么p 必须全是 *
        if (sIndex == s.length()) {
            return allStar(p, pIndex);
        }

        if (visited[sIndex][pIndex]) {
            return memo[sIndex][pIndex];
        }

        char sChar = s.charAt(sIndex);
        char pChar = p.charAt(pIndex);
        boolean match;

        if (pChar == '*') {
            match = isMatchHelper(s, sIndex, p, pIndex + 1, memo, visited) ||
                    isMatchHelper(s, sIndex + 1, p, pIndex, memo, visited);
        } else {
            match = charMatch(sChar, pChar) &&
                    isMatchHelper(s, sIndex + 1, p, pIndex + 1, memo, visited);
        }

        visited[sIndex][pIndex] = true;
        memo[sIndex][pIndex] = match;
        return match;
    }

    private boolean charMatch(char sChar, char pChar) {
        return (sChar == pChar || pChar == '?');
    }

    private boolean allStar(String p, int pIndex) {
        for (int i = pIndex; i < p.length(); i++) {
            if (p.charAt(i) != '*') {
                return false;
            }
        }
        return true;
    }

    /**
     * 2. DP
     *
     * dp[i][j]: true if the first i char in String s matches the first j chars in String p
     * Be careful about the difference of String index (0 to len-1) and dp index (0 to len)!
     *
     * Base case:
     * origin: dp[0][0]: they do match, so dp[0][0] = true
     * first row: dp[0][j]: except for String p starts with *, otherwise all false
     * first col: dp[i][0]: can't match when p is empty. All false.
     *
     * Recursion:
     * Start from 0 to len, iterate through every dp[i][j]
     *
     * dp[i][j] = true:
     *      if (s[ith] == p[jth] || p[jth] == '?') && dp[i-1][j-1] == true
     *      elif p[jth] == '*' && (dp[i-1][j] == true || dp[i][j-1] == true)
     *          - dp[i-1][j]: * acts like any sequences
     *              eg: ab, ab*
     *          - dp[i][j-1]: * acts like an empty sequence
     *              eg: abcd, ab*
     *
     * Output put should be dp[s.len][p.len], referring to the whole s matches the whole p
     *
     * Time: O(mn)
     * Space: O(mn)
     */
    public boolean isMatch2(String s, String p) {
        if (s == null || p == null) {
            return false;
        }

        int sLen = s.length();
        int pLen = p.length();
        boolean[][] dp = new boolean[sLen + 1][pLen + 1];

        // Base cases:
        dp[0][0] = true;
        for (int j = 1; j <= pLen; j++){
            if(p.charAt(j-1) == '*'){
                dp[0][j] = dp[0][j-1];
            }
        }

        // Recursion:
        for (int i = 1; i <= sLen; i++){
            for (int j = 1; j <= pLen; j++) {
                char sChar = s.charAt(i - 1);
                char pChar = p.charAt(j - 1);
                if((sChar == pChar || pChar == '?') && dp[i-1][j-1]) {
                    dp[i][j] = true;
                } else if (pChar == '*' && (dp[i-1][j] || dp[i][j-1])) {
                    dp[i][j] = true;
                }
            }
        }
        return dp[sLen][pLen];
    }

    /**
     * 3. Iterative backtracking, Dfs with greedy (?)
     * https://leetcode.com/problems/wildcard-matching/discuss/17810/Linear-runtime-and-constant-space-solution
     * https://leetcode-cn.com/problems/wildcard-matching/solution/tong-pei-fu-pi-pei-by-leetcode-solution/
     *
     * DFS:
     * * leads to branches
     *
     * The key point is how to deal with the '*' pattern.
     * In this solution, '*' pattern try matching as less characters as possible (greedy)
     * If the '*' pattern match less than expect, it use star to revert
     * (ex. string is "abcbc" and pattern is "ab*c", '*' match nothing at first.
     * When p reaches end, but there is more characters in s, revert and make '*' match "cb").
     *
     * star only remember the last revert point,
     * For example, if string is "abcbcbc" and pattern is "ab*c*c", first '*' matching "" or "cb" does not matter,
     * because we can make the second '*' to match more if the first one matching less than expect.
     * So, we do not need to revert to old point anymore and only the last revert point need to be remembered.
     *
     * The gready idea: if you have multiple * in pattern, you check match for last .
     * This is really hard to tell but the idea is: if there is a mismatch, you can choose to go back to any prev , but if you choose a previous * not last one, for the p,s to match, you need the left s and left p with * to mach. However, for this part to match, you also need guarantee it's subset(the substring start with last) to match some substr(end in last char in s) in left s. This equals the whole left substr(from dismatch) can be matched by substr start form last as * can match any chars.
     * Thus checking any prev* is equal to check last* and thus only check last*.
     * In a recusion formular, dp[prev*,end] = dp[last*,end], this is hard to think, but really elegant.
     * The avg speed boost comparing with generally dp. Obviously less state to check as only check last* not all *. This is the major speed boost because of greedy algorithm.
     *
     * current star matches [sTmpIdx - startIdx] characters
     *
     * Time: O(mn)
     *
     * input: bbbbbbbbbbbb
     * pattern: *bbbb
     *
     * Space: O(1)
     */
    public boolean isMatch3(String s, String p) {
        int sLen = s.length(), pLen = p.length();
        int sIdx = 0, pIdx = 0;
        int starIdx = -1, sTmpIdx = -1; // sTmpIdx - startIdx

        while (sIdx < sLen) {
            // If the pattern character = string character
            // or pattern character = '?'
            if (pIdx < pLen && (p.charAt(pIdx) == '?' || p.charAt(pIdx) == s.charAt(sIdx))){
                ++sIdx;
                ++pIdx;
            }
            // If pattern character = '*'
            else if (pIdx < pLen && p.charAt(pIdx) == '*') {
                // Check the situation
                // when '*' matches no characters
                sTmpIdx = sIdx;
                starIdx = pIdx;
                ++pIdx;
            }
            // If pattern character != string character
            // or pattern is used up
            // and there was no '*' character in pattern
            else if (starIdx == -1) {
                return false;
            }
            // If pattern character != string character
            // or pattern is used up
            // and there was '*' character in pattern before
            else {
                // Backtrack: check the situation
                // when '*' matches one more character
                sIdx = sTmpIdx + 1;
                sTmpIdx = sIdx;
                pIdx = starIdx + 1;
            }
        }

        // The remaining characters in the pattern should all be '*' characters
        for(int i = pIdx; i < pLen; i++) {
            if (p.charAt(i) != '*') {
                return false;
            }
        }

        return true;
    }
}
