import java.util.*;

/**
 * DP:
 * 1) overlapping sub-problems: "bca" can develop into "bcad" 'bcaa" ...
 * 2) optimal substructure: curr word biggest chain length = prev word biggest chain length + 1
 */
public class _1048LongestStringChain {
    /**
     * 1. DP (bottom up) + HashMap
     *
     * Bottom up requires sorting
     *
     * Sort the words by word's length. (also can apply bucket sort)
     * For each word, loop on all possible previous word with 1 letter missing.
     * If we have seen this previous word, update the longest chain for the current word.
     * Finally return the longest word chain.
     *
     * Time complexity:
     * 1 <= n <= 1000
     * 1 <= word len <= 16
     *
     * sort: O(n * logn)
     * O(n * len of word * len of word)
     *
     * Space complexity:
     * Map: O(n)
     * sort: O(logn) to O(n)
     *
     * @param words
     * @return
     */
    public int longestStrChain(String[] words) {
        // string -> biggest word chain length so far
        Map<String, Integer> dp = new HashMap<>();
        Arrays.sort(words, (a, b)->a.length() - b.length());
        int res = 0;
        for (String word : words) {
            int best = 0;
            for (int i = 0; i < word.length(); ++i) {
                StringBuilder sb = new StringBuilder(word);
                String prev = sb.deleteCharAt(i).toString();
                best = Math.max(best, dp.getOrDefault(prev, 0) + 1);
            }
            dp.put(word, best);
            res = Math.max(res, best);
        }
        return res;
    }

    /**
     * Replace HashMap (string -> biggest chain length) with arr (string idx -> biggest chain length)
     *
     * Time complexity:
     * sort: O(nlogn)
     * loop: O(n * number of words with 1 less char * len of str) -> O(n ^ 2 * word len)
     * ['a', 'a', ..., 'a', 'ab']
     *
     * Space complexity:
     * sort: O(logn) ~ O(n)
     */
    public int longestStrChain12(String[] words) {
        Arrays.sort(words, (w1, w2) -> w1.length() - w2.length());
        int[] dp = new int[words.length];
        int maxLen = 0;
        for (int i = 0; i < words.length; i++) {
            dp[i] = 1;
            for (int j = i - 1; j >= 0 && words[i].length() - words[j].length() <= 1; j--) {
                if (isPredecessor(words[j], words[i])) {
                    dp[i] = Math.max(dp[i], dp[j] + 1);
                }
            }
            maxLen = Math.max(maxLen, dp[i]);
        }
        return maxLen;
    }

    private boolean isPredecessor(String s1, String s2) {
        if (s2.length() == s1.length()) {
            return false;
        }
        int diff = 0;
        for (int i = 0, j = 0; i < s1.length(); ) {
            if (s1.charAt(i) == s2.charAt(j)) {
                i++;
                j++;
            } else {
                diff++;
                if (diff > 1) {
                    return false;
                }
                j++;
            }
        }
        return true;
    }


    /**
     * 2. DFS + Memorization (no sort). DP (top-down)
     *
     * If no set
     * ["a","b","ba","bca","bda","bdca"]
     * Output 5, but expect 4
     *
     *
     * Time complexity:
     * set add: O(n)
     * loop words: N
     * loop a word and delete ith char : L^2
     * O(N + N * (L ^ 2 * N)) (last N for dfs)
     *
     * Space complexity:
     * set: O(n)
     * map: O(n)
     * recursion stack = O(len)
     *
     */
    public int longestStrChain2(String[] words) {
        // Prune recursion
        Set<String> set = new HashSet<>();
        for(String word : words) {
            set.add(word);
        }

        int ans = 0;
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            int best = helper(map, set, word);
            ans = Math.max(ans, best);
        }
        return ans;
    }

    // what is the biggest length for a word chain ends up at 'word'
    private int helper(Map<String, Integer> map, Set<String> set, String word){
        if (map.containsKey(word)) {
            return map.get(word);
        }
        int bestPrev = 0;
        for (int i = 0; i < word.length(); i++){
            // word.substring(0, i) + word.substring(i+1) -> StringBuilder: 34ms -> 25ms
            StringBuilder sb = new StringBuilder(word);
            String prev = sb.deleteCharAt(i).toString();
            if (set.contains(prev)){
                bestPrev = Math.max(bestPrev, helper(map, set, prev));
            }
        }
        map.put(word, 1 + bestPrev);
        return 1 + bestPrev;
    }
}
