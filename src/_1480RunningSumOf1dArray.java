public class _1480RunningSumOf1dArray {
    /**
     * 1. Prefix Sum
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int[] runningSum(int[] nums) {
        if (nums == null) {
            return null;
        }

        if (nums.length == 0) {
            return new int[]{};
        }

        int len = nums.length;
        int[] res = new int[len];

        res[0] = nums[0];
        for (int i = 1; i < len; i++) {
            res[i] = res[i - 1] + nums[i];
        }
        return res;

        // Stream
        // return IntStream.range(0,nums.length).map(i->i==0?nums[i]:(nums[i]+=nums[i-1])).toArray();
    }


}
