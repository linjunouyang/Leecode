public class _1041RobotBoundedInCircle {
    /**
     * 1. Math, concrete examples -> rules
     * turn right (idx + 1) % 4
     * turn left: (idx + 3) % 4 (turn left = turn right 3 times)
     *
     * A cycle exists if
     * a) go back to center
     * or b) changes direction (hard to find out this rule from concrete examples)
     *
     * another way to think about b)
     * if the end direction is east, south or west, then, if we treat east as if north, the next position will be a similar position, we can actually use some middle school math (which I've forgotten:) to calculate the next position;
     * but the idea should be easy to understand now, we just move the line in 90 or 180 or 270 degrees, and will should become a square going back to [0,0] after 4 steps.
     *
     * https://en.wikipedia.org/wiki/Pythagorean_theorem#/media/File:Pythagoras_algebraic2.svg
     *
     * proof:
     * https://leetcode.com/problems/robot-bounded-in-circle/solution/
     *
     * Time: O(N)
     * Space: O(1)
     */
    public boolean isRobotBounded(String instructions) {
        // north = 0, east = 1, south = 2, west = 3
        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        // Initial position is in the center
        int x = 0, y = 0;
        // facing north
        int idx = 0;

        for (char i : instructions.toCharArray()) {
            if (i == 'L')
                idx = (idx + 3) % 4;
            else if (i == 'R')
                idx = (idx + 1) % 4;
            else {
                x += directions[idx][0];
                y += directions[idx][1];
            }
        }

        // after one cycle:
        // robot returns into initial position
        // or robot doesn't face north
        return (x == 0 && y == 0) || (idx != 0);
    }
}
