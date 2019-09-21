package DFS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _78Subsets {

    /**
     *
     * Driver
     *
     * Time complexity: O(n * 2^n)
     * the base: the amount of forks we can make at each decision point
     * the power: the depth of the tree, the amount of choices
     * n: copy each subset is going to take linear time
     *
     * Space complexity: O(2^n) * O(n/2) = O(n * 2^n)
     * the number of subsets O(2^n)
     * the average length of subset O(n/2)
     *
     * we don't always include output in space complexity,
     * if not included, O(n) because of the call stack
     *
     * https://www.youtube.com/watch?time_continue=144&v=3dEVYiyFKac
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets(int[] nums)  {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    /**
     * 1. 在nums找到所有以 subset 开头的集合，放到results
     *
     * When working with collections and objects,
     * always think am I adding a reference or a deep copy
     *
     * backtrack(list, [], nums, 0)
     *   list [[]]
     *   tempList [1]
     *   backtrack(list, [1], nums, 1)
     *     list[[], [1]]
     *     tempList [1,2]
     *     backtrack(list, [1,2], nums, 2)
     *       list[[], [1], [1,2]]
     *       tempList [1, 2, 3]
     *       backtrack(list, [1, 2, 3], nums, 3)
     *         list[[], [1], [1,2], [1,2,3]]
     *       tempList [1, 2]
     *     tempList [1]
     *   tempList []
     *
     *   tempList[2]
     *   backtrack(list, [2], nums, 2)
     *     list [[], [1], [1,2], [1,2,3], [2]]
     *     tempList [2,3]
     *     backtrack(list, [2, 3], nums, 3)
     *       list [[], [1], [1,2], [1,2,3], [2], [2,3]]
     *     tempList [2]
     *   temp []
     *   tempList[3]
     *   backtrack(list, [3], nums, 3)
     *     list [[], [1], [1,2], [1,2,3], [2], [2,3], [3]]
     *   tempList[]
     *
     * @param list
     * @param tempList
     * @param nums
     * @param start
     */
    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int start) {
        // 2. 递归的拆解 deep copy
        list.add(new ArrayList<>(tempList));

        for (int i = start; i < nums.length; i++) {
            // [1] -> [1,2]
            tempList.add(nums[i]);
            // 寻找所有以 [1,2] 开头的集合, 并扔到 results
            backtrack(list, tempList, nums, i + 1);
            // [1,2] -> [1] 回溯
            tempList.remove(tempList.size() - 1);
        }

        // 3. 递归出口
    }

    /**
     * 1. 递归的定义
     * 以 subset 开头的，配上 nums 以 index 开始的数所有组合放到 results 里
     *
     * Runtime: 0 ms, faster than 100.00% of Java online submissions for Subsets.
     * Memory Usage: 37.3 MB, less than 99.18% of Java online submissions for Subsets.
     *
     * @param nums
     * @param index
     * @param subset
     * @param results
     */
    private void dfs(int[] nums, int index, List<Integer> subset, List<List<Integer>> results) {
        // 3. 递归的出口
        // decision point out of array's bound
        if (index == nums.length) {
            results.add(new ArrayList<>(subset));
            return;
        }

        // 2. 递归的拆解（如何进入下一层）
        // decide to include
        subset.add(nums[index]);
        // explore
        dfs(nums, index + 1, subset, results);

        // decide not to include
        subset.remove(subset.size() - 1);
        // explore
        dfs(nums, index + 1, subset, results);
    }
}
