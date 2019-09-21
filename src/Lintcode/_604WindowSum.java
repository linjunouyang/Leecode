package Lintcode;

public class _604WindowSum {
    public int[] winSum(int[] nums, int k) {
        if (nums == null || nums.length == 0 || nums.length < k || k <= 0) {
            return new int[0];
        }

        int[] sums = new int[nums.length - k + 1];

        int sum = 0;

        for (int i = 0; i <= k - 1; i++) {
            sum += nums[i];
        }

        int j = 0;
        sums[j++] = sum;

        for (int i = k; i < nums.length; i++) {
            sum = sum + nums[i] - nums[i - k];
            sums[j++] = sum;
        }

        return sums;
    }
}
