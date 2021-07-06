public class _0004MedianOfTwoSortedArrays {
    /**
     * 1. Two Pointers
     *
     * Time: O(m + n)
     * Space: O(1)
     */
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int index1 = 0;
        int index2 = 0;
        int med1 = 0;
        int med2 = 0;
        int end = (nums1.length + nums2.length) / 2;

        for (int i = 0; i <= end; i++) {
            med1 = med2;
            if (index1 == nums1.length) {
                med2 = nums2[index2];
                index2++;
            } else if (index2 == nums2.length) {
                med2 = nums1[index1];
                index1++;
            } else if (nums1[index1] < nums2[index2] ) {
                med2 = nums1[index1];
                index1++;
            }  else {
                med2 = nums2[index2];
                index2++;
            }
        }

        // the median is the average of two numbers
        if ((nums1.length + nums2.length) % 2 == 0) {
            return (med1 + med2) / 2.0;
        }

        return med2;
    }

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
     */
    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
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

    /**
     * 3. Binary Search
     *
     * https://leetcode-cn.com/problems/median-of-two-sorted-arrays/solution/he-bing-yi-hou-zhao-gui-bing-guo-cheng-zhong-zhao-/#comment
     *
     * Time: O(log min(m, n))
     * Space: O(1)
     */
    public double findMedianSortedArrays3(int[] nums1, int[] nums2) {
         if (nums1.length > nums2.length) {
             // 1. When we compare nums1[i], nums2[j], i is calculated from valid [left, right]
             //    but j might be out of bounds, so always make nums2 point to longer array
             // 2. Improves time complexity: O(log min(m, n))
             int[] temp = nums1;
             nums1 = nums2;
             nums2 = temp;
         }

        int m = nums1.length;
        int n = nums2.length;

        // 分割线左边的所有元素需要满足的个数 m + (n - m + 1) / 2;
        int totalLeft = (m + n + 1) / 2;

        // 在 nums1 的区间 [0, m] 里查找恰当的分割线，
        // 使得 nums1[i - 1] <= nums2[j] && nums2[j - 1] <= nums1[i]
        int left = 0;
        int right = m; // not m - 1, because we're trying to find first index of right part (m means it's empty)

        // we want to achieve: nums1[i - 1] <= nums2[j] && nums2[j - 1] <= nums1[i]
        while (left < right) {
            // Why +1?
            // we need to access nums1[i - 1], to avoid i - 1 < 0
            int i = left + (right - left + 1) / 2;
            int j = totalLeft - i;

            if (nums1[i - 1] > nums2[j]) {
                // because i is right-skewed, setting right = i will cause dead loop.
                // 下一轮搜索的区间 [left, i - 1]
                right = i - 1;
            } else {
                // we know the answer definitely exists, so no need to check second condition here
                // nums2[j - 1] > nums[1]

                // because i is right-skewed, if left + 1 = right (i = right), setting left = i + 1 will be out of bounds
                // 下一轮搜索的区间 [i, right]
                left = i;
            }
        }

        int i = left;
        int j = totalLeft - i;

        int nums1LeftMax = i == 0 ? Integer.MIN_VALUE : nums1[i - 1];
        int nums1RightMin = i == m ? Integer.MAX_VALUE : nums1[i];
        int nums2LeftMax = j == 0 ? Integer.MIN_VALUE : nums2[j - 1];
        int nums2RightMin = j == n ? Integer.MAX_VALUE : nums2[j];

        if (((m + n) % 2) == 1) {
            return Math.max(nums1LeftMax, nums2LeftMax);
        } else {
            return (double) ((Math.max(nums1LeftMax, nums2LeftMax) + Math.min(nums1RightMin, nums2RightMin))) / 2;
        }

    }
}
