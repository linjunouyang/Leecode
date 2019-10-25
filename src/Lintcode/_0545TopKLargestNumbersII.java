package Lintcode;

import java.util.*;

/**
 * A priority queue is unbounded,
 * but has an internal capacity governing the size of an array used to store the elements on the queue.
 * It is always at least as large as the queue size.
 * As elements are added to a priority queue, its capacity grows automatically
 *
 *
 *
 *
 */
public class _0545TopKLargestNumbersII {
    private int maxSize;
    private Queue<Integer> minheap;

    public _0545TopKLargestNumbersII(int k) {
        minheap = new PriorityQueue<>();
        maxSize = k;
    }

    /**
     * Time complexity: O(logk)
     * When the array grows, O(n) for copy process, but such amortized cost is O(1)
     *
     * Space complexity:
     * When the array grows, O(length of array * 2), but such amortized cost is O(1)
     *
     * @param num
     */
    public void add(int num) {
        if (minheap.size() < maxSize) {
            minheap.offer(num);
            return;
        }

        if (num > minheap.peek()) {
            minheap.poll();
            minheap.offer(num);
        }
    }

    /**
     * Time complexity: O(klogk)
     * Space complexity: O(k)
     *
     *
     * Under the covers, Collections.sort works by copying the collection to an array, sorting the array, then copying the array back to the collection.
     * (Collections.java) Collections.sort(List<T>) -> list.sort(null)
     * (ArrayList.java) sort(Comparator<? super E> c) -> Arrays.sort((E[]) elementData, 0, size, c);
     *
     *
     * ArrayList sort is in-place
     *
     * https://blog.csdn.net/linkingfei/article/details/80031034
     * https://blog.csdn.net/timheath/article/details/68930482
     * https://blog.csdn.net/zuochao_2013/article/details/79622369
     * https://stackoverflow.com/questions/52714131/why-is-collections-sort-much-slower-than-arrays-sort
     *
     * Arrays.sort():
     * 1) For primitive types: (e.g. Array.sort(int[])
     * DualPivotQuickSort (combination of insertion sort and quick sort), fewer data movements, not stable,
     * Time complexity: O(nlogn)
     * Space complexity: in-place, O(logn) for call stack
     *
     *
     * 2) For reference types: (e.g. Arrays.sort(T[])
     * TimSort, (a variation of MergeSort), more data movements, less comparison (save time)
     * Time complexity: O(nlogn)
     * Space complexity: for stability, O(n) (Merge Sort) -> O(k) (Tim Sort for nearly sorted)
     *
     *
     * ---------
     * About Collections.sort
     *
     * Implementation note:
     * This implementation is a stable, adaptive, iterative mergesort
     * that requires far fewer than nlg(n) comparisons when the input array is partially sorted,
     * while offering the performance of a traditional mergesort when the input array is randomly ordered.
     * If the input array is nearly sorted, the implementation requires approximately n comparisons.
     *
     * Temporary storage requirements vary from
     * a small constant for nearly sorted input arrays to n/2 object references for randomly ordered input arrays.
     *
     * The implementation takes equal advantage of ascending and descending order in its input array,
     * and can take advantage of ascending and descending order in different parts of the same input array.
     * It is well-suited to merging two or more sorted arrays: simply concatenate the arrays and sort the resulting array.
     *
     * The implementation was adapted from Tim Peters's list sort for Python ( TimSort).
     * It uses techiques from Peter McIlroy's "Optimistic Sorting and Information Theoretic Complexity",
     * in Proceedings of the Fourth Annual ACM-SIAM Symposium on Discrete Algorithms, pp 467-474, January 1993.
     *
     * This implementation dumps the specified list into an array,
     * sorts the array, and iterates over the list resetting each element from the corresponding position in the array.
     * This avoids the n^2log(n) performance that would result from attempting to sort a linked list in place.
     *
     * @return
     */
    public List<Integer> topk() {
        Iterator it = minheap.iterator();
        List<Integer> result = new ArrayList<Integer>();

        while (it.hasNext()) {
            result.add((Integer) it.next());
        }

        Collections.sort(result, Collections.reverseOrder());
        result.sort(Collections.reverseOrder());
        return result;
    }
}
