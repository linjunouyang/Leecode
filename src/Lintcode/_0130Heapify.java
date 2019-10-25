package Lintcode;

public class _0130Heapify {
    /**
     * 1. iterate from bottom to top, shift nodes down
     *
     * Time complexty: O(n)
     * https://www.geeksforgeeks.org/time-complexity-of-building-a-heap/
     *
     * Space complexity: O(1)
     *
     * @param A
     * @param k
     */
    private void siftdown(int[] A, int k) {
        while (k * 2 + 1 < A.length) {
            int smallest = k;

            if (A[k * 2 + 1] < A[smallest]) {
                smallest = k * 2 + 1;
            }

            if (k * 2 + 2 < A.length && A[k * 2 + 2] < A[smallest]) {
                smallest = k * 2 + 2;
            }

            if (smallest == k) {
                break;
            }

            int temp = A[smallest];
            A[smallest] = A[k];
            A[k] = temp;

            k = smallest;
        }
    }

    public void heapify(int[] A) {
        for (int i = A.length/ 2 - 1; i >= 0; i--) {
            siftdown(A, i);
        } // for
    }


    /**
     * Time complexity: O(nlgn)
     * Space complexity: O(1)
     *
     * @param A
     * @param k
     */
    private void siftup(int[] A, int k) {
        while (k != 0) {
            int parent = k / 2 - 1;

            if (A[k] > A[parent]) {
                break;
            }

            int temp = A[k];
            A[k] = A[parent];
            A[parent] = temp;

            k = parent;
        }
    }

    public void heapify2(int[] A) {
        for (int i = 0; i < A.length; i++) {
            siftup(A, i);
        }
    }
}
