import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class _0480SlidingWindowMedian {
    /**
     * 1. Brute Force
     *
     * Copy each window, sort, find median
     *
     * Time: O(n klogk)
     * Space: O(k)
     */

    /**
     * 1. Max Heap + Min Heap
     *
     * Time: O(n k)
     * Space: O(k)
     */
    public double[] medianSlidingWindow(int[] nums, int k) {
        double[] medians = new double[nums.length - k + 1];
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(Collections.reverseOrder());
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();

        for(int i = 0; i < nums.length; i++) {
            if (minHeap.size() <= maxHeap.size()) {
                maxHeap.add(nums[i]);
                minHeap.add(maxHeap.remove());
            } else {
                minHeap.add(nums[i]);
                maxHeap.add(minHeap.remove());
            }


            if (minHeap.size() + maxHeap.size() == k) {
                int start = i - k + 1;
                if(minHeap.size() == maxHeap.size()) {
                    medians[start] = ((long) minHeap.peek() + maxHeap.peek()) / 2.0;
                } else {
                    medians[start] = (double) minHeap.peek();
                }

                if(!minHeap.remove(nums[start])) {
                    maxHeap.remove(nums[start]);
                }
            }
        }
        return medians;
    }

    /**
     * 3. TreeMap -> TreeSet
     *
     * PriorityQueue remove(element) takes linear time
     * TreeMap / TreeSet remove(element) takes logarithmic time
     *
     * TreeMap / TreeSet does not allow duplicates
     *
     * TreeMap: number -> count (complicated)
     * https://leetcode.com/problems/sliding-window-median/discuss/96353/Easy-to-understand-O(nlogk)-Java-solution-using-TreeMap
     *
     * TreeSet: store index, and define custom comparator
     * https://leetcode.com/problems/sliding-window-median/discuss/96346/Java-using-two-Tree-Sets-O(n-logk)
     *
     * Time: O(n logk)
     * Space: O(k)
     */
    public double[] medianSlidingWindow2(int[] nums, int k) {
        Comparator<Integer> comparator = new Comparator<Integer>(){
            public int compare(Integer i1, Integer i2) {
                if (nums[i1] != nums[i2]) {
                    return Integer.compare(nums[i1], nums[i2]);
                } else {
                    return Integer.compare(i1, i2);
                }
            }
        };

        // to reverse ordering: comparator.reversed()
        TreeSet<Integer> smaller = new TreeSet<>(comparator);
        TreeSet<Integer> bigger = new TreeSet<>(comparator);

        double[] medians = new double[nums.length - k + 1];

        for (int i = 0; i < nums.length; i++) {
            if (smaller.size() <= bigger.size()) {
                bigger.add(i);
                smaller.add(bigger.pollFirst());
            } else {
                smaller.add(i);
                bigger.add(smaller.pollLast());
            }

            if (i >= k - 1) {
                int start = i - k + 1;
                if (k % 2 == 0) {
                    medians[start] = ((long) nums[smaller.last()] + nums[bigger.first()]) / 2.0;
                } else {
                    medians[start] = nums[smaller.last()];
                }

                if (!smaller.remove(start)) {
                    bigger.remove(start);
                }
            }
        }

        return medians;
    }
}
