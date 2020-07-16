public class _0303RangeSumQuery_Immutable {
    private int[] sums;

    /**
     * 1. Prefix Sum
     *
     * Preprocessing:
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Query:
     * Time complexity: O(1)
     * Space complexity: O(1)
     * @param nums
     */

    public _0303RangeSumQuery_Immutable(int[] nums) {
        int len = nums.length;
        sums = new int[len];
        int sumSoFar = 0;
        for (int i = 0; i < len; i++) {
            sumSoFar += nums[i];
            sums[i] = sumSoFar;
        }
    }

    public int sumRange(int i, int j) {
        if (i < 0 || j < 0 || i > sums.length || j > sums.length) {
            return 0;
        }

        if (i == 0) {
            return sums[j];
        } else {
            return sums[j] - sums[i - 1];
        }
    }
}
