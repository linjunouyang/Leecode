import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Similar: 252, 253, 435
 *
 */
public class _0056MergeIntervals {
    /**
     * 1. Brute Force - connected components
     *
     * https://leetcode.com/problems/merge-intervals/solution/
     *
     * Draw a graph (intervals as nodes) that contains undirected edges between all pairs of intervals that overlap,
     * then all intervals in each connected component of the graph can be merged into a single interval.
     *
     * Here, adjacent List is represented as a HashMap. Interval -> LinkedList of overlap intervals
     *
     */

    /**
     * 2. Sorting
     *
     * Proof of correctness:
     *
     * Consider i < j < k, only (i, k) ban be merged.
     * Suppose we fail to merge this
     *
     * https://leetcode.com/problems/merge-intervals/solution/
     *
     *
     */
    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length == 0) {
            return new int[0][0];
        }


        Arrays.sort(intervals, new Comparator<int[]>(){
            public int compare (int[] e1, int[] e2) {
                return e1[0] - e2[0];
            }
        });
        // OR USING lAMBDA:
        // Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));


        List<int[]> list = new ArrayList<>();
        int[] prev = intervals[0];
        list.add(prev);
        for (int i = 1; i < intervals.length; i++) {
            if (prev[1] >= intervals[i][0]) {
                prev[1] = Math.max(intervals[i][1], prev[1]);
            } else {
                prev = intervals[i];
                list.add(prev);
            }
        }


        return list.toArray(new int[1][1]);

    }

    /**
     * Facebook Follow-up:
     *
     * Question: How do you add intervals and merge them for a large stream of intervals?
     * https://leetcode.com/problems/merge-intervals/discuss/355318/Fully-Explained-and-Clean-Interval-Tree-for-Facebook-Follow-Up-No-Sorting
     */
}
