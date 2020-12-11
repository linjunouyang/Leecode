import java.util.Arrays;

public class _1326MinimumNumberOfTapsToOpenToWaterAGarden {
    /**
     * 1. DP (not good)
     *
     * dp[i] is the minimum number of taps to water [0, i] (whole interval, not just every integer)
     * Initialize dp[i] with max = n + 2
     * dp[0] = [0] as we need no tap to water nothing.
     *
     * Find the leftmost point of garden to water with tap i.
     * Find the rightmost point of garden to water with tap i.
     * We can water [left, right] with onr tap,
     * and water [0, left - 1] with dp[left - 1] taps.
     *
     * Time: O(NR)
     * Space: O(N)
     */
    public int minTaps(int n, int[] A) {
        int[] dp = new int[n + 1];
        Arrays.fill(dp, n + 2);
        dp[0] = 0;
        for (int i = 0; i <= n; ++i) {
            int left = Math.max(0, i - A[i]);
            int right = Math.min(n, i + A[i]);
            for (int j = left; j <= right; ++j) {
                // j: considering tap i, all position that can be watered (Math.max(i - A[i], 0), Math.min(i + A[i], n))
                // [0, i - A[i]] needs to be watered (dp[max(0, i - A[i])])
                // , and [i - A[i], j] can be watered by tap i (+1)
                dp[j] = Math.min(dp[j], dp[Math.max(0, i - A[i])] + 1);
            }
        }
        return dp[n] < n + 2 ? dp[n] : -1;
    }

    /**
     * 2. Greedy
     *
     * For this problem, we just need construct a new array to move the value into the leftmost point we can water,
     * so the problem becomes Jump Game II. For example, at index i we could water (i - arr[i]) ~ (i + arr[i]).
     * So we store the farthest point we can water at "i - arr[i]". Then "left" in range [left, right] is index and "right" is the value in arr[index].
     *
     * Similar:
     * 1024 Video Stitching
     * 45   Jump Game II
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int minTaps2(int n, int[] ranges) {
        // ith: using taps after i that can cover i, what is the furthest we can reach
        int[] arr = new int[n + 1];
        for (int i = 0; i < ranges.length; i++) {
//            if (ranges[i] == 0) {
//                continue;
//            }
            int left = Math.max(0, i - ranges[i]);
            arr[left] = Math.max(arr[left], i + ranges[i]);
        }

        // same part like previous problem
        int end = 0, furthest = 0, cnt = 0;
        int i = 0;
        while (i < arr.length && end < n) {
            cnt++;
            while (i < arr.length && i <= end) {
                furthest = Math.max(furthest, arr[i++]);
            }
            if (end == furthest) {
                return -1;
            }
            end = furthest;
        }
        return cnt;
    }


}
