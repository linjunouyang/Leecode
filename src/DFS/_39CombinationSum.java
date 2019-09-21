package DFS;

import java.util.ArrayList;
import java.util.List;

public class _39CombinationSum {

    /**
     * 1. DFS
     *
     * Runtime: 5 ms, faster than 49.00% of Java online submissions for Combination Sum.
     * Memory Usage: 37.5 MB, less than 100.00% of Java online submissions for Combination Sum.
     *
     * Time complexity:
     * O(答案总数 * 构造每个答案的时间）
     * O(s * target)
     *
     * Space complexity:
     * O(target)
     * 每一层都是1
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> combinationSum(int[] nums, int target) {
        List<List<Integer>> list = new ArrayList<>();
        backtrack(list, new ArrayList<>(), nums, target, 0);
        return list;
    }

    // 1. 递归的定义
    // 从 nums 的 start 开始挑一些数，放到 combination 中，且他们的和为 remain
    private void backtrack(List<List<Integer>> list, List<Integer> tempList, int[] nums, int remain, int start) {
        // 3. 递归的出口
        if (remain < 0) {
            return;
        }

        if (remain == 0) {
            list.add(new ArrayList<>(tempList));
            return;
        }

        // current tempList as the root
        // 2. 递归的拆解 [1, 2], [1, 3], [1, 4] ... [1, 5]
        for (int i = start; i < nums.length; i++) {
            // each iteration is an exploration of a specific branch

            // [1] -> [1, 2]
            tempList.add(nums[i]);

            // 把所有[1, 2]开头的剩余的和为remain-nums[i]的集合，都找到，放入到results
            backtrack(list, tempList, nums, remain - nums[i], i);

            // [1, 2] -> [1]
            tempList.remove(tempList.size() - 1);
        }
    }

}
