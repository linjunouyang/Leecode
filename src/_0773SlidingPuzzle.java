import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;

public class _0773SlidingPuzzle {
    /**
     * 1. BFS
     *
     * At each step, we have four options
     * -> whole board is a state (a node in the map)
     *
     * least number of moves to achieve certain state
     * -> shortest path from initial node to a certain node
     * -> BFS
     *
     * Where to check a node is our target?
     * 1. right before push -> 2 places
     * initial push (easy to forget), and following push inside while loop
     * 2. right after poll -> 1 places
     *
     * Print path:
     * HashMap<String, String>: newState -> parent state
     *
     * Could also use Two-way BFS:
     * https://leetcode.com/problems/sliding-puzzle/discuss/113620/JavaPython-3-BFS-clean-codes-w-comment-Time-and-space%3A-O(m-*-n-*-(m-*-n)!).
     *
     * Number of possible board states: (rows * cols)!
     * Time: O(rows * cols * (rows * cols)!)
     * Space: O(rows * cols * (rows * cols)!)
     */
    private final static int ROWS = 2;
    private final static int COLS = 3;
    private final static int[] MOVES = new int[]{-1, 1, 3, -3};
    private final static String END = "123450";

    public int slidingPuzzle(int[][] board) {
        StringBuilder start = new StringBuilder();
        for (int row = 0; row < ROWS; row++) {
            for (int col = 0; col < COLS; col++) {
                start.append(board[row][col]);
            }
        }

        String startStr = start.toString();
        Deque<String> queue = new ArrayDeque<>();
        queue.offer(startStr);
        HashSet<String> visited = new HashSet<>();
        visited.add(startStr);

        int steps = 0;

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                String state = queue.poll();
                if (state.equals(END)) {
                    return steps;
                }

                char[] chars = state.toCharArray();
                int zeroPos = state.indexOf('0');
                for (int move : MOVES) {
                    int zeroNextPos = zeroPos + move;
                    if (!isValid(zeroPos, zeroNextPos)) {
                        continue;
                    }

                    chars[zeroPos] = chars[zeroNextPos];
                    chars[zeroNextPos] = '0';

                    String nextState = String.valueOf(chars);
                    if (!visited.contains(nextState)) {
                        queue.offer(nextState);
                        visited.add(nextState);
                    }

                    // don't forget to revert when newState is visited
                    chars[zeroNextPos] = chars[zeroPos];
                    chars[zeroPos] = '0';
                }
            }
            steps++;
        }

        return -1;
    }

    private boolean isValid(int zeroPos, int zeroNextPos) {
        int last = ROWS * COLS - 1;
        if (zeroNextPos < 0 || zeroNextPos > last ||
                // don't forget these two situations
                (zeroPos == COLS && zeroNextPos == COLS - 1) ||
                (zeroPos == COLS - 1 && zeroNextPos == COLS)) {
            return false;
        }
        return true;
    }

    /**
     * 2. DFS
     *
     * Follow-up: print solution path
     * https://leetcode.com/problems/sliding-puzzle/discuss/113615/Java-Intuitive-DFS%2BBacktracking
     */
}
