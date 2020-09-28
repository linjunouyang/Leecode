/**
 * Similar:
 * 852. Peak Index in a Mountain Array
 */
public class _0162FindPeakElement {
    /**
     * 1. Binary Search
     *
     * Let D stands for the derivative, we know that D(-1)>0, D(n-1)<0 (nums[-1] = nums[n] = -∞)
     * If D(mid-1) < 0, there must be a value x between [low, mid-1] for that from left to right of it ,
     * D>0 becomes D<0.
     *
     * Another perspective:
     * Lets say you have a mid number at index x, nums[x]
     * if (num[x+1] > nums[x]), that means a peak element HAS to exist on the right half of that array,
     * because every number is unique ->
     * 1) the numbers keep increasing on the right side, and the peak will be the last element.
     * 2) the numbers stop increasing at nums[y-1] > nums[y], nums[y-1] is a peak element.
     *
     * This same logic can be applied to the left hand side (nums[x] < nums[x-1]).
     *
     * ----
     *
     * Assume we initialize left = 0, right = nums.length - 1. The invariant I'm using is the following:
     * nums[left - 1] < nums[left] && nums[right] > nums[right + 1]
     *
     * Time: O(log n)
     * Space: O(1)
     *
     */
    public int findPeakElement(int[] nums) {
        // base cases
        if (nums == null) {
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (nums[mid] < nums[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
