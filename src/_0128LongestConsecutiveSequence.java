import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * TO-DO:
 * Other solutions? Union Find?
 */
public class _0128LongestConsecutiveSequence {

    /**
     * 1. Brute Force
     *
     * Consider each number, attempting to count as high as possible from that number
     *
     *
     * Time complexity: O(n^3)
     * Space complexity: O(1)
     *
     * @param nums
     * @return
     */
    public int longestConsecutive1(int[] nums) {
        int maxLen = 0;

        for (int num : nums) {
            int currentNum = num;
            int curLen = 1;

            while (arrayContains(nums, currentNum + 1)) {
                currentNum += 1;
                curLen += 1;
            }

            maxLen = Math.max(maxLen, curLen);
        }

        return maxLen;
    }

    private boolean arrayContains(int[] arr, int num) {
        for (int number : arr) {
            if (number == num) {
                return true;
            }
        }

        return false;
    }

    /**
     * 2. Sorting
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(1)
     *
     * Runtime: 3 ms, faster than 98.10% of Java online submissions for Longest Consecutive Sequence.
     * Memory Usage: 37.7 MB, less than 74.14% of Java online submissions for Longest Consecutive Sequence.
     *
     * @param nums
     * @return
     */
    public int longestConsecutive2(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Arrays.sort(nums);

        int maxLen = 1;
        int curLen = 1;

        for (int i = 1; i < nums.length; i++) {
            if (nums[i] == nums[i - 1] + 1) {
                curLen++;
                maxLen = Math.max(maxLen, curLen);
            } else if (nums[i] > nums[i - 1] + 1) {
                curLen = 1;
            }
        }

        return maxLen;
    }

    /**
     * 3. HashSet
     *
     * only attempt to build sequences from numbers that are not already part of a longer sequence.
     * This is done by first ensuring that the number that immediately precede the current number in a sequence is not present,
     * as that number would necessarily be part of a longer sequence.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param nums
     * @return
     */
    public int longestConsecutive(int[] nums) {
        Set<Integer> num_set = new HashSet<Integer>();
        for (int num : nums) {
            num_set.add(num);
        }

        int longestStreak = 0;

        for (int num : num_set) {
            if (!num_set.contains(num-1)) {
                int currentNum = num;
                int currentStreak = 1;

                while (num_set.contains(currentNum+1)) {
                    currentNum += 1;
                    currentStreak += 1;
                }

                longestStreak = Math.max(longestStreak, currentStreak);
            }
        }

        return longestStreak;
    }


    /**
     * 3.1 HashSet
     *
     * We have a set of nodes in a graph (nums[]). We want to find the largest connected component.
     * components are connected if they are a consecutive sequence.
     * So starting at each point, go -1 and +1 to find the largest connected component.
     * Use a set to keep track of unvisited.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 6 ms, faster than 84.66% of Java online submissions for Longest Consecutive Sequence.
     * Memory Usage: 38.1 MB, less than 62.07% of Java online submissions for Longest Consecutive Sequence.
     *
     * @param nums
     * @return
     */
    public int longestConsecutive31(int[] nums) {
        Set<Integer> set = new HashSet<Integer>();

        for (int num : nums) {
            set.add(num);
        }

        int max = 0;

        for (int num : nums) {
            if (set.remove(num)) {
                int val = num;
                int sum = 1;

                while (set.remove(val - 1)) {
                    val--;
                }
                sum += num - val;

                val = num;
                while (set.remove(val + 1)) {
                    val++;
                }
                sum += val - num;

                max = Math.max(max, sum);
            }
        }

        return max;
    }

}
