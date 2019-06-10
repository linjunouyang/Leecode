package BinarySearch;

public class _74SearchA2DMatrix {
    /*
    Binary Search
    Treat n*m matrix as array => matrix[x][y] = a[x * m + y]
    Treat array as n*m matrix => a[x] => matrix[x/m][x%m]

    Time Complexity: O(log(rows*cols))
    Which is the same as first searching for the row, then searching for the column
    Space Complexity: O(1)

    Note:
    1. row_num * col_num might cause overflow.
    2. % and / are expensive
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int row_num = matrix.length;
        int col_num = matrix[0].length;

        int begin = 0, end = row_num * col_num - 1; // row_num * col_num might cause overflow

        while (begin <= end) {
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


}
