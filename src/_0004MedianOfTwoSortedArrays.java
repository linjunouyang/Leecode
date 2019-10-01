public class _4MedianOfTwoSortedArrays {
    /**
     * 1. findKth()
     *
     * O(log(m + n)) -> O(log((m + n) / 2) -> O(log Kth)
     *
     * -> T(k) = T(k/2) + O(1)
     *
     * findKth(A, B, k) -> findKth(A, B, k/2) -> we need to throw away k/2 elements in O(1) time
     *
     * -> Comparing A[k/2 - 1] B[k/2 - 1]
     *
     * Time complexity: O(log(m + n))
     * Space complexity: O(log(m + n))
     *
     * Runtime: 2 ms, faster than 100.00% of Java online submissions for Median of Two Sorted Arrays.
     * Memory Usage: 45.1 MB, less than 93.75% of Java online submissions for Median of Two Sorted Arrays.
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;

        if (len % 2 == 0) {
            return (findKth(nums1, 0, nums2, 0, len / 2)
                    + findKth(nums1, 0, nums2, 0, len / 2 + 1)
            ) / 2.0;
        }

        return findKth(nums1, 0, nums2, 0, len / 2 + 1);
    }

    // find kth number of two sorted array

    // [1, 2] [3, 4]

    // findKth(A, 0, B, 0, 2)
    // A[hOA = 0 + 1 - 1 = 0] = 1, B[hoB = 0 + 1 - 1 = 0] = 3
    // findKth(A, 1, B, 0, 1)
    // return Math.min(A[1], B[0])

    // findKth(A, 0, B, 0, 3)
    // hOA = A[0 + 1 - 1] = 1, hoB = B[ 0 + 1 - 1] = 3;
    // findKth(A, 1, B, 0, 2)
    // hoA = A[1 + 1 - 1] = 2 < hoB = B[0 + 1 - 1] = 3;
    // findKth(A, 2, B, 0, 1)
    // Math.min(A[2], B[0])

    public static int findKth(int[] A, int startOfA, int[] B, int startOfB, int k) {
        // recursive calls might cause startOfA or startOfB bigger than size (whole array is thrown away)
        // Math.min(A[startOfA], B[startOfB]) ArrayIndexOutOfBounds
        if (startOfA >= A.length) {
            return B[startOfB + k - 1];
        }

        if (startOfB >= B.length) {
            return A[startOfA + k - 1];
        }

        // k = 1, halfKthOfA & halfKthOfB is not right.
        if (k == 1) {
            return Math.min(A[startOfA], B[startOfB]);
        }

        // if a small array doesn't have k/2 elements,
        // we can just throw away k/2 from the big array
        // small array ( < k / 2) + thrown elements from big array ( = k / 2) < k
        // our target is not thrown away
        // To make sure we throw k/2 elements from big array, we set halfKthofSmall = Integer.MAX_VAlUE;
        // (just imagine two endless arrays: origin elements + endless Integer.MAX_VALUE)
        // NOTICE: we are comparing k/2th element, so it's k/2 - 1
        int halfKthOfA = startOfA + k / 2 - 1 < A.length
                ? A[startOfA + k / 2 - 1]
                : Integer.MAX_VALUE;
        int halfKthOfB = startOfB + k / 2 - 1 < B.length
                ? B[startOfB + k / 2 - 1]
                : Integer.MAX_VALUE;

        if (halfKthOfA < halfKthOfB) {
            // k - k / 2 instead of k /2
            // for odd numbers like 5, we throw away 2, we want to find the NEW 3rd (5 - 5/2 = 3 instead of 5/2 = 2) number
            // NOTICE: we are throwing away k/2 elements, so it's k/2
            return findKth(A, startOfA + k / 2, B, startOfB, k - k / 2);
        } else {
            return findKth(A, startOfA, B, startOfB + k / 2, k - k / 2);
        }
    }
}
