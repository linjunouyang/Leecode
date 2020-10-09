public class _0829ConsecutiveNumbersSum {
    /**
     * 1. Math
     *
     * N can be expressed as k, k + 1, k + 2, ..., k + (i - 1),
     * where k is the start (a positive integer), i is the length of consecutive sequence
     * therefore
     * N = k * i + (i - 1) * i / 2 => N - (i - 1) * i / 2 = k * i,
     * which implies that as long as N - (i - 1) * i / 2 is k times of i,
     * we get a solution corresponding to i;
     * Hence iteration of all possible values of i, starting from 1, will cover all cases of the problem.
     *
     * Time: O(N ^ 0.5)
     * Space: O(1)
     */
    public int consecutiveNumbersSum(int N) {
        int ans = 0;
        for (int i = 1; i * (i - 1) / 2 < N; ++i) {
            if ((N - i * (i - 1) / 2) % i == 0) {
                ++ans;
            }
        }
        return ans;
    }
}
