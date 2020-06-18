public class _0070ClimbingStairs {
    /**
     * 1. Dynamic Programming
     *
     * n <= 0: 0
     *
     * the total ways to get to the point [n] is n1 + n2.
     * Because from the [n-1] point, we can take one single step to reach [n].
     * And from the [n-2] point, we could take two steps to get there."
     * from n-1 to n (taking 1 step) and from n-2 to n (take 2 steps or just 1 step) can't be counted as a new way.
     * for n-1, no matter how many ways u get n-1, you can only take 1 step to n, so there is not a new way.
     * for n-2, if you take 2 steps, it is covered by n-1 scenario, if you take 1 steps(2 stairs), same consideration as n-1 to n.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param n
     * @return
     */
    public int climbStairs(int n) {
        // Question guarantees n is positive
        if (n == 1) {
            return n;
        }
        int last = 1;
        int lastlast = 1;
        int now = 0;
        for (int i = 2; i <= n; i++) {
            now = last + lastlast;
            lastlast = last;
            last = now;
        }
        return now;
    }

    /**
     * 2. Dynamic Programming with memorization
     *
     * Nine chapter solution.
     *
     * Don't think this is a good solution
     */
    int[] result = null;

    // f(4) -> f(3) -> f(2) -> f(1): [-1, 1, -1, -1, -1]
    //                         f(0): [ 1, 1, -1, -1, -1]
    //                 f(2)        : [ 1, 1,  2, -1, -1]
    //                 f(1)        : [ 1, 1,  2, -1, -1]
    //         f(3)                : [ 1, 1,  2,  3, -1]
    //         f(2)                :
    // f(4)                        : [ 1, 1,  2,  3,  5]

    void f(int X) {
        if (result[X] != -1) return;
        if (X == 0 || X == 1) {
            result[X] = 1;
            return;
        }

        f(X - 1);
        f(X - 2);
        result[X] = result[X - 1] + result[X - 2];
    }

    public int climbStairs2(int n) {
        result  = new int[n + 1]; // 5
        for (int i = 0; i <= n; ++i) {
            result[i] = -1; // [-1, -1, -1, -1, -1]
        }

        f(n);
        return result[n];
    }
}
