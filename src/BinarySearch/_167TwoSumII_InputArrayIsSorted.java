package BinarySearch;

public class _167TwoSumII_InputArrayIsSorted {

    /**
     * 1. Two pointers
     *
     * Each sum is characterized by two indices (i, j), where 0 <= i < j < n with n the length of the input array.
     * If we were to compute them explicitly, we end up with an n-by-n matrix.
     *
     * If the array is not sorted, you must compare it with elements from the above matrix one by one.
     * This is the naive O(n^2) solution.
     * you can use a HashMap to memorize visited elements and cut down the time to O(n) so we have the classic space-time tradeoff.
     *
     * Now if the input array is sorted, the n-by-n summation matrix will have the following properties:
     *
     * Integers in each row are sorted in ascending order from left to right.
     * Integers in each column are sorted in ascending order from top to bottom.
     *
     * To find the target, we do not have to scan the whole matrix now since it exhibits some partial order.
     * We may start from the top-right (or bottom-left) corner, then proceed to the next row or previous column
     * depending on the relationship between the matrix element and the target until either it is found or all rows and columns are exhausted.
     * The key here is that we can get rid of a whole row or column due to the two properties of the matrix specified above.
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param numbers
     * @param target
     * @return
     */
    public int[] twoSum1(int[] numbers, int target) {
        int[] res = new int[2];
        int start = 0;
        int end = numbers.length - 1;

        while (start <= end) {
            // in case of the integer overflow
            long sum = numbers[start] + numbers[end];
            if (sum == target) {
                res[0] = start + 1;
                res[1] = end + 1;
                return res;
            } else if (sum < target) {
                start = start + 1;
            } else {
                end = end - 1;
            }
        }

        return res;
    }


}
