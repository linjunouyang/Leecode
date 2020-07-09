public class _0005LongestPalindromicSubstring {
    /**
     * 1. Brute Force
     *
     * Nested for loops generated all substrings
     * call helper function isPanlindrome
     *
     * Time complexity:
     * O(n^2) * O(n) = O(n ^ 3)
     *
     * Space complexity:
     * O(1) if we use index to check substring
     * O(n) if we use substring() to check substring
     *
     * Trivial improvement:
     * keep track of maxLen, ignore all substrings shorter than maxLen
     *
     *
     */


    /**
     * 2. Expand around center
     *
     * Enumerate all centers (pointed by start end end)
     * if s[start] == s[end], then start--, end++, update answer
     * keep the previous step until s[start] != start[end]
     *
     * Notice the center for odd and even length palindrome is different
     *
     * Time complexity:
     * enumerate all centers O(n)
     * expand and ccheck: O(n)
     * Total: O(n ^ 2)
     *
     * Space complexity: O(1)
     *
     * @param s
     * @return
     */
    public String longestPalindrome2(String s) {
        int len = s.length();
        int maxLen = 0;
        String result = null;
        for (int center = 0; center < len; center++) {
            // 奇数长度情况
            for (int start = center, end = center;
                 valid(start, end, len); start--, end++) {
                if (s.charAt(start) != s.charAt(end)) {
                    break;
                }
                int newLen = end - start + 1;
                if (newLen > maxLen) {
                    maxLen = newLen;
                    result = s.substring(start, end + 1);
                }
            }
            // 偶数长度情况
            for (int start = center, end = center + 1;
                 valid(start, end, len); start--, end++) {
                if (s.charAt(start) != s.charAt(end)) {
                    break;
                }
                int newLen = end - start + 1;
                if (newLen > maxLen) {
                    maxLen = newLen;
                    result = s.substring(start, end + 1);
                }
            }
        }
        return result;
    }
    private boolean valid(int start, int end, int len) {
        return start > -1 && end < len;
    }



    /**
     * 3. Dynamic Programming
     *
     * Time complexity: O(len * len)
     * Space complexity: O(len * len)
     *
     * @param s
     * @return
     */
    public String longestPalindrome3(String s) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        int len = s.length();
        boolean[][] isPalindrome = new boolean[len][len];
        int maxLen = 1;
        int start = 0;

        // 将长度为 1 的 dp 值设为真
        for (int i = 0; i < len; i++) {
            isPalindrome[i][i] = true;
        }

        for (int i = 0; i + 1 < len; i++) {
            if (s.charAt(i) == s.charAt(i + 1)) {
                isPalindrome[i][i + 1] = true;
                maxLen = 2;
                start = i;
            }
        }

        for (int left = len - 1; left >= 0; left--) {
            for (int right = left + 2; right < len; right++) {
                if (isPalindrome[left + 1][right - 1]
                        && s.charAt(left) == s.charAt(right)) {

                    isPalindrome[left][right] = true;
                    // 更新答案
                    if (right - left + 1 > maxLen) {
                        maxLen = right - left + 1;
                        start = left;
                    }
                }
            }
        }
        return s.substring(start, start + maxLen);
    }
}
