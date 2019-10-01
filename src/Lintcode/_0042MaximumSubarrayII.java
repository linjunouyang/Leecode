package Lintcode;

import java.util.List;

public class _0042MaximumSubarrayII {
    public int maxTwoSubArrays(List<Integer> nums) {
        int size = nums.size();

        // maximum sums on the left
        int[] left = new int[size];
        // maximum sums on the right
        int[] right = new int[size];

        // sum of nums[0 ... i]
        int sum = 0;
        // minimum sum of all the possible subarray ending at or before i
        int minSum = 0;
        // maximum sum of all the possible subarray ending at or before i
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < size; i++) {
            sum += nums.get(i);
            // sum(fixed) - minSum =  max subarray sum (ending at i)
            max = Math.max(max, sum - minSum);
            minSum = Math.min(sum, minSum);
            left[i] = max;
        }

        sum = 0;
        minSum = 0;
        max = Integer.MIN_VALUE;

        for (int i = size - 1; i >= 0; i--) {
            sum += nums.get(i);
            max = Math.max(max, sum - minSum);
            minSum = Math.min(sum, minSum);
            right[i] = max;
        }

        // iterate the boundary of two subarrays
        max = Integer.MIN_VALUE;
        for (int i = 0; i < size - 1; i++) {
            max = Math.max(max, left[i] + right[i + 1]);
        }

        return max;
    }
}
