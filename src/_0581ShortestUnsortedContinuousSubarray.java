public class _0581ShortestUnsortedContinuousSubarray {
    /**
     * 1. Brute Force
     *
     * Every possible subarray (nested for loop: start, end):
     *  find min and max in the window
     *  make sure max from 1st part <= min in the window && max in the window <= min in the 3rd part
     *  check whether 1st and 3rd part is sorted
     *
     * Time: O(n ^ 3)
     * Space: O(1)
     *
     */

    /**
     * 3. Sorting
     *
     * Sort a copy and compare
     *
     * Time: O(nlogn)
     * Space: O(n)
     */

    /**
     * 4. Visualization / slope
     *
     * Pre-idea: using stack:
     * Sol4:
     * https://leetcode.com/problems/shortest-unsorted-continuous-subarray/solution/#approach-5-without-using-extra-space-accepted
     *
     * Sol5 same idea but easier to understand:
     * left-right: when slope falls, find min value
     * right-left: when slope rises, find max value
     * find pos for min and max
     *
     * Time: O(n)
     * Space: O(1)
     *
     */
    public int findUnsortedSubarray(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int max = Integer.MIN_VALUE;
        int end = -1;
        //iterate from beginning of array
        //find the last element which is smaller than the last seen max from
        //its left side and mark it as end
        for (int i = 0; i < nums.length; i ++) {
            if (nums[i] < max) {
                // unsorted part ends AT LEAST here
                end = i;
            }
            max = Math.max(max, nums[i]);
        }

        int min = Integer.MAX_VALUE;
        int begin = -1;
        //iterate from end of array
        //find the last element which is bigger than the last seen min from
        //its right side and mark it as begin
        for (int i = nums.length - 1; i >= 0; i --) {
            if (nums[i] > min) {
                // unsorted part starts AT LEAST here
                begin = i;
            }
            min = Math.min(min, nums[i]);
        }

        // another trick is to set end = -2 and begin = -1 to avoid this check
        if (end == -1 && begin == -1) {
            // already sorted, including cases like nums.length = 1
            return 0;
        }

        return end - begin + 1;
    }
}
