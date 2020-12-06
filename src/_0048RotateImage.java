public class _0048RotateImage {
    /**
     * 1. Transpose + flip columns
     * 1  2  3
     * 4  5  6
     * 7  8  9
     *
     * transpose
     * 1  4  7
     * 2  5  8
     * 3  6  9
     *
     * flip column
     * 7  4  1
     * 8  5  2
     * 9  6  3
     *
     * optimization:
     * extract flip method
     *
     * Follow-up:
     * How to do anticlockwise rotation?
     * transpose -> flip rows
     * or
     * flip columns -> transpose
     *
     * Time: O(n ^ 2)
     * Space: O(1)
     */
    public void rotate(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++){
            for(int j = i + 1; j < matrix[0].length; j++){
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = temp;
            }
        }

        for (int i =0 ; i < matrix.length; i++){
            for(int j = 0; j < matrix.length/2; j++){
                int temp = 0;
                temp = matrix[i][j];
                matrix[i][j] = matrix[i][matrix.length-1-j];
                matrix[i][matrix.length-1-j] = temp;
            }
        }
    }

    /**
     * 1  2  3
     * 4  5  6
     * 7  8  9
     *
     * flip row
     * 7  8  9
     * 4  5  6
     * 1  2  3
     *
     * transpose
     * 7  4  1
     * 8  5  2
     * 9  6  3
     */
    public void rotate11(int[][] matrix) {
        int len = matrix.length;
        for(int i = 0; i < len / 2; i++) {
            int[] tmp = matrix[i];
            matrix[i] = matrix[len - 1 - i];
            matrix[len - 1 - i] = tmp;
        }

        for(int i = 0; i < len; i++) {
            for(int j = 0; j < i; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[j][i];
                matrix[j][i] = tmp;
            }
        }
    }

    /**
     * 2. One loop
     *
     * https://leetcode.com/problems/rotate-image/discuss/18895/Clear-Java-solution
     * illustration in the comment
     *
     * Similar idea but use length circle to decide for-loop:
     * https://leetcode.com/problems/rotate-image/discuss/247174/Easy-Java-solution-with-explanation-processing-the-matrix-from-outer-to-inner
     *
     * Time: O(n ^ 2)
     * Space: O(1)
     *
     * @param matrix
     */
    public void rotate2(int[][] matrix) {
        int n=matrix.length;
        for (int i=0; i<n/2; i++) {
            for (int j = i; j < n - i - 1; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[n - j - 1][i];
                matrix[n - j - 1][i] = matrix[n - i - 1][n - j - 1];
                matrix[n - i - 1][n - j - 1] = matrix[j][n - i - 1];
                matrix[j][n - i - 1] = tmp;
            }
        }
    }

}
