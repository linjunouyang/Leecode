public class _1060MissingElementInSortedArray {
    /**
     * 1. Brute Force
     *
     * Linear Search
     */

    /**
     * 2. Binary Search
     *
     * What we are trying to find by binary search is a index where its missing number less than k,
     * but at its next index, the missing number more than k
     *
     * Time: O(logn)
     * Space: O(1)
     */
    public int missingElement(int[] nums, int k) {
        int totalMissInArray = countMissing(nums, nums.length - 1);
        if (k > totalMissInArray) {
            // [1, 2, 3, 5] total missing: 5 - 1 - 3 = 1 < k = 2
            // 5 + (2 - 1) = 6
            return nums[nums.length - 1] + (k - totalMissInArray);
        }

        int start = 0, end = nums.length;
        while(start < end) {
            int m = start + (start + end) / 2;
            int missing = countMissing(nums,m);
            if (missing >= k) {
                end = m;
            } else {
                start = m + 1;
            }
        }
        start--; //offset after binary search, otherwise the index is not correct, since we have `s = m + 1`
        return nums[start] + k - countMissing(nums, start);
    }

    private int countMissing(int[] nums, int i) {
        return nums[i] - nums[0] - i;
    }
}
