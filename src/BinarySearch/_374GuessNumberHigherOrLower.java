package BinarySearch;

/**
 *
 * >>> is faster than / : mid = (low + high) >>> 1;
 *
 *
 * -----
 * 8.17 Passed
 */
public class _374GuessNumberHigherOrLower {

    private int guess(int num) {
        // return -1 if my number is lower, 1 if my number is higher, otherwise return 0
        return -1;
    }

    /**
     * Brute Force
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param n
     * @return
     */

    public int guessNumber0(int n) {
        for (int i = 1; i < n; i++) {
            if (guess(i) == 0) {
                return i;
            }
        }
        return n;
    }

    /**
     * Intuitive Binary Search
     *
     * Time complexity: O(logn)
     * Space complexity: O(1)
     *
     * @param n
     * @return
     */
    public int guessNumber(int n) {
        int start = 1;
        int end = n;

        while (start <= end) {
            int mid = start + (end - start) / 2;
            int res = guess(mid);

            if (res == 0) {
                return mid;
            } else if (res == 1) {
                start = mid + 1;
            } else {
                end = mid - 1;
            }
        }

        return -1;
    }


    /**
     * Comprehensive Binary Search
     *
     * Time Complexity: O(logn)
     * Space Complexity: O(1)
     *
     * @param n
     * @return
     */
    public int guessNumber2(int n) {
        int start = 1;
        int end = n;

        while (start + 1 < end) {
            int mid = start + (end - start) / 2;
            int res = guess(mid);

            if (res == 0) {
                return mid;
            } else if (res == 1) {
                start = mid;
            } else {
                end = mid;
            }
        }

        if (guess(start) == 0) {
            return start;
        }

        if (guess(end) == 0) {
            return end;
        }

        return 0;
    }

    /**
     * Ternary Search
     *
     * Time complexity: O(log3n)
     * Space complexity: O(1)
     */
    public int guessNumber3(int n) {
        int low = 1;
        int high = n;

        while (low <= high) {
            int mid1 = low + (high - low) / 3;
            int mid2 = high - (high - low) / 3;
            int res1 = guess(mid1);
            int res2 = guess(mid2);

            if (res1 == 0) {
                return mid1;
            }

            if (res2 == 0) {
                return mid2;
            }

            if (res1 < 0) {
                high = mid1 - 1;
            } else if (res2 > 0) {
                low = mid2 + 1;
            } else {
                low = mid1 + 1;
                high = mid2 - 1;
            }
        }

    }


}
