import java.util.HashMap;
import java.util.Map;

/**
 * Notice
 * 1. sum % k == 0 (k might be zero)
 * 2. negative k makes sense
 */
public class _0523ContinuousSubarraySum {
    /**
     * 1. Prefix Sums
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n)
     */
    public boolean checkSubarraySum(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int n = nums.length;
        int[] prefixSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            prefixSum[i] = prefixSum[i - 1] + nums[i - 1];
        }

        for (int i = 0; i <= n - 2; i++) {
            for (int j = i + 2; j <= n; j++) {
                int sum = prefixSum[j] - prefixSum[i];
                if (sum == k || (k != 0 && sum % k == 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 2. Remainder Property: (a + (n * x)) % x = a % x
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     */
    public boolean checkSubarraySum2(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        map.put(0, -1); // for cases like ([0, 0] 0) ([1, 1] 2) ([any, any] -1)
        int runningSum = 0;
        for (int i=0;i<nums.length;i++) {
            runningSum += nums[i];
            if (k != 0) runningSum %= k;
            Integer prev = map.get(runningSum);
            if (prev != null) {
                if (i - prev > 1) {
                    return true;
                }
                // if prev != null, but distance <= 1, we don't update the entry
            } else {
                map.put(runningSum, i);
            }
        }
        return false;
    }

}
