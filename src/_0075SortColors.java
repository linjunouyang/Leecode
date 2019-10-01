public class _0075SortColors {
    /**
     * 1. partition (2 parts), 2 times
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sort Colors.
     * Memory Usage: 35.4 MB, less than 99.21% of Java online submissions for Sort Colors.
     *
     * @param nums
     */
    public void sortColors(int[] nums) {
        if (nums == null) {
            return;
        }

        int lastZeroIndex = -1;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] == 0) {
                lastZeroIndex++;
                swap(nums, lastZeroIndex, i);
            }
        }

        int lastOneIndex = lastZeroIndex;

        for (int i = lastOneIndex + 1; i < nums.length; i++) {
            if (nums[i] == 1) {
                lastOneIndex++;
                swap(nums, lastOneIndex, i);
            }
        }

        return;
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /**
     * 2. Three Pointers
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Sort Colors.
     * Memory Usage: 35.1 MB, less than 99.21% of Java online submissions for Sort Colors.
     *
     * @param nums
     */
    public void sortColors2(int[] nums) {
        if (nums == null) {
            return;
        }

        int lastZeroIndex = -1;
        int firstTwoIndex = nums.length;

        int i = 0;

        while (i < firstTwoIndex) {
            if (nums[i] == 0) {
                lastZeroIndex++;
                swap(nums, lastZeroIndex, i);
                i++;
            } else if (nums[i] == 2) {
                // 可能换过来一个2，因此i不能++
                // l 1 2(i) 0 f
                //   1 0(i) 2(f)
                firstTwoIndex--;
                swap(nums, firstTwoIndex, i);
            } else if (nums[i] == 1) {
                i++;
            }
        }

        return;
    }
}
