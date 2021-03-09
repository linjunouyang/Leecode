/**
 * when x = 0:
 * 0^0:
 *  In algebra and combinatorics, the generally agreed upon value is 00 = 1,
 *  whereas in mathematical analysis, the expression is sometimes left undefined.
 *
 * 0^(negative number):
 * undefined
 *
 * n:
 * n < 0: exclude x = 0;
 * 1) convert to -n (pay attention to Integer.MIN_VALUE)
 * or 2) convert result in the end (iteration) or in the base case (recursion)
 * n = 0: 1 unless x = 0;
 */
public class _0050PowXn {
    /**
     * 1. Brute Force Iteration (TLE)
     * Time: O(n)
     * Space: O(1)
     */
    public double myPow1(double x, int n) {
        double res = 1;
        if (n < 0) {
            x = 1 / x;
            n = -n;
        }

        for (int i = 1; i <= n; i++) {
            res = res * x;
        }

        return res;
    }


    /**
     * 2. Binary Search (similar to 29)
     *
     * Time: O(logn)
     * Space: O(logn)
     */
    public double myPow21(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == Integer.MIN_VALUE) {
            // INT_MIN is -2147483648 but INT_MAX is 2147483647 ,so n = -n is failed!!
            x = x * x;
            n = n / 2;
        }
        if (n < 0) {
            n = -n;
            x = 1 / x;
        }

        // OR combine negative n into one block
//        if(n<0){
//            return 1/x * myPow(1/x, -(n + 1));
//        }
        if (n % 2 == 0) {
            return myPow21(x * x, n / 2);
        } else {
            return x * myPow21(x * x, n / 2);
        }
    }

    public double myPow22(double x, int n) {
        if (n == 0) {
            return 1;
        }
        if (n == 1) {
            return x;
        }
        if (n == -1) {
            return 1 / x;
        }

        double num = myPow22(x, n / 2);
        if (n % 2 == 0) {
            return num * num;
        } else {
            return num * num * myPow22(x, n % 2);
        }
    }

    /**
     * 3. Bit representation of n
     *
     * Time: O(logn)
     * Space: O(1)
     */
    public double myPow(double x, int n) {
        double result = 1.0;
        for (int i = n; i != 0; i /= 2) {
            if (i % 2 != 0) {
                result *= x; // ^2
            }
            x *= x; //^2 ^4
        }

        // i = 10 -> x=^2, i=5
        // i = 5 -> res=^2 x=^4 i=2
        // i = 2 -> x=^8 i=1
        // i = 1 -> res=^2^8 x^16 i=0

        if (n < 0) {
            return 1 / result;
        } else {
            return result;
        }
    }

    /**
     * >>> logical (unsigned) shift
     * >> arithmetic (signed) shift
     */
    public double myPow32(double x, int n) {
        double result = 1.0;
        long power = Math.abs((long)n);
        for(long i = power; i != 0; i = i >> 1) {
            if( (i & 1) == 1 ) {
                result *= x; // ^2
            }
            x *= x; //^2 ^4
        }

        // i = 10 -> x=^2, i=5
        // i = 5 -> res=^2 x=^4 i=2
        // i = 2 -> x=^8 i=1
        // i = 1 -> res=^2^8 x^16 i=0

        if (n < 0) {
            return 1 / result;
        } else {
            return result;
        }

    }
}