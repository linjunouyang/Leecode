public class _186ReverseWordsInAStringII {

    /**
     * 1. In-place reversion
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Reverse Words in a String II.
     * Memory Usage: 43.4 MB, less than 84.62% of Java online submissions for Reverse Words in a String II.
     *
     * @param s
     */
    public void reverseWords(char[] s) {
        if (s == null) {
            return;
        }

        reverse(s, 0, s.length - 1);

        for (int start = 0; start < s.length; start++) {
            int end = start;
            while (end < s.length && s[end] != ' ') end++;
            reverse(s, start, end - 1);
            start = end;
        }
    }

    private void reverse(char[] s, int start, int end) {
        while (start < end) {
            char c = s[start];
            s[start++] = s[end];
            s[end--] = c;
        }
    }
}
