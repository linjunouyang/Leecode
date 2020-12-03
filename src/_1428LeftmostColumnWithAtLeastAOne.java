import java.util.ArrayList;
import java.util.List;

public class _1428LeftmostColumnWithAtLeastAOne {
    interface BinaryMatrix {
        public default int get(int row, int col) {}
      public default List<Integer> dimensions() {};
    };

    /**
     * 1. Binary Search
     *
     * Remember the check whether answer exists after exiting while (left < right)
     *
     * https://leetcode.com/problems/leftmost-column-with-at-least-a-one/solution/
     * Another BS implementation:
     * record smallestIndex, wrap BS inside row iteration
     * update smallestIndex in each BS
     *
     *
     *
     * Time: O(rows * log cols)
     * Space: O(1)
     */
    public int leftMostColumnWithOne(BinaryMatrix binaryMatrix) {
        List<Integer> dimensions = binaryMatrix.dimensions();
        int rows = dimensions.get(0);
        int cols = dimensions.get(1);

        int left = 0;
        int right = cols - 1;
        while (left < right) {
            int col = left + (right - left) / 2;
            boolean hasOne = false;
            for (int row = 0; row < rows; row++) {
                if (binaryMatrix.get(row, col) == 1)  {
                    hasOne = true;
                    break;
                }
            }

            if (hasOne) {
                right = col;
            } else {
                left = col + 1;
            }
        }

        for (int row = 0; row < rows; row++) {
            if (binaryMatrix.get(row, left) == 1)  {
                return left;
            }
        }
        return -1;
    }

    /**
     * 2. Start at Top Right, Move Only Left and Down
     *
     * Time: O(M + N)
     * Space: O(1)
     */
    public int leftMostColumnWithOne2(BinaryMatrix binaryMatrix) {

        int rows = binaryMatrix.dimensions().get(0);
        int cols = binaryMatrix.dimensions().get(1);

        // Set pointers to the top-right corner.
        int currentRow = 0;
        int currentCol = cols - 1;

        // Repeat the search until it goes off the grid.
        while (currentRow < rows && currentCol >= 0) {
            if (binaryMatrix.get(currentRow, currentCol) == 0) {
                currentRow++;
            } else {
                currentCol--;
            }
        }

        // If we never left the last column, this is because it was all 0's.
        return (currentCol == cols - 1) ? -1 : currentCol + 1;
    }
}
