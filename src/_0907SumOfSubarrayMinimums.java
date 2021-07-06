import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class _0907SumOfSubarrayMinimums {
    /**
     * 1. Monotonous increase stack
     *
     * ---
     * handling duplicate elements:
     * Set strict less and non-strict less(less than or equal to) for finding NLE and PLE respectively.
     * The order doesn't matter.
     *
     * s ….    m1 ….     m2 ….     s’
     * ....r1 ...... r2 ...... r3
     *
     * A[s] is the PLE of A[m1] (strictly less), and all the elements in-between are denoted by r1 is greater than A[m1].
     * A[m1] == A[m2], and all elements in-between which I denote by r2 are greater, a
     * nd A[s'] is the NLE of A[m2] (strictly less), and again all elements in-between which I denote by r3 are greater.
     *
     * If we used the strictly less for both PLE and NLE, then A[s] and A[s'] are the PLE and NLE for both A[m1] and A[m2].
     * m1 will count [r1, r2] and m2 will count [r2, r3], but both will count [r1, r3].
     *
     * To avoid this, notice our definition of NLE is less than or equal.
     * So that means NLE of A[m1] is A[m2]. This means [r1, r3] is only counted once.
     *
     * ---
     * Why multiplication gives us the number of sub-arrays:
     * The number of sub-arrays with 3 in it will be anything that starts in [L..3] and ends in [3..R].
     * So we have [L..3] starting positions and [3..R] ending positions.
     * In other words, we have d1 starting positions and d2 ending positions.
     * So the total number of subarrays is just d1*d2.
     *
     * For example:
     * in [9,7,8,3,4,6]
     * we have 4 choices to start with (9,7,8,3)
     * we have 3 choices to end with (3,4,6)
     * So answer is just 4*3.
     * ---
     *
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int sumSubarrayMins(int[] A) {
        int MOD = 1_000_000_007;
        int N = A.length;

        // 第 1 步：计算当前下标 i 的左边第 1 个比 A[i] 小的元素的下标
        Deque<Integer> stack1 = new ArrayDeque<>();
        int[] prev = new int[N];
        for (int i = 0; i < N; i++) {
            while (!stack1.isEmpty() && A[i] <= A[stack1.peekLast()]) {
                stack1.removeLast();
            }
            prev[i] = stack1.isEmpty() ? -1 : stack1.peekLast();
            stack1.addLast(i);
        }

        // 第 2 步：计算当前下标 i 的右边第 1 个比 A[i] 小的元素的下标
        Deque<Integer> stack2 = new ArrayDeque<>();
        int[] next = new int[N];
        for (int i = N - 1; i >= 0; i--) {
            while (!stack2.isEmpty() && A[i] < A[stack2.peekLast()]) {
                stack2.removeLast();
            }
            next[i] = stack2.isEmpty() ? N : stack2.peekLast();
            stack2.addLast(i);
        }

        // 第 3 步：计算结果
        long ans = 0;
        for (int i = 0; i < N; ++i) {
            // 注意：乘法可能越界，须要先转成 long 类型
            ans += (long) (i - prev[i]) * (next[i] - i) % MOD * A[i] % MOD;
            ans %= MOD;
        }
        return (int) ans;
    }

    /**
     * 1.1. One Pass
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int sumSubarrayMins2(int[] A) {
        Deque<Integer> stack = new ArrayDeque<>(); // increasing stack
        int n = A.length;
        int res = 0;
        int mod = (int) (1e9 + 7);

        for (int i = 0; i <= n; i++) {
            int cur = i == n ? 0 : A[i]; // A[i] >= 1,
            while (!stack.isEmpty() && A[stack.peek()] > cur) {
                // k ... j ... i
                // A[j] is the min of any sub-arr with start: [k+1, j], end: [j, i - 1]
                int pivot = stack.pop();

                // empty -> all prev nums are bigger
                // -> cur num can be min for any sub-arr with start: [0, j]
                // -> set j-k=j+1 -> k = -1
                int left = stack.isEmpty() ? -1 : stack.peek();

                // res and * * are within int range
                // but the sum of these two might be out of int max
                // number of left bound choices: (pivot - left)
                // number of right bound choices: (i - pivot)
                res = (int) ((res + (long) A[pivot] * (pivot - left) * (i - pivot)) % mod);
            }
            stack.push(i);
        }

        return res;
    }
}
