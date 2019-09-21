package Lintcode;

/**
 * Quicksort, Quickselect idea
 */
public class _373PartitionArraybyOddAndEven {
    /**
     * 1. Two Pointer
     *
     * 1st pointer: last odd index
     * 2nd pointer: iteration pointer
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param nums
     */
    public void partitionArray(int[] nums) {
        // write your code here
        if (nums == null) {
            return;
        }

        int lastOddIndex = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] % 2 != 0) {
                lastOddIndex++;
                swap(nums, lastOddIndex, i);
            }
        }

        return;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 2. Two pointers
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param nums
     */
    public void partitionArray2(int[] nums) {
        int start = 0;
        int end = nums.length - 1;

        while (start < end) {
            while (start < end && nums[start] % 2 == 1) {
                start++;
            }

            while (start < end && nums[end] % 2 == 0) {
                end--;
            }

            if (start < end) {
                int temp = nums[start];
                nums[start] = nums[end];
                nums[end] = temp;

                start++;
                end--;
            }
        }
    }
}
