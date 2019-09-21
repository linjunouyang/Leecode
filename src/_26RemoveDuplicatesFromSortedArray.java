public class _26RemoveDuplicatesFromSortedArray {
    /**
     * 1. Two Pointers
     *
     * i is length, also the position for next different number
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param nums
     * @return
     */
    public int removeDuplicates(int[] nums) {
        int i = nums.length > 0 ? 1: 0;

        for (int n : nums) {
            if (n > nums[i - 1]) {
                nums[i] = n;
                i++;
            }
        }

        return i;
    }
}
