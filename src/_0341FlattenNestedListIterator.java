import java.util.*;

interface NestedInteger {
    // @return true if this NestedInteger holds a single integer, rather than a nested list.
    boolean isInteger();

    // @return the single integer that this NestedInteger holds, if it holds a single integer
    // Return null if this NestedInteger holds a nested list
     Integer getInteger();

     // @return the nested list that this NestedInteger holds, if it holds a nested list
     // Return null if this NestedInteger holds a single integer
    List<NestedInteger> getList();
}

/**
 * Queue is better than stack in this question
 *
 * ? How to write hasNext() without side effect
 */
public class _0341FlattenNestedListIterator implements Iterator<Integer> {
    private Deque<NestedInteger> stack;

    /**
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
//    public _0341FlattenNestedListIterator(List<NestedInteger> nestedList) {
//        stack = new ArrayDeque<>();
//        pushListToStack(nestedList);
//    }

    @Override
    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     */
    public Integer next() {
        if (!hasNext()) {
            return null;
        }

        return stack.pop().getInteger();
    }

    @Override
    /**
     * Time complexity: O(the sum of number of elements in all the nested lists within the first element)
     * Space complexity:
     * if stack.peek() is Integer: O(1)
     * if stack.peek() is List: O(sum of number of elements within each nesting)
     */
    public boolean hasNext() {
        while (!stack.isEmpty() && !stack.peek().isInteger()) {
            pushListToStack(stack.pop().getList());
        }

        return !stack.isEmpty();
    }

    /**
     * n: length of list
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param list
     */
    private void pushListToStack (List<NestedInteger> list) {
        Deque<NestedInteger> temp = new ArrayDeque<>();

        for (NestedInteger nested : list) {
            temp.push(nested);
        }

        while (!temp.isEmpty()) {
            stack.push(temp.pop());
        }
    }


    // 2. Queue
    // Runtime: 2 ms, faster than 100.00% of Java online submissions for Flatten Nested List Iterator.
    //Memory Usage: 37.5 MB, less than 100.00% of Java online submissions for Flatten Nested List Iterator.
    private Queue<Integer> queue;

    // Time complexity: O(number of [lists + integers])
    // Space complexity: O(maximum number of nesting)
//    public _0341FlattenNestedListIterator(List<NestedInteger> nestedList) {
//        queue = new LinkedList<>();
//        enqueueList(nestedList);
//    }

    /**
     * Time complexity: O(number of [lists + integers])
     * Space complexity: O(maximum number of nesting)
     *
     * @param list
     */
    private void enqueueList(List<NestedInteger> list) {
        for (NestedInteger in : list) {
            if (in.isInteger()) {
                queue.offer(in.getInteger());
            } else {
                enqueueList(in.getList());
            }
        }
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     *
     * @return
     */
    public Integer next1() {
        if (!queue.isEmpty()) {
            return queue.poll();
        }

        return null;
    }

    /**
     * Time complexity: O(1)
     * Space complexity: O(1)
     *
     * @return
     */
    public boolean hasNext1() {
        return !queue.isEmpty();
    }
}
