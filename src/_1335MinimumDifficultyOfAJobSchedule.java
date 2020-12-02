import java.util.Arrays;

public class _1335MinimumDifficultyOfAJobSchedule {
    /**
     * 1. bottom-up dp
     *
     * Time: O(jobs * jobs * d)
     * Space: O(jobs * d)
     *
     * https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/discuss/490297/Java-Bottom-Up-DP
     *
     * Similar Logic:
     * Split Array Largest Sum (Binary Search Solution is also there)
     * Pallindrome Partitioning III
     */
    public int minDifficulty(int[] jobDifficulty, int d) {
        int jobs = jobDifficulty.length;
        if (jobs < d) {
            return -1;
        }
        int[][] dp = new int[d][jobs];

        dp[0][0] = jobDifficulty[0];
        for(int job = 1; job < jobs; job++){
            dp[0][job] = Math.max(jobDifficulty[job], dp[0][job - 1]);
        }

        for (int day = 1; day < d; day++){
            for (int job = day; job < jobs; job++){
                int localMax = jobDifficulty[job];
                dp[day][job] = Integer.MAX_VALUE;
                for (int lastDayFirstJob = job; lastDayFirstJob >= day; lastDayFirstJob--){
                    // More expensive to calculate max if starting from the other end
                    localMax = Math.max(localMax,jobDifficulty[lastDayFirstJob]);
                    // We can't dp[d+1][n+1] and delete initialization
                    // because for first day, it's max(dp[0][j] = 0, dp[0][r-1] + localMax)
                    dp[day][job] =  Math.min(dp[day][job], dp[day - 1][lastDayFirstJob -1] + localMax);
                }
            }
        }

        return dp[d - 1][jobs - 1];
    }

    /**
     * 1.1 bottom-up dp (space optimized)
     *
     * Time: O(jobs * jobs * d)
     * Space: O(jobs)
     */
    public int minDifficulty11(int[] jobDifficulty, int d) {
        int jobs = jobDifficulty.length;
        if (jobs < d) {
            return -1;
        }
        int[] dp = new int[jobs];

        dp[0] = jobDifficulty[0];
        for(int job = 1; job < jobs; job++){
            dp[job] = Math.max(jobDifficulty[job], dp[job - 1]);
        }

        for (int day = 1; day < d; day++){
            for (int job = jobs - 1; job >= day; job--){
                int localMax = jobDifficulty[job];
                dp[job] = Integer.MAX_VALUE;
                for (int lastDayFirstJob = job; lastDayFirstJob >= day; lastDayFirstJob--){
                    // More expensive to calculate max if starting from the other end
                    localMax = Math.max(localMax,jobDifficulty[lastDayFirstJob]);
                    // We can't dp[d+1][n+1] and delete initialization
                    // because for first day, it's max(dp[0][j] = 0, dp[0][r-1] + localMax)
                    dp[job] =  Math.min(dp[job], dp[lastDayFirstJob -1] + localMax);
                }
            }
        }

        return dp[jobs - 1];
    }

    /**
     * 2. Top-down DP with cache (dfs)
     *
     * Time: O(days * jobs * jobs)
     * Space: O(days * jobs)
     */
    public int minDifficulty2(int[] jobDifficulty, int d) {
        int jobs = jobDifficulty.length;
        if (jobs < d) {
            return -1;
        }

        int[][] memo = new int[jobs][d + 1];
        for (int[] row : memo) {
            Arrays.fill(row, -1);
        }

        return dfs(d, 0, jobDifficulty, memo);
    }

    private int dfs(int leftDays, int firstJob, int[] jobDifficulty, int[][] memo){
        int jobs = jobDifficulty.length;
        if (leftDays == 0 && firstJob == jobs) {
            return 0;
        }

        if (leftDays == 0 || firstJob == jobs) {
            // Invalid arrangement -> return MAX instead of -1 because we use min later
            // 1. still have jobs when no days left
            // 2. finish all jobs before last day
            return Integer.MAX_VALUE;
        }

        if (memo[firstJob][leftDays] != -1) {
            return memo[firstJob][leftDays];
        }

        int curMax = jobDifficulty[firstJob];
        int min = Integer.MAX_VALUE;
        // with job < jobs - leftDays + 1, we can ignore firstJob == jobs
        for (int job = firstJob; job < jobs - leftDays + 1; ++job){
            curMax = Math.max(curMax, jobDifficulty[job]);
            int temp = dfs(leftDays - 1, job + 1, jobDifficulty, memo);
            if (temp != Integer.MAX_VALUE) {
                min = Math.min(min, temp + curMax);
            }
        }

        return memo[firstJob][leftDays] = min;
    }

    /**
     * 3. Monolithic stack?
     *
     * https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/discuss/494187/O(n*d)-time-and-O(n)-space-solution
     *https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/discuss/490316/JavaC%2B%2BPython3-DP-O(nd)-Solution
     *https://leetcode.com/problems/minimum-difficulty-of-a-job-schedule/discuss/796192/Java.-O(nd)-time-O(n)-space
     *
     * Firstly we have the basic O(nnd) DP solution:
     * f[i][j] = min{f[i-1][k] + max(val[k+1 .. j])}, i-2 <= k < j
     * where f[i][j] is the minimum difficulty for first j jobs done in i days, and val=jobDifficulty
     * Define t=l[j] to be the largest t such that t<j and val[t]>=val[j]
     *
     * Then we know that 1) for t<=k<j, max(val[k+1..j])=val[j], so we only need to calculate min(f[i-1][t..j-1])
     *
     * 2) for k<t, max(val[k+1..j])=max(val[k+1..t])
     * So for k<t case we have min{f[i-1][k]+max(val[k+1..j])}=f[i][t]
     *
     * The DP equation becomes:
     * f[i][j] = min(f[i][l[j]], val[j]+min(f[i-1][l[j]..j-1]))
     *
     * Here we notice that if a>b and val[a] > val[b] then for any c>a, l[c]!=b
     * Therefore for any l[a]<b<a<c, we have l[c]!=b, so we can suppress the interval l[a]..a-1 since they will always be included together
     * Finally we use a monotonic stack to maintain the array f[i-1][l[a]..a-1] where a={...,l[l[j]],l[j],j} is the representatives for the intervals
     * The time complexity is O(nd) since in each of the d rounds every j will be popped only once, and the space is O(n) because f[i][...] only depends on f[i-1][...]
     */
}
