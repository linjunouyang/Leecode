package Lintcode;

import java.util.Arrays;

/**
 * Given an array of integers, find two numbers that their difference equals to a target value.
 * where index1 must less than index2.
 *
 * Please note that your returned answers (both index 1 and
 * index2) are not zero based.
 *
 * Given nums [2, 7, 15, 24], target = 5
 * return [1, 2] (7 - 2 = 5)
 *
 *
 * Java Arrays.sort()
 * NULL: null pointer exception
 * Length 0: no problem
 */
public class TwoSum_DifferenceEqualsToTarget {

    public int[] twoSum7(int[] nums, int target) {
        int[] res = new int[2];

        if (nums == null || nums.length < 2) {
            return res;
        }

        if (target < 0) {
            target = -target;
        }

        Arrays.sort(nums);

        int j = 0;
        for (int i = 0; i < nums.length; i++) {
            // i, j
            if (i == j) {
                j++;
            }

            while (j < nums.length && nums[j] - nums[i] < target) {
                j++;
            }

            if (j < nums.length && nums[j] - nums[i] == target) {
                res[0] = i + 1;
                res[1] = j + 1;

//                if (res[0] > res[1]) {
//                    int temp = res[0];
//                    res[0] = res[1];
//                    res[1] = temp;
//                }

                return res;
            }
        }

        return res;
    }

}
