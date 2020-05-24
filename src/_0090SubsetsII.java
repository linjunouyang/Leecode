import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _0090SubsetsII {
    /**
     * 1. DFS, recursion
     *
     * Complexity Analysis: same as 0078
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        // write your code here
        List<List<Integer>> results = new ArrayList<List<Integer>>();
        if (nums == null || nums.length == 0) {
            results.add(new ArrayList<Integer>());
            return results;
        }
        Arrays.sort(nums);

        List<Integer> subset = new ArrayList<Integer>();
        helper(nums, 0, subset, results);

        return results;
    }

    public void helper(int[] nums, int startIndex, List<Integer> subset, List<List<Integer>> results){
        // 2. 递归的拆解 deep copy
        results.add(new ArrayList<Integer>(subset));

        for(int i=startIndex; i<nums.length; i++){
            if (i != startIndex && nums[i]==nums[i-1]) {
                continue;
            }
            subset.add(nums[i]);
            helper(nums, i+1, subset, results);
            subset.remove(subset.size()-1);
        }

        // 3. 递归出口
    }

    /**
     * 2. backtracking without loop, just recursion
     * @param nums
     * @return
     */
    public List<List<Integer>> subsetsWithDup2(int[] nums) {
        List<List<Integer>> subsets = new ArrayList<>();

        Arrays.sort(nums);
        dfs(nums, 0, -1, new ArrayList<>(), subsets);

        return subsets;
    }

    private void dfs(int[] nums,
                     int index,
                     int lastSelectedIndex,
                     List<Integer> subset,
                     List<List<Integer>> subsets) {
        if (index == nums.length) {
            subsets.add(new ArrayList<Integer>(subset));
            return;
        }

        dfs(nums, index + 1, lastSelectedIndex, subset, subsets);

        if (index > 0 && nums[index] == nums[index - 1] && index - 1 != lastSelectedIndex) {
            // index = 0, index - 1 out of bound
            return;
        }

        subset.add(nums[index]);
        dfs(nums, index + 1, index, subset, subsets);
        subset.remove(subset.size() - 1);
    }



}
