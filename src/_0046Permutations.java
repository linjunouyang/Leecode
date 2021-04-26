import java.util.ArrayList;
import java.util.List;

/**
 *
 * --
 * 8.29 First Pass
 */
public class _0046Permutations {
    /**
     * 1. DFS, backtrack
     *
     * Check visited elements with a boolean array O(1)
     *
     * Time complexity: O(n * n!)
     * n: the length of a permutation
     * n!: the number of permutations
     *
     * Space complexity: O(n)
     */
    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();

        if (nums == null || nums.length == 0) {
            return permutations;
        }

        boolean[] isVisited = new boolean[nums.length];
        ArrayList<Integer> permutation = new ArrayList<>();

        backtrack(nums, permutation, isVisited, permutations);

        return permutations;
    }

    // based on permutation, find all possible permutations and add them to the list
    private void backtrack(int[] nums, List<Integer> permutation, boolean[] isVisited, List<List<Integer>> permutations) {
        if (permutation.size() == nums.length) {
            permutations.add(new ArrayList<>(permutation));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (isVisited[i]) {
                continue;
            }

            isVisited[i] = true;
            permutation.add(nums[i]);

            backtrack(nums, permutation, isVisited, permutations);

            permutation.remove(permutation.size() - 1);
            isVisited[i] = false;
        }
    }
}

