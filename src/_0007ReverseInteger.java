public class _0007ReverseInteger {

    /**
     * For your reference, to test if there is overflow for any integer x of the form x = a * 10 + b where |b| < 10,
     * the right way should be comparing x / 10 (integer division) with a.
     * If x / 10 != a, there is overflow, otherwise no overflow can happen.
     *
     * The proof is as follows: x itself is a signed integer, therefore we have INT_MIN <= x <= INT_MAX, which implies INT_MIN/10 <= x/10 <= INT_MAX/10.
     * assume x / 10 == a, we have INT_MIN/10 <= a <= INT_MAX/10.
     * Since |b| < 10, then a * 10 + b can overflow only if a = INT_MAX/10 or a = INT_MIN/10.
     * For signed 32-bit integers, we have INT_MAX = 2147483647 and INT_MIN = -2147483648.
     *
     * 1) For a = INT_MAX/10 = 214748364, overflow will happen only if b = 8 or 9.
     * if this is the case, then x = a * 10 + b will be negative and x / 10 cannot be the same as a, contradicting our assumption above.
     * 2) Similarly if a = INT_MIN/10 = -214748364, overflow will happen only if b = -9 but then x = a * 10 + b will be positive and x / 10 != a.
     * -> In summary, x / 10 != a <==> overflow.
     *
     * As for the test condition in the original post, which is equivalent to testing (x - b) / 10 != a,
     * it will only cover the cases when |a| > INT_MAX/10 but not the cases when |a| = INT_MAX/10, b = 8 or 9 for positive a and -9 for negative a.
     * The test condition works here because the edge cases mentioned above won't happen due to the fact that the input itself is a signed integer.
     * If the input is something else, say a string (see String to Integer (atoi)), the test condition above will fail the edge cases.
     *
     * Suppose a = Integer.MAX_VALUE + 1;
     * a = -2147483648
     * a - 8 = 2147483640
     * (a-8)/10 = 214748364
     *
     * Time: O(log10 x)
     * Space: O(1)
     *
     */
    public int reverse1(int x)
    {
        int result = 0;

        while (x != 0)
        {
            int tail = x % 10;
            int newResult = result * 10 + tail;
            if (newResult  / 10 != result) {
                return 0;
            }
            result = newResult;
            x = x / 10;
        }

        return result;
    }

    /**
     * 1. Math
     *
     * Notice this solution is not optimal:
     * Assume we are dealing with an environment that could only store integers
     * within the 32-bit signed integer range: [−231,  231 − 1].
     *
     * Time: O(log10 x)
     * Space: O(1)
     */
    public int reverse2(int x) {
        long res = 0;
        long divisor = 1;
        boolean isNegative = x < 0;
        while (x != 0) {
            long digit = Math.abs(x % 10);
            res = res * 10 + digit;
            x /= 10;
        }

        if (isNegative) {
            res = -res;
        }

        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
            return 0;
        }

        return (int)res;
    }

    /**
     * 0000       0 + 0 + 0 + 0           0
     * 0001       0 + 0 + 0 + 2^0         1
     * 0010       0 + 0 + 2^1 + 0         2
     * 0011       0 + 0 + 2^1 + 2^0       3
     * 0100       0 + 2^2 + 0 + 0         4
     * 0101       0 + 2^2 + 0 + 2^0       5
     * 0110       0 + 2^2 + 2^1 + 0       6
     * 0111       0 + 2^2 + 2^1 + 2^0     7 -> the most positive value
     * 1000      -2^3 + 0 + 0 + 0        -8 -> the most negative value
     * 1001      -2^3 + 0 + 0 + 2^0      -7
     * 1010      -2^3 + 0 + 2^1 + 0      -6
     * 1011      -2^3 + 0 + 2^1 + 2^0    -5
     * 1100      -2^3 + 2^2 + 0 + 0      -4
     * 1101      -2^3 + 2^2 + 0 + 2^0    -3
     * 1110      -2^3 + 2^2 + 2^1 + 0    -2
     * 1111      -2^3 + 2^2 + 2^1 + 2^0  -1
     *
     * 1 + MAX = MIN
     * MIN - 1 = MAX
     */

}
