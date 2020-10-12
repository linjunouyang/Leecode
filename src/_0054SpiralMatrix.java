import java.util.ArrayList;
import java.util.List;

public class _0054SpiralMatrix {
    /**
     * 1.
     *
     * Time: O(m * n)
     * Space: O(1)
     *
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
        int up = 0,  down = rows - 1;
        int left = 0, right = cols - 1;
        while (res.size() < n) {
            for (int j = left; j <= right && res.size() < n ; j++)
                res.add(matrix[up][j]);
            up++;

            for (int i = up; i <= down && res.size() < n ; i++)
                res.add(matrix[i][right]);
            right--;

            for (int j = right; j >= left && res.size() < n; j--)
                res.add(matrix[down][j]);
            down--;

            for (int i = down; i >= up && res.size() < n; i--)
                res.add(matrix[i][left]);
            left++;

        }
        return res;
    }
}
