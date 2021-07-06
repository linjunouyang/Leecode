import java.util.HashMap;
import java.util.Map;

/**
 * Target string might contain duplicate characters
 * SubString should contain each target char with corresponding frequency in target
 *
 * A template for substring problems: (HashMap + Two Pointers)
 * https://leetcode.com/problems/minimum-window-substring/discuss/26808/Here-is-a-10-line-template-that-can-solve-most-'substring'-problems
 * https://leetcode.com/problems/longest-substring-with-at-most-two-distinct-characters/discuss/49708/Sliding-Window-algorithm-template-to-solve-all-the-Leetcode-substring-search-problem.
 *
 * 1. Use two pointers: start and end to represent a window.
 * 2. Move end to find a valid window.
 * 3. When a valid window is found, move start to find a smaller window.
 *
 * Similar Questions: 3, 76, 159
 *
 */
public class _0076MinimumWindowSubstring {
    /**
     * 1. Two Pointers
     *
     * Time complexity: O(|s| + O|t|)
     * Space complexity: O(1)
     */
    public String minWindow(String s, String t) {
        int[] charMap = new int[128];
        for (int i = 0; i < t.length(); i++) {
            charMap[t.charAt(i)]++;
        }

        int left = 0;
        int right = 0;
        int unmatched = t.length();
        int minLen = Integer.MAX_VALUE;
        int start = 0;

        while (right < s.length()) {
            char c = s.charAt(right);
            if (charMap[c] > 0) {
                unmatched--;
            }
            charMap[c]--;

            if (unmatched == 0) {
                while (left < right && charMap[s.charAt(left)] < 0) {
                    charMap[s.charAt(left)]++;
                    left++;
                }

                if (minLen > right - left + 1) {
                    minLen = right - left + 1;
                    start = left;
                }

                // this window is the min possible window for the given left
                charMap[s.charAt(left)]++;
                unmatched++;
                left++;
            }

            right++;
        }

        if (minLen == Integer.MAX_VALUE) {
            return "";
        } else {
            return s.substring(start, start + minLen);
        }
    }
}
