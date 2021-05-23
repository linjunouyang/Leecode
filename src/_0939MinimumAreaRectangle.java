import java.util.*;

public class _0939MinimumAreaRectangle {
    /**
     * 1. HashMap (x -> set of y values)
     *
     * finding two points which are possible diagonals of a rectangle.
     * Then, we check if we are able to find the other two diagonal points.
     * If they exist, we found a rectangle.
     *
     * I guess we can optimize it more by discarding the other two points for future check,
     * because they in turn will find current two points as their diagonal points and hence will lead to the same rectangle.
     *
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     */
    public int minAreaRect(int[][] points) {
        HashMap<Integer, Set<Integer>> map = new HashMap<>(); // x -> y
        for (int[] p : points) {
            if (!map.containsKey(p[0])) {
                map.put(p[0], new HashSet<>());
            }
            map.get(p[0]).add(p[1]);
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < points.length; i++) {
            int[] p1 = points[i];
            // stop at i - 1, avoid rechecking same diagonals
            for (int j = 0; j < i; j++) {
                int[] p2 = points[j];
                if (p1[0] == p2[0] || p1[1] == p2[1]) {
                    // Since sides are parallel to the x-axis and y-axis,
                    // the two points of diagonal cannot have the same x or y.
                    continue;
                }

//                if (map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1])) {
//                    // find other two points
//                    min = Math.min(min, Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]));
//                }

                // it's intuitive to write the above code, but calculating is faster than map query
                // so using area comparison to filter out unnecessary queries. 199ms -> 36ms

                int possibleNewArea = Math.abs(p1[0] - p2[0]) * Math.abs(p1[1] - p2[1]);
                if (possibleNewArea < min) {
                    if (map.get(p1[0]).contains(p2[1]) && map.get(p2[0]).contains(p1[1])) {
                        min = possibleNewArea;
                    }
                }


            }
        }
        return min == Integer.MAX_VALUE ? 0 : min;
    }
}
