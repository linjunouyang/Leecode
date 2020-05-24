import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Backtracking general idea:
 *
 * Pick a starting point.
 * while(Problem is not solved)
 *     For each path from the starting point.
 *         check if selected path is safe, if yes select it
 *         and make recursive call to rest of the problem
 *         before which undo the current move.
 *     End For
 * If none of the move works out, return false, NO SOLUTION.
 *
 */
public class _0078Subsets {

    /**
     * 1. DFS, recursion
     *
     * Time complexity: O(n * 2^n)
     * the base: the amount of forks we can make at each decision point
     * the power: the depth of the tree, the amount of choices
     * n: copy each subset is going to take linear time
     *
     * 1. The recursive function is called 2^n times.
     * Because we have 2 choices at each iteration in nums array.
     * Either we include nums[i] in the current set, or we exclude nums[i].
     * This array nums is of size n = number of elements in nums.
     *
     * 2. We need to create a copy of the current set
     * because we reuse the original one to build all the valid subsets.
     * This copy costs O(n) and it is performed at each call of the recursive function,
     * which is called 2^n times as mentioned in above. So total time complexity is O(n x 2^n).
     *
     * Space complexity: O(2^n) * O(n/2) = O(n * 2^n)
     * the number of subsets O(2^n)
     * the average length of subset O(n/2)
     *
     * - For recursion: max depth the call stack is going to reach at any time is length of nums, n.
     * - For output: we're creating 2^n subsets where the average set size is n/2
     * (for each A[i], half of the subsets will include A[i], half won't) = n/2 * 2^n = O(n * 2^n).
     * Or in a different way, the total output size is going to be the summation of the binomial coefficients,
     * the total number of r-combinations we can make at each r size * r elements from 0..n which evaluates to n*2^n.
     * More informally, at size 0, how many empty sets can we make from n elements,
     * then at size 1 how many subsets of 1 elements can we make from n elements,
     * at size 2, how many subsets of 2 elements can we make ... at size n, etc.
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

        if (nums == null || nums.length == 0) {
            return list;
        }

        // Arrays.sort(nums);
        backtrack(list, new ArrayList<>(), nums, 0);
        return list;
    }

    /**
     * 1. 在nums找到所有以 subset 开头的集合，放到results
     *
     * When working with collections and objects,
     * always think about adding a reference or a deep copy
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
     * Basically the same idea as above, not using loop
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

    /**
     * 2. BFS
     *
     * or Think in dynamic programming:
     * Suppose S(i) is the super set or sub array a[0], a[1], .., a[i]. Then S(i+1) = merge(S(i), Set of (each set in S(i) + a[i+1]))
     *
     * Time complexity: O(n * 2^n)
     * Space complexity: O(n * 2^n)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets2(int[] nums) {
        if (nums == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> queue = new ArrayList<>();
        queue.add(new LinkedList<Integer>());
        //Arrays.sort(nums);

        for (int num : nums) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                List<Integer> subset = new ArrayList<>(queue.get(i));
                subset.add(num);
                queue.add(subset);
            }
        }

        return queue;
    }


    /**
     * 3. Another BFS
     *
     * []
     * [1] [2] [3]
     * [1, 2] [1, 3] [2, 3]
     * [1, 2, 3]
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> subsets3(int[] nums) {
        if (nums == null) {
            return new ArrayList<>();
        }

        List<List<Integer>> queue = new ArrayList<>();
        int index = 0;

        Arrays.sort(nums);
        queue.add(new LinkedList<Integer>());
        while (index < queue.size()) {
            List<Integer> subset = queue.get(index++);
            for (int i = 0; i < nums.length; i++) {
                if (subset.size() != 0 && subset.get(subset.size() - 1) >= nums[i]) {
                    // We won't add 2 to [1, 3], or 1 to [2, 3]
                    continue;
                }
                List<Integer> newSubset = new ArrayList<>(subset);
                newSubset.add(nums[i]);
                queue.add(newSubset);
            }
        }

        return queue;

    }

}
