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
     * 3. Sliding window
     *
     * Translation:
     * Find the longest subarray with at most K zeros.
     *
     * variant:
     * https://leetcode.com/problems/max-consecutive-ones-iii/discuss/248287/java-sliding-windows-with-comments-in-line
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int longestOnes2(int[] nums, int k) {
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

        return end - start;
    }
}
