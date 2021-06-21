import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Stack;

public class _0084LargestRectangleInHistogram {
    /**
     * 1. Brute Force
     *
     * Time complexity: O(n ^ 3)
     * Space complexity: O(1)
     */
    public int largestRectangleArea(int[] heights) {
        int maxArea = 0;

        for (int i = 0; i < heights.length; i++) {
            for (int j = i; j < heights.length; j++) {
                int minHeight = Integer.MAX_VALUE;
                for (int k = i; k <= j; k ++) {
                    minHeight = Math.min(minHeight, heights[k]);
                }
                maxArea = Math.max(maxArea, minHeight * (j - i + 1));
            }
        }

        return maxArea;
    }

    /**
     * 2. Brute Force with slight improvement
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(1
     */
    public int largestRectangleArea2(int[] heights) {
        int maxArea = 0;

        for (int i = 0; i < heights.length; i++) {
            int minHeight = Integer.MAX_VALUE;

            for (int j = i; j < heights.length; j++) {
                minHeight = Math.min(minHeight, heights[i]);
                maxArea = Math.max(maxArea, minHeight * (j - i + 1));
            }
        }

        return maxArea;
    }

    /**
     * 3. Divide and Conquer
     *
     * the rectangle with maximum area will be the maximum of:
     * 1) The widest possible rectangle with height equal to the height of the shortest bar.
     * 2) The largest rectangle confined to the left of the shortest bar(subproblem).
     * 3) The largest rectangle confined to the right of the shortest bar(subproblem).
     *
     * Time complexity:
     * O(nlgn)
     * worst: O(n^2)
     *
     * Space complexity:
     * O(n): Recursion with worst case depth
     */
    public int largestRectangleArea3(int[] heights) {
        return calculateArea(heights, 0, heights.length - 1);
    }

    public int calculateArea(int[] heights, int start, int end) {
        if (start > end) {
            return 0;
        }

        int minIndex = start;

        for (int i = start; i <= end; i++) {
            if (heights[minIndex] > heights[i]) {
                minIndex = i;
            }
        }

        return Math.max(heights[minIndex] * (end - start + 1),
                Math.max(calculateArea(heights, start, minIndex - 1),
                        calculateArea(heights, minIndex + 1, end)));
    }

    // 4. Better Divide and conquer with segment tree
    // https://leetcode.com/problems/largest-rectangle-in-histogram/solution/
    // we won't gain much advantage with that approach if the array happens to be sorted in either ascending or descending order,
    // since every time we need to find the minimum number in a large subarray O(n).
    // Thus, the overall complexity becomes O(n^2) in the worst case.
    // We can reduce the time complexity by using a Segment Tree to find the minimum every time which can be done in O(logn) time.

    /**
     * 5. Increasing Stack, end sentinel
     *
     * https://leetcode-cn.com/problems/largest-rectangle-in-histogram/solution/bao-li-jie-fa-zhan-by-liweiwei1419/
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Everytime we pop,
     * height: heights[i]
     */
    public int largestRectangleArea4(int[] heights) {
        if (heights == null || heights.length == 0) {
            return 0;
        }

        Deque<Integer> stack = new ArrayDeque<>();
        int max = 0;

        for (int i = 0; i <= heights.length; i++) {
            // 0 as end sentinel, so that we can process every single height as the height of rectangle
            int curt = (i == heights.length) ? 0 : heights[i];

            while (!stack.isEmpty() && curt < heights[stack.peek()]) {
                int h = heights[stack.pop()];

                // To current h (the element that we just pop): because of increasing stack
                // cur stack top is the first smaller height's index on the left
                // i is the first smaller height's index on the right
                int w = 0;
                if (stack.isEmpty()) {
                    w = (i - 1) - 0 + 1;
                } else {
                    w = (i - 1) - (stack.peek() + 1) + 1;
                }

                max = Math.max(max, h * w);
            }

            stack.push(i);
        }

        return max;
    }

}
