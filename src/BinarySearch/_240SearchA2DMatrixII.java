package BinarySearch;

public class _240SearchA2DMatrixII {
    /*
    Binary-Search like method

    Explanation:
    Start from top right corner (or bottom left corner).
    If the target is bigger, then the target can't be in entire row because the row is sorted.
    If the target is smaller, then the target can't in the entire column because the column is sorted too.
    Rule out one row or one column each time, so the time complexity is O(m+n).
    (It's like the matrix contains two binary search trees with two corresponding roots)

    Potential Improvement:
    1. In the while loop you put '==' at first, but mostly it will be passed.
    We can end the if-chain earlier by putting the '==' at last. It beats 93%.
    When you put '==' at first, it beat 56%.

    Time Complexity: O(m+n)
    Space Complexity: O(1)

     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return false;
        }

        int startRow = matrix.length - 1, endRow = 0;
        int startCol = 0, endCol = matrix[0].length - 1;

        while (startRow >= endRow && startCol <= endCol) {
            int guess = matrix[startRow][startCol];
            if (guess == target) {
                return true;
            } else if (guess < target) {
                startCol++;
            } else {
                startRow--;
            }
        }

        return false;
    }
}
