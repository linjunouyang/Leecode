import java.util.HashMap;
import java.util.HashSet;

public class _0356LineReflection {
    /**
     * 1. HashMap
     *
     * xMax and xMin can constraint line candidates into one possible choice
     *
     * In this question, one point can be the mapping of multiple points.
     * If we want 1-1 mapping, we can change the hashmap into
     * y -> (x -> count)
     *
     * Time: O(n)
     * Space: O(n)
     */
    public boolean isReflected(int[][] points) {
        int n = points.length;
        int xMax = Integer.MIN_VALUE;
        int xMin = Integer.MAX_VALUE;
        HashMap<Integer, HashSet<Integer>> yToXs = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int x = points[i][0];
            int y = points[i][1];
            xMax = Math.max(x, xMax);
            xMin = Math.min(x, xMin);
            if (!yToXs.containsKey(y)) {
                HashSet<Integer> xSet = new HashSet<>();
                xSet.add(x);
                yToXs.put(y, xSet);
            } else {
                yToXs.get(y).add(x);
            }
        }

        for (int i = 0; i < n; i++) {
            int x = points[i][0];
            int y = points[i][1];
            int mirroredX = xMax + xMin - x;
            if (!yToXs.containsKey(y) || !yToXs.get(y).contains(mirroredX)) {
                return false;
            }
        }
        return true;
    }
}
