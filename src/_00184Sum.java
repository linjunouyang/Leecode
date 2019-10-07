import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class _00184Sum {
    /**
     * 1. 4 pointers
     *
     * Time complexity: O(n ^ 3)
     * Space complexity: O(1)
     *
     * Runtime: 11 ms, faster than 83.60% of Java online submissions for 4Sum.
     * Memory Usage: 37.6 MB, less than 100.00% of Java online submissions for 4Sum.
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum(int[] nums, int target) {
        List<List<Integer>> res = new ArrayList<>();

        if (nums == null || nums.length < 4) {
            return res;
        }

        Arrays.sort(nums);

        // early exit
        if (4 * nums[0] > target || 4 * nums[nums.length - 1] < target) {
            return res;
        }

        // explore first number, * stops at nums.length - 4 *
        for (int a = 0; a < nums.length - 3; a++) {
            // avoid duplicates
            if (a != 0 && nums[a] == nums[a - 1]) {
                continue;
            }

            // explore second number, * stops at nums.length - 3 *
            for (int b = a + 1; b < nums.length - 2; b++) {
                // avoid duplicates: * b != a + 1 * instead of * b != 0*
                if (b != a + 1 && nums[b] == nums[b - 1]) {
                    continue;
                }

                int c = b + 1;
                int d = nums.length - 1;

                while (c < d) {
                    int sum = nums[a] + nums[b] + nums[c] + nums[d];

                    if (sum < target) {
                        c++;
                    } else if (sum > target) {
                        d--;
                    } else {
                        List<Integer> combo = new ArrayList<>();
                        combo.add(nums[a]);
                        combo.add(nums[b]);
                        combo.add(nums[c]);
                        combo.add(nums[d]);
                        res.add(combo);

                        c++;
                        d--;

                        // avoid duplicates
                        while (c < d && nums[c] == nums[c - 1]) {
                            c++;
                        }

                        // avoid duplicates
                        while (c < d && nums[d] == nums[d + 1]) {
                            d--;
                        }
                    }
                }
            }
        }

        return res;
    }

    // hashmap? https://www.jiuzhang.com/solutions/4sum/#tag-other


    /**
     * 2. Generalized K Sum
     *
     * Time complexity: O(N ^ (k-1))
     * Space complexity: O(k - 1)
     *
     * @param nums
     * @param target
     * @return
     */
    public List<List<Integer>> fourSum1(int[] nums, int target) {
        Arrays.sort(nums);
        return kSum(nums, 0, 4, target);
    }

    private List<List<Integer>> kSum(int[] nums, int start, int k, int target) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();

        if (k == 2) {
            int left = start;
            int right = len - 1;

            while (left < right) {
                int sum = nums[left] + nums[right];

                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    List<Integer> path = new ArrayList<>();
                    path.add(nums[left]);
                    path.add(nums[right]);
                    res.add(path);

                    // avoid duplicates
                    while (left < right && nums[left] == nums[left + 1]) {
                        left++;
                    }

                    while (left < right && nums[right] == nums[right -1 ]) {
                        right--;
                    }

                    left++;
                    right--;
                }
            }
        } else {
            for (int i = start; i < len - (k - 1); i++) {
                // remove duplicates
                if (i > start && nums[i] == nums[i - 1]) {
                    continue;
                }

                List<List<Integer>> temp = kSum(nums, i + 1, k - 1, target - nums[i]);

                // add previous number
                for (List<Integer> t: temp) {
                    t.add(0, nums[i]);
                }

                res.addAll(temp);
            }
        }
        return res;
    }

//    public List<List<Integer>> fourSum2(int[] nums, int target) {
//        // https://leetcode.com/problems/4sum/discuss/8653/On-average-O(n2)-and-worst-case-O(n3)-java-solution-by-reducing-4Sum-to-2Sum
//    }
}
