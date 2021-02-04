import java.util.ArrayDeque;
import java.util.Deque;

/**
 * The length of image and image[0] will be in the range [1, 50].
 * The given starting pixel will satisfy 0 <= sr < image.length and 0 <= sc < image[0].length.
 * The value of each color in image[i][j] and newColor will be an integer in [0, 65535].
 *
 * No visited array because we change the input array
 *
 *
 * DIRS = {0, 1, 0, -1, 0};
 */
public class _0733FloodFill {
    /**
     * 1. Recursive DFS
     *
     * Time: O(rows * cols)
     * Space: O(rows * cols)
     */
    public int[][] floodFill(int[][] image, int sr, int sc, int newColor) {
        if (newColor == image[sr][sc]) {
            // TLE without this
            // because image[row][col] always equal to oldColor(newColor)
            return image;
        }

        int rows = image.length;
        int cols = image[0].length;

        int oldColor = image[sr][sc];
        /**
         * equivalent of marking as visited
         * if we want to do post-order, we then need a visited array.
         */
        image[sr][sc] = newColor;


        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] direction : directions) {
            int row = sr + direction[0];
            int col = sc + direction[1];

            if (isValid(row, rows, col, cols) && image[row][col] == oldColor) {
                floodFill(image, row, col, newColor);
            }
        }
        return image;

    }

    private boolean isValid(int row, int rows, int col, int cols) {
        return 0 <= row && row < rows && 0 <= col && col < cols;
    }

    /**
     * 2. Iterative DFS / BFS (changing stack to queue)
     *
     * Time: O(rows * cols)
     * Space: O(rows * cols)
     *
     */
    public int[][] floodFill2(int[][] image, int sr, int sc, int newColor) {
        if (newColor == image[sr][sc]) {
            // TLE if not exiting early
            return image;
        }

        int rows = image.length;
        int cols = image[0].length;
        int[][] directions = new int[][]{{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int oldColor = image[sr][sc];
        image[sr][sc] = newColor;
        Deque<int[]> stack = new ArrayDeque<>();
        stack.push(new int[]{sr, sc});

        while (!stack.isEmpty()) {
            int[] pos = stack.pop();

            for (int[] direction : directions) {
                int row = pos[0] + direction[0];
                int col = pos[1] + direction[1];
                if (isValid(row, rows, col, cols) && image[row][col] == oldColor) {
                    image[row][col] = newColor;
                    stack.push(new int[]{row, col});
                }
            }
        }

        return image;
    }
}
