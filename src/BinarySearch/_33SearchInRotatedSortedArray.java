package BinarySearch;

public class _33SearchInRotatedSortedArray {
    /*
    Two Binary Search.

    First, binary search for existing min index.
    start < end prevents useless loop (start = end, that must be the min)
    When nums[mid] < nums[end], mid might be min, so end = mid instead of mid - 1.
    Otherwise, mid can't be min, so start = mid + 1;
    searching for min is an implicit goal.
    Even if the first mid = min, we still must narrow range down to 1

    Next, binary search for potential target.
    Decide search range by comparing target and nums[end];
    start = end doesn't guarantee the answer, we still need to check.
    searching for a specifc target is clear.
    we can check nums[mid] == target in the loop to exit loop earlier

    Time Complexity: O(logn)
    Space Complexity: O(1)

    Follow up:
    How to work for both ascending and descending order?
    (Microsoft and others)
    */
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0;
        int m = nums.length - 1;
        int end = m;
        while (start < end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < nums[end]) {
                end = mid; // end = mid - 1 is wrong for [8, 9, 2, 3, 4]
            } else {
                start = mid + 1;
            }
        }

        int minIdx = start;

        start = (target <= nums[m]) ? minIdx : 0;
        end = (target <= nums[m]) ? m : minIdx -1;
        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (target < nums[mid]) {
                end = mid - 1;
            } else if (target > nums[mid]) {
                start = mid + 1;
            } else {
                return mid;
            }
        }

        // must check nums[start] == target if use start < end.
        return -1;
    }

}
