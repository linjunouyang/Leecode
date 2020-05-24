package Tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _0040CombinationSumII {
    /**
     * 1. Backtracking
     *
     * Time complexity: O(target * 2^n)
     *
     * Suppose we have n candidates, for each candidate, we have two choices: choose or not.
     * Think of it as a tree. The leaf nodes are all combinations (2^n)
     * Traversing the tree (deciding including the element or not) is O(2 ^ n)
     * + copy each list O(n * 2^n)
     *
     * Space complexity: O(target)
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(candidates);
        backtrack(candidates, target, res, new ArrayList<>(), 0);
        return res;
    }

    private void backtrack(int[] candidates, int target, List<List<Integer>> res, List<Integer> temp, int start) {
        if (target == 0) {
            res.add(new ArrayList<>(temp));
        }

        for (int i = start; i < candidates.length; i++) {
            if (candidates[i] > target) {
                return;
            }

            if (i > start && candidates[i] == candidates[i - 1]) {
                // i > start: now we don't consider the previous same element
                continue;
            }
            temp.add(candidates[i]);
            backtrack(candidates, target - candidates[i], res, temp, i + 1);
            temp.remove(temp.size() - 1);
        }
    }
}
