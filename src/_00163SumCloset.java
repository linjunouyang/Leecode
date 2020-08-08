import java.util.Arrays;

/**
 * Two pointers can (only?) work on sorted list, just like binary search
 *
 * we can't do binary search for all three pointers, because target for each search is not clear
 */
public class _00163SumCloset {
    /**
     * 1. Two pointers
     *
     * Pre-increment is faster than post-increment
     * because post increment keeps a copy of previous value and adds 1 in the existing value
     * while pre-increment is simply adds 1 without keeping the existing value.
     *
     * Possible pruning at three places?
     * https://www.jiuzhang.com/solution/3sum-closest/#tag-lang-java
     *
     * Time complexity: O(n ^ 2)
     * Space complexity: O(logn) to O(n) (depending on Arrays.sort)
     *
     * Follow up:
     * How to reduce space to O(1)
     */
    public int threeSumClosest(int[] nums, int target) {
        int diff = Integer.MAX_VALUE;
        Arrays.sort(nums);
        // selectionSort(nums);
        for (int first = 0; first < nums.length - 2; ++first) {
            // Prune 1
            if (first > 0 && nums[first] == nums[first - 1]) {
                continue;
            }
            int second = first + 1;
            int third = nums.length - 1;
            while (second < third) {
                int sum = nums[first] + nums[second] + nums[third];
                if (sum == target) {
                    return target;
                }
                if (Math.abs(target - sum) < Math.abs(diff)) {
                    diff = target - sum;
                }
                if (sum < target) {
                    ++second;
                    // Prune 2
                    while (second < third && nums[second] == nums[second - 1]){
                        ++second;
                    }
                } else {
                    --third;
                    // Prune 3
                    while (third > second && nums[third] == nums[third + 1]){
                        --third;
                    }
                }
            }
        }
        return target - diff;
    }

    /**
     * Time complexity: O(n ^ 2)
     * Space complexity: O(1)
     */
    private void selectionSort(int[] nums) {
        // i: start of unsorted part
        for (int i = 0; i < nums.length - 1; ++i) {
            int minPos = i;
            for (int j = i + 1; j < nums.length - 1; ++j) {
                if (nums[j] < nums[i]) {
                    minPos = j;
                }
            }
            int temp = nums[i];
            nums[i] = nums[minPos];
            nums[minPos] = temp;
        }
    }

    /**
     * 2. Binary Search
     *
     * Time complexity:
     * Sorting O(nlgn)
     * Three pointers O(n^2 logn)
     *
     * Space complexity: O(logn) to O(n) (depending on Arrays.sort)
     */
    public int threeSumClosest2(int[] nums, int target) {
        if (nums == null) {
            return 0;
        }

        int res = 0;
        boolean isResSet = false;

        Arrays.sort(nums);

        for (int first = 0; first < nums.length; first++) {
            for (int second = first + 1; second < nums.length; second++) {
                int left = target - nums[first] - nums[second];
                int thirdStart = second + 1;
                int thirdEnd = nums.length - 1;
                while (thirdStart <= thirdEnd) {
                    int thirdMid = thirdStart + (thirdEnd - thirdStart) / 2;
                    if (!isResSet || Math.abs(target - res) > Math.abs(target - nums[first] - nums[second] - nums[thirdMid])) {
                        res = nums[first] + nums[second] + nums[thirdMid];
                        isResSet = true;
                    }
                    if (nums[thirdMid] < left) {
                        thirdStart = thirdMid + 1;
                    } else if (nums[thirdMid] > left) {
                        thirdEnd = thirdMid - 1;
                    } else {
                        return target;
                    }
                }
            }
        }

        return res;
    }
}
