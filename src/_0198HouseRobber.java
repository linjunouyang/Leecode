/**
 * A good explanation:
 * https://leetcode.com/problems/house-robber/discuss/156523/From-good-to-great.-How-to-approach-most-of-DP-problems.
 *
 * Finding recurrence relation is important
 */
public class _0198HouseRobber {
    /**
     * 1. Dynamic Programming
     *
     * 由抢房屋的性质可以看出，抢前i个房屋能得到的最大值，与后面如何抢的方案无关，只与前i - 1个房屋的最优方案有关。
     * 这满足了动态规划的无后效性和最优子结构。
     *
     * 同时，由于题目不能抢相邻房屋，那么如果抢了第i个房屋，就不能抢第i - 1个房屋，
     * 可以得出前i个的最优方案也与前i - 2个的最优方案有关。
     *
     * f(k) = largest amount you can rob from considering the first k houses
     * f(k) = max(f(k - 2) + Ak, f(k -1))
     *
     * We choose the base case as f(–1) = f(0) = 0, which will greatly simplify our code as you can see.
     *
     * The answer will be calculated as f(n). We could use an array to store and calculate the result, but since at each step you only need the previous two maximum values, two variables are suffice.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param nums
     * @return
     */
    public int rob1(int[] nums) {
        int prevMax = 0;
        int currMax = 0;
        for (int x : nums) {
            int temp = currMax;
            currMax = Math.max(prevMax + x, currMax);
            prevMax = temp;
        }
        return currMax;
    }


    public int rob2(int[] nums) {
        int numHouse = nums.length;

        if (nums == null || numHouse== 0) {
            return 0;
        }

        if (numHouse == 1) {
            return nums[0];
        }

        int[] dp = new int[numHouse];
        dp[0] = nums[0];
        dp[1] = nums[1];
        int ans = Math.max(dp[0], dp[1]);
        int maxPrev = nums[0];

        for (int i = 2; i < numHouse; i++) {
            maxPrev = Math.max(maxPrev, dp[i - 2]);
            dp[i] = maxPrev + nums[i];
            ans = Math.max(ans, dp[i]);
        }

        return ans;
    }

}
