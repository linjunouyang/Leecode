import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * At first I thought about binary search on the end.
 * Maybe two pointers and binary search is interchangeable in many problemss
 */
public class _0532K_DiffPairsInAnArray {
    /**
     * 1. Two Pointers
     *
     * Extending the range between left and right pointers
     * when the difference between left and right pointers is less than k
     * (i.e. the range is too small).
     * Therefore, we extend the range (by incrementing the right pointer) when left and right pointer are pointing to the same number.
     *
     * Contracting the range between left and right pointers
     * when the difference between left and right pointers is more than k
     * (i.e. the range is too large).
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(lgn) to O(n) depending on Arrays.sort (some says it's O(n))
     */
    public int findPairs(int[] nums, int k) {

        Arrays.sort(nums);

        int left = 0, right = 1;
        int result = 0;

        while (left < nums.length && right < nums.length) {
            if (left == right || nums[right] - nums[left] < k) {
                // List item 1 in the text
                right++;
            } else if (nums[right] - nums[left] > k) {
                // List item 2 in the text
                left++;
            } else {
                // List item 3 in the text
                result++;
                left++;
                while (left < nums.length && nums[left] == nums[left - 1])
                    left++;
            }
        }
        return result;
    }

    /**
     * 2. HashMap
     *
     * Time complexity: O(n)
     * n: numbers in nums
     *
     * One thing to note about is the hash key lookup.
     * The time complexity for hash key lookup is O(1)
     * but if there are hash key collisions, the time complexity will become O(N). However those cases are rare
     *
     * Space complexity: O(number of unique numbers)
     *
     */
    public int findPairs2(int[] nums, int k) {
        if (nums == null || nums.length == 0 || k < 0)   {
            // k is the 'Absolute' difference
            return 0;
        }

        int result = 0;

        HashMap<Integer,Integer> counter = new HashMap<>();
        for (int n: nums) {
            counter.put(n, counter.getOrDefault(n, 0)+1);
        }


        for (Map.Entry <Integer, Integer> entry: counter.entrySet()) {
            int x = entry.getKey();
            int val = entry.getValue();
            if (k > 0 && counter.containsKey(x + k)) {
                result++;
            } else if (k == 0 && val > 1) {
                result++;
            }
        }
        return result;
    }
}
