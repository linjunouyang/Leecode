import java.util.HashMap;

/**
 * This problem can't be solved with two pointers.
 *
 * Summary (but I don't think this is 100% correct)
 * https://leetcode.com/problems/subarray-sum-equals-k/discuss/301242/General-summary-of-what-kind-of-problem-can-cannot-solved-by-Two-Pointers
 */
public class _0560SubarraySumEqualsK {
    /**
     * 1.
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(1)
     */
    public int subarraySum(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k)
                    count++;
            }
        }
        return count;
    }

    /**
     * 2. HashMap
     *
     *  the intuition regarding the O(n) solution is we're recording earlier cumulative sums (cumsums)
     *  so that when we encounter the latest cumsum that is k away from an earlier cumsum,
     *  we know to increment count (lines 8-9).
     *
     *  We record a "number of times" for earlier values,
     *  ensuring we capture all extended up-and-down sequences where elements cancel.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     */
    public int subarraySum2(int[] nums, int k) {
        if (nums == null) {
            return 0;
        }
        int count = 0, sum = 0;
        HashMap< Integer, Integer > map = new HashMap < > ();
        map.put(0, 1);
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            if (map.containsKey(sum - k))
                count += map.get(sum - k);
            map.put(sum, map.getOrDefault(sum, 0) + 1);
        }
        return count;
    }

}
