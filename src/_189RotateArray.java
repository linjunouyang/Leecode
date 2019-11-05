public class _189RotateArray {
    /**
     * 1. Using Extra Array
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 1 ms, faster than 51.94% of Java online submissions for Rotate Array.
     * Memory Usage: 36.6 MB, less than 100.00% of Java online submissions for Rotate Array.
     *
     * @param nums
     * @param k
     */
    public void rotate(int[] nums, int k) {
        if (nums == null || k % nums.length == 0) {
            return;
        }

        int[] temp = new int[nums.length];

        for (int i = 0; i < nums.length; i++) {
            temp[(i + k) % nums.length] = nums[i];
        }

        for (int i = 0; i < nums.length; i++) {
            nums[i] = temp[i];
        }
    }

    /**
     * 2. Using Cyclic Replacements
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate Array.
     * Memory Usage: 37.8 MB, less than 96.15% of Java online submissions for Rotate Array.
     *
     * To-do: check out proof written by mglezer
     * https://leetcode.com/problems/rotate-array/solution/
     *
     *
     * @param nums
     * @param k
     */
    public void rotate2(int[] nums, int k) {
        k = k % nums.length;

        int count = 0;

        for (int start = 0; count < nums.length; start++) {
            // current position
            int current = start;
            // current element
            int currNum = nums[start];
            do {
                // next position
                int next = (current + k) % nums.length;
                // temp: next element
                int nextNum = nums[next];

                nums[next] = currNum;

                current = next;
                currNum = nextNum;

                count++;
            } while (start != current);
        }
    }


    /**
     * 3. Using Reverse
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Goal1:
     * Assume range1 = [0, n - k - 1]. need to move to the right by k steps.
     * i - > i+k
     *
     * Goal2:
     * Assume range2 = [n - k, n - 1]. need  move beyond the boundary of the array,
     * i -> (i + k) % n = i + k - n
     *
     * First reverse: i -> (n - 1) - i
     * range 1 = [0, n - k - 1] -> [k, n - 1]
     * range 2 = [n - k, n - 1] -> [0, k - 1]
     *
     * Second reverse: i -> (k - 1) - i
     * k - 1 - (n - 1 - i) -> k - n + i
     *
     * Third reverse: i -> k + (n - 1) - i
     * k + (n - 1) - (n - 1 - i) = k + n - 1 - n + 1 + i = i + k
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Rotate Array.
     * Memory Usage: 37.1 MB, less than 100.00% of Java online submissions for Rotate Array.
     *
     * @param nums
     * @param k
     */
    public void rotate3(int[] nums, int k) {
        k %= nums.length;
        reverse(nums, 0, nums.length - 1);
        reverse(nums, 0, k - 1);
        reverse(nums, k, nums.length - 1);
    }

    public void reverse(int[] nums, int start, int end) {
        while (start < end) {
            int temp = nums[start];
            nums[start] = nums[end];
            nums[end] = temp;

            start++;
            end--;
        }
    }




}
