import java.util.Stack;

/**
 * Needs to understand multiple solutions
 * Needs to understand 85
 */
public class _0085MaximalRectangle {
    public int maximalRectangle(boolean[][] matrix) {
        if (matrix == null||matrix.length==0||matrix[0].length==0) {
            return 0;
        }

        int ans=0;
        int rows = matrix.length;
        int cols = matrix[0].length;
        // num of '1' straight above (i, j)
        int[][] dp=new int[rows][cols+1];

        for (int i = 0; i < rows; i++) {
            for (int j = 0;j < cols; j++) {
                if (i == 0 && matrix[i][j]) {
                    dp[i][j] = 1;
                } else if (matrix[i][j]) {
                    dp[i][j] = dp[i - 1][j] + 1;
                }
            }
        }

        // Find max area for all rectangles whose base is at ith row. (把每一行作为底找最大矩形)
        for (int i = 0; i < rows; i++) {
            ans = Math.max(ans, largestRectangleArea(dp[i]));
        }

        return ans;
    }

    private int largestRectangleArea(int[] height) {
        Stack<Integer> S = new Stack<>(); // increasing stack
        height[height.length-1]=0;
        int sum = 0;
        for (int i = 0; i < height.length; i++) {
            if (S.empty() || height[i] > height[S.peek()]) {
                S.push(i);
            } else {
                int tmp = S.pop();
                sum = Math.max(sum, height[tmp] * (S.empty() ? i : i - S.peek() - 1));
                i--;  //拿着右边界， 寻找左边界；
            }
        }
        return sum;
    }
}
