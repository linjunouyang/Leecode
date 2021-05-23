import java.util.*;
import java.util.Map;

public class _0219ContainsDuplicateII {
    /**
     * 1. Brute Force
     *
     * Time complexity: O(n * min(k, n))
     * Space compelxity: O(1)
     *
     * Runtime: 287 ms, faster than 14.48% of Java online submissions for Contains Duplicate II.
     * Memory Usage: 40.8 MB, less than 100.00% of Java online submissions for Contains Duplicate II.
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicat0(int[] nums, int k) {
        for (int i = 0; i < nums.length; ++i) {
            for (int j = Math.max(i - k, 0); j < i; ++j) {
                if (nums[i] == nums[j]) return true;
            }
        }
        return false;
    }

    /**
     * 2. HashMap
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 15 ms, faster than 18.82% of Java online submissions for Contains Duplicate II.
     * Memory Usage: 47.3 MB, less than 5.26% of Java online submissions for Contains Duplicate II.
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate1(int[] nums, int k) {
        // number -> positions
        HashMap<Integer, List<Integer>> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            List<Integer> positions = map.getOrDefault(nums[i], new ArrayList<>());
            positions.add(i);
            map.put(nums[i], positions);
        }

        for (Map.Entry<Integer, List<Integer>> entry : map.entrySet()) {
            Integer num = entry.getKey();
            List<Integer> positions = entry.getValue();

            for (int i = 1; i < positions.size(); i++) {
                if (positions.get(i) - positions.get(i - 1) <= k) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * 3. Sliding Window
     *
     * It iterates over the array using a sliding window.
     * The front of the window is at i, the rear of the window is k steps back.
     * The elements within that window are maintained using a Set.
     * While adding new element to the set,
     * if add() returns false, it means the element already exists in the set. At that point, we return true.
     *
     * If the control reaches out of for loop, it means that inner return true never executed, meaning no such duplicate element was found.
     *
     * Time complexity: O(n)
     * Space complexity: O(min(n, k))
     *
     * Runtime: 7 ms, faster than 90.28% of Java online submissions for Contains Duplicate II.
     * Memory Usage: 44 MB, less than 28.95% of Java online submissions for Contains Duplicate II.
     *
     * @param nums
     * @param k
     * @return
     */
    public boolean containsNearbyDuplicate2(int[] nums, int k) {
        Set<Integer> set = new HashSet<Integer>();

        for(int i = 0; i < nums.length; i++){
            if(i > k) {
                set.remove(nums[i - k - 1]);
            }

            if(!set.add(nums[i])) {
                return true;
            }
        }

        return false;
    }






}
