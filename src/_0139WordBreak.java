import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * References:
 * https://leetcode.com/problems/word-break/solution/
 * https://www.jiuzhang.com/solution/word-break/#tag-highlight-lang-java
 */
public class _0139WordBreak {
    /**
     * 1. Brute Force - DFS / backtracking
     *
     * The naive approach to solve this problem is to use recursion and backtracking.
     * For finding the solution, we check every possible prefix of that string in the dictionary of words,
     * if it is found in the dictionary, then the recursive function is called for the remaining portion of that string.
     * And, if in some function call it is found that the complete string is in dictionary,
     * then it will return true.
     *
     * Time complexity: at least O(n ^ dict length)
     *
     * ---
     * ?
     * s = "aaaaaaa" dic = ['a', 'aa', 'aaa', 'aaaa', 'aaaaa', 'aaaaaaa', 'aaaaaaa']
     *
     * contains(a) && dfs(aaaaaa) -> true
     * (omit contains and dfs symbol below)
     *  a && aaaaa -> true
     *   a && aaaa -> true
     *    a && aaa -> true
     *     a && aa -> true
     *      a && a -> true
     *       a && true -> true
     * each contains takes O(n)
     *
     * There could be worse cases like s = s + 'b'
     * or dic contains more words.
     * ---
     *
     * subString(): O(n)
     * computing hash code and full comparison for verifying hash table hit: O(n)
     * hashSet contains is more efficient than
     *
     * Space complexity: O(n)
     *
     * @param s
     * @param dict
     * @return
     */
    public boolean wordBreak(String s, List<String> dict) {
        return dfs(s, new HashSet(dict), 0);
    }

    /**
     * Recursion definition:
     * determine string s[start: ] can be composed of words from dict
     **/
    public boolean dfs(String s, Set<String> dict, int now) {
        // Recursion exit
        if (now == s.length()) {
            return true;
        }

        // Recursion breakdown：enumerate the length of next sub string
        for (int len = 1; now + len <= s.length(); len++) {
            if (dict.contains(s.substring(now, now + len)) && dfs(s, dict, now + len)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 2. Recursion with memorization
     *
     * Time complexity O(n ^ dict length)
     *
     * s = "aaaaaaa" dic = ['a', 'aa', 'aaa', 'aaaa', 'aaaaa', 'aaaaaaa', 'aaaaaaa']
     *      *
     *      * contains(a) && dfs(aaaaaa) -> true
     *      * (omit contains and dfs symbol below)
     *      *  a && aaaaa -> true
     *      *   a && aaaa -> true
     *      *    a && aaa -> true
     *      *     a && aa -> true
     *      *      a && a -> true
     *      *       a && true -> true
     *      * each contains takes O(n)
     *
     * Space complexity : O(n).
     * The depth of recursion tree can go up to n.
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak2(String s, List<String> wordDict) {
        return word_Break(s, new HashSet(wordDict), 0, new Boolean[s.length()]);
    }

    public boolean word_Break(String s, Set<String> wordDict, int start, Boolean[] memo) {
        if (start == s.length()) {
            return true;
        }
        if (memo[start] != null) {
            return memo[start];
        }
        for (int end = start + 1; end <= s.length(); end++) {
            if (wordDict.contains(s.substring(start, end)) && word_Break(s, wordDict, end, memo)) {
                return memo[start] = true;
            }
        }
        return memo[start] = false;
    }

    /**
     * 3. TODO BFS
     */

    /**
     * 4. Dynamic Programming
     *
     * Intuition:
     * s can be divided into s1 and s2.
     * If s1 and s2 satisfy the required conditions, s also satisfies.
     *
     * s -> s1[0, j] + s2[j + 1, i]
     *
     * f[i] stands for whether subarray(0, i) can be segmented into words from the dictionary.
     * So f[0] means whether subarray(0, 0) (which is an empty string) can be segmented,
     * and of course the answer is yes.
     *
     * Time complexity: O(n ^ 3 + m)
     * m: size of dictionary
     * substring: O(n)
     * hashing a string of length N takes O(N) time
     * if there is a hash table hit, a full string comparison to confirm the hit
     *
     *
     * Space complexity: O(n)
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak3(String s, List<String> wordDict) {
        Set<String> wordDictSet=new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;
        for (int i = 1; i <= s.length(); i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && wordDictSet.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[s.length()];
    }
}
