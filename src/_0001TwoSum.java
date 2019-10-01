import java.util.HashMap;
import java.util.Map;

public class _0001TwoSum {
    /**
     * 1. Two Pointers
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(1)
     *
     * Runtime: 26 ms, faster than 18.49% of Java online submissions for Two Sum.
     * Memory Usage: 37.1 MB, less than 98.95% of Java online submissions for Two Sum.
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum(int[] nums, int target) {
        int[] res = new int[2];

        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    res[0] = i;
                    res[1] = j;
                    return res;

                    // return new int[] {i, j};
                }
            }
        }

        return res;
    }

    /**
     * 2. HashMap
     *
     * Review on map interface:
     * containsKey
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 2 ms, faster than 98.85% of Java online submissions for Two Sum.
     * Memory Usage: 36.9 MB, less than 99.08% of Java online submissions for Two Sum.
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum2(int[] nums, int target) {
        int[] res = new int[2];

        // num -> index
        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            if (map.containsKey(target - nums[i])) {
                res[0] = map.get(target - nums[i]);
                res[1] = i;
            }

            map.put(nums[i], i);

        }

        return res;
    }



}
