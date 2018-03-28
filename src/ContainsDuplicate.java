import java.util.Arrays;
import java.util.HashSet;

public class ContainsDuplicate {
    /** Using hashset
     * Time complexity: O(n)
     * Space complexity: O(n
     */
    public boolean Solution1(int[] nums) {
        if (nums == null || nums.length <= 1) return false;
        HashSet<Integer> hashset = new HashSet<>();

        for (int num: nums) {
            if (!hashset.add(num)) return true;
        }

        return false;
    }

    /** Using sort and comparison
     *  Time complexity: O(nlogn)
     *  Space complexity: O(1)
     */
    public boolean Solution2(int[] nums) {
        if (nums == null || nums.length <= 1) return false;
        Arrays.sort(nums);

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1]) {
                return true;
            }
        }

        return false;
    }
}
