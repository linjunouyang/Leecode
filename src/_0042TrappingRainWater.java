import java.util.Stack;

public class _0042TrappingRainWater {
    /**
     * 1. Brute Force
     *
     * For ** each element ** in the array,
     * the maximum level of water it can trap = Math.min(max_left, max_right) - height[i];
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(1)
     *
     * Runtime: 60 ms, faster than 5.53% of Java online submissions for Trapping Rain Water.
     * Memory Usage: 41.5 MB, less than 5.48% of Java online submissions for Trapping Rain Water.
     *
     * @param height
     * @return
     */
    public int trap(int[] height) {
        int ans = 0;

        for (int i = 0; i < height.length; i++) {
            int max_left = 0;
            int max_right = 0;
            for (int j = i; j >= 0; j--) {
                max_left = Math.max(max_left, height[j]);
            }
            for (int j = i; j < height.length; j++) {
                max_right = Math.max(max_right, height[j]);
            }
            ans += Math.min(max_left, max_right) - height[i];
        }

        return ans;
    }

    /**
     * 2. Dynamic Programming?
     *
     * In brute force, we iterate over the left and right parts again and again
     * just to find the highest bar size upto that index. But, this could be stored.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param height
     * @return
     */
    public int trap2(int[] height) {
        if (height == null || height.length == 0) {
            return 0;
        }

        int ans = 0;
        int size = height.length;

        int[] left_max = new int[size];
        int[] right_max = new int[size];

        left_max[0] = height[0];
        for (int i = 1; i < size; i++) {
            left_max[i] = Math.max(height[i], left_max[i - 1]);
        }

        right_max[size - 1] = height[size - 1];
        for (int i = size - 2; i >= 0; i--) {
            right_max[i] = Math.max(height[i], right_max[i + 1]);
        }

        for (int i = 1; i < size - 1; i++) {
            ans += Math.min(left_max[i], right_max[i]) - height[i];
        }

        return ans;
    }

    /**
     * 3. Stack
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 5 ms, faster than 17.64% of Java online submissions for Trapping Rain Water.
     * Memory Usage: 36.3 MB, less than 100.00% of Java online submissions for Trapping Rain Water.
     *
     * @param height
     * @return
     */
    public int trap3(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        Stack<Integer> stack = new Stack<>();
        int total = 0;
        int i = 0;

        while (i < height.length) {
            if (stack.isEmpty() || height[i] <= height[stack.peek()]) {
                stack.push(i++);
            } else {
                int pre = stack.pop();
                if (!stack.isEmpty()) {
                    // find the smaller height between the two sides
                    int minHeight = Math.min(height[stack.peek()], height[i]);
                    // calculate the area
                    total += (minHeight - height[pre]) * (i - stack.peek() - 1);
                }
            }
        }

        return total;
    }

    /**
     * 4. Two Pointers
     *
     * Calculate the stored water at two pointers.
     * At the start of every loop,
     * update the current maximum height from left side (A[0,1...a]) and the maximum height from right side(A[b,b+1...n-1]).
     *
     * if(leftmax<rightmax)
     * -> at least (leftmax-A[a]) water can definitely be stored no matter what happens between [a,b]
     * since we know there is a barrier at the rightside(rightmax>leftmax).
     * On the other side, we cannot store more water than (leftmax-A[a]) at index a since the barrier at left is of height leftmax.
     * So, we know the water that can be stored at index a is exactly (leftmax-A[a]).
     *
     * The same logic applies to the case when (leftmax>rightmax).
     *
     * At each loop we can make a and b one step closer. Thus we can finish it in linear time.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 1 ms, faster than 98.28% of Java online submissions for Trapping Rain Water.
     * Memory Usage: 37.4 MB, less than 98.63% of Java online submissions for Trapping Rain Water.
     *
     * @param height
     * @return
     */
    public int trap4(int[] height) {
        int total = 0;

        int left = 0;
        int right = height.length - 1;

        int left_max = 0;
        int right_max = 0;

        while (left < right) {
            left_max = Math.max(left_max, height[left]);
            right_max = Math.max(right_max, height[right]);

            if (left_max < right_max) {
                total += left_max - height[left];
                left++;
            } else {
                total += right_max - height[right];
                right--;
            }
        }

        return total;
    }
}
