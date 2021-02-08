import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;

public class _0218TheSkylineProblem {
    /**
     * 1. Sweep Line
     *
     * 3 edge cases:
     * A. Multiple buildings have same starts
     * -> examine higher building first to overshadow lower starts
     * (0, 3, e), (0, 2, e), (1, 2, e), (2, 3, e)
     *
     * B. Multiple buildings have same ends
     * -> examine lower ones first to avoid wrong key point which height is not 0
     * (3, 3, s), (4, 2, s), (5, 2, e), (5, 3, e)
     *
     * c. prev end = next start
     * -> examine start first to avoid wrong 0 height point
     * (6, 2, s), (7, 3, s), (7, 2, e), (8, 3, e)
     *
     * Solutions:
     * 1. PriorityQueue:
     * O(logn): peek(), offer(e)
     * O(n): remove(O)
     * -> implement customized PQ
     *
     * 2. TreeMap
     *
     * Time: O(nlogn)
     * Space: O(n)
     */
    class Point implements Comparable<Point> {
        int x;
        boolean isStart;
        int height;

        public Point(int x, boolean isStart, int height) {
            this.x = x;
            this.isStart = isStart;
            this.height = height;
        }

        @Override
        public int compareTo(Point other) {
            if (this.x != other.x) {
                // return Integer.compare(this.x, other.x);
                return this.x - other.x;
            }

//            if (this.isStart && other.isStart) {
//                return Integer.compare(other.height, this.height);
//            } else if (!this.isStart && !other.isStart) {
//                return Integer.compare(this.height, other.height);
//            } else {
//                return this.isStart ? -1 : 1;
//            }
            // if two starts are compared then higher height building should be picked first
            // if two ends are compared then lower height building should be picked first
            // if one start and end is compared then start should appear before end
            return (this.isStart ? -this.height : this.height) - (other.isStart ? -other.height : other.height);
        }
    }

    public List<List<Integer>> getSkyline(int[][] buildings) {
        int num = buildings.length;
        Point[] points = new Point[2 * num];
        int idx = 0;
        for (int[] building : buildings) {
            points[idx++] = new Point(building[0], true, building[2]);
            points[idx++] = new Point(building[1], false, building[2]);
        }
        Arrays.sort(points);

        List<List<Integer>> keyPoints = new ArrayList<>();
        TreeMap<Integer, Integer> heightToCount = new TreeMap<>();
        heightToCount.put(0, 1);
        int prevMaxHeight = 0;

        for (Point point : points) {
            if (point.isStart) {
                heightToCount.compute(point.height, (key, value) -> {
                    if (value != null) {
                        return value + 1;
                    }
                    return 1;
                });
            } else {
                heightToCount.compute(point.height, (key, value) -> {
                    if (value == 1) {
                        return null;
                    }
                    return value - 1;
                });
            }

            int curMaxHeight = heightToCount.lastKey();
            if (prevMaxHeight != curMaxHeight) {
                List<Integer> keyPoint = new ArrayList<>();
                keyPoint.add(point.x);
                keyPoint.add(curMaxHeight); // not point.height
                keyPoints.add(keyPoint);
                prevMaxHeight = curMaxHeight;
            }
        }

        return keyPoints;
    }
}
