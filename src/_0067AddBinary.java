public class _0067AddBinary {
    /**
     * 1.
     *
     * Time:  O(max(a, b))
     * Space: O(max(a, b))
     *
     * @param a
     * @param b
     * @return
     */
    public String addBinary(String a, String b) {
        if (a == null || b == null) {
            return null;
        }

        int carry = 0;
        int i = a.length() - 1;
        int j = b.length() - 1;
        StringBuilder res = new StringBuilder();

        while (i >= 0 || j >= 0) {
            int digitSum = carry;
            digitSum += i >= 0 ? a.charAt(i) - '0' : 0;
            digitSum += j >= 0 ? b.charAt(j) - '0' : 0;
            res.append(digitSum % 2);
            carry = digitSum / 2;
            i--;
            j--;
        }

        if (carry == 1) {
            res.append(1);
        }

        return res.reverse().toString();

    }
}
