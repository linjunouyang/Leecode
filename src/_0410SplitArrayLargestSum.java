public class _0410SplitArrayLargestSum {
    /**
     * 1. Brute Force: DFS + greedy?
     *
     * For each element, append it to the previous subarray or start a new one (if the number of subarrays does not exceed m).
     * The sum of the current subarray can be updated at the same time.
     *
     * Time complexity: O(n ^ m) (n - 1, m - 1) solutions (factorial)
     * Space complexity: O(n)
     */

    /**
     * 2. DP
     *
     * non-aftereffect property:
     * once the state of a certain stage is determined, it is not affected by the state in the future.
     * In this problem, if we get the largest subarray sum for splitting nums[0..i] into j parts,
     * this value will not be affected by how we split the remaining part of nums.
     *
     * f[i][j]: the minimum largest subarray sum for splitting nums[0 ... i] into j parts
     *
     * f[i][j] = min( max(f[k][j-1], nums[k + 1] + ... + nums[i]) ) (0 <= k < i)
     *
     * For corner situations, all the invalid f[i][j] should be assigned with INFINITY,
     * and f[0][0] should be initialized with 0.
     *
     * let's say m = 2
     * 0 0xx
     * 1 xOx
     * 2 xOO
     * 3 xOO
     * 4 xOO
     * 5 xOO
     *
     * Time complexity: O(n ^ 2 * m)
     * O(n * m) number of states
     * To compute each state f[i][j], we need to go through the whole array to find the optimum k.
     * This requires another O(n)O(n) loop. So the total time complexity is O(n ^ 2 * m)
     *
     * Space complexity: O(n * m)
     */
    public int splitArray2(int[] nums, int m) {
        int n = nums.length;
        int[][] f = new int[n + 1][m + 1];
        int[] sub = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            for (int j = 0; j <= m; j++) {
                f[i][j] = Integer.MAX_VALUE;
            }
        }
        for (int i = 0; i < n; i++) {
            sub[i + 1] = sub[i] + nums[i];
        }
        f[0][0] = 0;
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                for (int k = 0; k < i; k++) {
                    f[i][j] = Math.min(f[i][j], Math.max(f[k][j - 1], sub[i] - sub[k]));
                }
            }
        }
        return f[n][m];
    }

    /**
     * 2. Efficient DP
     *
     * dp[s,j] is the solution for splitting subarray n[0]...n[j] into s parts.
     *
     * dp[s+1,i] = min{ max{dp[s,j], n[j + 1] + ... n[i]}}   s <= j < i
     * so for every row, we update from right to left;
     *
     * https://leetcode.com/problems/split-array-largest-sum/discuss/89816/DP-Java
     *
     *
     */
    public int splitArray22(int[] nums, int m) {
        int L = nums.length;
        int[] S = new int[L+1];
        S[0]=0;
        for(int i=0; i<L; i++)
            S[i+1] = S[i]+nums[i];

        int[] dp = new int[L];
        for (int i=0; i<L; i++) {
            dp[i] = S[i + 1];
        }

        for(int s=2; s<=m; s++)
        {
            for(int i = L - 1; i >= s - 1; i--)
            {
                dp[i]=Integer.MAX_VALUE;
                for(int j=i-1; j>=s-2; j--)
                {
                    // faster than reversed j order
                    // as j becomes smaller, dp[j] becomes smaller
                    // but last subarray becomes bigger
                    int t = Math.max(dp[j], S[i+1]-S[j+1]);
                    if(t<=dp[i])
                        dp[i]=t;
                    else
                        break;
                }
            }
        }

        return dp[L - 1];
    }

    /**
     * 3. Greedy + Binary Search
     *
     * Explanation:
     * Solution comment
     * https://leetcode.com/problems/split-array-largest-sum/discuss/89817/Clear-Explanation%3A-8ms-Binary-Search-Java
     *
     *
     * Set the search range between min=(largest single value) and max=(sum of all values).
     * because we're looking for the min largest sum
     * no matter what groups you create, largest sum of the subarray must be greater than or equal to the largest value in the original input array.
     * (This assumes no negative numbers.)
     *
     * Calculate the midpoint between min and max.
     * This midpoint is the subSum target when we split array into m parts without exceeding it.
     *
     * Split the nums list into groups such that no group has a value larger than the chosen midpoint.
     * Note that we may end up with too many or too few groups. That's fine.
     *
     * Compare the number of groups we created against the target m.
     * 1) If we created too many groups, the final answer must be between mid+1 and max.
     * because we need fewer groups and the way to achieve fewer groups is to increase the allowed maximum sum in each group.
     *
     * 2) if the number of groups is too small, we know the final answer is between min and mid-1
     * because we need to increase the number of groups -> the target sum is something smaller than the one we used.
     * This is actually also a possible answer assuming m is valid because you can always take any group and split it up to make more groups,
     * so the mid value you targeted is at worst, higher than the real value.
     *
     * 3) if the number of groups is just right, we have a possible answer, so remember that answer.
     * However, we should keep searching just in case there is a better answer.
     * We're ultimately looking for smaller maximum sums, so the potentially better answer is between min and mid;
     *
     * Repeat the process until there is nothing else to search.
     * Then use the minimum value we found during the above process.
     *
     * Time complexity: O(n * log(nums sum - max num))
     * Space complexity: O(1)
     */
    public int splitArray(int[] nums, int m) {
        int max = 0; long sum = 0;
        for (int num : nums) {
            max = Math.max(num, max);
            sum += num;
        }
        if (m == 1) return (int)sum;
        // binary search
        long l = max; long r = sum;
        while (l < r) {
            long mid = (l + r)/ 2;
            if (valid(mid, nums, m)) {
                // if count<=m, we use r=mid; Because mid is still a possible value to divide the array into m sub-arrays.
                // if we use r=mid-1, we may bypass the true value.
                r = mid;
            } else {
                // if count>m, we use l=mid+1; mid here is not a possible candidate value, s
                // since we have at least (m+1) sub-arrays when using mid value.
                l = mid + 1;
            }
        }
        // the exit condition for binary search is r == l, so we can return either of them
        return (int)l;
    }

    // Whether we can divide nums into m parts, and max sub-sum is <= target
    public boolean valid(long target, int[] nums, int m) {
        int count = 1;
        long total = 0;
        for(int num : nums) {
            total += num;
            if (total > target) {
                total = num;
                count++;
                if (count > m) {
                    return false;
                }
            }
        }
        return true;
    }
}
