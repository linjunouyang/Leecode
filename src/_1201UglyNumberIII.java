public class _1201UglyNumberIII {
    /**
     * 1. Binary Search
     *
     * LCM: Least common multiple
     * GCD: greatest common divisor(factor)
     *
     * Suppose two numbers x, y, their GCD is a
     * then lcm = a * (x/a) * (y/a) = xy/a
     * lcm*gcd = x*y
     *
     * Integer max: 2e9 (2 billion)
     * 1 <= a * b * c <= 10^18 -> need to use long for lcm
     *
     * Time: O(log(MAX_ANS))
     * Space: O(1)
     *
     */
    private static final int MAX_ANS = (int) 2e9; // 2*10^9
    public int nthUglyNumber(int n, int a, int b, int c) {
        int left = 0, right = MAX_ANS;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (count(mid, a, b, c) >= n) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }

    // number of ugly numbers that is <= num
    // Inclusion-Exclusion Principle
    private int count(long num, long a, long b, long c) {
        return (int) (num / a + num / b + num / c
                - num / lcm(a, b)
                - num / lcm(b, c)
                - num / lcm(a, c)
                + num / (lcm(a, lcm(b, c))));
    }

    /**
     * 例如，求（319，377）：
     * ∵ m 319 ÷ n 377= 0（余319）-> n = result = 319 m = 377 ∴（319，377）=（377，319；
     * ∵ 377÷319=1（余58）-> n = result = 58, m = 319 ∴（377，319）=（319，58）；
     * ∵ 319÷58=5（余29） ∴ （319，58）=（58，29）； ∵ 58÷29=2（余0） ∴ （58，29）= 29； ∴
     */
    private long gcd(long m, long n) {
        long result = 0;

        while (n != 0) {
            result = m % n;
            m = n;
            n = result;
        }
        return m;
    }

    private long lcm(long a, long b) {
        return a * b / gcd(a, b);
    }

}
