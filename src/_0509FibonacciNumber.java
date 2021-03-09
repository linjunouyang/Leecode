import java.util.HashMap;

public class _0509FibonacciNumber {
    /**
     * 1. Top-down recursion
     *
     * Time: O(2 ^ n)
     * https://leetcode.com/problems/fibonacci-number/solution/
     * Space: O(n)
     */
    public int fib(int n) {
        if (n <= 1) {
            return n;
        }

        return fib(n - 1) + fib(n - 2);
    }

    /**
     * 2. Top-down Recursion with Memoization
     *
     * Time: O(n)
     * Space: O(n) -> O(1)
     */
    public int fib2(int n) {
        HashMap<Integer, Integer> fibMap = new HashMap<>();

        return fibHelper(n, fibMap);
    }

    private int fibHelper(int n, HashMap<Integer, Integer> fibMap) {
        if (n <= 1) {
            return n;
        }

        if (fibMap.containsKey(n)) {
            return fibMap.get(n);
        }

        int res = fibHelper(n - 1, fibMap) + fibHelper(n - 2, fibMap);
        fibMap.put(n, res);
        fibMap.remove(n - 2); // O(n) -> O(1)

        return res;
    }

    /**
     * 3. Bottom-up
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int fib3(int n) {
        if (n <= 1) {
            return n;
        }

        int first = 0;
        int second = 1;
        int res = 0;
        for (int i = 2; i <= n; i++) {
            res = first + second;
            first = second;
            second = res;
        }
        return res;
    }
}
