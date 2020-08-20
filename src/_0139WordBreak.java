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
     * For dp problems, many times we go into iterative dp directly without even thinking about dfs.
     * This is a great example showing that dfs is better than dp.
     * DFS returns as soon as it finds one way to break the word
     * while dp computes if each substring starting/ending at i is breakable.
     * The test cases of this problem do not show it but it is shown in a similar problem Concatenated Words.
     */

    /**
     * 4. Dynamic Programming (bottom up)
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

     * Space complexity: O(n)
     *
     * @param s
     * @param wordDict
     * @return
     */
    public boolean wordBreak3(String s, List<String> wordDict) {
        Set<String> dict=new HashSet(wordDict);
        boolean[] dp = new boolean[s.length() + 1];
        dp[0] = true;

        int maxLength = 0;
        for(String t : dict){
            maxLength = Math.max(maxLength, t.length());
        }

        for (int i = 1; i <= s.length(); i++) {
            int leftMost = Math.max(0, i - maxLength);
            // s[0:i] = prev examined substring + can this part from dict
            for (int j = i - 1; j >= 0; j++) {
                if (dp[j] && dict.contains(s.substring(j, i))) {
                    dp[i] = true;
                    break;
                }
            }
        }
        /**
         * Another way of DP
         *          * Time complexity: [s len][dict size][avg len of words in dic]
         *          *
         *          * Further optimization
         *          * 1. remove [size of dict] by using Tire
         *          * 2. remove [avg length of words in dict] by KMP, and what's more
         *          * 3. remove both [size of dict] and [avg length of words in dict] by AC-automata.
         *          *
         *          *         for(int i = 1; i <= s.length(); i++){
         *          *             // substring = previous examined prefix + dict word
         *          *             for(String str: dict){
         *          *                 if(str.length() <= i){
         *          *                     if(f[i - str.length()]){
         *          *                         if(s.substring(i - str.length(), i).equals(str)){
         *          *                             f[i] = true;
         *          *                             break;
         *          *                         }
         *          *                     }
         *          *                 }
         *          *             }
         *          *         }
         */
        return dp[s.length()];
    }

    /**
     * 5. Dynamic Programming - top down
     *
     * Time complexity:
     * Set init: O(size of dict)
     * helper: O(len * len)
     *
     * Space complexity:
     * Boolean array: O(s len)
     * Helper call stack: O(s len)
     */
    public boolean wordBreak5(String s, List<String> wordDict) {
        // create the memoization array to save results and avoid repeat computations
        Boolean[] canBreak = new Boolean[s.length()];

        // convert the list into set for faster lookup
        Set<String> wordSet = new HashSet<>(wordDict);

        // recursion with memoization
        return helper(s, 0, wordSet, canBreak);
    }

    /**
     * Determine whether s[startIdx:] can be broken down into words from wordSet
     */
    private boolean helper(String s, int startIdx, Set<String> wordSet, Boolean[] canBreak) {
        // in case we've reached the end of string, return true
        if (startIdx == s.length()) {
            return true;
        }
        // else if we've already computed on current substring before
        if (canBreak[startIdx] != null) {
            return canBreak[startIdx]; // auto-unboxing
        }

        boolean res = false;
        // iterate through all indices after startIdx, explore every possible word
        for (int i = startIdx + 1; i <= s.length(); i++) {
            String currWord = s.substring(startIdx, i);
            // skip if this is not a word in the input dictionary
            if (!wordSet.contains(currWord)) {
                continue;
            }
            // recursively call upon the rest of string
            if (helper(s, i, wordSet, canBreak)) {
                res = true;
                break;
            }
        }
        // add result to memo and return the result
        canBreak[startIdx] = res;
        return res;
    }

    /**
     * 6. DP assisted with Trie
     *
     * Time complexity: O(n ^ 2) (No need to call substring)
     *
     * Space complexity:
     * Trie O(ave dict word len * dict size)
     * boolean array: O(s len)
     *
     */
    public class TrieNode {
        boolean isWord;
        TrieNode[] c;

        public TrieNode(){
            isWord = false;
            c = new TrieNode[128];
        }
    }

    public void addWord(TrieNode t, String w) {
        for (int i = 0; i < w.length(); i++) {
            int j = (int)w.charAt(i);
            if (t.c[j] == null) {
                t.c[j] = new TrieNode();
            }
            t = t.c[j];
        }
        t.isWord = true;
    }

    public boolean wordBreak6(String s, Set<String> wordDict) {
        TrieNode t = new TrieNode();
        for (String i : wordDict) {
            addWord(t, i);
        }
        char[] str = s.toCharArray();
        int len = str.length;
        boolean[] f = new boolean[len + 1];
        f[len] = true;

        TrieNode cur;
        for (int i = len - 1; i >= 0; i--) {
            //System.out.println(str[i]);
            cur = t;
            for (int j = i; cur != null && j < len ; j++) {
                cur = cur.c[(int)str[j]];
                if (cur != null && cur.isWord && f[j + 1]) {
                    f[i] = true;
                    break;
                }
            }
        }
        return f[0];
    }
}
