import java.util.HashSet;
import java.util.Set;

interface Robot {
    // returns true if next cell is open and robot moves into the cell.
    // returns false if next cell is obstacle and robot stays on the current cell.
    boolean move();

    // Robot will stay on the same cell after calling turnLeft/turnRight.
    // Each turn will be 90 degrees.
    void turnLeft();
    void turnRight();

    // Clean the current cell.
    void clean();
}

public class _0489RobotRoomCleaner {
    /**
     * 1. Backtracking
     *
     * Locate positions and cache visited positions:
     * typical: given fixed coordinates.
     * here: Set starting point as (0, 0), and use relative coordinates
     *
     * Backtrack:
     * typical: change direction, no need to deal with actual movement
     * here: backtrack to the original status (position + direction) with given API
     *
     * N: number of cells, M: number of obstacles
     * Time: 4 * O(N - M) = O(N - M)
     * visit each non-obstacle cell once. check 4 directions.
     * Space: O(N - M) by recursion call stack and hashset
     */
    static final int[][] DIRECTIONS = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public void cleanRoom(Robot robot) {
        dfs(robot, new HashSet<>(), 0, 0, 0);
    }

    public void dfs(Robot robot, Set<String> visited, int x, int y, int curDir) {
        String pos = x + "," + y;
        visited.add(pos);
        robot.clean();

        for (int i = 0; i < 4; i++) {
            int dir = (curDir + i) % 4;
            int nextX = x + DIRECTIONS[dir][0];
            int nextY = y + DIRECTIONS[dir][1];
            String nextPos = nextX + "," + nextY;

            if (!visited.contains(nextPos) && robot.move()) {
                dfs(robot, visited, nextX, nextY, dir);
                backtrack(robot);
            }

            robot.turnRight(); // stay consistent with DIRECTIONS array order
        }
    }

    private void backtrack(Robot robot) {
        robot.turnLeft();
        robot.turnLeft();
        robot.move();
        robot.turnRight();
        robot.turnRight();
    }
}
