import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.Random;

public class _215KthLargestElementInAnArray {
    /**
     * 1. Sort and find
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(1)
     *
     * 1 <= k <= arrays.length, k is always valid, so array at least has 1 element;
     *
     * Extra work we did: we find positions for all elements.
     *
     * Runtime: 3 ms, faster than 76.61% of Java online submissions for Kth Largest Element in an Array.
     * Memory Usage: 37.2 MB, less than 90.16% of Java online submissions for Kth Largest Element in an Array.
     *
     * ? Arrays.sort(null, 0?）
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    /**
     * 2. Min oriented priority queue (store the kth largest values)
     *
     * Extra work we did: found positions for k largest numbers
     *
     * Time complexity: O(N lg k)
     *
     * Other opinion:
     * O(k + (n-k)logk)
     * O(k) for adding k initial elements. Doing at most n-k removals which take O(logk)
     *
     * Space complexity: O(k)
     *
     * @param nums
     * @param k
     * @return
     */
    public int findKthLargest2(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int num : nums) {
            pq.offer(num);

            if (pq.size() > k) {
                pq.poll();
            }
        }

        return pq.peek();
    }


    /**
     * 3. Quick select
     *
     * https://en.wikipedia.org/wiki/Quickselect
     * https://leetcode.com/problems/kth-largest-element-in-an-array/discuss/60294/Solution-explained
     *
     * Relationship between n and k:
     * the kth largest number will be at index n - k
     *
     * Put pivot at the end to make sure it doesn't stand in the way.
     * Partition the space around the pivot.
     * Finally, pivot is at its final position of sorted order.
     * Compare (n-k) and pivot final position, decide to go left or go right (like Binary Search)
     *
     * If we find a good pivot, we chop the space in half each time
     *
     * ---------------------
     * Time complexity:
     * T(n) = T(n/2) + (n-1)
     *
     * num comparisons
     * n   (n-1)
     * n/2 (n/2 - 1)
     * n/4 (n/4 - 1)
     * 1   0
     *
     * sum(n / (2^i) - 1) (for i = 0 to lg(n) - 1)
     * n * sum(1 / (2^i)) - sum(1) (for i = 0 to lg(n) - 1)
     * n * sum(1 / (2^i)) - lg(n) (for i = 0 to lg(n) - 1)
     * n * sum( (1 - 0.5^lgn) / 0.5) - lg(n) (for i = 0 to lg(n) - 1)
     * n * sum( (1 - 1/n) / 0.5) - lgn (for i = 0 to lg(n) - 1)
     * 2 (n - 1) - lgn (for i = 0 to lg(n) - 1)
     * O(n)
     *
     * formula: sum(x^k) (for k = 0 ... n-1) = (1 - x^n) / (1 - x)
     *
     * Best case: O(n)
     * Average case: O(n)
     * Worst case: O(n^2)
     *
     * Space complexity: O(1)
     *
     * Runtime: 1 ms, faster than 99.46% of Java online submissions for Kth Largest Element in an Array.
     * Memory Usage: 38.6 MB, less than 36.27% of Java online submissions for Kth Largest Element in an Array.
     *
     * ---------------------
     * Quickselect is still worst-case O(n^2), even with shuffling.
     *
     * @param nums
     * @param k
     * @return
     */

    public int findKthLargest3(int[] nums, int k) {
        // keep track of the left and right where target can be
        // use the bound to partition around a chosen pivot
        int n = nums.length;
        int left = 0;
        int right = n - 1;

        // to repeatedly choose random pivots later
        Random rand = new Random(0);

        while (left <= right) {
            int pivotIndex = rand.nextInt(right - left + 1) + left;

            int finalPivotIndex = partition(nums, left, right, pivotIndex);

            if (finalPivotIndex < n - k) {
                // k'th largest must be in the right partition. We "undershot" and need to go right (and we do this by narrowing the left bound)
                left = finalPivotIndex + 1;
            } else if (finalPivotIndex > n - k){
                // k'th largest must be in the left partition. We "overshot" and need to go left ( and we do this by narrowing the right bound)
                right = finalPivotIndex - 1;
            } else {
                return nums[finalPivotIndex];
            }
        }

        // unreachable, necessary to compile
        return -1;
    }

    /**
     *  So this subroutine is exactly what we do in QuickSort...partition around the value
     *   that the 'pivotIndex' holds
     *   The result is a "sandwich" at the end.
     *   [ items < than the pivot ... pivotItem ... items > than the pivot]
     *
     * @param arr
     * @param left
     * @param right
     * @param pivotIndex
     * @return
     */
    private int partition(int[] arr, int left, int right, int pivotIndex) {
        int pivotValue = arr[pivotIndex];

        // keep track of the tail of section where numbers less than pivot
        int smallerNumsTailIndex = left;

        // move the pivot out of the way. we're about to bulldoze through
        // the partitioning space and we don't want it in the way
        swap(arr, pivotIndex, right);

        // iterate from the left bound to 1 index right before the right bound (where pivot is now)
        for (int i = left; i < right; i++) {
            if (arr[i] < pivotValue) {
                // move the num to the section of nums less than the pivot
                swap(arr, i, smallerNumsTailIndex);
                smallerNumsTailIndex++;
            }
        }

        // swap the pivot back to its final place
        swap(arr, right, smallerNumsTailIndex);

        return smallerNumsTailIndex;
    }

    private void swap(int[] nums, int i, int j) {
        int tmp = nums[i];
        nums[i] = nums[j];
        nums[j] = tmp;
    }
}
