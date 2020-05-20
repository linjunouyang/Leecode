public class _0334IncreasingTripletSubsequence {
    /**
     * 1. One Pass with Two minimum keeper
     *
     * Notice that:
     *
     * for [1, 3, 0, 5] we will eventually arrive at big = 3 and small = 0
     * However, the solution still works, because big only gets updated when there exists a small that comes before it.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Increasing Triplet Subsequence.
     * Memory Usage: 38.9 MB, less than 95.35% of Java online submissions for Increasing Triplet Subsequence.
     *
     * @param nums
     * @return
     */
    public boolean increasingTriplet(int[] nums) {
        int small = Integer.MAX_VALUE;
        int big = Integer.MAX_VALUE;

        for (int n : nums) {
            if (n <= small) {
                small = n;
            } else if (n <= big) {
                big = n;
            } else {
                return true;
            }
        }

        return false;
    }
}
