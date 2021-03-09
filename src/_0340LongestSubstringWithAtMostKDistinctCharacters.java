import java.util.HashMap;
import java.util.Map;

/**
 * A very general template for sliding windows
 *
 * https://leetcode.com/problems/find-all-anagrams-in-a-string/discuss/92007/Sliding-Window-algorithm-template-to-solve-all-the-Leetcode-substring-search-problem
 *
 *
 */
public class _0340LongestSubstringWithAtMostKDistinctCharacters {
    /**
     * 1. Sliding Window
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public int lengthOfLongestSubstringKDistinct2(String s, int k) {
        Map<Character, Integer> map = new HashMap<>();

        int end = 0;
        int start = 0;
        int max = 0;

        while(end < s.length()) {
            char c = s.charAt(end);
            map.put(c, map.getOrDefault(c, 0) + 1);
            end++;

            // sliding window
            // value - 1, and delete if 0
            while (map.size() > k) {
                c = s.charAt(start);
                if (map.containsKey(c)) {
                    map.put(c, map.get(c) - 1);
                    if (map.get(c) == 0) map.remove(c);
                }
                start++;
            }

            max = Math.max(max, end - start);
        }
        return max;
    }


}
