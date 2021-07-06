public class _1004MaxConsecutiveOnesIII {
    /**
     * 1. Brute Force
     * Examine every possible flipped array.
     * number of 0s to flip = Math.min(number of 0s, k)
     * Time: O(arr len * C(number of 0s, number of 0S to flip))
     * Space: O(arr len * C(number of 0s, number of 0S to flip))
     */

    /**
     * 2. Backtracking
     *
     * If the current number is
     * 0: a) flip to 1 if flipQuota > 0 b) remains 0
     * 1: proceed
     *
     * Time: TLE
     * number of 0s to flip = Math.min(number of 0s, k)
     * O(nums len * 2 ^ number of 0s to flip)
     *
     * Space: O(nums len) by recursion call stack
     */
    public int longestOnes(int[] nums, int k) {
        int[] max = new int[]{-1};
        countOnes(nums, 0, k, 0, max);
        return max[0];
    }

    private void countOnes(int[] nums, int i, int flipQuota, int length, int[] max) {
        if (i == nums.length) {
            max[0] = Math.max(max[0], length);
            return;
        }

        if (nums[i] == 1) {
            countOnes(nums, i + 1, flipQuota, length + 1, max);
        } else {
            if (flipQuota > 0) {
                countOnes(nums, i + 1, flipQuota - 1, length + 1, max);
            }
            max[0] = Math.max(length, max[0]);
            countOnes(nums, i + 1, flipQuota, 0, max);
        }
    }

    /**
     * To solve TLE, we might want to cache the result: cache[nums.length][k + 1]
     * notice that: 1 <= nums.length <= 10^5 0 <= k <= nums.length
     * cache might end up too big -> MLE
     *
     * Bottom-up (spaced optimized) might be the only possible solution in this category
     *
     * dp[i][k] 表示将A[i]置为1并且作为开头的最长子数组的长度，置换次数最多为k。0<=k<=K
     *
     * 状态转换方程：
     * dp[i][k] = A[i]==0? dp[i+1][k-1]+1 : dp[i+1][k]+1, k>0;
     * dp[i][0] = A[i]==0? 0 : dp[i+1][0]+1, k==0
     * 目标：max{dp[i][K]}
     *
     * 空间优化：使用一维滚动数组优化，k从最大的K开始，递减至0。
     */
    public int longestOnes22(int[] A, int K) {
        int n = A.length;
        int[] dp = new int[K+1];

        int ans = 0;
        for (int i = n - 1; i >= 0; i--){
            for (int k = K; k > 0; k--){
                dp[k] = (A[i] == 0) ? (dp[k - 1] + 1) : (dp[k] + 1);
            }
            dp[0] = (A[i] == 0) ? 0 : (dp[0] + 1);
            ans = Math.max(dp[K], ans);
        }

        return ans;
    }

    /**
     * 3. Sliding window
     *
     * Translation:
     * Find the longest subarray with at most K zeros.
     *
     * longest subarray -> sliding window
     *
     * variant:
     * https://leetcode.com/problems/max-consecutive-ones-iii/discuss/248287/java-sliding-windows-with-comments-in-line
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int longestOnes2(int[] nums, int k) {
        // in this implementation, once we found a bigger valid window, our window size never shrinks (don't care about validity)
        int start = 0;
        int end = 0;

        while (end < nums.length) {
            if (nums[end] == 0) {
                k--;
            }

            // violate constraint
            if (k < 0) {
                if (nums[start] == 0) {
                    // update count if necessary
                    k++;
                }

                // move start
                start++;
            }

            // always moving end because we want the longest
            end++;
        }

        return end - start; // eventually end = nums.length, so no need to plus 1
    }

    public int longestOnes21(int[] A, int K) {
        // in this implementation, our window is always valid, so need to remeber the max valid
        int max = 0;
        int zeroCount = 0; // zero count in current window
        int i = 0; // slow pointer

        for (int j = 0; j < A.length; ++j) {
            if (A[j] == 0) { // move forward j, if current is 0, increase the zeroCount
                zeroCount++;
            }

            // when current window has more than K, the window is not valid any more
            // we need to loop the slow pointer until the current window is valid
            while (zeroCount > K) {
                if (A[i] == 0) {
                    zeroCount--;
                }
                i++;
            }

            max = Math.max(max, j - i + 1); // everytime we get here, the current window is valid
        }
        return max;
    }
}
