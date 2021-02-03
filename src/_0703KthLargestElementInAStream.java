import java.util.PriorityQueue;

public class _0703KthLargestElementInAStream {
    class KthLargest {
        PriorityQueue<Integer> minHeap;
        int size;

        /**
         * Time: O(nlogk)
         * Space: O(k)
         */
        public KthLargest(int k, int[] nums) {
            size = k;
            minHeap = new PriorityQueue<>(); // initialize with k;
            for (int num : nums) {
                add(num);
            }
        }

        /**
         * Time: O(logk)
         * Space: O(1)
         */
        public int add(int val) {
            // or pq.offer(val)


            if (minHeap.size() < size) {
                minHeap.offer(val);
            } else if (val > minHeap.peek()) {
                minHeap.poll();
                minHeap.offer(val);
            }
            return minHeap.peek();
        }
    }

    /**
     * 2. BST
     *
     * https://leetcode.com/problems/kth-largest-element-in-a-stream/discuss/147729/O(h)-Java-Solution-Using-BST
     */
}
