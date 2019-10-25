import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * ArrayList:
 *
 * get, set
 * throws IndexOutOfBoundsException - if the index is out of range (index < 0 || index >= size())
 *
 * Array, Array List and Map similarities:
 * key -> value
 * For Array, Array List, key is integer
 *
 */
public class _1086HighFive {
    /**
     * 1. ArrayList of PriorityQueues
     *
     * PriorityQueue
     * toArray() returns Object[]. (Always allocate a new array)
     * toArray(T[] a) returns <T> T[]. T's upper bound is Object.
     * ( If the queue fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the specified array and the size of this queue.)
     *
     * No Object[] -> Integer[]
     * https://stackoverflow.com/questions/1115230/casting-object-array-to-integer-array-error
     *
     * Time complexity: O(n)  (nlog5) -> o(n)
     * Space complexity: O(n)
     *
     * Runtime: 3 ms, faster than 96.68% of Java online submissions for High Five.
     * Memory Usage: 44 MB, less than 100.00% of Java online submissions for High Five.
     *
     * @param items
     * @return
     */
    public int[][] highFive(int[][] items) {
        List<PriorityQueue<Integer>> heaps = new ArrayList<>();

        for (int[] score : items) {
            PriorityQueue<Integer> heap;

            if (heaps.size() < score[0]) {
                heap = new PriorityQueue<>();
                heaps.add(score[0] - 1, heap);
            } else {
                heap = heaps.get(score[0] - 1);
            }

            if (heap.size() < 5) {
                heap.add(score[1]);
            } else if (score[1] > heap.peek()) {
                heap.poll();
                heap.add(score[1]);
            }
        }

        int[][] averages = new int[heaps.size()][2];

        for (int i = 0; i < heaps.size(); i++) {
            Object[] scores =  heaps.get(i).toArray();
            int average = 0;
            for (int j = 0; j < scores.length; j++) {
                average += (Integer) scores[j];
            }
            average /= 5;
            averages[i][0] = i + 1;
            averages[i][1] = average;
        }

        return averages;
    }
}
