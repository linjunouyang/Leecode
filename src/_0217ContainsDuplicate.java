import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class _0217ContainsDuplicate {

    /**
     * 1. Sort and scan
     *
     * Note:
     * The implementation here modifies the original array by sorting it.
     * In general, it is not a good practice to modify the input unless it is clear to the caller that the input will be modified.
     * One may make a copy of nums and operate on the copy instead.
     *
     * Arrays.sort(null) -> java.lang.UnsupportedOperation: Not an array: null
     * Arrays.sort([]) -> ok
     *
     *  Arrays.sort():
     *  1) For primitive types: (e.g. Array.sort(int[])
     *  DualPivotQuickSort (combination of insertion sort and quick sort), fewer data movements, not stable,
     *  Time complexity: O(nlogn)
     *  Space complexity: in-place, O(logn) for call stack
     *
     *  2) For reference types: (e.g. Arrays.sort(T[])
     *  TimSort, (a variation of MergeSort), more data movements, less comparison (save time)
     *  Time complexity: O(nlogn)
     *  Space complexity: for stability, O(n) (Merge Sort) -> O(k) (Tim Sort for nearly sorted)
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(logn)
     * ? O(1). Space depends on the sorting implementation which, usually, costs O(1) auxiliary space if heapsort is used.
     * ? O(1) not counting space used by sort
     *
     * Runtime: 5 ms, faster than 96.67% of Java online submissions for Contains Duplicate.
     * Memory Usage: 41.2 MB, less than 99.14% of Java online submissions for Contains Duplicate.
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate(int[] nums) {
        if (nums == null) {
            return false;
        }

        Arrays.sort(nums);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }

        return false;

    }

    /**
     * 2. Set
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 9 ms, faster than 57.40% of Java online submissions for Contains Duplicate.
     * Memory Usage: 43.8 MB, less than 64.65% of Java online submissions for Contains Duplicate.
     *
     * For certain test cases with not very large nn, the runtime of this method can be slower than Approach #2.
     * The reason is hash table has some overhead in maintaining its property.
     * keep in mind that real world performance can be different from what the Big-O notation says.
     * The Big-O notation only tells us that for sufficiently large input, one will be faster than the other.
     * Therefore, when n is not sufficiently large, an O(n) algorithm can be slower than an O(nlogn) algorithm.
     *
     * @param nums
     * @return
     */
    public boolean containsDuplicate2(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num)) {
                return true;
            }
            set.add(num);
        }

        return false;
    }
}
