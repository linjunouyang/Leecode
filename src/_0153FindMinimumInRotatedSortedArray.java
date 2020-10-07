public class _0153FindMinimumInRotatedSortedArray {
    /**
     * 1. Binary Search
     *
     * You use while (start <= end) if you are returning the match from inside the loop.
     *
     * You use while (start < end) if you want to exit out of the loop first,
     * and then use the result of start or end to return the match.
     *
     * (1) loop is left < right, which means inside the loop, left always < right
     * (2) since we use round up for mid, and left < right from (1), right would never be the same as mid
     * (3) Therefore, we compare mid with right, since they will never be the same from (2)
     * (4) if nums[mid] < nums[right], we will know the minimum should be in the left part, so we are moving right.
     * We can always make right = mid while we don't have to worry the loop will not ends. Since from (2), we know right would never be the same as mid, making right = mid will assure the interval is shrinking.
     * (5) if nums[mid] > nums[right], minimum should be in the right part, so we are moving left. Since nums[mid] > nums[right],mid can't be the minimum, we can safely move left to mid + 1, which also assure the interval is shrinking
     *
     * comparing with nums[left] won't work
     * consider two situations:
     * 1) [left, right] is increasing
     * 2) [left, right] has two increasing parts, but rotated.
     *
     * Time: O(logn)
     * Space: O(1)
     */
    public int findMin(int[] nums) {
        if (nums == null) {
            return Integer.MIN_VALUE;
        }

        int left = 0;
        int right = nums.length - 1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            if(nums[mid] < nums[right]) {
                right = mid;
            } else {
                left = mid + 1;
            }
        }
        return nums[left];
    }
}
