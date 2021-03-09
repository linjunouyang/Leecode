public class _0696CountBinarySubstrings {
    /**
     * 1. Linear scan
     *
     * use different variable to keep track of sizes of different consecutive parts
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int countBinarySubstrings(String s) {
        int prevRunLength = 0;
        int curRunLength = 1;
        int res = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i-1)) {
                curRunLength++;
            } else {
                prevRunLength = curRunLength;
                curRunLength = 1;
            }
            if (prevRunLength >= curRunLength) {
                res++;
            }
        }
        return res;
    }

    public int countBinarySubstrings2(String s) {
        int cur = 1;
        int pre = 0;
        int res = 0;
        for (int i = 1; i < s.length(); i++) {
            if (s.charAt(i) == s.charAt(i - 1)) {
                cur++;
            } else {
                res += Math.min(cur, pre);
                pre = cur;
                cur = 1;
            }
        }
        return res + Math.min(cur, pre);
    }
}
