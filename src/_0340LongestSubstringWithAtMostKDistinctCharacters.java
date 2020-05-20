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
     * 1. Sliding Window for ASCII
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 4 ms, faster than 75.89% of Java online submissions for Longest Substring with At Most K Distinct Characters.
     * Memory Usage: 42.7 MB, less than 6.38% of Java online submissions for Longest Substring with At Most K Distinct Characters.
     *
     *
     * @param s
     * @param k
     * @return
     */
    public int lengthOfLongestSubstringKDistinct(String s, int k) {
        int[] count = new int[256];

        int left = 0;
        int maxLength = 0;
        int distinct = 0;

        for (int right = 0; right < s.length(); right++) {
            if (count[s.charAt(right)]++ == 0) {
                distinct++;
            }

            while (left < s.length()  && distinct > k) {
                count[s.charAt(left)]--;

                if (count[s.charAt(left)] == 0) {
                    distinct--;
                }

                left++;
            }

            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    /**
     * 2. Sliding Window for Unicode
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param s
     * @param k
     * @return
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
