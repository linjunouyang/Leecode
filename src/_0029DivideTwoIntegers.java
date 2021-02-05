/**
 * a / 2 -> a >> 1
 * a * 2 -> a << 1
 *
 * we strongly advise against allowing overflows to happen at all.
 * For some compilers/interpreters/languages, INT_MAX + 1 ≡ INT_MIN.
 * For others, INT_MAX + 1 ≡ INT_MAX. And for others again, it is undefined or crash.
 * Some people on the discussion forum have written code that actually relies on specific overflow behaviour for correctness.
 * While this can be quite "clever", it's not portable at all.
 * For code like that to be shippable, you'd need to be certain of the behaviour of the specific system it is to run on,
 * and that no future system upgrade would change the behaviour.
 * If it works on your machine, but not on Leetcode's machine, it's incorrect code.
 *
 * Math.abs(-Integer.MIN_VALUE) -2147483648
 * -Integer.MIN_VALUE
 *
 * https://leetcode.com/problems/divide-two-integers/solution/
 *
 *
 */

public class _0029DivideTwoIntegers {
    /**
     * 1. Increment
     *
     * Time: O(dividend)
     * Space: O(1)
     */
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            return Integer.MAX_VALUE;
        }

        int sign = 1;
        if ((dividend < 0 && divisor > 0) || (dividend > 0 && divisor < 0)) {
            sign = -1;
        }

        dividend = Math.abs(dividend);
        divisor = Math.abs(divisor);

        int sum = 0;
        int quotient = 0;

        while (sum + divisor <= dividend) {
            sum += divisor;
            quotient++;
        }

        if (sign == 1) {
            return quotient;
        } else {
            return -quotient;
        }
    }

    /**
     * 2. Exponential Search (like Binary search)
     *
     * searching sorted spaces of unknown size for the first value that past a particular condition. It it a lot like binary search, having the same time complexity of O(\log \, n)O(logn
     *
     */


    /**
     * 4. Signed left shift <<
     *
     * Bit pattern << number of positions to shift
     * -> Bit pattern * (2 ^ number of positions to shift)
     *
     * However this might not be a good solution, since we used long
     * (the question says only integer)
     *
     * Similar:
     * https://leetcode.com/problems/divide-two-integers/discuss/13467/Very-detailed-step-by-step-explanation-(Java-solution)
     *
     *
     * Time:
     * divisor * 2^n <= dividend
     * n <= log(dividend/divisor)
     *
     * Space: O(1)
     */
    public int divide2(int dividend, int divisor) {
        if (divisor == 0) {
            // although question specifies divisor != 0
            return dividend >= 0? Integer.MAX_VALUE : Integer.MIN_VALUE;
        }

        // dividend = 0 is covered in general cases

        if (dividend == Integer.MIN_VALUE && divisor == -1) {
            // -2147483648/-1 = 2147483648 (which overflows)
            return Integer.MAX_VALUE;
        }

        boolean isNegative = (dividend < 0 && divisor > 0) ||
                (dividend > 0 && divisor < 0);

        // left shifting might cause overflow, so use long
        long a = Math.abs((long)dividend);
        long b = Math.abs((long)divisor);
        int result = 0;
        while (a >= b) {
            int shift = 0;
            while (a >= (b << shift)) {
                shift++;
            }
            a -= b << (shift - 1);
            result += 1 << (shift - 1);
        }
        return isNegative? -result: result;
    }

    /**
     * https://leetcode.com/problems/divide-two-integers/discuss/142849/C%2B%2BJavaPython-Should-Not-Use-%22long%22-Int
     * a - b >= 0
     * a - (b << x << 1) >= 0
     */
}
