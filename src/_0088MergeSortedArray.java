import java.util.Arrays;

public class _0088MergeSortedArray {
    /**
     * 1. Intuition
     *
     * Time complexity: O((m+m)lg(m+n))
     * Space complexity: O(1)
     *
     * @param nums1
     * @param m
     * @param nums2
     * @param n
     */
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        if (nums1 == null && nums2 == null) {
            return;
        }

        for (int i = m; i < m + n; i++) {
            nums1[i] = nums2[i - m];
        }

        Arrays.sort(nums1);
    }

    /**
     * 2. Reversed of normal merging two sorted array
     *
     * Time complexity: O(m + n)
     * Space complexity: O(1)
     */
    public void merge2(int[] nums1, int m, int[] nums2, int n) {
        int end1 = m - 1;
        int end2 = n - 1;
        int end = m + n - 1;

        while (end1 >= 0 && end2 >= 0) {
            if (nums1[end1] >= nums2[end2]) {
                nums1[end--] = nums1[end1--];
            } else {
                nums1[end--] = nums2[end2--];
            }
        }

        while (end2 >= 0) {
            nums1[end--] = nums2[end2--];
        }



    }
}
