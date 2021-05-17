import java.util.HashMap;

public class _0996NumberOfSquarefulArrays {
    /**
     * 1. Backtracking
     *
     * Time: O(n * n!)
     * Ignore Math.sqrt
     *
     * Space: O(n)
     * HashMap: O(n)
     * Recursion call stack: O(n)
     *
     * Similar idea:
     * https://leetcode.com/problems/number-of-squareful-arrays/discuss/238593/Java-DFS-%2B-Unique-Perms
     * Arrays.sort, prev, curIdx, used
     * Avoid duplicate permutations caused by duplicate numbers
     * if (used[i] || (i > 0 && A[i] == A[i-1] && !used[i-1])) continue;
     */
    public int numSquarefulPerms(int[] nums) {
        HashMap<Integer, Integer> numToCount = new HashMap<>();
        for (int num : nums) {
            int oldCount = numToCount.getOrDefault(num, 0);
            numToCount.put(num, oldCount + 1);
        }

        return backtrackPermute(numToCount, 0, nums.length, 0);
    }

    private int backtrackPermute(HashMap<Integer, Integer> numToCount,
                                 int idx, int end, int prev) {
        if (idx == end) {
            return 1;
        }

        int numSquareArrs = 0;
        for (int num : numToCount.keySet()) {
            int count = numToCount.get(num);
            if (count == 0) {
                continue;
            }

            if (idx == 0) {
                numToCount.put(num, count - 1);
                numSquareArrs += backtrackPermute(numToCount, idx + 1, end, num);
                numToCount.put(num, count);
            } else if (isSquare(prev + num)) {
                numToCount.put(num, count - 1);
                numSquareArrs += backtrackPermute(numToCount, idx + 1, end, num);
                numToCount.put(num, count);
            }
        }

        return numSquareArrs;
    }

    private boolean isSquare(int num) {
        double sqrt = Math.sqrt(num);
        int closest = (int) Math.round(sqrt);
        return sqrt == closest;
    }

    /**
     * 2. TODO: DP
     * https://leetcode.com/problems/number-of-squareful-arrays/discuss/238871/Java-DP-7ms
     */
}
