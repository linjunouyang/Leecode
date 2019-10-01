package Lintcode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given an array of integers, find how many unique pairs in the array
 * such that their sum is equal to a specific target number.
 * Please return the number of pairs
 */
public class TwoSum_UniquePairs {
    /**
     * 1. Sorting +  Two Pointers
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(n)
     *
     *
     * @param nums
     * @param target
     * @return
     */
    public int twoSum6(int[] nums, int target) {
        if (nums == null) {
            return 0;
        }

        Arrays.sort(nums);

        int count = 0;
        int left = 0;
        int right = nums.length - 1;


        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum < target) {
                left++;
            } else if (sum > target) {
                right--;
            } else {
                count++;
                left++;
                right--;

                while (left < right && nums[left] == nums[left - 1]) {
                    left++;
                }

                while (left < right && nums[right] == nums[right + 1]) {
                    right++;
                }
            }
        }

        return count;
    }


    /**
     * 2. HashMap
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param nums
     * @param target
     * @return
     */

    public int twoSum62(int [] nums, int target) {
        if (nums == null) {
            return 0;

        }

        // num -> whether num appears in a pair
        Map<Integer, Boolean> map = new HashMap<>();

        int count = 0;
        for (int num : nums) {
            if (map.containsKey(target - num)) {
                count++;
                map.put(target - num, true);
                map.put(num, true);
            } else {
                map.put(num, false);
            }
        }

        return count;

    }
}
