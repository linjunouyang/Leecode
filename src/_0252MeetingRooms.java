import java.util.Arrays;

public class _0252MeetingRooms {
    /**
     * 0. Brute Force
     *
     * Compare every meeting with others
     *
     *
     * Math.min(end1, end2) > Math.max(start1, start2)
     *
     * Time: O(n ^ 2)
     * Space: O(1)
     */

    /**
     * 1. Sorting
     *
     * Why lambda is slower than traditional comparator class:
     *
     * As already mentioned in the comments, the classes for lambda expressions are generated at runtime rather than being loaded from your class path.
     *
     * However, being generated isn’t the cause for the slowdown.
     * After all, generating a class having a simple structure can be even faster than loading the same bytes from an external source.
     * And the inner class has to be loaded too.
     * But when the application hasn’t used lambda expressions before, even the framework for generating the lambda classes has to be loaded (Oracle’s current implementation uses ASM under the hood).
     * This is the actual cause of the slowdown, loading and initialization of a dozen internally used classes, not the lambda expression itself.
     *
     * Compare int inside lambda expression:
     * Arrays.sort(intervals, (a,b) -> a[0]-b[0]); can break in case of very large numbers as it can overflow/underflow
     * Take this for an example: [[-2147483646,-2147483645],[2147483646,2147483647]] ,
     * so your solution of Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[0], i2[0]));
     * is better as it does not use subtraction. Reference
     *
     * Time: O(nlogn)
     * Space: O(1)
     */
    public boolean canAttendMeetings(int[][] intervals) {
        Arrays.sort(intervals, (i1, i2) -> Integer.compare(i1[1], i2[1])); // or sort starting time
        int meetings = intervals.length;
        for (int meeting = 1; meeting < meetings; meeting++) {
            if (intervals[meeting][0] < intervals[meeting - 1][1]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Sort starts and ends (with proof)
     * https://leetcode.com/problems/meeting-rooms/discuss/67780/Easy-JAVA-solution-beat-98
     */

    /**
     * Sort with TreeMap<start, end>
     * https://leetcode.com/problems/meeting-rooms/discuss/166681/Java-solution-using-TreeMap.-Performs-better-than-simple-sorting-based-implementation
     *
     * for every event e,
     * compare (floorEntry.end) vs (e.start)
     * compare (ceilingEntry.start) vs (e.end)
     * put(e.start, e.end)
     */
}
