package BinarySearch;

/**
 *
 * -----
 * 8.22 First Pass. final return start takes me a while
 */
public class _35SearchInsertPosition {

    /**
     * 1. Binary Search
     *
     * Time complexity: O(lgn)
     * Space complexity: O(1)
     * @param nums
     * @param target
     * @return
     */
    public int searchInsert(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (target < nums[mid]) {
                end = mid - 1;
            } else if (target > nums[mid]) {
                start = start + 1;
            } else {
                return mid;
            }
        }

        return start;

    }

}
