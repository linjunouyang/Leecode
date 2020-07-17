import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class _1130MinimumCostTreeFromLeafValues {
    /**
     * Example array:
     * 1,2,3,4
     *
     * Variants
     * 1 and 2,3,4
     * 1,2 and 3,4
     * 1,2,3 and 4
     *
     * 1-st variant divided further in its right part on 2 variants
     * 2 and 3,4
     * 2,3 and 4
     *
     * 3-d variant divided on 2 in its left part
     * 1 and 2,3
     * 2,3 and 1
     *
     * You see? Sub-trees of trees? This is where recursion works...
     *
     */

    /**
     * 1. Dynamic Programming (bottom-up)
     *
     * after division the array becomes 2 subarrays, now we have 2 subproblems.
     * let's try all possible ways to divide the array. Use a 2d array for memorization.
     *
     * 0 1 5 8 10
     * 0 0 2 6 9
     * 0 0 0 3 7
     * 0 0 0 0 4
     * 0 0 0 0 0
     *
     * 0 represents default value (we don't compute)
     * 1-10 represents the order of computation in this code
     * For example, 5 derives from (1 and the 0 below 2) and (2 and 0 on the left of 1)
     *
     * dp(i, j)= min(max(arr[i .. k]) * max(arr[k+1 .. j]) + dp(i, k) + dp(k + 1, j))
     * where k go from left to right-1
     * dp(i, j) = 0 for i = j
     *
     * Time complexity: O(n^3)
     * n^2 state and O(n) to compute each
     *
     * Space complexity: O(n^2)
     */
    public int mctFromLeafValues1(int[] arr) {
        int[][] dp = new int[arr.length][arr.length];
        int[][] max = new int[arr.length][arr.length];

        // Finding O(n ^ 2)
        for(int i = 0; i < arr.length; i ++) {
            int localMax = 0;
            for(int j = i; j < arr.length; j ++) {
                if(arr[j] > localMax) {
                    localMax = arr[j];
                }
                max[i][j] = localMax;
            }
        }

        // Diagonal, top left -> bottom right (draw a matrix, think about the source of value)
        for(int len = 2; len <= arr.length; len ++) {
            for(int left = 0; left + len - 1 < arr.length; left ++) { // reversed order also works
                int right = left + len - 1;
                dp[left][right] = Integer.MAX_VALUE;
                for (int k = left; k < right; k++) { // reversed order also works
                    int rootVal = max[left][k] * max[k + 1][right];
                    dp[left][right] = Math.min(dp[left][right],
                            dp[left][k] + dp[k + 1][right] + rootVal);
                }
            }
        }

        return dp[0][arr.length-1];
    }

    /**
     * 2. Dynamic Programming (top-down)
     *
     * Time complexity: O(n ^ 3)
     * Space complexity: O(n ^ 2)
     *
     * @param arr
     * @return
     */
    public int mctFromLeafValues2(int[] arr) {
        int n = arr.length;
        int[][] dp = new int[n][n];
        return dfs(arr, 0, n - 1, dp);
    }

    public int dfs(int[] arr, int s, int e, int[][] dp) {
        if (s == e) return 0;
        if (dp[s][e] > 0) return dp[s][e]; // arr entries are positive
        int ans = Integer.MAX_VALUE;
        for (int i = s; i < e; i++) {
            int left = dfs(arr, s, i, dp);
            int right = dfs(arr, i + 1, e, dp);

            // find local max
            int maxLeft = 0, maxRight = 0;
            for (int j = s; j <= i; j++) {
                maxLeft = Math.max(maxLeft, arr[j]);
            }
            for (int j = i + 1; j <= e; j++) {
                maxRight = Math.max(maxRight, arr[j]);
            }

            ans = Math.min(ans, left + right + maxLeft * maxRight);
        }
        dp[s][e] = ans;
        return ans;
    }

    /**
     * 3. Greedy
     *
     *    24        24
     *   6  8     12  4
     *     2 4   6  2
     *
     * DP is like brute force since we calculate and compare the results all possible pivots.
     * Notice that when we build each level of the binary tree,
     * it is the max left leaf node and max right lead node that are being used,
     * so we would like to put big leaf nodes close to the root.
     * Otherwise, taking the leaf node with max value in the array as an example,
     * if its level is deep, for each level above it, its value will be used to calculate the non-leaf node value,
     * which will result in a big total sum.
     *
     * the greedy approach is to find the smallest value in the array,
     * use it and its smaller neighbor to build a non-leaf node,
     * then we can delete it since it's smaller than its neightbor so it will never be used again.
     * Repeat this process until there is only one node left in the array (which means we cannot build a new level any more)
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     *
     * @param arr
     * @return
     */
    public int mctFromLeafValues3(int[] arr) {
        public int mctFromLeafValues(int[] arr) {
            int res = 0;
            List<Integer> nums = new ArrayList<>();
            for (int a : arr) nums.add(a);
            while (nums.size() > 1) {
                int min = Integer.MAX_VALUE, l = 0, r = 0;
                for (int i = 1; i < nums.size(); i++) {
                    if (nums.get(i) * nums.get(i - 1) < min) {
                        min = nums.get(i) * nums.get(i - 1);
                        l = i - 1;
                        r = i;
                    }
                }
                res += min;
                if (nums.get(l) > nums.get(r)) nums.remove(r);
                else nums.remove(l);
            }
            return res;
        }
    }

    /**
     * 4. Stack
     *
     * In the greedy approach, every time we delete the current minimum value,
     * we need to start over and find the next smallest value again, so repeated operations are more or less involved.
     *
     * To further accelerate it, one observation is that for each leaf node in the array,
     * when it becomes the minimum value in the remaining array,
     * its left and right neighbors will be the first bigger value in the original array to its left and right.
     * This observation is a clue of a possible monotonic stack solution as follows.
     *
     * -----
     * Use stack to keep a decreasing order by adding smaller values, while there is bigger value
     *  arr[i] than the peek, pop it and store as mid and calculate the multiplication mid*min(arr[i],
     *  stack.peek()).
     *
     * ----
     * While searching for the smallest product pair, we do a lot of recompuation for product. lee215 's solution eliminates the recomputation by maintaining a monotonic decreasing array.
     * I am here to provide some insignt maybe how he reaches this idea.
     *
     * 1. Why decreasing ?
     * Because we use small leave once and discard them, large leave stays.
     *
     * 2. When we met a small leave, like [..b, a, c..]withb > a and a < c, can we remove a and get product a*min(b,c) ?
     * It seems not guaranteed to be the smallest product pair in the remaining leaves at first glance.
     * But yes it's safe to do so. Let's consider the general case, after some removal the array looks like: [..a, c, d..].
     * If d < a , then d will get removed and c stays.
     * if d > a, then a*c < c*d, pair(a,c) will have higher priority in the original greedy solution
     * and thus to be removed first.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public int mctFromLeafValues4(int[] A) {
        int res = 0;
        Stack<Integer> stack = new Stack<>();
        stack.push(Integer.MAX_VALUE);
        for (int a : A) {
            while (stack.peek() <= a) {
                int mid = stack.pop();
                res += mid * Math.min(stack.peek(), a);
            }
            stack.push(a);
        }
        while (stack.size() > 2) {
            res += stack.pop() * stack.peek();
        }
        return res;
    }


}
