import java.util.ArrayList;
import java.util.List;

public class _0054SpiralMatrix {
    /**
     * 1. Matrix Manipulation
     *
     * Time: O(m * n)
     * Space: O(1)
     */
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> res = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return res;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int n = rows * cols;

        // when we go right, down, left, up - which row, col, row, col we're at
        int up = 0;
        int down = rows - 1;
        int left = 0;
        int right = cols - 1;
        while (res.size() < n) {
            // why res.size() < n -> for cases: left = right, top = down
            // change it into just top <= i <= down OR (need to be AND) left <= j <= right won't work
            for (int j = left; j <= right && res.size() < n ; j++) {
                res.add(matrix[up][j]);
            }
            up++;

            for (int i = up; i <= down && res.size() < n ; i++) {
                res.add(matrix[i][right]);
            }
            right--;

            for (int j = right; j >= left && res.size() < n; j--) {
                res.add(matrix[down][j]);
            }
            down--;

            for (int i = down; i >= up && res.size() < n; i--) {
                res.add(matrix[i][left]);
            }
            left++;

        }
        return res;
    }

    /**
     * 1.1
     */
    public List<Integer> spiralOrder11(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int top = 0; // 1
        int bottom = rows - 1; // 1
        int left = 0; // 1
        int right = cols - 1; // 1

        int[][] directions = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
        int dirIdx = 0;
        int x = 0;
        int y = 0;

        List<Integer> res = new ArrayList<>();

        while (top <= bottom && left <= right) {
            while (top <= x && x <= bottom && left <= y && y <= right) {
                res.add(matrix[x][y]); // [1, 2, 3]
                x = x + directions[dirIdx][0]; // x -> 3
                y = y + directions[dirIdx][1]; // y -> 0
            }

            x -= directions[dirIdx][0]; // x -> 2
            y -= directions[dirIdx][1]; // y -> 0

            if (dirIdx == 0) {
                top++; // top -> 1
            } else if (dirIdx == 1) {
                right--;
            } else if (dirIdx == 2) {
                bottom--;
            } else {
                left++;
            }

            dirIdx = (dirIdx + 1) % directions.length; // dirIdx -> 1

            x = x + directions[dirIdx][0];
            y = y + directions[dirIdx][1];
        }

        return res;
    }
}
