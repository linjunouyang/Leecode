public class _0852PeakIndexInAMountainArray {
    /**
     * 1. Binary Search
     *
     * Time: O(logn)
     * Space: O(1)
     */
    public int peakIndexInMountainArray(int[] arr) {
        // base cases
        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < arr[mid + 1]) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        return left;
    }
}
