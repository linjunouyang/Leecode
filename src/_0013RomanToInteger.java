public class _0013RomanToInteger {
    /**
     * 1. One Pass
     *
     * Time complexity: O(length of s)
     * Space complexity: O(1)
     *
     * @param s
     * @return
     */
    public int romanToInt(String s) {
        int ans;
        char[] sc = s.toCharArray();

        ans = toInt(sc[0]);
        for (int i = 1; i < s.length(); i++) {
            ans += toInt(sc[i]);
            if (toInt(sc[i - 1]) < toInt(sc[i])) {
                ans -= toInt(sc[i - 1]) * 2;
            }
        }
        return ans;
    }

    private int toInt(char s) {
        switch(s) {
            case 'I': return 1;
            case 'V': return 5;
            case 'X': return 10;
            case 'L': return 50;
            case 'C': return 100;
            case 'D': return 500;
            case 'M': return 1000;
        }
        return 0;
    }
}
