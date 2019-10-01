package Lintcode;

public class _144InterleavingPositiveAndNegativeNumbers {
    /**
     * 1. Two Pointers
     *
     * Usual two pointers solution:
     * put one category of elements on the left, the other category on the right
     * The number of elements in each category doesn't matter
     *
     * Here,
     * We want interleave positive and negative,
     * the number of pos and negs decides which category goes first
     *
     * Template:
     *
     * while (scan pointers are in the bound) {
     *     // find first position whose element is supposed to be category 1, but not.
     *     while (scan pointer 1 in the bound && curr element is legal) {
     *         precede scan pointer 1;
     *     }
     *
     *     // find first position whose element is supposed to be category 2, but not.
     *     while (scan pointer 2 in the bound && curr element is legal) {
     *         precede scan pointer 2;
     *     }
     *
     *     if (two scan pointers out of bound) {
     *         break;
     *     }
     *
     *     swap pointer 1's element and pointer 2's element;
     * }
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param A
     */
    public void rerange(int[] A) {
        if (A == null) {
            return;
        }

        int len = A.length;
        int countPositive = 0;
        int lastPositiveIndex = 0;

        for (int i = 0; i < len; i++) {
            if (A[i] > 0) {
                countPositive++;

                lastPositiveIndex++;
                swap(A, lastPositiveIndex, i);
            }
        }

        int pos = countPositive > len / 2 ? 0 : 1;
        int neg = countPositive > len / 2 ? 1 : 0;

        while (pos < len && neg < len) {
            while (pos < len && A[pos] > 0) {
                pos += 2;
            }

            while (neg < len && A[neg] < 0) {
                neg += 2;
            }


            if (neg >= len && pos >= len) {
                break;
            }

            swap(A, pos, neg);
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
