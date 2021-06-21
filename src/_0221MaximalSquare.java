public class _0221MaximalSquare {
    /**
     * 1. (BFS-like) Search
     *
     * Time: O(mn*mn)
     * Space: O(1)
     */
    public int maximalSquare(char[][] matrix) {
        // corners cases
        int rows = matrix.length;
        int cols = matrix[0].length;
        int maxSize = 0;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (matrix[row][col] == '1') { // notice typiing
                    maxSize = Math.max(maxSize, expand(matrix, row, col, maxSize));
                }
            }
        }

        return maxSize * maxSize;
    }

    private int expand(char[][] matrix, int row, int col, int maxSoFar) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        if (row + maxSoFar >= rows || col + maxSoFar >= cols) {
            return 0;
        }

        int size = 1;
        while (row + size <= rows && col + size <= cols) {
            // rightmost col
            int rightestCol = col + size - 1;
            for (int row1 = row; row1 < row + size; row1++) {
                if (matrix[row1][rightestCol] == '0') { // notice typing
                    return size - 1;
                }
            }

            // bottommost row
            int bottomRow = row + size - 1;
            for (int col1 = col; col1 < col + size; col1++) {
                if (matrix[bottomRow][col1] == '0') { // notice typiing
                    return size - 1;
                }
            }

            size++;
        }

        return size - 1;
    }

    // 1 0 1 0 0
    // 1 0 1 1 1
    // 1 1 1 1 1

    // 1 1 0 1 0

    // -> 1 0 1 0 0
    // -> 1 0

    /**
     * 2. Dynamic Programming
     *
     * dp(i,j): the side length of the maximum square
     * whose **bottom right corner is (i,j)**
     * -> if we define dp(i,j) w/o bottom right constraint,
     * it's hard to reuse sub-problems solutions
     *
     * how to get rid of branch (row == 0 || col == 0)
     * dp = new int[rows + 1][cols + 1] and start iteration with i = 1, j = 1.
     *
     * Time complexity: O(mn)
     * Space complexity: O(mn)
     */
    public int maximalSquare2(char[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];
        int maxLength = 0;

        for (int row = 0; row < rows; row++) {
            if (matrix[row][0] == '1') {
                dp[row][0] = 1;
                maxLength = 1;
            }
        }

        for (int col = 0; col < cols; col++) {
            if (matrix[0][col] == '1') {
                dp[0][col] = 1;
                maxLength = 1;
            }
        }


        for (int row = 1; row < rows; row++) {
            for (int col = 1; col < cols; col++) {
                if (matrix[row][col] == '1') {
                    // don't assume dp[r][c] = dp[r - 1][c - 1] + 1
                    int min = Math.min(dp[row - 1][col - 1],
                            Math.min(dp[row - 1][col], dp[row][col - 1]));
                    dp[row][col] = min + 1;
                    maxLength = Math.max(maxLength, dp[row][col]);

                }
            }
        }

        return maxLength * maxLength;
    }


    /**
     * 2.1 Dynamic Programming with optimized space
     *
     * Time complexity: O(mn)
     * Space complexity: O(n)
     */
    public int maximalSquare21(char[][] matrix) {
        int rows = matrix.length, cols = rows > 0 ? matrix[0].length : 0;
        int[] dp = new int[cols + 1];
        int maxsqlen = 0;
        for (int i = 1; i <= rows; i++) {
            int prev = 0;
            for (int j = 1; j <= cols; j++) {
                int temp = dp[j];
                if (matrix[i - 1][j - 1] == '1') {
                    dp[j] = Math.min(Math.min(dp[j - 1], prev), dp[j]) + 1;
                    maxsqlen = Math.max(maxsqlen, dp[j]);
                } else {
                    dp[j] = 0;
                }
                prev = temp;
            }
        }
        return maxsqlen * maxsqlen;
    }

    public int maximalSquare22(char[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        int[] dp = new int[cols];
        int maxLength = 0;

        for (int col = 0; col < cols; col++) {
            if (matrix[0][col] == '1') {
                dp[col] = 1;
                maxLength = 1;
            }
        }

        for (int row = 1; row < rows; row++) {
            int topLeft = dp[0];
            dp[0] = matrix[row][0] == '1' ? 1 : 0;
            maxLength = Math.max(maxLength, dp[0]); // update mL whenever dp is updated
            for (int col = 1; col < cols; col++) {
                int old = dp[col];
                if (matrix[row][col] == '1') {
                    int min = Math.min(topLeft,
                            Math.min(dp[col], dp[col - 1]));
                    dp[col] = min + 1;
                    maxLength = Math.max(maxLength, dp[col]);
                } else {
                    //[["1","1","1","1","1"],
                    // ["1","1","1","1","1"],
                    // ["0","0","0","0","0"], ->
                    // ["1","1","1","1","1"],
                    // ["1","1","1","1","1"]]
                    dp[col] = 0;
                }
                topLeft = old;
            }
        }

        return maxLength * maxLength;
    }
}
