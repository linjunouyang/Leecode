import java.util.concurrent.locks.ReentrantLock;

/**
 * Less Attribute:
 * Less attributes usually implies little or no redundancy among the attributes.
 * The less redundant the attributes are, the simpler the manipulation logic, which eventually could be less error-prone.
 * Less attributes also requires less space and therefore it could also bring efficiency to the runtime performance.
 *
 * However, it is not advisable to seek for the minimum set of attributes. Sometimes, a bit of redundancy could help with the time complexity.
 *
 */
public class _0622DesignCircularQueue {
    /**
     * 1. Circular Array
     *
     * Time: O(1)
     * Space: O(1)
     */
    class MyCircularQueue {
        private int[] arr;
        private int start;
        private int len; // if using end, hard to calculate len
        private ReentrantLock queueLock = new ReentrantLock();

        public MyCircularQueue(int k) {
            arr = new int[k];
            start = 0;
            len = 0;
        }

        public boolean enQueue(int value) {
            queueLock.lock();
            try {
                if (len == arr.length) {
                    return false;
                }
                int idx = (start + len) % arr.length;
                arr[idx] = value;
                len++;
            } finally {
                queueLock.unlock();
            }
            return true;
        }

        public boolean deQueue() {
            queueLock.lock();
            try {
                if (len == 0) {
                    return false;
                }
                start = (start + 1) % arr.length;
                len--;
            } finally {
                queueLock.unlock();
            }
            return true;
        }

        public int Front() {
            if (len == 0) {
                return -1;
            }
            return arr[start];
        }

        public int Rear() {
            if (len == 0) {
                return -1;
            }
            return arr[(start + len - 1) % arr.length];
        }

        public boolean isEmpty() {
            return len == 0;
        }

        public boolean isFull() {
            return len == arr.length;
        }
    }

    /**
     * 2. Circular Linked List
     *
     * Advantage: does not pre-allocate memory for unused capacity
     *
     * The purpose of a Circular Queue is to minimize the number of times the application calls malloc() and to make scanning memory blocks more efficient.
     * The malloc() operation is an expensive operation.
     * The system needs to find a contiguous block of memory that is the desired size and this takes time, especially in low memory conditions.
     * This means that implementing a circular queue as a linked list, where each node is effectively malloced independently, is the wrong choice.
     *
     * The benefits of a Circular Queue are that the memory for the queue is allocated once at a fixed size
     * and that iterating through the queue is a simple matter of iterating contiguous blocks of memory.
     *
     * Time: O(1)
     * Space: O(1)
     */
    class MyCircularQueue2 {
        Node dummyHead;
        Node dummyTail;
        int capacity;
        int size;

        public MyCircularQueue2(int k) {
            dummyHead = new Node(0, null, null);
            dummyTail = new Node(0, null, null);
            dummyHead.next = dummyTail;
            dummyTail.prev = dummyHead;
            capacity = k;
            size = 0;
        }

        public boolean enQueue(int value) {
            if (size == capacity) {
                return false;
            }
            dummyTail.prev.next = new Node(value, dummyTail.prev, dummyTail);
            dummyTail.prev = dummyTail.prev.next;
            size++;
            return true;
        }

        public boolean deQueue() {
            if (size == 0) {
                return false;
            }
            dummyHead.next = dummyHead.next.next;
            dummyHead.next.prev = dummyHead;
            size--;
            return true;
        }

        public int Front() {
            if (size == 0) {
                return -1;
            }
            return dummyHead.next.val;
        }

        public int Rear() {
            if (size == 0) {
                return -1;
            }
            return dummyTail.prev.val;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isFull() {
            return size == capacity;
        }
    }

    class Node {
        int val;
        Node prev;
        Node next;

        public Node(int val, Node prev, Node next) {
            this.val = val;
            this.prev = prev;
            this.next = next;
        }
    }


}
