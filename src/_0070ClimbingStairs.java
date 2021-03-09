import java.util.HashMap;

public class _0070ClimbingStairs {
    /**
     * 1. Bottom up Recursion (TLE)
     *
     * Time: O(2 ^ n)
     * Space: O(n)
     */

    /**
     * 2. Recursive Bottom up with Memoization
     *
     * Helper definition:
     * start from i, how many ways to reach n;
     *
     * Time: O(n)
     * Space: O(n) recursion stack + Map
     */
    public int climbStairs2(int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        return climbHelper2(0, n, map);
    }
    public int climbHelper2(int i, int n, HashMap<Integer, Integer> map) {
        if (i > n) {
            return 0;
        }
        if (i == n) {
            return 1;
        }
        if (map.containsKey(i)) {
            return map.get(i);
        }

        int ways = climbHelper2(i + 1, n, map) + climbHelper2(i + 2, n, map);
        map.put(i, ways);
        return ways;
    }

    /**
     * 3. Iterative Bottom up
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
     * Time: O(n)
     * Space: O(n) -> O(1)
     */
    public int climbStairs3(int n) {
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
     * 4. Recursive top-down DP with memoization
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int climbStairs4(int n) {
        HashMap<Integer, Integer> map = new HashMap<>();
        return climbHelper(n, map);
    }

    private int climbHelper(int n, HashMap<Integer, Integer> map) {
        if (n <= 1) {
            return 1;
        }

        if (map.containsKey(n)) {
            return map.get(n);
        }

        int res = climbHelper(n - 1, map) + climbHelper(n - 2, map);
        map.put(n, res);
        return res;
    }
}
