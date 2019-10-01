public class _0283MoveZeroes {
    /**
     * 1. Two Pointers
     *
     * left: new array
     * right: old array
     *
     * Example
     * [0(l, r), 1, 0, 3, 12]
     * [0(l), 1(r), 0, 3, 12]
     * [1, 0(l), 0(r), 3, 12]
     * [1, 0(l), 0, 3(r), 12]
     * [1, 3, 0(l), 0, 12(r)]
     * [1, 3, 12, 0(l), 0] (r = nums.length)
     *
     *
     * Rewrite:
     *     int insertPos = 0;
     *
     *     for (int num: nums) {
     *         if (num != 0) nums[insertPos++] = num;
     *     }
     *
     *     while (insertPos < nums.length) {
     *         nums[insertPos++] = 0;
     *     }
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Move Zeroes.
     * Memory Usage: 38.1 MB, less than 83.92% of Java online submissions for Move Zeroes.
     *
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int left = 0;
        int right = 0;

        while (right < nums.length) {
            // we can't use nums[left] == 0
            // because nums[right] might still be zero
            if (nums[right] != 0) {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
            }
            right++;
        }
    }

    // [2(l, i), 1, 3, 0, 5, 0, 6]
    // [2, 1(l, i), 3, 0, 5, 0, 6]
    // [2, 1, 3(l, i), 0, 5, 0, 6]
    // [2, 1, 3, 0(l, i), 5, 0, 6]
    // [2, 1, 3, 0(l), 5(i), 0, 6]
    // [2, 1, 3, 5, 0(l), 0(i), 6]
    // [2, 1, 3, 5, 0(l), 0, 6(i)]
    // [2, 1, 3, 5, 6, 0(l), 0] i = length

    /**
     * 2.
     * doesn't use a temp variable and avoids unnecessary swaps when nums has leading non-zero element.
     *
     * Idea - Set leftMostZeroIndex to 0.
     * Iterate through the array, at each iteration i,
     * if nums[i] != 0 and i > leftMostZeroIndex, r
     * eplace the leftmost zero element nums[leftMostZeroIndex] with nums[i], and set nums[i] to 0.
     *
     * Note that i >= leftMostZeroIndex is always true,
     * and i == leftMostZeroIndex happens when nums has leading non-zero elements, e.g., {2, 1, 3, 0, 5, 0, 6}.
     * In such a case, we don't perform any swap, and keep incrementing i and leftMostZeroIndex until i > leftMostZeroIndex.
     *
     * @param nums
     */
    public void moveZeroes2(int[] nums) {
        int leftMostZeroIndex = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (i > leftMostZeroIndex) {
                    nums[leftMostZeroIndex] = nums[i];
                    nums[i] = 0;
                }

                leftMostZeroIndex++;
            }
        }
    }

}
