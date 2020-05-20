/**
 * To-do:
 * KMP, and other solutions
 */
public class _0028ImplementStrStr {
    /**
     * 1. Sliding Window
     *
     *
     * e.g.
     *      * haystack.length() = 5
     *      * needle.length() = 2
     *      *
     *      * h - n = 3
     *      * [0, 1, 2, (3), 4]
     *
     * Time complexity: O(haystack - needle) * needle)
     * Space complexity: O(1)
     *
     * Runtime: 2 ms, faster than 52.61% of Java online submissions for Implement strStr().
     * Memory Usage: 35.8 MB, less than 100.00% of Java online submissions for Implement strStr().
     *
     * @param haystack
     * @param needle
     * @return
     */
    public int strStr(String haystack, String needle) {
        if (needle.isEmpty()) {
            return 0;
        }

        for (int i = 0; i < haystack.length() - needle.length(); i++) {
            for (int j = 0; j < needle.length() && haystack.charAt(i + j) == needle.charAt(j); j++) {
                if (j == needle.length() - 1) {
                    return i;
                }
            }
        }

        return -1;
    }
}
