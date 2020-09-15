public class _0034FindFirstAndLastPositionOfElementInSortedArray {
    /**
     * 1. Binary Search
     *
     * Time: O(logn)
     * Space: O(1)
     *
     * Variations:
     * https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/discuss/14701/A-very-simple-Java-solution-with-only-one-binary-search-algorithm
     * implement bs as find first >=, and find(target) find (target + 1)
     * but needs to watch out for overflow from target + 1
     * Solution: make target of type long
     *
     *
     */
    public int[] searchRange(int[] nums, int target) {
        // nums == null, 0 length
        int[] range = {-1, -1};

        if (nums == null) {
            return range;
        }

        // explore left bound
        binarySearch(nums, target, true, range);

        if (range[0] == -1) {
            return range;
        }

        // explore right bound
        binarySearch(nums, target, false, range);

        return range;
    }

    private void binarySearch(int[] nums, int target, boolean goLeft, int[] range) {
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = mid + 1;
            } else if (nums[mid] > target) {
                end = mid - 1;
            } else {
                if (goLeft) {
                    if (mid < range[0] || range[0] == -1) {
                        range[0] = mid;
                    }
                    end = mid - 1;
                } else {
                    if (mid > range[1]) {
                        range[1] = mid;
                    }
                    start = mid + 1;
                }
            }
        }
    }
}
