package DFS;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * --
 * 8.29 First Pass
 */
public class _46Permutations {
    /**
     * 1. DFS
     *
     * Check visited elements with list.contains() O(n)
     *
     * Time complexity: O(n^2 * n!)
     * n^2: list contains() takes linear time * the length of a permutation
     * n!: the number of permutations
     *
     * https://keaoxu.files.wordpress.com/2018/08/permutation_output_algo_analysis.pdf
     *
     * Space complexity: O(n) for recursive call stack
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();

        if (nums == null) {
            return permutations;
        }

        if (nums.length == 0) {
            permutations.add(new ArrayList<>());
            return permutations;
        }

        ArrayList<Integer> permutation = new ArrayList<>();
        helper(nums, permutation, permutations);

        return permutations;
    }

    // based on permutation, find all possible permutations and add them to the list
    private void helper(int[] nums, List<Integer> permutation, List<List<Integer>> permutations) {
        if (permutation.size() == nums.length) {
            permutations.add(new ArrayList<>(permutation));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (permutation.contains(nums[i])) {
                continue;
            }

            permutation.add(nums[i]);
            helper(nums, permutation, permutations);
            permutation.remove(permutation.size() - 1);
        }
    }

    /**
     * 2. DFS
     *
     * Check visited elements with a boolean array O(1)
     *
     * Time complexity: O(n * n!)
     * n: the length of a permutation
     * n!: the number of permutations
     *
     * Space complexity: O(n) for recursive call stack
     *
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> permute2(int[] nums) {
        List<List<Integer>> permutations = new ArrayList<>();

        if (nums == null) {
            return permutations;
        }

        if (nums.length == 0) {
            permutations.add(new ArrayList<>());
            return permutations;
        }

        boolean[] isVisited = new boolean[nums.length];
        ArrayList<Integer> permutation = new ArrayList<>();
        helper(nums, permutation, isVisited, permutations);

        return permutations;
    }

    // based on permutation, find all possible permutations and add them to the list
    private void helper2(int[] nums, List<Integer> permutation, boolean[] isVisited, List<List<Integer>> permutations) {
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
            helper2(nums, permutation, isVisited, permutations);
            permutation.remove(permutation.size() - 1);
            isVisited[i] = false;
        }
    }
}

