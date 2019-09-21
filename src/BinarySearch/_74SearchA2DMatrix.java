package BinarySearch;

/**
 * 1. Intuition: Sorted -> Binary Search -> logn
 * 2. The visualization (the way we interpret data) is not necessarily the same
 * as the way the data is formatted in memory. Try to MAP it.
 * 3. When doing search, 1st to think: how to reduce search space,
  */

public class _74SearchA2DMatrix {

    /**
     *  1. Binary Search
     *  Treat n*m matrix as array => matrix[x][y] = a[x * m + y]
     *  Treat array as n*m matrix => a[x] => matrix[x/m][x%m]
     *
     *  Time Complexity: O(log(rows*cols))
     *  Which is the same as first searching for the row, then searching for the column
     *  Space Complexity: O(1)
     *
     *  Note:
     *  1. row_num * col_num might cause overflow.
     *  2. % and / are expensive
     *  3. bs by row, then bs by column avoids multiplication and has better cache hit rate
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix1(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int row_num = matrix.length;
        int col_num = matrix[0].length;

        int begin = 0, end = row_num * col_num - 1; // row_num * col_num might cause overflow

        while (begin <= end) {
            // or (start + end) >>> 1;
            int mid = begin + (end - begin) / 2;
            int mid_value = matrix[mid/col_num][mid%col_num];

            if (mid_value == target) {
                return true;
            } else if (mid_value < target) {
                // Move forward, otherwise dead loop
                begin = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return false;
    }


    /**
     * 3. Two binary searches
     *
     * Remember to set row = end, and check whether row >= 0:
     * Just think about two corner cases of first while loop:
     * 1) start = end
     * 2) start + 1 = end
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix2(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0] == null || matrix[0].length == 0) {
            return false;
        }

        // binary search row
        int start = 0;
        int end = matrix.length - 1;

        while (start <= end) {
            int mid = start + (end - start) / 2;

            if (matrix[mid][0] < target) {
                start = mid + 1;
            } else if (matrix[mid][0] > target) {
                end = mid - 1;
            } else {
                return true;
            }
        }

        // binary search column
        int row = end;

        if (row >= 0) {
            start = 0;
            end = matrix[row].length - 1;

            while (start <= end) {
                int mid = start + (end - start) / 2;

                if (matrix[row][mid] < target) {
                    start = mid + 1;
                } else if (matrix[row][mid] > target) {
                    end = mid - 1;
                } else {
                    return true;
                }
            }
        }

        return false;
    }



    /**
     * 3. Two Pointers
     *
     */
    public boolean searchMatrix3(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        int row = 0;
        int col = matrix[0].length - 1;

        while (row <= matrix.length - 1 && col >= 0) {
            if (matrix[row][col] < target) {
                row = row + 1;
            } else if (matrix[row][col] > target) {
                col = col - 1;
            } else {
                return true;
            }
        }

        return false;
    }


}
