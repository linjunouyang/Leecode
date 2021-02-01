public class _0268MissingNumber {
    /**
     * 1. Sorting
     *
     * Time: O(nlogn)
     * Space: O(1)
     */

    /**
     * 2. HashSet
     *
     * Time: O(n)
     * Space: O(n)
     */

    /**
     * 3. Gauss's Formula
     *
     * Watch out for overflow
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int leftSum = n * (n + 1) / 2;
        for (int num : nums) {
            leftSum -= num;
        }
        return leftSum;
    }

    /**
     * 4. Bit Manipulation
     *
     * a^b^b =a, two xor operations with the same number will eliminate the number and reveal the original number.
     * In this solution, I apply XOR operation to both the index and value of the array.
     * In a complete array with no missing numbers, the index and value should be perfectly corresponding( nums[index] = index),
     * so in a missing array, what left finally is the missing number.
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int missingNumber2(int[] nums) {
        int res = nums.length;
        for(int i=0; i<nums.length; i++){
            res = res ^ i ^ nums[i]; // a^b^b = a
        }
        return res;
    }

    /**
     * 5. Swapping
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int findMissing3(int[] nums) {
        // write your code here
        int n = nums.length, i = 0;
        while (i<n) {
            while (nums[i]!=i && nums[i]<n) {
                int t = nums[i];
                nums[i] = nums[t];
                nums[t] = t;
            }
            ++i;
        }
        for (i=0; i<n; ++i)
            if (nums[i]!=i) return i;
        return n;
    }

    /**
     * Amazon Follow-up:
     * nums is sorted -> find the first number such that index != nums[index]
     *
     * OR
     *
     * nums contain duplicate -> sort
     */
}
