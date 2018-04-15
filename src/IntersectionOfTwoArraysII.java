import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class IntersectionOfTwoArraysII {
    public int[] intersect(int[] nums1, int[] nums2) {
        /**
         * Time Complexity: O(nlogn)
         * Space Complexity: O(n)
         */
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[]{};
        }

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        ArrayList<Integer> list = new ArrayList<>();
        int i = 0;
        int j = 0;
        while (i < nums1.length && j < nums2.length) {
            if (nums1[i] == nums2[j]) {
                list.add(nums1[i]);
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                i++;
            }
        }

        int[] res = new int[list.size()];
        int p = 0;
        for (int num : list) {
            res[p++] = num;
        }

        return res;
    }

    public int[] intersect2(int[] nums1, int[] nums2) {
        /**
         * Time Complexity: O(n);
         * Space Complexity: O(n);
         */
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[]{};
        }

        HashMap<Integer, Integer> map1 = new HashMap<>();
        for (int num : nums1) {
            if (map1.containsKey(num)) {
                map1.put(num, map1.get(num) + 1);
            } else {
                map1.put(num, 1);
            }
        }

        ArrayList<Integer> list = new ArrayList<>();
        for (int num : nums2) {
            if (map1.containsKey(num)) {
                if (map1.get(num) > 0) {
                    list.add(num);
                    map1.put(num, map1.get(num) - 1);
                }
            }
        }

        int[] res = new int[list.size()];
        int i = 0;
        for (int num : list) {
            res[i++] = num;
        }
        return res;
    }

    public int[] intersect3(int[] nums1, int[] nums2) {
        /**
         * time complexity: max(O(mlgm), O(nlgn), O(mlgn)) or max(O(mlgm),
         * O(nlgn), O(nlgm))
         * O(mlgm) <-- sort first array
         * O(nlgn) <— sort second array
         * O(mlgn) <— for each element in nums1, do binary search in nums2
         * O(nlgm) <— for each element in nums2, do binary search in nums1
         * space complexity: O(n) or O(m)
         */
        if (nums1 == null || nums2 == null || nums1.length == 0 || nums2.length == 0) {
            return new int[]{};
        }

        Arrays.sort(nums1);
        Arrays.sort(nums2);

        ArrayList<Integer> list = new ArrayList<>();
        int j = 0;
        for(int i = 0; i < nums1.length; i = j) {
            int index = binarySearch(nums2, nums1[i]); // lower-bound
            int count2 = 0;
            while (index >= 0 && index < nums2.length && nums2[index] == nums1[i]) {
                count2++;
                index++;
            }

            int count1= 0;
            while (j < nums1.length && nums1[j] == nums1[i]) {
                count1++;
                j++;
            }

            for (int p = 1; p <= Math.min(count1, count2); p++) {
                list.add(nums1[i]);
            }
        }

        int[] res = new int[list.size()];
        int k = 0;
        for (int num : list) {
            res[k++] = num;
        }
        return res;
    }

    public int binarySearch(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }

        int start = 0;
        int end = nums.length - 1;

        while (start + 1 < end) {
            int mid = (end - start) / 2 + start;
            if (nums[mid] < target) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (nums[start] == target) {
            return start;
        }
        if (nums[end] == target) {
            return end;
        }
        return -1;
    }
}
