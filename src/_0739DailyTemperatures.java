import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 */
public class _0739DailyTemperatures {
    /**
     * 1. Monotonous (decreasing) Stack
     *
     * Similar concept as 84. Largest Rectangle in Histogram
     *
     * Store currently unsolved elements, later if there is a bigger number,
     * withdraw the unsolved elements and get the answer.
     *
     * T = [73, 74, 75, 71, 69, 72, 76, 73]
     * a = [ 1,  1,  4,  2,  1,  1,  0,  0]
     *
     * ArrayDeque:
     * peek() returns null if empty
     * pop() throws NoSuchElementException if empty
     * -> check isEmpty() before calling them
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param temperatures
     * @return
     */
    public int[] dailyTemperatures(int[] temperatures) {
        Deque<Integer> stack = new ArrayDeque<>();
        int[] ret = new int[temperatures.length];
        for (int i = 0; i < temperatures.length; i++) {
            while(!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                int idx = stack.pop();
                ret[idx] = i - idx;
            }
            stack.push(i);
        }
        return ret;
    }

    /**
     * 2. Monotonous (decreasing) stack (implemented using array)
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public int[] dailyTemperatures2(int[] temperatures) {
        int[] stack = new int[temperatures.length];
        int top = -1;
        int[] ret = new int[temperatures.length];
        for(int i = 0; i < temperatures.length; i++) {
            while(top > -1 && temperatures[i] > temperatures[stack[top]]) {
                int idx = stack[top--];
                ret[idx] = i - idx;
            }
            stack[++top] = i;
        }
        return ret;
    }
}
