import java.util.Arrays;

public class _0945MinimumIncrementToMakeArrayUnique {
    /**
     * 1. Sorting, Greedy
     *
     * https://leetcode.com/problems/minimum-increment-to-make-array-unique/discuss/199952/Java-Greedy-algorithm-and-concise-code-with-detailed-explanation
     *
     * Let say A is the original array, B is the target array. Since the order does not matter, we sort them in an ascending order.
     * From the greedy perspective, intuitively, we find that B[0] = A[0].
     * In order to do least increaments, we will do increament on A[1] to create B[1]. So, how many increaments should we do to satisfy the constraints?
     * B[1] >= B[0] + 1 // make B unique
     * B[1] >= A[1] // we can only do increament or not
     * so B[1] = max(A[1], B[0] + 1)
     * we let this keep going, we assume I[i] is the increments we need from A[i] to B[i], we can conclude:
     *
     * B[i] = max(A[i ], B[i - 1] + 1)
     * I[i] = B[i] - A[i]
     * Since B[i] is derived from B[i - 1], we do not have to store the B before B[i - 1]. Hence, we use a variable pre replace B[i - 1] and update it in every iteration and calculate the I[i] immediately.
     *
     * In conclusion, it is an O(nlogn) running time and O(1) extra space solution. Since the range the number is not very large, we can use the bucket sort to reduce the time complexity to O(m).
     *
     * Time: O(nlogn)
     * Space: O(1)
     */
    public int minIncrementForUnique(int[] A) {
        if (A.length == 0) return 0;
        Arrays.sort(A);
        int pre = A[0], res = 0;
        for (int i = 1; i < A.length; i++) {
            pre = Math.max(pre + 1, A[i]);
            res += pre - A[i];
        }
        return res;
    }

    /**
     * 2. Counting sort
     *
     * Time: O(max num), Space: O(max num)
     */
    public int minIncrementForUnique2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }

        int moves = 0;
        int[] freq = new int[80000]; //given the max num = 40000 and array length = 39999, the worst case will fit in 80000
        for (int num : arr) {
            freq[num]++;
        }

        for (int i = 0; i < freq.length - 1; i++) {
            if (freq[i] <= 1) {
                //no need to move anything!
                continue;
            }

            //consider an example like where frequency of number 3 is 4
            //remaining that needs to be "reevaluated" is 3 (since one 3 is valid in this slot)
            //if we were to add 1 to value 3, it is 4
            //since we have frequency of 3, its like now 4 has 3 frequency
            //we repeat this process
            int remain = freq[i] - 1;
            moves += remain;
            freq[i + 1] += remain;
        }

        return moves;
    }

}
