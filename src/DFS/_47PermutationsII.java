package DFS;

import java.util.*;

public class _47PermutationsII {
    /**
     * 1. DFS
     *
     *
     * Runtime: 3 ms, faster than 33.43% of Java online submissions for Permutations II.
     * Memory Usage: 38.6 MB, less than 98.51% of Java online submissions for Permutations II.
     *
     * @param nums
     * @return
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
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Permutations II.
     * Memory Usage: 39.1 MB, less than 80.60% of Java online submissions for Permutations II.
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
     * For any recursive function, the time complexity is O(branches^depth) * amount of work at each node in the recursive call tree. However, in this case, we have n*(n-1)*(n*2)*(n-3)*...*1 branches at each level = n!, so the total recursive calls is O(n!)
     * We do n-amount of work in each node of the recursive call tree, (a) the for-loop and (b) at each leaf when we add n elements to an ArrayList. So this is a total of O(n) additional work per node.
     * Therefore, the upper-bound time complexity is O(n! * n).
     *
     * We can do some optimizations on the above code:
     *
     * Only considering unique elements
     * Add each unique element (as a key) and its frequency (as the value) to a Hash Table
     * Iterate through each key of the Hash Table, and decrease the quantity of each element we use in each recursive call
     * Advantages of the optimized approach:
     *
     * Reduce the size of the for-loop -- rather than iterating through ALL of the elements of the array, we only need to iterate through the unique elements
     * Sorting step also becomes unnecessary
     * Minimize the space complexity; we don't need to allocate a huge array when we have many duplicates
     * Through some involved math we can probably derive a tighter upper bound for this approach, since we are doing less work at each level, e.g. n amount of work at each of the first level nodes, n-1 amount of work at each of the second level nodes, etc, but for our (interview) purposes the best upper bound is still the same, equal to O(n! * n).
     *
     */

    public List<List<Integer>> permuteUnique3(int[] nums) {
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if(nums == null || nums.length == 0) return results;
        Map<Integer, Integer> map = new HashMap<>();
        for(int n : nums) {
            map.put(n,map.getOrDefault(n,0)+1);
        }
        permuteUniqueHelper3(map, nums.length, new Integer[nums.length], 0, results);
        return results;
    }

    private void permuteUniqueHelper3(Map<Integer,Integer> m, int l, Integer[] p, int i, List<List<Integer>> r) {
        if(i == l) {
            r.add(Arrays.asList(Arrays.copyOf(p,l)));
            return;
        }
        for(int key : m.keySet()) {
            if(m.get(key) > 0) {
                m.put(key,m.get(key)-1);
                p[i] = key;
                permuteUniqueHelper3(m,l,p,i+1,r);
                m.put(key,m.get(key)+1);
            }
        }
    }
}
