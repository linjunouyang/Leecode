import java.util.*;
import java.util.Map;

public class _0047PermutationsII {
    /**
     * 1. DFS
     *
     *
     */
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums == null || nums.length == 0) {
            return results;
        }

        Arrays.sort(nums);
        boolean[] isVisited = new boolean[nums.length];
        List<Integer> permutation = new ArrayList<>();

        helper(nums, isVisited, permutation, results);

        return results;
    }

    private void helper(int[] nums, boolean[] isVisited, List<Integer> permutation, List<List<Integer>> results) {
        if (permutation.size() == nums.length) {
            results.add(new ArrayList<>(permutation));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (isVisited[i]) {
                continue;
            }

            // if previous identical number is NOT used, use this number
            if (i != 0 && nums[i] == nums[i - 1] && isVisited[i - 1]) {
                continue;
            }

            isVisited[i] = true;
            permutation.add(nums[i]);

            helper(nums, isVisited, permutation, results);

            isVisited[i] = false;
            permutation.remove(permutation.size() - 1);
        }
    }

    /**
     * 2. DFS
     *
     */
    public List<List<Integer>> permuteUnique2(int[] nums) {
        List<List<Integer>> results = new ArrayList<>();

        if (nums == null || nums.length == 0) {
            return results;
        }

        Arrays.sort(nums);
        boolean[] isVisited = new boolean[nums.length];
        List<Integer> permutation = new ArrayList<>();

        helper(nums, isVisited, permutation, results);

        return results;
    }

    private void helper2(int[] nums, boolean[] isVisited, List<Integer> permutation, List<List<Integer>> results) {
        if (permutation.size() == nums.length) {
            results.add(new ArrayList<>(permutation));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if (isVisited[i]) {
                continue;
            }

            // if previous identical number is used, then use the current number
            if (i != 0 && nums[i] == nums[i - 1] && !isVisited[i - 1]) {
                continue;
            }

            isVisited[i] = true;
            permutation.add(nums[i]);

            helper2(nums, isVisited, permutation, results);

            isVisited[i] = false;
            permutation.remove(permutation.size() - 1);
        }
    }

    /**
     *The worst-case time complexity is O(n! * n).
     *
     * For any recursive function, the time complexity is O(branches^depth) * amount of work at each node in the recursive call tree.
     * However, in this case, we have n*(n-1)*(n*2)*(n-3)*...*1 branches at each level = n!, so the total recursive calls is O(n!)
     * We do n-amount of work in each node of the recursive call tree,
     * (a) the for-loop and (b) at each leaf when we add n elements to an ArrayList. So this is a total of O(n) additional work per node.
     * Therefore, the upper-bound time complexity is O(n! * n).
     *
     * We can do some optimizations on the above code:
     *
     * Only considering unique elements
     * Add each unique element (as a key) and its frequency (as the value) to a Hash Table
     * Iterate through each key of the Hash Table, and decrease the quantity of each element we use in each recursive call
     *
     * Advantages of the optimized approach:
     * Reduce the size of the for-loop -- rather than iterating through ALL of the elements of the array, we only need to iterate through the unique elements
     * Sorting step also becomes unnecessary
     * Minimize the space complexity; we don't need to allocate a huge array when we have many duplicates
     * Through some involved math we can probably derive a tighter upper bound for this approach, since we are doing less work at each level, e.g. n amount of work at each of the first level nodes, n-1 amount of work at each of the second level nodes, etc, but for our (interview) purposes the best upper bound is still the same, equal to O(n! * n).
     *
     * Time: O(n! * n)
     * at most n! permutations, each takes O(n) to build and create a defensive copy
     *
     * Space:
     * HashMap: O(n)
     * permutation: O(n)
     * Recursions: O(n * n) (n depth, each level requires O(n) because of keySet)
     */
    public List<List<Integer>> permuteUnique3(int[] nums) {
        HashMap<Integer, Integer> numToCount = new HashMap<>();
        for (int num : nums) {
            int oldCount = numToCount.getOrDefault(num, 0);
            numToCount.put(num, oldCount + 1);
        }

        List<List<Integer>> permutations = new ArrayList<>();
        List<Integer> permutation = new ArrayList<>();
        choose(numToCount, permutation, nums.length, permutations);
        return permutations;
    }

    private void choose(HashMap<Integer, Integer> numToCount,
                        List<Integer> permutation, int n,
                        List<List<Integer>> permutations) {
        if (permutation.size() == n) {
            permutations.add(new ArrayList<>(permutation));
            return;
        }

        for (int num : numToCount.keySet()) {
            int count = numToCount.get(num);
            if (count == 0) {
                continue;
            }
            numToCount.put(num, count - 1);
            permutation.add(num);

            choose(numToCount, permutation, n, permutations);

            permutation.remove(permutation.size() - 1);
            numToCount.put(num, count);
        }
    }
}
