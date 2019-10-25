import java.util.ArrayDeque;
import java.util.Deque;

public class _0232ImplementQueueUsingStacks {
    class MyQueue {
        private Deque<Integer> stack1;
        private Deque<Integer> stack2;

        /** Initialize your data structure here: O(1). */
        public MyQueue() {
            stack1 = new ArrayDeque<>();
            stack2 = new ArrayDeque<>();
        }

        /** Push element x to the back of queue: O(1) */
        public void push(int x) {
            stack1.push(x);
        }

        /** Removes the element from in front of queue and returns that element: */
        /** Amortized O(1), Worst-case O(n) */
        /**
         *
         * Amortized analysis gives the **average** performance (over time) of each operation in the **worst case**.
         * The basic idea is that a worst case operation can alter the state
         * in such a way that **the worst case cannot occur again for a long time**, thus amortizing its cost.
         *
         * Consider this example where we start with an empty queue with the following sequence of operations applied:
         *
         * push_1, push_2, push_n, pop_1, pop_2, pop_n
         *
         *
         * The worst case time complexity of a single pop operation is O(n). S
         * ince we have n pop operations, using the worst-case per operation analysis gives us a total of O(n^2)
         *
         * However, in a sequence of operations the worst case does not occur often in each operation
         * some operations may be cheap, some may be expensive.
         * Therefore, a traditional worst-case per operation analysis can give overly pessimistic bound.
         *
         * the number of times pop operation can be called is limited by the number of push operations before it.
         * Although a single pop operation could be expensive, it is expensive only once per n times (queue size),
         * when s2 is empty and there is a need for data transfer between s1 and s2.
         *
         * Hence the total time complexity of the sequence is :
         * n (for push operations) + 2*n (for first pop operation) + n - 1 ( for pop operations)
         * which is O(2n).This gives O(2n/2n) = O(1) average time per operation.
         *
         * @return
         */
        public int pop() {
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.pop();
        }

        /** Get the front element: O(n) */
        public int peek() {
            if (stack2.isEmpty()) {
                while (!stack1.isEmpty()) {
                    stack2.push(stack1.pop());
                }
            }
            return stack2.peek();
        }

        /** Returns whether the queue is empty: O(1) */
        public boolean empty() {
            return stack1.isEmpty() && stack2.isEmpty();
        }
    }

}
