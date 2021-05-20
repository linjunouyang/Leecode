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
     * Time: O(nlogn)
     * Space: O(n)
     *
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
            if (intervals[indices.get(mid)][0] < end) {
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
        int idx;
        int val;
        boolean isStart;

        public Point(int idx, int val, boolean isStart) {
            this.idx = idx;
            this.val = val;
            this.isStart = isStart;
        }
    }

    class PointComparator implements Comparator<Point> {
        public int compare(Point p1, Point p2) {
            if (p1.val != p2.val) {
                return Integer.compare(p1.val, p2.val);
            }
            if (p1.isStart) {
                return 1;
            }
            return -1;
        }
    }

    public int[] findRightInterval4(int[][] intervals) {
        int n = intervals.length;
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            points.add(new Point(i, intervals[i][0], true));
            points.add(new Point(i, intervals[i][1], false));
        }

        PointComparator comparator = new PointComparator();
        Collections.sort(points, comparator);

        PriorityQueue<Point> startMinHeap = new PriorityQueue<>(comparator);
        int[] res = new int[n];
        for (int i = points.size() - 1; i >= 0; i--) {
            Point point = points.get(i);
            if (point.isStart) {
                startMinHeap.offer(point);
            } else {
                if (startMinHeap.size() == 0) {
                    res[point.idx] = -1;
                } else {
                    res[point.idx] = startMinHeap.peek().idx;
                }
            }
        }

        return res;
    }


}
