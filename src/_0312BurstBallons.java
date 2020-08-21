public class _0312BurstBallons {
    /**
     * -> Intuition: Backtracking: O(n!)
     *
     * -> [Identify the redundant work and optimize]
     *
     * for any balloons left, the maxCoins doesn't depends on the balloons already bursted.
     * Indicating that we can use memorization (top down) or dynamic programming (bottom up)
     * for all the cases from small numbers of balloon until n balloons.
     *
     * How many cases are there? For k balloons there are C(n, k) cases
     * And for each case it need to scan the k balloons to compare.
     * The sum is quite big still. It is better than O(n!) but worse than O(2^n).
     *
     * -> [many similar sub problems -> Divide and Conquer?]
     * the nature way to divide the problem is burst one balloon
     * and separate the balloons into 2 sub sections one on the left and one one the right.
     * However, the left and right become adjacent and have effects on the maxCoins in the future.
     *
     * * [Reverse Thinking]
     * the coins you get for a balloon does not depend on the balloons already burst.
     * So instead of dividing the problem by the 1st balloon, divide it by the last balloon.
     *
     * Why is that?
     * Because only the 1st and last balloons we are sure of their adjacent balloons before hand!
     * 1) For the first we have nums[i-1]*nums[i]*nums[i+1]
     * 2) for the last we have nums[-1]*nums[i]*nums[n].
     *
     * OK. Think about n balloons if i is the last one to burst, what now?
     * We can see that the balloons is again separated into 2 sections.
     * But this time since the balloon i is the last balloon of all to burst,
     * the left and right section now has well defined boundary and do not affect each other!
     * Therefore we can do either recursive method with memoization or dp.
     */

    /**
     * 1. Divide and Conquer with Memoization
     *
     * ? How to come up with the params for memo
     *
     * Tricks:
     * a) Pad the beginning & end of the array with 1,
     * since the problem defines it this way, it won't affect the final value,
     * and most importantly it eliminates the need to deal with these special cases
     *
     * b) working backwards will allow us to cleanly divide the array into sub-problems
     * ("reverse thinking")
     * Suppose we work frontwards, then relation looks like:
     * dp[i][j] = max(nums[k - 1] * nums[k] * nums[k + 1] + dp[i][k] + dp[k][j]) (k in (i,j))
     * but now nums[k - 1] right neighbor becomes nums[k + 1], nums[k + 1] left neighbor becomes nums[k - 1]
     * and these two neighbor index is out of bound of the sub-problems range (i, k) and (k, j)
     *
     * c) Pop all the 0 balloons first and remove them from the array (since they are worth nothing)
     *
     * d) There are 3 variables in our main equation: the values of the 3 balloons to pop.
     * Again, we use the two 1's we just padded the array with to eliminate two of those variables off the bat
     *
     * e) Now just try all the possible middle balloons to pop (the 3rd variable).
     * For each balloon we choose, use it as the right and left ballon of the next level of recursion, along with the padded 1's, and so forth.
     *
     * 5) Base case is when there are no more balloons between the left and right balloon indexes (left+1 == right)
     *
     * Time complexity:
     * Space complexity:
     */
    public int maxCoins1(int[] iNums) {
        int[] nums = new int[iNums.length + 2];
        int n = 1;
        for (int x : iNums) {
            if (x > 0) {
                nums[n++] = x;
            }
        }
        nums[0] = nums[n++] = 1;

        int[][] memo = new int[n][n];
        return burst(memo, nums, 0, n - 1);
    }

    public int burst(int[][] memo, int[] nums, int left, int right) {
        if (left + 1 == right) {
            return 0;
        }
        if (memo[left][right] > 0) {
            return memo[left][right];
        }
        int ans = 0;
        for (int i = left + 1; i < right; ++i) {
            ans = Math.max(ans, nums[left] * nums[i] * nums[right]
                    + burst(memo, nums, left, i) + burst(memo, nums, i, right));
        }
        memo[left][right] = ans;
        return ans;
    }

    /**
     * 2. DP
     *
     * dp[i][j]: coins obtained from bursting all the balloons (i, j)
     * dp[i][j] = max(nums[i] * nums[k] * nums[j] + dp[i][k] + dp[k][j]) (k in (i+1,j))
     *
     * a) Bottom-up approaches always starts from the base cases and builds upward
     * b) Base-case is: when left+1 == right, then return 0. A.k.a. there are no balloons in the middle to pop.
     * So these base case values are already stored in our DP array!
     * c) The next case is when we have only 1 balloon between left and right.
     * k represents the distance between left and right, and k = 2 means that there is one balloon between left and right
     * d) So first we iterate through the entire array and find every result when there is just 1 ballon between left and right,
     * and when that's finished, increase the distance k between left and right
     * e) Notice that dp line assigns dp[left][right] to nums[left] * nums[i] * nums[right] + dp[left][i] + dp[i][right])
     * f) dp[left][i] and dp[i][right]) when k=2 are equal to the base cases! Equals 0!
     * We are always referencing values from the previous iterations.
     * g) Keep increasing the number of balloons in between left and right, until we reach the final case left = 0 and right=n-1
     *
     * Time complexity: O(n ^ 3)
     * Space complexity:
     */
    public int maxCoins2(int[] iNums) {
        int[] nums = new int[iNums.length + 2];
        int n = 1;
        for (int x : iNums) if (x > 0) {
            nums[n++] = x;
        }
        nums[0] = nums[n++] = 1;


        int[][] dp = new int[n][n];
        for (int k = 2; k < n; ++k)
            for (int left = 0; left < n - k; ++left) {
                // left < n - k, right < n
                // k = 2, left = 0, right = 2, i = left + 1 = 1
                int right = left + k;
                for (int i = left + 1; i < right; ++i) {
                    dp[left][right] = Math.max(dp[left][right],
                            nums[left] * nums[i] * nums[right] + dp[left][i] + dp[i][right]);
                }
            }

        return dp[0][n - 1];
    }
}
