import java.util.*;

/**
 * Before thinking about this weighted Interval Scheduling problem,
 * let's take a look at Unweighted Interval Scheduling, which is problem 646. Maximum Length of Pair Chain.
 *
 * For Unweighted Interval Scheduling, we can easily use greedy algorithm.
 * (Greedy template: consider some natural order: earliest start time, earliest finish time,
 * shortest interval, or fewest conflicts, and think about counterexamples)
 * First sort by finish time(ascending order) then decide whether to fit the next interval in or not based on its start time.
 * See the prove here
 * http://www.cs.cornell.edu/courses/cs482/2007su/ahead.pdf.
 * https://www.cs.princeton.edu/~wayne/kleinberg-tardos/pearson/04GreedyAlgorithms-2x2.pdf
 *
 * But Greedy algorithm can fail spectacularly if arbitrary weights are allowed.
 * So that's when DP comes in.
 * https://courses.cs.washington.edu/courses/cse521/13wi/slides/06dp-sched.pdf
 * From my understanding, greedy is a specific kind of DP, and DP is a general greedy.
 * (greedy is local optimization, dp is global optimization)
 *
 * For this problem we can still first sort by finish time(ascending order)
 * then use DP to decide whether it is profitable to put in the next interval based on its value.
 * Here is the essence:
 *
 * Define Job j starts at sj, finishes at fj, and has weight or value vj, and p(j) = largest index i < j such that job i is compatible with j.
 * Then it should be like:
 * DP[j] = max(vj + DP[p(j)], DP[j-1])
 *
 * For optimizing we can use binary search to locate p(j).
 */
public class _1235MaximumProfitInJobScheduling {
    /**
     * 1. DP (sorting by end time or start time)
     *
     * Why sorting?
     * https://courses.cs.washington.edu/courses/cse521/13wi/slides/06dp-sched.pdf
     * allows us to consider only a small number of
     * sub-problems (O(n)), vs the exponential number that seem to be needed if
     * the jobs aren’t ordered (seemingly, any of the 2n possible
     * subsets might be relevant)
     *
     * Source:
     * Sort by increasing end time
     * https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/408957/DP%2BBinary-search-(Java)
     * Sort by decreasing start time
     * https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409188/C%2B%2B-with-picture
     * Sort by increasing start time
     * https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/733167/Thinking-process-Top-down-DP-Bottom-up-DP
     *
     * Similar idea but using time as index:
     * https://leetcode.com/problems/maximum-profit-in-job-scheduling/discuss/409009/JavaC%2B%2BPython-DP-Solution
     *
     * Time O(NlogN) for sorting
     * Time O(NlogN) for binary search for each job
     * Space O(N)
     */
    class Job {
        int start, finish, profit;
        Job(int start, int finish, int profit) {
            this.start = start;
            this.finish = finish;
            this.profit = profit;
        }
    }

    public int jobScheduling(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        for(int i=0;i<n;i++) {
            jobs[i] = new Job(startTime[i],endTime[i],profit[i]);
        }
        return scheduleApt(jobs);
    }

    private int scheduleApt(Job[] jobs) {
        // Sort jobs according to finish time
        Arrays.sort(jobs, Comparator.comparingInt(a -> a.finish));  // or -a.start (late start comes first)
        // dp[i] stores the profit for considering up to jobs[i]
        int n = jobs.length;
        int[] dp = new int[n];
        dp[0] = jobs[0].profit;
        for (int i=1; i<n; i++) {
            // Profit including the current job
            int profit = jobs[i].profit;
            int l = search(jobs, i);
            if (l != -1)
                profit += dp[l];
            // Store maximum of including and excluding
            dp[i] = Math.max(profit, dp[i-1]);
        }

        return dp[n-1];
    }

    /**
     * Find the latest job before the current job (in sorted array)
     * that doesn't conflict with current job
     */
    private int search(Job[] jobs, int index) {
        int start = 0, end = index - 1;
        while (start <= end) {
            int mid = (start + end) / 2;
            if (jobs[mid].finish <= jobs[index].start) {  // jobs[mid].start >= jobs[index].end
                if (jobs[mid + 1].finish <= jobs[index].start) { // jobs[mid + 1].start >= jobs[index].end
                    start = mid + 1;
                } else {
                    return mid;
                }
            } else {
                end = mid - 1;
            }
        }
        return -1;

        // OR

//        int l = 0, r = index;
//        while (l < r) {
//            int m = (l + r) / 2;
//            if (jobs[m].finish <= jobs[index].start) {
//                l = m + 1;
//            } else {
//                r = m;
//            }
//        }
//        return l-1;
    }

    /**
     * 2. TreeMap
     *
     * Time complexity: O(n logn)
     * Space complexity: O(n)
     *
     */
    public int jobScheduling2(int[] startTime, int[] endTime, int[] profit) {
        int n = startTime.length;
        Job[] jobs = new Job[n];
        for (int i=0; i < n; i++) {
            jobs[i] = new Job(startTime[i], endTime[i], profit[i]);
        }
        Arrays.sort(jobs, (a,b)->a.finish -b.finish);

        TreeMap<Integer, Integer> map = new TreeMap<>();
        int ans = 0;

        for (Job job: jobs) {
            Integer prev = map.floorKey(job.start);
            int prevSum = prev==null?0:map.get(prev);
            ans = Math.max(ans, prevSum+job.profit);
            map.put(job.finish, ans);
        }
        return ans;
    }
}
