import java.util.Arrays;

public class _0621TaskScheduler {
    /**
     * 1. Greedy (from Solution)
     *
     * Total time = busy + idle
     * Max possible number of idle slots: n * (f(most frequent task) - 1)
     *
     * Greedy:
     * sort the tasks by frequency in the descending order and fulfill as many idle slots as one could.
     *
     * A more detailed analysis:
     * Top 1 discussion:
     * https://leetcode.com/problems/task-scheduler/discuss/104500/Java-O(n)-time-O(1)-space-1-pass-no-sorting-solution-with-detailed-explanation
     *
     * Time complexity:
     * O(n) n: number of tasks
     *
     * Space complexity:
     * O(1)
     *
     *
     * @param tasks
     * @param n
     * @return
     */
    public int leastInterval(char[] tasks, int n) {
        // frequencies of the tasks
        int[] frequencies = new int[26];
        for (int t : tasks) {
            frequencies[t - 'A']++;
        }

        Arrays.sort(frequencies);

        // max frequency
        int f_max = frequencies[25];
        int idle_time = (f_max - 1) * n;

        for (int i = frequencies.length - 2; i >= 0 && idle_time > 0; --i) {
            idle_time -= Math.min(f_max - 1, frequencies[i]);
        }
        idle_time = Math.max(0, idle_time);

        return idle_time + tasks.length;
    }
}
