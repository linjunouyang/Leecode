import java.util.*;

/**
 * PriorityQueue<>(initialCapacity, Collections.reverseOrder());
 *
 * Object[]: priorityQueue.toArray()
 * for (Object distance: distanceArr) {  // If declare Integer -> Object cannot be converted to Integer
 *     ....(Integer)distance // correct way to convert
 * }
 *
 */

class SortByDistance implements Comparator<int[]> {
    public int compare(int[] a, int[] b) {
        return a[0] * a[0] + a[1] * a[1] - b[0] * b[0] - b[1] * b[1];
    }
}

public class _0973KClosestPointsToOrigin {
    /**
     * 1. Sort
     *
     * Have to know all of the points previously, and it is unable to deal with real-time(online) case, it is an off-line solution.
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(lgn)
     *
     * Arrays.sort():
     * 1) For primitive types: (e.g. Array.sort(int[])
     * Main: DualPivotQuickSort, fewer data movements, not stable,
     * Time complexity: O(nlogn)
     * Space complexity: in-place, O(logn) for call stack
     *
     * @param points
     * @param K
     * @return
     */
    public int[][] kClosest0(int[][] points, int K) {
        // Arrays.sort(points, (p1, p2) -> p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1]);
        Arrays.sort(points, new SortByDistance());
        return Arrays.copyOfRange(points, 0, K);
    }
    /**
     * 2. Max-heap
     *
     * Time complexity: nlogK
     * Space complexity: O(K)
     *
     * @param points
     * @param K
     * @return
     */
    public int[][] kClosest1(int[][] points, int K) {
        PriorityQueue<int[]> maxHeap = new PriorityQueue<>(
                (p1, p2) -> p2[0] * p2[0] + p2[1] * p2[1] - p1[0] * p1[0] - p1[1] * p1[1]
        );

        for (int[] p : points) {
            maxHeap.offer(p);
            if (maxHeap.size() > K) {
                maxHeap.poll();
            }
        }

        int[][] res = new int[K][2];
        // Notice we're using --K, so the termination condition is K > 0;
        while (K > 0) {
            res[--K] = maxHeap.poll();
        }
        return res;
    }

    /**
     * 3. Quick Select (off-line, not stable)
     *
     * each iteration, we choose a pivot and then find the position p the pivot should be.
     * Then we compare p with the K,
     * 1) p < K: elements on the left of the pivot are all smaller but it is not adequate,
     * we have to do the same thing on right side, and vice versa.
     * 2) p = K, we've found the K-th position.
     * Therefore, we just return the first K elements, since they are not greater than the pivot.
     *
     * https://leetcode.com/problems/k-closest-points-to-origin/discuss/220235/Java-Three-solutions-to-this-classical-K-th-problem.
     * [to-do] commment: two pivot, 3 parts
     *
     * Time complexity:
     * average O(n)
     * worst case: O(n ^ 2)
     *
     * Space complexity:
     * O(1)
     */
    public int[][] kClosest2(int[][] points, int K) {
        int left = 0;
        int right = points.length - 1;

        while (left < right) {
            int pivotIdx = partition(points, left, right);
            if (pivotIdx == K - 1) {
                break;
            } else if (pivotIdx > K - 1) {
                right = pivotIdx - 1;
            } else {
                left = pivotIdx + 1;
            }
        }

        int[][] res = new int[K][2];
        for (int i = 0; i < K; i++) {
            res[i] = points[i];
        }
        return res;
    }

    // lomuto scheme
    private int partition(int[][] points, int left, int right) {
        // Random version
//        int pivot = left + (int)(Math.random() * (right - left + 1));
//        swap(points, pivot, right);

        int smallerEnd = left; // not 0
        for (int i = left; i < right; i++) {
            if (closer(points[i], points[right])) {
                swap(points, i, smallerEnd);
                smallerEnd++;
            }
        }
        swap(points, right, smallerEnd);

        return smallerEnd;
    }

    private int partition2(int[][] points, int left, int right) {
        int pivot = left;
        while (left <= right) {
            while (left <= right && closer(points[left], points[pivot])) {
                left++;
            }

            while (left <= right && closer(points[pivot], points[right])) {
                right--;
            }

            if (left <= right) {
                swap(points, left, right);
            }
        }

        // left <= right guarantees 1 not bigger element + ALL bigger element
        // and right current points to the [1 not bigger element
        swap(points, pivot, right); // don't forget about this
        return right;
    }

    private void swap(int[][] points, int i, int j) {
        int[] temp = points[i];
        points[i] = points[j];
        points[j] = temp;
    }

    private boolean closer(int[] p1, int[] p2) {
        return p1[0] * p1[0] + p1[1] * p1[1] <= p2[0] * p2[0] + p2[1] * p2[1];
    }

    /**
     *
     * Review - QuickSort O(nlgn), QuickSelect O(n):
     * Quicksort considers two sides of the pivot: n + 2 *(n/2) + 4*(n/4) ... = nlogn
     * Quickselect considers only one side: n + n/2 + n/4 + ... = n
     *
     * Worst case for quickselect (already sorted)
     * n - 1 + ... + 1 = O(n^2)
     *
     *
     * Different than common swap operations:
     * Since pivot=A[l], it is like that dig a hole at l(save value in pivot) ,
     * then put the smaller one(A[r]) into this hole.
     * Now there will be a hole at position r, then put larger one(A[l]) in it.
     * Repeat the process, and in the end, put the '''pivot''' at last hole. It is exact same as swap.
     *
     *
     * @param A
     * @param l
     * @param r
     * @return
     */
    private int helper(int[][] A, int l, int r) {
        int[] pivot = A[l];

        while (l < r) {
            while (l < r && compare(A[r], pivot) >= 0) {
                r --;
            }
            A[l] = A[r];
            while (l < r && compare(A[l], pivot) <= 0) {
                l++;
            }
            A[r] = A[l];
        }
        A[l] = pivot;
        return l;
    }

    private int compare(int[] p1, int[] p2) {
        return p1[0] * p1[0] + p1[1] * p1[1] - p2[0] * p2[0] - p2[1] * p2[1];
    }
}
