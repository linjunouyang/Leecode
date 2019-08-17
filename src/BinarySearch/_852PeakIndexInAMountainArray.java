package BinarySearch;

/**
 * Category: Explicit target
 *
 * The target is not a single invariant number,
 * instead it's a position that satisfies two inequations (A[i-1] < A[i] > A[i+1])
 *
 * The answer is guaranteed to exist, so no worries for array out of bound error inside the loop
 *
 * -----
 * 8.16 Passed by Intuitive solution and Comprehensive solution
 *
 */
public class _852PeakIndexInAMountainArray {

    /**
     * 1. Intuitive Binary Search
     *
     * Time complexity: O(logn)
     * Space complexity: O(1)
     *
     *
     * @param A
     * @return
     */
    public int peakIndexInMountainArray(int[] A) {
        int start = 0;
        int end = A.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (A[mid] < A[mid + 1]) {
                start = mid + 1;
            } else if (A[mid - 1] > A[mid]){
                end = mid - 1;
            } else {
                return mid;
            }
        }

        return -1;
    }

    /**
     * 2. Comprehensive Binary Search
     *
     * Time complexity: O(logn)
     * Space complexity: O(1)
     *
     * @param A
     * @return
     */
    public int peakIndexInMountainArray2(int[] A) {
        int start = 0;
        int end = A.length - 1;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;

            if (A[mid] < A[mid + 1]) {
                start = mid;
            } else if (A[mid - 1] > A[mid]) {
                end = mid;
            } else {
                return mid;
            }
        }

        if (A[start - 1] < A[start] ) {
            return start;
        }

        if (A[end - 1] < A[end] ) {
            return end;
        }

        return -1;

    }
}
