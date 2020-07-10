public class _0053MaximumSubarray {

    /**
     * Intuition:
     *
     * 1. cubic solution
     * for (int left = 0; left < n; left++) {
     *     for (int right = left; right < n; right++) {
     *         for (int k = left; k <= right; k++)
     *     }
     * }
     *
     * there are some duplicate work:
     *         When we know the sum of the subarray from
     *         0 to right - 1...why would we recompute the sum
     *         for the subarray from 0 to right?
     *         This is unnecessary. We just add on the item at
     *         nums[right].
     *
     * 2. quadratic solution:
     * for (int left = 0; left < n; left++) {
     *     for (int right = left; right < n; right++) {
     *
     *     }
     * }
     *
     *
     */

    /**
     * 1. Dynamic Programming
     *
     * https://leetcode.com/problems/maximum-subarray/discuss/20193/DP-solution-and-some-thoughts
     *
     * opt problem -> DP
     *
     * 1. format of sub problems -> recursive relation
     *
     * a. maxSubArray(int A[], int i, int j) -> maxSubArray(A, 0, A.length - 1)
     * hard to find connection from sub problems to the original problems
     *
     * b. maxSubArray(int A[], int i), which must has A[i] as the end element.
     *
     * maxSubArray(A, i) = Math.max(A[i] + maxSubArray(A, i - 1), A[i]);
     * OR
     * maxSubArray(A, i) = A[i] + maxSubArray(A, i - 1) > 0 ? maxSubArray(A, i - 1) : 0;
     *
     * In other words,
     * To calculate sum(0,i), you have 2 choices: either adding sum(0,i-1) to a[i], or not.
     * If sum(0,i-1) is negative, adding it to a[i] will only make a smaller sum,
     * so we add only if it's non-negative.
     *
     * ----
     *
     * original problem solution:
     * max = Math.max(maxSubArray(A, i)) (i = 0, ..., A.length - 1)
     *
     * 2. optimization
     * no need to keep dp[] array, just need previous one and a global variable
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param A
     * @return
     */
    public int maxSubArray(int[] A) {
        if (A == null || A.length == 0) {
            return 0;
        }

        int max = A[0];
        int sum = A[0];

        for (int i = 1; i < A.length; i++) {
            if (sum < 0) {
                sum = A[i];
            } else {
                sum += A[i];
            }

            max = Math.max(max, sum);
        }

        return max;
    }

    /**
     * 2. Prefix Sum (Nine chapter)
     * @param A
     * @return
     */
    public int maxSubArray2(int[] A) {
        // A.length -> A != null
        // A.length = 0, but max = Integer.MIN_value, should return 0;
        if (A == null || A.length == 0) {
            return 0;
        }

        // the largest sum of all contiguous subarray so far
        int max = Integer.MIN_VALUE;
        // sum from A[0] ... A[i]
        int sum = 0;
        // the min sum for first k (0 -> i) numbers
        int minSum = 0;

        for (int i = 0; i < A.length; i++) {
            sum += A[i];
            max = Math.max(max, sum - minSum);
            minSum = Math.min(minSum, sum);
        }

        return max;
    }

    /**
     * 4. Divide and Conquer
     *
     *
     * Time complexity: O(NlogN)
     * Space complexity: O(logN)
     *
     * @param nums
     * @return
     */
    public int maxSubArray4(int[] nums) {
        if (nums == null) {
            return 0;
        }

        return helper(nums, 0, nums.length - 1);
    }

    private int helper(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }

        int middle = left + (right - left) / 2;
        int leftSum = helper(nums, left, middle);
        int rightSum = helper(nums, middle + 1, right);
        int crossSum = crossSum(nums, left, right);

        return Math.max(Math.max(leftSum, rightSum), crossSum);
    }

    private int crossSum(int[] nums, int left, int right) {
        if (left == right) {
            return nums[left];
        }

        int middle = left + (right - left) / 2;

        int leftSum = Integer.MIN_VALUE;
        int curSum = 0;
        for (int i = middle; i >= left; i--) {
            curSum += nums[i];
            leftSum = Math.max(leftSum, curSum);
        }

        int rightSum = Integer.MIN_VALUE;
        curSum = 0;
        for (int i = middle + 1; i <= right; i++) {
            curSum += nums[i];
            rightSum = Math.max(rightSum, curSum);
        }

        return leftSum + rightSum;
    }
}
