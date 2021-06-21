import java.util.Arrays;

/**
 * Wiki:
 * Backtracking is a general algorithm for finding all (or some) solutions to some computational problems, notably constraint satisfaction problems,
 * that incrementally builds candidates to the solutions, and abandons a candidate ("backtracks") as soon as it determines that the candidate cannot possibly be completed to a valid solution."
 *
 * it is a NPC problem,which means :
 * 1. for a given specific answer, we can prove that where it can part into K subsets in polynomial equation,such as n+n^2+n^3+..+n^i(i is constant)
 * 2. but to prove whether it exists one answer to part into K subsets, it just can search brutely.
 * we can not make it in polynomial equation, such as 2^n,n!(scale will be larger while n is larger)
 *
 * Specialization:
 * Q416
 * Q473
 */
public class _0698PartitionToKEqualSumSubsets {
    /**
     * 0. TLE Backtracking
     *
     * For each number, there could be k choices -> k^n
     * There are duplicate partitions because of subset order involved
     */
    public boolean canPartitionKSubsets0(int[] nums, int k) {
        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        sum /= k;

        int[] subsets = new int[k];

        if (nums.length < k) {
            return false;
        }

        return partition(nums, 0, subsets, sum);
    }


    private boolean partition(int[] nums, int i,
                              int[] subsets, int target) {

        if (i == nums.length) {
            for (int subset : subsets) {
                if (subset != target) {
                    return false;
                }
            }
            return true;
        }

        for (int idx = 0; idx < subsets.length; idx++) {
            if (target - subsets[idx] < nums[i]) {
                continue;
            }
            subsets[idx] += nums[i];
            if (partition(nums, i + 1, subsets, target)) {
                return true;
            }
            subsets[idx] -= nums[i];
        }

        return false;
    }

    /**
     * 1. Backtracking DFS
     *
     * Time Complexity:
     * we traverse the entire nums array for each subset (once we formed one subset, for the next subset we are starting again with index 0).
     * So for each subset, we are choosing the suitable elements from the nums array
     * (iterate over nums and for each element either use it or drop it, which is roughly O(2^n) operation where n is the size of nums
     * roughly: some numbers have been marked as visited).
     * We are doing the same for each subset. Total subsets are k. So Time Complexity becomes O(k*(2^n)).
     *
     * Space Complexity:
     * Even though we are traversing the entire array for each subset,
     * the height of the recursion tree would still remain O(n)
     * because we won't be calling the recursive function for already visited elements.
     * Also, O(n) for the visited array.
     *
     */
    public boolean canPartitionKSubsets(int[] nums, int k) {
        int sum = 0;
        int maxNum = 0;
        for (int num : nums) {
            sum += num;
            maxNum = Math.max(maxNum, num);
        }
        if (sum % k != 0 || maxNum > sum / k || k <= 0) {
            return false;
        }
        return canPartitionKSubsetsFrom(nums, 0, new boolean[nums.length], 0, k, sum / k);
    }

    /**
     *
     * We use an array visited[] to record which element in nums[] is used.
     * Each time when we get a cur_sum = sum/k, start from nums[0] to look up the elements that aren't used and find another cur_sum = sum/k.
     *
     * comparison between curSubsetSum and targetSubsetSum indicates whether we find a valid subset.
     *
     * Since the order of numbers within a subset doesn't matter,
     * we add nextIndexToCheck for the inner recursion to avoid duplicate calculations.
     */
    private boolean canPartitionKSubsetsFrom(int[] nums, int nextIndexToCheck, boolean[] visited,
                                             int curSubsetSum, int k, int targetSubsetSum) {
        if (k == 0 || k == 1) {
            // We exclude sum % k != 0 before initial call, so when k == 1
            // We've already found k - 1 subsets with target sum.
            return true;
        }

        if (curSubsetSum == targetSubsetSum) {
            // notice we start from 0 again
            return canPartitionKSubsetsFrom(nums, 0, visited, 0, k - 1, targetSubsetSum);
        }

        for (int i = nextIndexToCheck; i < nums.length; i++) {
            if (!visited[i] && curSubsetSum + nums[i] <= targetSubsetSum) {
                visited[i] = true;
                if (canPartitionKSubsetsFrom(nums, i + 1, visited, curSubsetSum + nums[i], k, targetSubsetSum)) {
                    return true;
                }
                // OR combine two cases:
//                if (curSubsetSum + nums[i] < targetSubsetSum) {
//                    if (canPartitionKSubsetsFrom(nums,
//                            k,
//                            visited,
//                            targetSubsetSum,
//                            curSubsetSum + nums[i],
//                            i + 1)) {
//                        return true;
//                    }
//                } else {
//                    if (canPartitionKSubsetsFrom(nums,
//                            k - 1,
//                            visited,
//                            targetSubsetSum,
//                            0,
//                            0)) {
//                        return true;
//                    }
//                }
                visited[i] = false;
            }
        }

        return false;
    }

    /**
     * 2. Dynamic Programming with Bit Masking
     *
     * A {1,2,3,4,5}. We can represent any subset of A using a bitmask of length 5,
     * with an assumption that if ith (0<=i<=4) bit is set (1) then it means ith element is present in subset.
     * So the bitmask 01010 represents the subset {2,4}.
     *
     * A = 54321
     * b = 01010
     * Set the ith bit: b | (1 << i)
     * e.g. i = 0, (1 << i) = 00001, b | (1 << i) = 01011
     * -> {1, 2, 4}
     *
     * Unset the ith bit: b & !(1 << i)
     * e.g. i = 1, (1 << i) = 00010, !(1 << i) = 11101
     * -> 01000 -> {4}
     *
     * Check if ith bit is set: b & (1 << i)
     * let i = 3, (1 << i) = 01000
     * 01010 & 01000 = 01000
     *
     * dp[i]: whether array (represented by binary form of i) can develop into desired answer
     * the last entry (every bit set to 1) shows whether the whole array can be partitioned into k subsets of equal sum.
     *
     * dp[i] represents the validity of the i-th subset: at each step when an element is inserted into this subset, the sum of elements is always <= target.
     * It doesn't mean we can form k partitions with all the elements in the current subset, it just means that we are on a valid path towards the goal of forming k partitions,
     * and only dp[2^n - 1] provides the final answer when all elements are used (because we filtered out sum % k != 0)
     *
     * total[i] stores the sum of subset (represented by binary form of i) with sum less than equal to target sum
     * (total sum/k why? because we need to split array into k subset).
     *
     * e.g
     * nums: 4323521
     * dp:  10000000
     *
     * Further Optimization:
     * Get rid of dp[]
     * initialize total with -1
     * dp[i] = false -> total[i] = -1
     * In the end, should check whether total[(1 << n) - 1] == total sum of nums.
     *
     * Time complexity: O(n * 2 ^ n)
     * Space complexity: O(2 ^ n)
     *
     * https://leetcode.com/problems/partition-to-k-equal-sum-subsets/discuss/335668/DP-with-Bit-Masking-Solution-%3A-Best-for-Interviews
     */
    public boolean canPartitionKSubsets2(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int n = nums.length;
        // result array
        boolean[] dp = new boolean[1 << n];
        int[] total = new int[1 << n];
        dp[0] = true;

        int sum = 0;
        for (int num : nums) {
            sum += num;
        }
        Arrays.sort(nums);
        if (sum % k != 0 || nums[n - 1] > sum) {
            return false;
        }

        sum /= k;

        // Loop over every possible subset
        for (int i = 0; i < (1 << n); i++) {
            if (dp[i]) {
                // explore candidate for next number
                for (int j = 0; j < n; j++) {
                    // set the jth bit
                    int temp = i | (1 << j);
                    if (temp != i) {
                        // if total sum is less than target store in dp and total array
                        if (nums[j] <= (sum - (total[i] % sum))) {
                            // make sure that only j with condition: nums[j] smaller than ( or equal to) sum- total[i] can be selected and then we continue.
                            // When nums[j] equals to sum- total[i], nums[j] is added to total[i], now we have finished finding one of k groups.
                            // and for this new total, total % sum = 0;
                            // Now we move to find next group, so % can make sure we ignore the sum of existed groups whose sum = total / k
                            dp[temp] = true;
                            total[temp] = nums[j] + total[i];
                        } else {
                            // Notice 'break' only terminates current level of loop.
                            // Why break?
                            // because we sorted nums, adding any of the following j will definitely exceed target sum
                            // but it seems like [without Arrays.sort], this break still makes sense?
                            // because we form new subset by (previous subset + one num)
                            // and this new subset has multiple ways for such formation.
                            // but if we don't sort, I think we shouldn't break (hard to explain)
                            break;
                        }
                    }
                }
            }
        }

        return dp[(1<<n) - 1];
    }
}
