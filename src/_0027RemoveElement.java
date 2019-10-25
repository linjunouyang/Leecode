public class _0027RemoveElement {
    /**
     * 1. Two Pointers
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement(int[] nums, int val) {
        if (nums == null) {
            return 0;
        }

        int len = 0;
        for (int p = 0; p < nums.length; p++) {
            if (nums[p] != val) {
                nums[len++] = nums[p];
            }
        }

        return len;
    }

    /**
     * 2. Two Pointers - Consider elements to remove are rare
     *
     * For example, nums = [1,2,3,5,4], val = 4
     * he previous algorithm will do unnecessary copy operation of the first four elements.
     *
     * Another example is nums = [4,1,2,3,5], val = 4
     * It seems unnecessary to move elements [1,2,3,5][1,2,3,5] one step left
     * as the problem description mentions that the order of elements could be changed.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param nums
     * @param val
     * @return
     */
    public int removeElement2(int[] nums, int val) {
        int i = 0;
        int n = nums.length;

        while (i < n) {
            if (nums[i] == val) {
                nums[i] = nums[n - 1];
                n--;
            } else {
                i++;
            }
        }

        return n;
    }
}
