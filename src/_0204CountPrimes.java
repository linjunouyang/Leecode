/**
 * Prime Number:
 *
 * Definition:
 *
 * An integer [greater than one] is called a prime number
 * if its only positive divisors (factors) are one and itself.
 *
 * Remember:
 * 1. 0 and 1 is not prime
 * 2. Optimization:
 * a) the number must not be divisible by any number > n / 2,
 * we can immediately cut the total iterations half by dividing only up to n / 2
 *
 * b) we only need to consider factors up to √n
 *
 * 2 3 5 7 11 13
 *
 */
public class _0204CountPrimes {
    /**
     * 1.
     *
     * ?the terminating loop condition can be p < √n, as all non-primes ≥ √n must have already been marked off.
     *
     * Time complexity: O(n logn logn)
     * Space complexity: O(n)
     */
    public int countPrimes1(int n) {
        boolean[] isPrime = new boolean[n];
        for (int i = 2; i < n; i++) {
            isPrime[i] = true;
        }
        // Loop's ending condition is i * i < n instead of i < sqrt(n)
        // to avoid repeatedly calling an expensive function sqrt().
        for (int i = 2; i * i < n; i++) {
            if (!isPrime[i]) {
                continue;
            }
            for (int j = i * i; j < n; j += i) {
                isPrime[j] = false; // 4 6 8, 9 12 15
            }
        }

        int count = 0;
        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                count++;
            }
        }
        return count;
    }
    /**
     * 2. Fastest ?
     *
     * https://leetcode.com/problems/count-primes/discuss/57593/12-ms-Java-solution-modified-from-the-hint-method-beats-99.95
     *
     * First filter the even number, divide it into two-part -> n / 2
     * Then still use the formula p*p + n*p as the hint method.
     * I think its runtime complexity change to O (n log（ log n/2）/2)
     *
     *
     * @param n
     * @return
     */
    public int countPrimes(int n) {
        if (n < 3)
            return 0;

        boolean[] f = new boolean[n]; // is Composite?

        /**
         * Start with the assumption that half the numbers below n are
         * prime candidates, since we know that half of them are even,
         * and so _in general_ aren't prime.
         *
         * An exception to this is 2, which is the only even prime.
         * But also 1 is an odd which isn't prime.
         * These two exceptions (a prime even and a for-sure not-prime odd)
         * cancel each other out for n > 2, so our assumption holds.
         *
         * We'll decrement count when we find an odd which isn't prime.
         *
         * If n = 3,  c = 1.
         * If n = 5,  c = 2.
         * If n = 10, c = 5.
         */
        int count = n / 2;
        for (int i = 3; i * i < n; i += 2) {
            if (f[i])
                // already been decremented for this composite odd
                continue;

            /**
             * For each prime i, iterate through the odd composites
             * we know we can form from i, and mark them as composite
             * if not already marked.
             *
             * We know that i * i is composite.
             * We also know that i * i + i is composite, since they share
             * a common factor of i.
             * Thus, we also know that i * i + a*i is composite for all real a,
             * since they share a common factor of i.
             *
             * Note, though, that i * i + i _must_ be composite for an
             * independent reason: it must be even.
             * (all i are odd, thus all i*i are odd,
             * thus all (odd + odd) are even).
             *
             * Recall that, by initializing c to n/2, we already accounted for
             * all of the evens less than n being composite, and so marking
             * i * i + (odd)*i as composite is needless bookkeeping.
             *
             * So, we can skip checking i * i + a*i for all odd a,
             * and just increment j by even multiples of i,
             * since all (odd + even) are odd.
             */
            for (int j = i * i; j < n; j += 2 * i) {
                if (!f[j]) {
                    --count;
                    f[j] = true;
                }
            }
        }
        return count;

    }

}
