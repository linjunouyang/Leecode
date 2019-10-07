package Lintcode;

import java.util.Arrays;

/**
 * Given an array nums of n integers, find two integers in nums such that the sum is closest to a given
 * number, target.
 *
 * Return the difference between the sum of the two integers and the target
 *
 */
public class TwoSum_ClosetToTarget {

    /**
     * 1. Soring, Two Pointers
     *
     * Time complexity: O(nlgn \)
     * Space complexity: O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int twoSumCloset(int[] nums, int target) {
        if (nums == null && nums.length < 2) {
            return 0;
        }

        Arrays.sort(nums);

        int diff = Integer.MAX_VALUE;
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            if (nums[left] + nums[right] < target) {
                diff = Math.min(diff, target - nums[left] - nums[right]);
                left++;
            } else if (nums[left] + nums[right] > target) {
                diff = Math.min(diff, nums[left] + nums[right] - target);
                right--;
            } else {
                return 0;
            }
        }

        return diff;
    }
}
