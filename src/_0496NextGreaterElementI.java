import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

public class _0496NextGreaterElementI {
    /**
     * 0. Brute Force:
     *
     * a. for every num in nums1, find its idx in nums2 -> O(n)
     *  b. Iterate the right remaining part of nums2, find next greater element -> O(n)
     *
     * Speed up:
     * a: HashMap<number, index in nums2>
     * b: O(n) is needed, but instead of doing this for every num, we can pre-process and find all ans in O(n)
     * with nextGreater[] arr (index: index in nums2, value: next greater
     *
     * Space some space:
     * HashMap<Integer, Integer> number -> next greater number
     *
     * Time: O(len1 * len2)
     * Space: O(1)
     */

    /**
     * 1. Monotonous Decreasing Stack
     *
     * Why decreasing:
     * Because if subsequence is increasing, we can just update ans on the fly.
     * For decreasing subsequence, we don't have ans for it so we need to store it somehow
     *
     * Why stack instead of queue:
     * because the current num might be only larger than some of elements in the decreasing subsequence
     *
     * Time: O(len1 + len2)
     * Space: O(len1 + len2)
     */
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        int len2 = nums2.length;
        Deque<Integer> stack = new ArrayDeque<>();
        HashMap<Integer, Integer> numToNextGreater = new HashMap<>();

        for (int i = 0; i < len2; i++) {
            while (!stack.isEmpty() && stack.peek() < nums2[i]) {
                numToNextGreater.put(stack.pop(), nums2[i]);
            }

            stack.push(nums2[i]);
        }

        while (!stack.isEmpty()) {
            numToNextGreater.put(stack.pop(), -1);
        }

        int len1 = nums1.length;
        int[] ans = new int[len1];
        for (int i = 0; i < len1; i++) {
            ans[i] = numToNextGreater.get(nums1[i]);
        }

        return ans;
    }
}
