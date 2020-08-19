/**
 * Intuition:
 * Subsequence -> DP
 *
 */
public class _0300LongestIncreasingSubsequence {
    /**
     * 1. Brute Force
     *
     * The simplest approach is to try to find all increasing subsequences and then returning the maximum length of longest increasing subsequence.
     * In order to do this, we make use of a recursive function lengthOfLIS which returns the length of the LIS possible from the current element(corresponding to curPos) onwards(including the current element).
     *
     * Inside each function call, we consider two cases:
     * 1) curr > prev in the LIS: Max(including curr, not including curr)
     * 2) curr < prev in the LIS: not including the current element in the LIS, which is returned by the current function call.
     *
     * Time complexity: O(2 ^ n) (two to the power of n)
     * Space complexity: O(n)
     */
    public int lengthOfLIS(int[] nums) {
        return lengthofLIS(nums, Integer.MIN_VALUE, 0);
    }

    public int lengthofLIS(int[] nums, int prev, int curpos) {
        if (curpos == nums.length) {
            return 0;
        }
        int taken = 0;
        if (nums[curpos] > prev) {
            taken = 1 + lengthofLIS(nums, nums[curpos], curpos + 1);
        }
        int nottaken = lengthofLIS(nums, prev, curpos + 1);
        return Math.max(taken, nottaken);
    }

    /**
     * 2. Recursion with memoization (memo is hard to understand)
     *
     * memo[i][j] represents the length of the LIS possible
     * using nums[i] as the previous element considered to be included/not included in the LIS,
     * with nums[j] as the current element considered to be included/not included in the LIS.
     *
     * Time complexity: O(n ^ 2)
     * Space complexity O(n ^ 2)
     *
     */

    /**
     * 3. DP
     *
     * Observation:
     * the LTS possible upto the ith num is independent of the elements later on
     *
     * dp[i]: the length of LIS considering upto ith (& always including ith)
     * dp[i] = max(dp[j]) + 1, 0 <= j < i & nums[i] > nums[j]
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     */
    public int lengthOfLIS3(int[] nums) {
        if (nums == null) {
            return 0;
        }
        int[] dp = new int[nums.length];
        int globalMax = 0;
        for (int i = 0; i < nums.length; i++) {
            int localMax = 0;
            for (int j = 0; j < i; j++) {
                if (nums[i] > nums[j]) {
                    localMax = Math.max(localMax, dp[j]);
                }
            }
            dp[i] = localMax + 1;
            globalMax = Math.max(globalMax, dp[i]);

        }
        return globalMax;
    }

    /**
     * 4. DP with Binary Search? 九章最后一个?
     *
     * ? Patience Sorting
     *
     * For LIS, we need to know
     * 1) what's the last element
     * 2) what's the curr length
     *
     * In 3., we define 2) as state, and use 1) as condition to help state transition
     * In 4., we define 1) as state, and use 2) as condition to help state transition
     *
     * tails[i]: the smallest tail of all increasing subsequences with length i+1
     * For example, say we have nums = [4,5,6,3], then all the available increasing subsequences are:
     *
     * len = 1   :      [4], [5], [6], [3]   => tails[0] = 3
     * len = 2   :      [4, 5], [5, 6]       => tails[1] = 5
     * len = 3   :      [4, 5, 6]            => tails[2] = 6
     *
     * We can easily prove that tails is a increasing array (Imagine len = 2 : [2, 3], then tails[0] should be 2)
     * Therefore it is possible to do a binary search in tails array to find the one needs update.
     *
     * ------
     * Each time we only do one of the two:
     * (1) if x is larger than all tails, append it, increase the size by 1
     * //  it can give a new max length record, if n is greater than current highest record;
     * // extend increasing sequence with larger numbers,
     * (2) if tails[i-1] < x <= tails[i], update tails[i]
     * // minimize existing values with smaller ones - so we can use smaller numbers to extend it.
     * // it can lower the bar of a certain record so we can have better chance in the future to get a higher/longer record
     *
     * Time complexity: O(n logn)
     * Space complexity: O(n)
     */
    public int longestIncreasingSubsequence4(int[] nums) {
        int[] tails = new int[nums.length];
        int size = 0;
        for (int x : nums) {
            int i = 0, j = size;
            // Search for first idx such that tails[idx] > x
            while (i != j) {
                int m = (i + j) / 2;
                if (tails[m] < x) {
                    i = m + 1;
                } else {
                    j = m;
                }
            }
            tails[i] = x;
            if (i == size) ++size;
        }
        return size;
    }

    // GTE = Greater Than or Equal to
    private int firstGTE(int[] nums, int target) {
        int start = 0, end = nums.length - 1;
        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] >= target) {
                end = mid;
            } else {
                start = mid;
            }
        }
        if (nums[start] >= target) {
            return start;
        }
        return end;
    }
}
