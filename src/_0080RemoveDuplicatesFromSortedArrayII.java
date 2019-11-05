public class _0080RemoveDuplicatesFromSortedArrayII {
    /**
     * 1. Two Pointers
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int i = 0;
        for (int n : nums) {
            if (i < 2 || n > nums[i - 2]) {
                nums[i++] = n;
            }
        }
        return i;
    }
}
