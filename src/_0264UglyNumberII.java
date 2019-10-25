import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Queue;

public class _0264UglyNumberII {

    /**
     * 1. Dynamic Programming
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * @param n
     * @return
     */
    public int nthUglyNumber(int n) {
        int[] nums = new int[n];
        int index2 = 0;
        int index3 = 0;
        int index5 = 0;

        nums[0] = 1;
        for (int i = 1; i < nums.length; i++) {
            nums[i] = Math.min(Math.min(nums[index2] * 2, nums[index3] * 3), nums[index5] * 5);
            if (nums[i] == nums[index2] * 2) {
                index2++;
            }
            if (nums[i] == nums[index3] * 3) {
                index3++;
            }
            if (nums[i] == nums[index5] * 5) {
                index5++;
            }
        }

        return nums[n - 1];
    }

    /**
     * 2. HashSet + PriorityQueue
     *
     * Time complexity: O(nlgn)
     * Space complexity: O(n)
     *
     * Runtime: 100 ms, faster than 5.00% of Java online submissions for Ugly Number II.
     * Memory Usage: 38.3 MB, less than 9.09% of Java online submissions for Ugly Number II.
     *
     *
     */
    public int nthUglyNumber2(int n) {
        PriorityQueue<Long> heap = new PriorityQueue<>();
        HashSet<Long> hash = new HashSet<>();
        Long[] primes = new Long[]{2l, 3l, 5l};

        for (Long prime : primes) {
            heap.add(prime);
            hash.add(prime);
        }

        Long number = 1l;
        for (int i = 1; i < n; i++) {
            number = heap.poll();
            for (Long prime : primes) {
                Long newNum = number * prime;
                if (!heap.contains(newNum)) {
                    heap.add(number * prime);
                    hash.add(number * prime);
                }
            }
        }

        return number.intValue();
    }


}
