import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _0039CombinationSum {

    /**
     * 1. Backtracking
     *
     * Sorting is helpful for pruning results
     *
     * Time complexity ?
     * - O(答案总数 * 构造每个答案的时间) = O(s * target)
     *
     * - Time complexity is O(N^target) where N is a length of candidates array.
     * Just assuming that each recursive step we go over all existing candidates, so base N.
     * And go as deep as target in our recursive calls (if candidates are close to 1), so power of target.
     *
     * - O(target * 2^n)
     *
     * Space complexity:
     * O(target) (If every number is almost 1, recursion depth ~= target)
     */
    public List<List<Integer>> combinationSum1(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, target, 0);
        return list;
    }

    // 1. 递归的定义
    // 从 nums 的 start 开始挑一些数，放到 combination 中，且他们的和为 remain
    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int remain, int start) {
        // 3. 递归的出口
        if (remain == 0) {
            list.add(new ArrayList<>(tempList));
            return;
        }

        // current tempList as the root
        // 2. 递归的拆解 [1, 2], [1, 3], [1, 4] ... [1, 5]
        for (int i = start; i < nums.length; i++) {
            // each iteration is an exploration of a specific branch
            if (remain < nums[i]) {
                break;
            }

            // [1] -> [1, 2]
            tempList.add(nums[i]);

            // 把所有[1, 2]开头的剩余的和为remain - nums[i]的集合，都找到，放入到results
            backtrack(list, tempList, nums, remain - nums[i], i); // i, not start

            // [1, 2] -> [1]
            tempList.remove(tempList.size() - 1);
        }
    }

    /**
     * Another implementation
     *
     * focus on one index in each function call.
     */
    public List<List<Integer>> combinationSum11(int[] candidates, int target) {
        Arrays.sort(candidates);
        List<List<Integer>> combinations = new ArrayList<>();
        List<Integer> combination = new ArrayList<>();
        explore(candidates, 0, 0, target, combination, combinations);
        return combinations;
    }

    private void explore(int[] candidates, int idx,
                         int sum, int target,
                         List<Integer> combination,
                         List<List<Integer>> combinations) {
        // base cases: idx, sum target
        if (sum > target) {
            return;
        }

        if (idx == candidates.length) {
            return;
        }

        // not choose cur candidate
        explore(candidates, idx + 1, sum, target, combination, combinations);

        // choose
        combination.add(candidates[idx]);
        sum += candidates[idx];
        if (sum == target) {
            combinations.add(new ArrayList<>(combination));
            combination.remove(combination.size() - 1);
            return;
        }
        explore(candidates, idx, sum, target, combination, combinations);
        combination.remove(combination.size() - 1);
    }

}
