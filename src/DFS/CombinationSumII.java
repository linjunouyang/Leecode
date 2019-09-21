package DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CombinationSumII {

    /**
     * DFS
     *
     * Time complexity: O(the number of combinations * candidates.length)
     * A more loose bound: O(2^n) for each num you have two choices, pick it or not
     *
     * Space complexity: O(candidates.length)
     *
     * Runtime: 2 ms, faster than 99.95% of Java online submissions for Combination Sum II.
     * Memory Usage: 36.6 MB, less than 100.00% of Java online submissions for Combination Sum II.
     *
     * @param candidates
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        List<List<Integer>> results = new ArrayList<>();

        if (candidates == null || candidates.length == 0) {
            return results;
        }

        Arrays.sort(candidates);

        dfs(candidates, 0, target, new ArrayList<>(), results);

        return results;
    }

    private void dfs(int[] candidates, int startIndex, int target, List<Integer> combination, List<List<Integer>> results) {
        if (target == 0) {
            results.add(new ArrayList<>(combination));
            return;
        }

        for (int i = startIndex; i < candidates.length; i++) {
            // for duplicate elements, we choose the ones that appear early
            // i != startIndex -> candidates[starIndex] is not chosen, because we remove from last iteration
            // candidates[i] == candidates[i - 1] -> they are duplicate elemtemts
            if (i != startIndex && candidates[i] == candidates[i - 1]) {
                continue;
            }

            if (candidates[i] > target) {
                break;
            }

            combination.add(candidates[i]);

            dfs(candidates, i + 1, target - candidates[i], combination, results);

            combination.remove(combination.size() - 1);
        }
    }

}
