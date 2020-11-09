public class _0008StringToInteger {
    /**
     * 1.
     * corner cases:
     * loop through string: always make sure i < s.length();
     * empty string.
     *
     *
     * Time: O(len of s)
     * Space: O(1)
     */
    public int myAtoi(String s) {
        if (s.isEmpty()) {
            return 0;
        }

        int idx = 0;
        while (idx < s.length() && s.charAt(idx) == ' ') {
            idx++;
        }
        if (idx == s.length()) {
            return 0;
        }

        boolean isPositive = true;
        int res = 0;
        char first = s.charAt(idx);
        if (first == '-') {
            isPositive = false;
        } else if (Character.isDigit(first)) {
            res = first - '0';
        } else if (first != '+') {
            return 0;
        }

        for (int i = idx + 1; i < s.length(); i++) {
            char c = s.charAt(i);
            if (Character.isDigit(c)) {
                int newRes = res * 10 + (c - '0');
                if (newRes / 10 != res) {
                    // another way of checking
                    // if(Integer.MAX_VALUE/10 < total || Integer.MAX_VALUE/10 == total && Integer.MAX_VALUE %10 < digit)
                    return isPositive ? Integer.MAX_VALUE : Integer.MIN_VALUE;
                }
                res = newRes;
            } else {
                break;
            }
        }

        return isPositive ? res : -res;
    }
}
