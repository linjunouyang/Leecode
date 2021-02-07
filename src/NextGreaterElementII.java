import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

public class NextGreaterElementII {
    /**
     * 1. Brute Force
     *
     * Circular array -> using mod operator to calculate index
     *
     * Time: O(n^2)
     * Space: O(1)
     */
    public int[] nextGreaterElements(int[] nums) {
        int[] res = new int[nums.length];
        for (int i = 0; i < nums.length; i++) {
            res[i] = -1;
            for (int j = 1; j < nums.length; j++) {
                if (nums[(i + j) % nums.length] > nums[i]) {
                    res[i] = nums[(i + j) % nums.length];
                    break;
                }
            }
        }
        return res;
    }

    /**
     * 2. Monotonous decreasing stack
     *
     * Time: O(n)
     * Space: O(n)
     */
    public int[] nextGreaterElements2(int[] nums) {
        if (nums == null) {
            return new int[0];
        }

        int len = nums.length;
        int[] ans = new int[len];
        Arrays.fill(ans, -1);

        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i < 2 * len; i++) {
            int idx = i % len;
            while (!stack.isEmpty() && nums[idx] > nums[stack.peek()]) {
                ans[stack.pop()] = nums[idx];
            }
            if (i < len) {
                stack.push(i);
            }
        }

        return ans;
    }

    /**
     * from end to head
     * ? hard to understand
     */
    public int[] nextGreaterElements21(int[] nums) {
        int[] res = new int[nums.length];
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 2 * nums.length - 1; i >= 0; --i) {
            int idx = i % nums.length;
            while (!stack.isEmpty() && nums[stack.peek()] <= nums[idx]) {
                stack.pop();
            }
            res[idx] = stack.isEmpty() ? -1 : nums[stack.peek()];
            stack.push(idx);
        }
        return res;
    }

    /**
     * https://leetcode.com/problems/next-greater-element-ii/discuss/98264/NO-STACK%3A-O(n)-time-complexity-and-O(1)-space-complexity-using-DP
     * No stack?
     */
}
