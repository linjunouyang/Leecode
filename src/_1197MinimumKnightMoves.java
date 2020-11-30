
import java.util.*;

/**
 * The problem statement asks us to find minimum number of moves to reach a target position {x,y} on an infinite chessboard from {0,0}.
 * The key point to observe here is that we can reduce this to graph.
 * Each position on the board can be thought of as a node. Edges can be thought of as possible moves for a knight from one position to other.
 * This leads to an undirected uniform weighted graph. Thus the shortest distance to the target position is our answer.
 * Since the graph is undirected and has uniform weights we can use bfs to calculate the answer.
 */
public class _1197MinimumKnightMoves {
    /**
     * 1. BFS
     *
     * I guess the goal should be to first get an agreement on the approach (in this case BFS).
     * Then you could code an almost correct BFS solution that would in theory give you a right answer (even though it may timeout in the OJ here).
     *
     * The interviewer at this point may hint that this solution will take too long to run and you start looking at how to optimize it.
     * You realize that you can only stick to the first quadrant and you add the x >= 0 y >= 0 condition.
     *
     * The interviewer probably knows this will work for all but (1,1) and asks you to come up with few test cases (or even better gives you the test case (1,1) as input)
     * and you eventually realize that your condition should be x >= -1 y >= -1.
     *
     * In my opinion, this shows that you can code a right solution using an acceptable approach and can take hints/collaborate
     * and make it 100% correct and this is still achievable to do in 15 - 20 mins.
     *
     * Why Set<String> instead of Set<int[]>:
     * Because in java int[] hashcode is calculated as default hashcode (Object.hashcode()), but for String it calculates hashcode based on the value.
     * If you want to compare arrays of ints via hashcode you should consider using lists instead.
     * https://docs.oracle.com/javase/8/docs/api/java/util/List.html#hashCode--
     *
     * Time:
     * layer: Max(x, y)
     * O(8^layer)
     *
     * Space: O(8^layer)
     */
    private final int[][] DIRECTIONS = new int[][] {{2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}};

    public int minKnightMoves1(int x, int y) {
        x = Math.abs(x);
        y = Math.abs(y);

        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[] {0, 0});

        Set<String> visited = new HashSet<>();
        visited.add("0,0");

        int result = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] cur = queue.remove();
                int curX = cur[0];
                int curY = cur[1];
                if (curX == x && curY == y) {
                    return result;
                }

                for (int[] d : DIRECTIONS) {
                    int newX = curX + d[0];
                    int newY = curY + d[1];
                    StringBuilder newPosBuilder = new StringBuilder();
                    newPosBuilder.append(newX).append(',').append(newY);
                    String newPos = newPosBuilder.toString();
                    if (!visited.contains(newPos) && newX >= -1 && newY >= -1) {
                        /**
                         * Why >= -1, not >= 0:
                         *
                         * For example, to reach (1,1) from (0, 0),
                         * the best way is to get (2, -1) or (-1, 2) first, then (1,1) (two steps).
                         * If we eliminate all negative pos, then we can't reach (1,1) from (0, 0) within two steps.
                         *
                         *  The co-or in general to compute the knight moves are: (x-2, y-1) (x-2, y+1), (x-1, y-2) ...
                         *  where for all x,y>=2 the next "move" will always be >=0 (ie in the first quadrant).
                         *  Only for x=1/y=1, the next move may fall in the negative quad example (x-2,y-1) or (x-1, y-2),
                         *  and hence x=-1 y=-1 boundary is considered.
                         */
                        queue.add(new int[] {newX, newY});
                        visited.add(newPos);
                    }
                }
            }
            result++;
        }
        return -1;
    }


}
