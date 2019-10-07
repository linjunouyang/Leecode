import java.util.ArrayDeque;
import java.util.Queue;

public class _225ImplementStackUsingQueues {
    /**
     * 1. Two Queues Push O(1), Pop O(n)
     *
     * Runtime: 45 ms, faster than 11.10% of Java online submissions for Implement Stack using Queues.
     * Memory Usage: 34.1 MB, less than 91.67% of Java online submissions for Implement Stack using Queues.
     *
     */
    class MyStack1 {

        private Queue<Integer> q1;
        private Queue<Integer> q2;
        private int top;

        /** Initialize your data structure here. O(1) */
        public MyStack1() {
            q1 = new ArrayDeque<>();
            q2 = new ArrayDeque<>();
        }

        /** Push element x onto stack. O(1) */
        public void push(int x) {
            q1.offer(x);
            top = x;
        }

        /** Removes the element on top of the stack and returns that element. O(n) */
        public int pop() {
            while (q1.size() > 1) {
                top = q1.poll();
                q2.offer(top);
            }

            int pop = q1.poll();

            Queue<Integer> temp = q1;
            q1 = q2;
            q2 = temp;

            return pop;
        }

        /** Get the top element. O(1)*/
        public int top() {
            return top;
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return q1.isEmpty();
        }
    }

    /**
     *
     * 2. Two Queues, Push O(n), Pop O(1)
     *
     * Runtime: 43 ms, faster than 37.87% of Java online submissions for Implement Stack using Queues.
     * Memory Usage: 34.1 MB, less than 91.67% of Java online submissions for Implement Stack using Queues.
     */
    class MyStack2 {
        private Queue<Integer> queue1;
        private Queue<Integer> queue2;

        /** Initialize your data structure here. */
        public MyStack2() {
            queue1 = new ArrayDeque<>();
            queue2 = new ArrayDeque<>();
        }

        /** Push element x onto stack. */
        public void push(int x) {
            queue2.offer(x);

            while (!queue1.isEmpty()) {
                queue2.offer(queue1.poll());
            }

            Queue<Integer> temp = queue1;
            queue1 = queue2;
            queue2 = queue1;
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            return queue1.poll();
        }

        /** Get the top element. */
        public int top() {
            return queue1.peek();
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return queue1.isEmpty();
        }
    }

    /**
     * 3. One Queue. Maintain Stack Order after every insertion
     *
     *
     * Runtime: 42 ms, faster than 80.11% of Java online submissions for Implement Stack using Queues.
     * Memory Usage: 34.1 MB, less than 91.67% of Java online submissions for Implement Stack using Queues.
     */
    class MyStack3 {
        private Queue<Integer> queue;

        /** Initialize your data structure here. */
        public MyStack3() {
            queue = new ArrayDeque<>();
        }

        /** Push element x onto stack. */
        public void push(int x) {
            queue.offer(x);

            int count = queue.size();

            while (count > 1) {
                queue.offer(queue.poll());
                count--;
            }
        }

        /** Removes the element on top of the stack and returns that element. */
        public int pop() {
            return queue.poll();
        }

        /** Get the top element. */
        public int top() {
            return queue.peek();
        }

        /** Returns whether the stack is empty. */
        public boolean empty() {
            return queue.isEmpty();
        }
    }


}
