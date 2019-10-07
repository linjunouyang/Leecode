package Lintcode;

import java.util.Arrays;

/**
 * Not Authorized
 *
 * Given an array of integers, find how many pairs in the array such that their
 * sum is less than or equal to a specific target number.
 * Please return the number of pairs
 *
 * Given nums = [2, 7, 11, 15], target = 24
 * return 5
 * 2 + 7 < 24
 * 2 + 11 < 24
 * 2 + 15 < 24
 * 7 + 11 < 24
 * 7 + 15 < 25
 */
public class TwoSum_LessThanOrEqualToTarget {
    /**
     * 1. Two Pointers
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(1)
     *
     * @param nums
     * @param target
     * @return
     */
    public int twoSum5(int[] nums, int target) {
        if (nums == null || nums.length < 2) {
            return 0;
        }

        Arrays.sort(nums);

        int left = 0;
        int right = nums.length - 1;
        int count = 0;

        while (left < right) {
            if (nums[left] + nums[right] <= target) {
                count += right - left;
                left++;
            } else {
                right--;
            }
        }

        return count;
    }
}
