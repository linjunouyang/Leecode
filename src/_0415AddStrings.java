public class _0415AddStrings {
    /**
     * 1. SB
     *
     * Both num1 and num2 contains only digits 0-9.
     * Both num1 and num2 does not contain any leading zero.
     * You must not use any built-in BigInteger library or convert the inputs to integer directly. -> can't use int
     *
     * Time: O(max(n1, n2))
     * Space: O(max(n1, n2))
     */
    public String addStrings(String num1, String num2) {
        // base cases
        if (num1 == null || num2 == null) {
            return null;
        }

        int carry = 0;
        StringBuilder res = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        while (i >= 0 && j >= 0) {
            int digitSum = num1.charAt(i) - '0' + num2.charAt(j) - '0' + carry;
            res.append(digitSum % 10);
            carry = digitSum / 10;
            i--;
            j--;
        }

        while (i >= 0) {
            int digitSum = num1.charAt(i) - '0' + carry;
            res.append(digitSum % 10);
            carry = digitSum / 10;
            i--;
        }

        while (j >= 0) {
            int digitSum = num2.charAt(j) - '0' + carry;
            res.append(digitSum % 10);
            carry = digitSum / 10;
            j--;
        }

        if (carry == 1) {
            res.append(1);
        }

        return res.reverse().toString();
    }

    public String addStrings2(String num1, String num2) {
        // base cases
        if (num1 == null || num2 == null) {
            return null;
        }

        int carry = 0;
        StringBuilder res = new StringBuilder();
        int i = num1.length() - 1;
        int j = num2.length() - 1;
        while (i >= 0 || j >= 0) {
            int digitSum = carry;
            if (i >= 0) {
                digitSum += num1.charAt(i) - '0';
                i--;
            }
            if (j >= 0) {
                digitSum += num2.charAt(j) - '0';
                j--;
            }
            res.append(digitSum % 10);
            carry = digitSum / 10;
        }

        if (carry == 1) {
            res.append(1);
        }

        return res.reverse().toString();
    }
}
