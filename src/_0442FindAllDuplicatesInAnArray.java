import java.util.ArrayList;
import java.util.List;

public class _0442FindAllDuplicatesInAnArray {
    /**
     * 1. Negate number to indicate visited
     *
     * Time: O(n)
     * Space: O(1)
     *
     * @param nums
     * @return
     */
    public List<Integer> findDuplicates(int[] nums) {
        List<Integer> ans = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            if (nums[Math.abs(nums[i]) -  1] < 0) {
                ans.add(Math.abs(nums[i]));
            } else {
                nums[Math.abs(nums[i]) - 1] *= -1;
            }
        }

        return ans;
    }
}
