import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class _0349IntersectionOfTwoArrays {
    /**
     * 1. Two HashSet
     *
     * Set -> Array:
     * https://www.geeksforgeeks.org/set-to-array-in-java/
     *
     *
     * Time complexity: O(m + n)
     * Space complexity: O(min(m, n))
     *
     * Runtime: 2 ms, faster than 98.19% of Java online submissions for Intersection of Two Arrays.
     * Memory Usage: 37.4 MB, less than 72.97% of Java online submissions for Intersection of Two Arrays.
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection(int[] nums1, int[] nums2) {
        if (nums1 == null || nums2 == null) {
            return new int[]{};
        }

        int[] small = (nums1.length <= nums2.length) ? nums1 : nums2;
        int[] big = (small == nums1) ? nums2 : nums1;

        Set<Integer> hash = new HashSet<>();

        for (int num : small) {
            hash.add(num);
        }

        Set<Integer> intersection = new HashSet<>();

        for (int num : big) {
            if (hash.contains(num)) {
                intersection.add(num);
            }
        }

        int[] res = new int[intersection.size()];
        int i = 0;

        for (Integer num : intersection) {
            res[i++] = num;
        }

        return res;
    }

    /**
     * 2. Sort one, Binary search the other in sorted one
     *
     * Time complexity:
     * let min = min(m, n), max = max(m, n)
     * O(min * log(min) + max * log(min))
     *
     * Space complexity:
     * O(min)
     *
     * Runtime: 4 ms, faster than 15.98% of Java online submissions for Intersection of Two Arrays.
     * Memory Usage: 37.6 MB, less than 58.11% of Java online submissions for Intersection of Two Arrays.
     *
     * @param nums1
     * @param nums2
     * @return
     */

    public int[] intersection2(int[] nums1, int[] nums2) {
        int[] smallArr = (nums1.length <= nums2.length) ? nums1 : nums2;
        int[] bigArr = (smallArr == nums1) ? nums2 : nums1;

        Arrays.sort(smallArr);

        // binary search
        Set<Integer> hash = new HashSet<>();

        for (int target : bigArr) {
            if (hash.contains(target)) {
                continue;
            }

            if (binarySearch(smallArr, target)) {
                hash.add(target);
            }
        }

        int[] res = new int[hash.size()];
        int i = 0;

        for (int num : hash) {
            res[i++] = num;
        }

        return res;
    }

    private boolean binarySearch(int[] nums, int target) {
        int start = 0;
        int end = nums.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            if (nums[mid] < target) {
                start = start + 1;
            } else if (nums[mid] > target) {
                end = end - 1;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 3. Sort Two arrays, and merge
     *
     * Time complexity: O(nlogn + mlogm)
     * Space complexity: O(min)
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public int[] intersection3(int[] nums1, int[] nums2) {
        Arrays.sort(nums1);
        Arrays.sort(nums2);

        Set<Integer> common = new HashSet<>();

        int i = 0;
        int j = 0;

        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] < nums2[j]) {
                i++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                common.add(nums1[i]);
                i++;
                j++;
            }
        }

        int[] res = new int[common.size()];
        int index = 0;

        for (int num : common) {
            res[index++] = num;
        }

        return res;
    }
}
