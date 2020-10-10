public class _0031NextPermutation {
    /**
     * 1. Visualization, write some cases
     *
     * This problem says in-place:
     * in-place algorithm:
     * transforms input using no auxiliary data structure.
     * However a small amount of extra storage space is allowed for auxiliary variables.
     * The input is usually overwritten by the output as the algorithm executes.
     * In-place algorithm updates input sequence only through [replacement or swapping] of elements.
     * An algorithm which is not in-place is sometimes called not-in-place or out-of-place.
     *
     * if the elements are increasing from the right,
     * they are currently at their largest possible permutation,
     * so nothing can be done.
     * I think the tricky part is simply knowing where to swap and reversing the last digits.
     *
     * Time: O(n)
     * Space: O(1)
     * @param nums
     */
    public void nextPermutation(int[] nums) {
        int n = nums.length;
        int i = n - 2;
        while (i >= 0 && nums[i + 1] <= nums[i]) {
            i--;
        }
        if (i >= 0) {
            int j = n - 1;
            while (j > i && nums[j] <= nums[i]) {
                j--;
            }
            swap(nums, i, j);
        }
        reverse(nums, i + 1);
    }

    private void reverse(int[] nums, int start) {
        int i = start, j = nums.length - 1;
        while (i < j) {
            swap(nums, i, j);
            i++;
            j--;
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
