/**
 * TBD: Divide and Conquer
 *
 * Not binary search, but similar
 *
 * --
 * 8.22 passed
 */


public class _0240SearchA2DMatrixII {
    /*
    1. Binary-Search like method

    Explanation:
    Start from top right corner (or bottom left corner).
    If the target is bigger, then the target can't be in entire row because the row is sorted.
    If the target is smaller, then the target can't in the entire column because the column is sorted too.
    Rule out one row or one column each time, so the time complexity is O(m+n).
    (It's like the matrix contains two binary search trees with two corresponding roots)

    little Improvement:
    1. In the while loop you put '==' at first, but mostly it will be passed.
    We can end the if-chain earlier by putting the '==' at last. It beats 93%.
    When you put '==' at first, it beat 56%.

    2.  pre-increment is better than post-increments (++i is better than i++)
    because of the way it works internally avoiding an extra register copy.

    // ++i
    int pre_increment(int &i) {
        i = i + 1;
        return i;
    }

    // i++
    int post_increment(int &i) {
        int original_i = i;
        i = i + 1;
        return original_i;
    }

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

    /**
     * 2. Divide and Conquer
     */
}
