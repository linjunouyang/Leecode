import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _00153Sum {
    /**
     * 1. Sorting, Two pointers removing duplicates
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(n) (depends on Arrays.sort)
     *
     * @param nums
     * @return
     */
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();

        if (nums == null) {
            return res;
        }

        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            // exlore first (smallest) number
            if ( i == 0 || nums[i] == nums[i - 1]) {
                continue;
            }

            // explore second and third number
            int left = i + 1;
            int right = nums.length - 1;
            int target = -nums[i];

            twoSum(nums, left, right, target, res);
        }

        return res;
    }

    private void twoSum(int[] nums, int left, int right, int target, List<List<Integer>> results) {
        while (left < right) {
            if (nums[left] + nums[right] == target) {
                ArrayList<Integer> triple = new ArrayList<>();
                triple.add(-target);
                triple.add(nums[left]);
                triple.add(nums[right]);
                results.add(triple);

                left++;
                right--;

                // skip duplicate pairs with the same left
                while (left < right && nums[left] == nums[left - 1]) {
                    left++;
                }

                // skip duplicate pairs with the same right
                while (left < right && nums[right] == nums[right + 1]) {
                    right--;
                }
            } else if (nums[left] + nums[right] < target) {
                left++;
            } else {
                right--;
            }
        }
    }
}
