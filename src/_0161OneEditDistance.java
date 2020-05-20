public class _0161OneEditDistance {
    /**
     * 1. One Pass
     *
     * https://leetcode.com/problems/one-edit-distance/solution/
     *
     * Time complexity: O(n) when abs(sLen - tLen) <= 1
     * Space complexity: O(n) for substring cost
     *
     * Runtime: 1 ms, faster than 99.09% of Java online submissions for One Edit Distance.
     * Memory Usage: 37.7 MB, less than 88.24% of Java online submissions for One Edit Distance.
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isOneEditDistance(String s, String t) {
        if (s == null || t == null) {
            return false;
        }

        int sLen = s.length();
        int tLen = t.length();

        if (sLen > tLen) {
            // a good way to reduce cases
            return isOneEditDistance(t, s);
        }

        if (tLen - sLen > 1) {
            return false;
        }

        for (int i = 0; i < sLen; i++) {
            if (s.charAt(i) != t.charAt(i)) {
                if (sLen == tLen) {
                    return s.substring(i + 1).equals(t.substring(i + 1));
                } else {
                    return s.substring(i).equals(t.substring(i + 1));
                }
            }
        }

        return sLen + 1 == tLen;
    }
}
