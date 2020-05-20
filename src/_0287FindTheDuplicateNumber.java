public class _0287FindTheDuplicateNumber {
    /**
     * 1. Floyd's Tortoise and Hare (Cycle Detection)
     *
     *
     * n + 1 integers in [1, n] -> there must be a duplicate
     *
     * nums[0] must be outside any cycle because the range is [1, n]
     *
     *
     * Why two loops?
     * index : [0, 1, 2, 3, 4, 5, 6, 7, 8, 9]
     * number: [2, 5, 9, 6, 9, 3, 8, 9, 7, 1]
     *
     * First loop: The intersection of the indexes (might not be the duplicate)
     * slow  : 2 -> 9 -> 1 -> 5 -> 3 -> 6 -> 8 -> 7
     * fast  : 2 -> 1 -> 3 -> 8 -> 9 -> 5 -> 6 -> 7
     *
     * 2 -> 9 -> 1 -> 5 -> 3 -> 6 -> 8 -> 7 (points back to 9)
     *
     * Second loop: the starting point of cycle
     * -> at least two pointers point to same number
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Find the Duplicate Number.
     * Memory Usage: 36.3 MB, less than 100.00% of Java online submissions for Find the Duplicate Number.
     *
     * @param nums
     * @return
     */
    public int findDuplicate(int[] nums) {
        // Find the intersection point of the two runners.
        int slow = nums[0];
        int fast = nums[0];

        do {
            slow = nums[slow];
            fast = nums[nums[fast]];
        } while (slow != fast);

        // Find the "entrance" to the cycle.
        slow = nums[0];

        //[3, 1, 3, 4, 2]
        // slow = 3, 4, 2, 3
        // fast = 3, 2, 4, 3
        while (slow != fast) {
            slow = nums[slow];
            fast = nums[fast];
        }

        return slow;
    }
}
