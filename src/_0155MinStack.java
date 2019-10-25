import java.util.Stack;

public class _0155MinStack {
    /**
     * Two Stacks
     *
     * 还可以令 minStack 为单调栈, 即push时只有元素更小的时候才放入这个栈,
     * 而pop时只有栈顶与stack栈顶相同时才弹出
     *
     * 这样可以节约一定的空间, 但是实质上空间复杂度仍然是 O(n), 且多了一些判断, 并不一定更优
     *
     * https://www.jiuzhang.com/solution/min-stack/
     *
     */
    private Stack<Integer> stack;
    private Stack<Integer> minStack;

    /** initialize your data structure here. */
    // O(1) and O(1)
    public _0155MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }

    // O(1)
    public void push(int x) {
        stack.push(x);

        if (minStack.isEmpty()) {
            minStack.push(x);
        } else {
            minStack.push(Math.min(x, minStack.peek()));
        }
    }

    // O(1)
    public void pop() {
        minStack.pop();
        stack.pop();
    }

    // O(1)
    public int top() {
        return stack.peek();
    }

    // O(1)
    public int getMin() {
        return minStack.peek();
    }

    // https://leetcode.com/problems/min-stack/discuss/49014/Java-accepted-solution-using-one-stack
    // Using one stack

    // 3 2 1

    // [MAX, 3, 3, 2, 2, 1]

    // [MAX, 2, 3, 2, 1]

    // 2 2 2
    // [max, 2, 2, 2, 2, 2]
    

}
