import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

// todo: other solutions
public class _0239SlidingWindowMaximum {
    /**
     * 2. Deque <Monotonic Queue>
     *
     * Increasing or decreasing?
     * suppose i < j, and nums[i] < nums[j]
     * as we move window, we know nums[i] will never be max, and will be out of bounds earlier
     * i would be least possible candidate for max, so rule out smaller elements on the left
     *
     * [earlier to be out of window, .... later to be out of window]
     *
     * https://www.jiuzhang.com/solution/sliding-window-maximum/
     * 单调队列中的元素是严格单调的。我们在求解这个问题的时候需要维护他的单调性。
     * 队首元素即为当前位置的最大值。假设要求滑动窗口中的最大值。我们就需要确保滑动窗口中的元素从队首到队尾是递减的。
     * 每滑动一次就判断当前元素和队尾元素的关系，如果放入队尾满足单调递减，那么放入即可；如果放入不满足，就需要删除队尾元素直到放入当前元素之后满足队列单调递减。同时要确保已经出窗口的最大值（队首元素）被删除掉。
     *
     * Related:
     * https://1e9.medium.com/monotonic-queue-notes-980a019d5793
     *
     * Time: O(n)
     * Space: O(k)
     */
    public int[] maxSlidingWindow(int[] nums, int k) {
        int n = nums.length;
        if (n == 0) {
            return nums;
        }
        int[] result = new int[n - k + 1];
        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            // we can exchange if and while block
            if (!queue.isEmpty() && queue.peek() < i - k + 1) {
                // pop out out of range element in one round at most. (the leftmost)
                // (one round we only accept one element, so we pop at most one element out).
                queue.poll();
            }

            // notice isEmpty, peekLast and pollLast
            while (!queue.isEmpty() && nums[i] >= nums[queue.peekLast()]) {
                // 每滑动一次就判断当前元素和队尾元素的关系，
                // 1.
                // 如果放入队尾满足单调递减，放入即可
                // previous elements are bigger, and then could be max in the next window
                // 2.
                // 如果放入不满足，就需要删除队尾元素直到放入当前元素之后满足队列单调递减。同时要确保已经出窗口的最大值（队首元素）被删除掉。
                // previous elements are smaller, and they will be out of window first, so no need to keep them
                queue.pollLast();
            }

            // offer i, so i will be considered
            queue.offer(i);

            if (i - k + 1 >= 0) {
                result[i - k + 1] = nums[queue.peek()];
            }
        }
        return result;
    }
}
