/**
 * 0^0:
 *  In algebra and combinatorics, the generally agreed upon value is 00 = 1,
 *  whereas in mathematical analysis, the expression is sometimes left undefined.
 *
 * 0^(negative number):
 * undefined
 *
 *
 */
public class _0050PowXn {
    /**
     * 1. Binary Search (similar to 29)
     *
     * Time: O(logn)
     * Space: O(logn)
     *
     */
    public double myPow(double x, int n) {
        if(n == 0) {
            return 1;
        }
        if (n == Integer.MIN_VALUE){
            // INT_MIN is -2147483648 but INT_MAX is 2147483647 ,so n = -n is failed!!
            x = x * x;
            n = n/2;
        }
        if (n < 0) {
            n = -n;
            x = 1/x;
        }

        // OR combine negative n into one block
//        if(n<0){
//            return 1/x * myPow(1/x, -(n + 1));
//        }

        return (n%2 == 0) ? myPow(x * x, n/2) : x *  myPow(x * x, n/2);
    }

    /**
     * 2. Bit representation of n
     *
     * Time: O(n)
     * Space: O(1)
     */
    public double myPow2(double x, int n) {
        double mul = 1;
        if (n > 0) {
            mul = powIteration(x, n);
        } else {
            //单独考虑 n = -2147483648
            if (n == -2147483648) {
                // 1. x = -1 or 1, res = 1
                // 2. -1 < x < 1: Positive Infinity
                // 3. x < - 1 or x > 1: 0
                return myPow2(x, -2147483647) * (1 / x);
            }
            n = -n;
            mul *= powIteration(x, n);
            mul = 1 / mul;
        }
        return mul;
    }

    public double powIteration(double x, int n) {
        double ans = 1;
        //遍历每一位
        while (n > 0) {
            //最后一位是 1，加到累乘结果里
            if ((n & 1) == 1) {
                ans = ans * x;
            }
            //更新 x
            x = x * x;
            //n 右移一位
            n = n >> 1;
        }
        return ans;
    }


}
