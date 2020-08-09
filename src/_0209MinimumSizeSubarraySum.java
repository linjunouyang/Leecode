/**
 * Other discussion + Template
 *
 * Binary Search (TD)
 */
public class _0209MinimumSizeSubarraySum {
    /**
     * Brute Force:
     * i: 0 -> nums.length - 1, j: i -> nums.length - 1
     * calculate sum[i, j]
     * O(n^3)
     *
     * Brute Force Optimized:
     * storing sums from the start
     * O(n ^ 2)
     */

    /**
     * 1. Two Pointers
     *
     * Two pointers don't require input array to be sorted (Compare with 16).
     * Or think in this way:
     * we are trying to find minLen, so the 'element' for two pointers is actually len,
     * which is sorted.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     */
    public int minimumSize(int[] nums, int s) {
        if (nums == null) {
            return -1;
        }

        int left = 0;
        int currSum = 0;
        int res = Integer.MAX_VALUE;

        for (int right = 0; right < nums.length; right ++) {
            // 滑窗右边界扩张
            currSum += nums[right];

            // 满足条件
            while (currSum >= s) {
                // 更新res
                res = Math.min(res, right - left + 1);
                // 滑窗左边界收缩
                currSum -= nums[left];
                left ++;
            }
        }

        return res == Integer.MAX_VALUE ? -1: res;
    }

    /**
     * 2. Binary Search (TD)
     *
     * Time complexity: O(nlogn)
     * Space complexity: O(n)
     */
    public int minSubArrayLen(int s, int[] nums) {
        if (nums == null) {
            return -1;
        }

        // sums[i]: the sum of first i numbers
        int[] sums = new int[nums.length + 1];
        for (int i = 1; i < nums.length; i++) {
            sums[i] = sums[i - 1] + nums[i - 1];
        }

        int minLen = Integer.MAX_VALUE;

        for (int i = 0; i < sums.length; i++) {
            // starting with nums[i]. End Range: [i, nums.length - 1]
            int end = binarySearch(i + 1, sums.length - 1, s + sums[i], sums);
            if (end == sums.length) {
                break;
            }
            minLen = Math.min(minLen, end - i);
        }
        return minLen == Integer.MAX_VALUE ? 0 : minLen;
    }

    // sums[mid] - sums[start] >= target
    private int binarySearch(int lo, int hi, int target, int[] sums) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (sums[mid] > target){
                hi = mid - 1;
            } else if (sums[mid] < target) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return lo;
    }
}
