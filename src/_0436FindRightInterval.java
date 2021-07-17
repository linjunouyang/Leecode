import java.util.*;

public class _0436FindRightInterval {
    /**
     * 1. Brute Force
     *
     * Time: O(n^2)
     * Space: O(1)
     */

    /**
     * 2. Binary Search
     *
     * Sorting of an array of primitive values with a custom comparator is not supported by the standard Java libraries.
     * int[] indices = new int[n]
     * Arrays.sort(indices, (a, b) -> a - b); // doesnt' work
     *
     * Arrays.<T#1>sort(T#1[],Comparator<? super T#1>) is not applicable
     * inference variable T#1 has incompatible bounds
     * equality constraints: int
     * lower bounds: Object
     *
     * T#1 is type-variable:
     * T#1 extends Object declared in method <T#1>sort(T#1 [], Comparator<? super T#1>)
     *
     * Time: O(nlogn)
     * Space: O(n)
     */
    public int[] findRightInterval(int[][] intervals) {
        int n = intervals.length;

        List<Integer> indices = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            indices.add(i);
        }
        Collections.sort(indices, Comparator.comparingInt(a -> intervals[a][0]));

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            int end = intervals[i][1];
            res[i] = binarySearch(intervals, indices, end);
        }

        return res;
    }

    private int binarySearch(int[][] intervals, List<Integer> indices,
                             int end) {
        int left = 0;
        int right = intervals.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            int start = intervals[indices.get(mid)][0];

            if (start < end) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }

        int index = indices.get(left);
        if (intervals[index][0] >= end) {
            return index;
        } else {
            return -1;
        }
    }

    /**
     * 3. TreeMap
     *
     * Be aware of value (it's idx)
     *
     * Time: O(nlogn)
     * Space: O(n)
     */
    public int[] findRightInterval3(int[][] intervals) {
        int n = intervals.length;
        TreeMap<Integer, Integer> startToIdx = new TreeMap<>();
        for (int i = 0; i < n; i++) {
            startToIdx.put(intervals[i][0], i);
        }

        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            Integer rightStart = startToIdx.ceilingKey(intervals[i][1]);
            if (rightStart == null) {
                res[i] = -1;
            } else {
                res[i] = startToIdx.get(rightStart);
            }
        }

        return res;
    }

    /**
     * 4. Scanning (TODO)
     * https://www.jiuzhang.com/solution/find-right-interval
     *
     * traverse points from right to left:
     * because we're given end, we want to find start on the bigger side
     *
     * when val is equal, start comes after end
     *
     * Time: O(nlogn)
     * Space: O(n)
     */
    class Point {
        boolean isStart;
        int val;
        int idx;

        public Point(boolean isStart, int val, int idx) {
            this.isStart = isStart;
            this.val = val;
            this.idx = idx;
        }
    }

    public int[] findRightInterval4(int[][] intervals) {
        int n = intervals.length;
        List<Point> points = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            int[] interval = intervals[i];

            Point start = new Point(true, interval[0], i);
            Point end = new Point(false, interval[1], i);

            points.add(start);
            points.add(end);
        }

        Collections.sort(points, (p1, p2) -> {
            if (p1.val != p2.val) {
                return Integer.compare(p1.val, p2.val);
            }

            if (p1.isStart) {
                return 1;
            } else {
                return -1;
            }
        });

        int rightIdx = -1;
        int[] res = new int[n];

        for (int i = points.size() - 1; i >= 0; i--) {
            Point point = points.get(i);
            if (point.isStart) {
                rightIdx = point.idx;
            } else {
                res[point.idx] = rightIdx;
            }
        }

        return res;
    }
}
