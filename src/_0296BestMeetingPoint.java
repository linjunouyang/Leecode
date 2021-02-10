import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class _0296BestMeetingPoint {
    /**
     * 1. Brute Force
     *
     * Time: O(mn * number of homes)
     * Space: O(number of homes)
     */
    public int minTotalDistance(int[][] grid) {
        List<int[]> homes = new ArrayList<>();
        int rows = grid.length;
        int cols = grid[0].length;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid[row][col] == 1) {
                    homes.add(new int[]{row, col});
                }
            }
        }

        int minDistance = Integer.MAX_VALUE;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int distance = 0;
                for (int[] home : homes) {
                    distance += Math.abs(home[0] - row) + Math.abs(home[1] - col);
                }
                minDistance = Math.min(minDistance, distance);
            }
        }

        return minDistance;
    }

    /**
     * 2.
     *
     * Why median point:
     * 1. assume you are at a point which is smaller than the median.
     * You can always improve your performance by increase 1 toward the median,
     * since more points on the right can be benefited at cost of less points on the left.
     *
     * 2. As long as you have different numbers of people on your left and on your right,
     * moving a little to the side with more people decreases the sum of distances. So to minimize it,
     * you must have equally many people on your left and on your right.
     *
     * 3. https://leetcode.com/problems/best-meeting-point/discuss/74189/Am-I-the-only-person-who-don't-know-why-median-could-give-shortest-distance
     * Proof by induction
     * mean minimizes total distance for euclidian distance
     * median minimizes total distance for absolute deviation
     *
     *
     * Notice that the Manhattan distance is the sum of two independent variables.
     * Therefore, once we solve the 1D case, we can solve the 2D case as two independent 1D problems.
     *
     * Two pointers:
     * calculate the distance without knowing the median using a two pointer approach.
     *
     * Similar Idea but use bucketsort-ish idea: only O(m + n) space
     * https://leetcode.com/problems/best-meeting-point/discuss/74188/O(mn)-Java-2ms
     *
     * @param grid
     * @return
     */
    public int minTotalDistance2(int[][] grid) {
        List<Integer> rowList = new ArrayList<>();
        List<Integer> colList = new ArrayList<>();

        int rows = grid.length;
        int cols = grid[0].length;

        for (int row = 0; row < rows; row++){
            for (int col = 0; col < cols; col++){
                if (grid[row][col] == 1){
                    rowList.add(row);
                    colList.add(col);
                }
            }
        }

        return minDistance(rowList) + minDistance(colList);
    }

    private int minDistance(List<Integer> list){
        // or we can remove sorting by adding row and col separately in order
        Collections.sort(list);
        int left = 0;
        int right = list.size() - 1;
        int res = 0;
        while (left < right){
            res += list.get(right) - list.get(left);
            right--;
            left++;
        }
        return res;
    }

    /**
     * quick select solution:
     * https://leetcode.com/problems/best-meeting-point/discuss/74233/Share-my-Java-solution-using-quick-select
     */

    /**
     * https://leetcode.com/problems/best-meeting-point/discuss/74193/Java-2msPython-40ms-two-pointers-solution-no-median-no-sort-with-explanation
     * Two pointers suitable for follow-up: what if multiple homes at one location
     */
}
