import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _0658FindKClosestElements {
    /**
     * 1. Two Pointers
     *
     * two pointers representing the boundary
     *
     * Time: O(n)
     * Space: O(1)
     */
    public List<Integer> findClosestElements0(int[] arr, int k, int x) {
        int lo = 0;
        int hi = arr.length - 1;
        while (hi - lo >= k) {
            if (Math.abs(arr[lo] - x) > Math.abs(arr[hi] - x)) {
                lo++;
            } else {
                hi--;
            }
        }
        List<Integer> result = new ArrayList<>(k);
        for (int i = lo; i <= hi; i++) {
            result.add(arr[i]);
        }
        return result;
    }

    /**
     * 2. Binary Search
     *
     * Time: O(logn + k)
     * Space: O(1)
     */
    public List<Integer> findClosestElements1(int[] arr, int k, int x) {
        List<Integer> res = new ArrayList<>();
        if (arr == null) {
            return res;
        }

        int left = 0;
        int right = arr.length - 1;

        while (left < right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] < x) {
                left = mid + 1;
            } else if (arr[mid] > x) {
                right = mid;
            } else {
                left = mid;
                right = mid;
            }
        }

        // [0, *3*], i = 0, j = 1
        // [*0*, 3], i = -1, j = 0

        // (i, j) or [i + 1, j - 1] is the res range
        int i = left - 1; // -1
        int j = left; // 0
        while (j - i - 1 < k) {
            int diff1 = i >= 0 ? Math.abs(arr[i] - x) : Integer.MAX_VALUE;
            int diff2 = j < arr.length ? Math.abs(arr[j] - x) : Integer.MAX_VALUE;
            if (diff1 <= diff2) {
                i--;
            } else {
                j++;
            }
        }
        for (int p = i + 1; p < j;  p++) {
            res.add(arr[p]);
        }

        return res;
    }

    /**
     * 3. Binary Search + Sliding window (fixed size)
     *
     * assume A[mid] ~ A[mid + k] is sliding window
     *
     * case 1: x - A[mid] <= A[mid + k] - x, need to move window go left
     * -------x----A[mid]-----------------A[mid + k]----------
     *
     * case 2: x - A[mid] <= A[mid + k] - x, need to move window go left again
     * -------A[mid]----x-----------------A[mid + k]----------
     *
     * case 3: x - A[mid] > A[mid + k] - x, need to move window go right
     * -------A[mid]------------------x---A[mid + k]----------
     *
     * case 4: x - A[mid] > A[mid + k] - x, need to move window go right
     * -------A[mid]---------------------A[mid + k]----x------
     *
     * Arrays.stream(int[] array, int startInclusive, int endExclusive)
     * Returns a sequential IntStream with the specified range of the specified array as its source.
     *
     * boxed() from Stream<Integer>
     * Returns a Stream consisting of the elements of this stream, each boxed to an Integer
     *
     * collect(Collector<? super T,A,R> collector) from Stream<T>
     * Performs a mutable reduction operation on the elements of this stream using a Collector.
     *
     */
    public List<Integer> findClosestElements3(int[] arr, int k, int x) {
        int lo = 0;
        int hi = arr.length - k;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (arr[mid] + arr[mid + k] - 2 * x >= 0) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }
        return Arrays.stream(arr, lo, lo + k).boxed().collect(Collectors.toList());
    }

}
