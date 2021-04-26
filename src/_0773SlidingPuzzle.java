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
     * Could also use Two-way BFS:
     * https://leetcode.com/problems/sliding-puzzle/discuss/113620/JavaPython-3-BFS-clean-codes-w-comment-Time-and-space%3A-O(m-*-n-*-(m-*-n)!).
     *
     *
     * Number of possible board states: (rows * cols)!
     * Time: O(rows * cols * (rows * cols)!)
     * Space: O((rows * cols)!)
     */
    public int slidingPuzzle(int[][] board) {
        int rows = board.length;
        int cols = board[0].length;
        int steps = 0;
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        Deque<String> queue = new ArrayDeque<>();
        HashSet<String> states = new HashSet<>();

        // initial board state as root node
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                sb.append(board[row][col]);
            }
        }
        queue.offer(sb.toString());

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                // Check current board configuration
                String state = queue.poll();
                if (state.equals("123450")) {
                    return steps;
                }

                // Explore next possible board states
                char[] chars = state.toCharArray();
                int emptyIdx = state.indexOf('0');
                int emptyRow = emptyIdx / cols;
                int emptyCol = emptyIdx % cols;

                for (int[] direction : directions) {
                    // invalid next state
                    int nextRow = emptyRow + direction[0];
                    int nextCol = emptyCol + direction[1];
                    if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) {
                        continue;
                    }

                    // generate next state(node)
                    int otherIdx = nextRow * cols + nextCol;
                    chars[emptyIdx] = chars[otherIdx];
                    chars[otherIdx] = '0';

                    // ready to visit next state(node)
                    String nextState = String.valueOf(chars);
                    if (!states.contains(nextState)) {
                        queue.offer(nextState);
                        states.add(nextState);
                    }

                    // revert
                    chars[otherIdx] = chars[emptyIdx];
                    chars[emptyIdx] = '0';
                }
            }
            steps++;
        }

        return -1;
    }

    /**
     * 2. DFS
     *
     * Follow-up: print solution path
     * https://leetcode.com/problems/sliding-puzzle/discuss/113615/Java-Intuitive-DFS%2BBacktracking
     */
}
