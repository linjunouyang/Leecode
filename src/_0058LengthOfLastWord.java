public class _0058LengthOfLastWord {
    /**
     * 1.
     *
     * ----
     * \\s+
     * the backslash should be escaped, because Java would first try to escape the string to a special character,
     * and send that to be parsed.
     *
     * What you want, is the literal "\s", which means, you need to pass "\\s". It can get a bit confusing.
     *
     * The \\s is equivalent to [ \\t\\n\\x0B\\f\\r]
     *
     * "" -> length 1
     * " " -> length 0
     * ----
     *
     * Time complexity: O(length of s)
     * Space complexity: O(length of s)
     *
     *
     * Runtime: 1 ms, faster than 50.01% of Java online submissions for Length of Last Word.
     * Memory Usage: 36.2 MB, less than 100.00% of Java online submissions for Length of Last Word.
     *
     *
     * @param s
     * @return
     */
    public int lengthOfLastWord(String s) {
        if (s == null) {
            return 0;
        }

        String[] strs = s.split(" ");
        if (strs.length == 0) {
            return 0;
        }
        return strs[strs.length - 1].length();

    }

    /**
     *
     * Time complexity: O(length of last word)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Length of Last Word.
     * Memory Usage: 35.8 MB, less than 100.00% of Java online submissions for Length of Last Word.
     *
     * @param s
     * @return
     */
    public int lengthOfLastWord2(String s) {
        if (s == null) {
            return 0;
        }

        // find the last character of the last word
        int i = s.length() - 1;
        while (i >= 0 && s.charAt(i) == ' ') {
            i--;
        }

        // find the first character of the last word
        int j = i;
        while (j >= 0 && s.charAt(j) != ' ') {
            j--;
        }

        return i - j;
    }
}
