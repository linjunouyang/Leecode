public class _0371SumOfTwoIntegers {
    /**
     * 1. Bitwise add
     *
     * https://leetcode.com/problems/sum-of-two-integers/discuss/132479/Simple-explanation-on-how-to-arrive-at-the-solution
     *
     * XOR ^ : correct adds if carry is ignored
     * and & : carry
     *
     * why b will eventually become zero:
     * Suppose in the first iteration a&b = 1011 0101 , pay attention to all the zero bits.
     * (1) the operation a&b << 1 will introduce an 0-bit and has the ability to reduce a head 1-bit.
     * For example 1011 0101
     *          => 0110 1010,
     * the first 1-bit is removed and a brand new 0-bit is introduced at the end.
     * Thus, after assign a&b<<1 to b, b has less 1-bit and more 0-bit than a&b .
     *
     * (2) Continue iterating, the operation a & b preserves all the 0-bit in b no matter what a· is, as 0&(.) = 0.
     *
     * Thus, combining (1) and (2), b will hold less and less 1-bit during the iteration, and eventually become zero.
     *
     * 1101 (-3)
     * 0110 (6)
     * 0011
     *
     *
     * Time: O(number of bits)
     * @param a
     * @param b
     * @return
     */
    public int getSum(int a, int b) {

        int carry = 0;
        while (b != 0) {
            carry = a & b;
            a = a ^ b;
            b = carry << 1;
        }
        return a;
    }
}
