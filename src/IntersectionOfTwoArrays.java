import java.util.*;

public class IntersectionOfTwoArrays {
    /**
     * Solution A: Binary Search
     * Time complexity: O(nlogn)
     * Space complexity: O(n)
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[]{};
        }

        Arrays.sort(nums1);
        HashSet<Integer> set = new HashSet<>();

        for (Integer num : nums2) {
            if (binarySearch(nums1, num)) {
                set.add(num);
            }
        }

        int i = 0;
        int[] res = new int[set.size()];
        for (Integer num : set) {
            res[i++] = num;
        }
        return res;
    }

    public boolean binarySearch(int[] nums, int num) {
        if (nums == null || nums.length == 0) return false;
        int start = 0, end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] == num) {
                return true;
            } else if (nums[mid] > num) {
                end = mid;
            } else {
                start = mid;
            }
        }

        if (nums[start] == num || nums[end] == num) {
            return true;
        }
        return false;
    }

    /**
     * Solution B: Two-pointers in two sorted array
     * Time complexity: O(nlogn)
     * Space complexity: O(n)
     */
    public int[] intersection2(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[]{};
        }

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        int i = 0;
        int j = 0;
        HashSet<Integer> set = new HashSet<>();
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                set.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                i++;
            }
        }

        int p = 0;
        int[] res = new int[set.size()];
        for (Integer num : set) {
            res[p++] = num;
        }
        return res;
    }

    /**
     * Solution C: O(1) Search by HashSet's contains() method
     * Time Complexity: O(n)
     * Space Complexity: O(n)
     */

    public int[] intersection3(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[]{};
        }

        HashSet<Integer> set1 = new HashSet<>();
        for (Integer num : nums1) {
            set1.add(num);
        }

        HashSet<Integer> resSet = new HashSet<>();
        for (Integer num : nums2) {
            if (set1.contains(num)) {
                resSet.add(num);
            }
        }

        int i = 0;
        int[] res = new int[resSet.size()];
        for (Integer num : resSet) {
            res[i++] = num;
        }
        return res;
    }
}