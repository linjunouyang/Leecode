package HashTable;


import java.util.HashSet;
import java.util.Set;

/**
 * HashTable, Bit manipulation
 *
 * 6.30 Misunderstood the question at first try
 * Solved on the second try.
 * Not familiar with map methods and Map.Entry
 *
 *
 */

public class _136SingleNumber {

    /**
     * 1. HashTable -> Set
     *
     * Since there are only two states for every num: 1 or 2
     * We can use set instead.
     * Existence in set -> 1
     * Non-existence in set -> 2
     *
     * Time complexity: O(n)
     * Space complexity: n/2 elements O(n)
     */
    public int singleNumber(int[] nums) {
        Set<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (!set.add(num)) {
                set.remove(num);
            }
        }

        return set.iterator().next();
    }

    /**
     * 2. Bit manipulation
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public int singleNumber2(int[] nums) {
        int res = 0;

        for (int num : nums) {
            res ^= num;
        }
        
        return res;
    }
}
