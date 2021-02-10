public class _0169MajorityElement {
    /**
     * 1. HashMap
     *
     * Time: O(n)
     * Space: O(n)
     */

    /**
     * 2. Divide and conquer
     */
    private int countInRange(int[] nums, int num, int lo, int hi) {
        int count = 0;
        for (int i = lo; i <= hi; i++) {
            if (nums[i] == num) {
                count++;
            }
        }
        return count;
    }

    private int majorityElementRec(int[] nums, int lo, int hi) {
        // base case; the only element in an array of size 1 is the majority
        // element.
        if (lo == hi) {
            return nums[lo];
        }

        // recurse on left and right halves of this slice.
        int mid = (hi-lo)/2 + lo;
        int left = majorityElementRec(nums, lo, mid);
        int right = majorityElementRec(nums, mid+1, hi);

        // if the two halves agree on the majority element, return it.
        if (left == right) {
            return left;
        }

        // otherwise, count each element and return the "winner".
        int leftCount = countInRange(nums, left, lo, hi);
        int rightCount = countInRange(nums, right, lo, hi);

        return leftCount > rightCount ? left : right;
    }

    public int majorityElement(int[] nums) {
        return majorityElementRec(nums, 0, nums.length-1);
    }

    /**
     * 3. Boyer-Moore Voting Algorithm
     *
     * https://www.zhihu.com/question/49973163/answer/235921864
     *
     * When count != 0 , it means nums[1...i] has a majority,which is major in the solution.
     * When count == 0 , it means nums[1...i ] doesn't have a majority, so nums[1...i ] will not help nums[1...n].And then we have a subproblem of nums[i+1...n].
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int majorityElement3(int[] nums) {
        int majority = 0;
        int count = 0;

        for (int i = 0; i < nums.length; i++) {
            if (count == 0) {
                majority = nums[i];
            }
            if (nums[i] == majority) {
                count++;
            } else {
                count--;
            }
        }

        return majority;
    }

    /**
     * 4. Bit Manipulation
     *
     * >> Signed right shift:
     * works for positive/negative numbers
     * use sign bit (left most bit) to fill the trailing positions after shift.
     *
     *
     * alternative (counting bits):
     * https://leetcode.com/problems/majority-element/discuss/51649/Share-my-solution-Java-Count-bits
     */
    public int majorityElement4(int[] nums) {
        int[] bit = new int[32];
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < 32; j++) {
                bit[j] += (nums[i] >> j) & 1;
            }
        }
        int majority = 0;
        for (int j = 0; j < 32; j++) {
            if (bit[j] > nums.length / 2) {
                majority += 1 << j;
            }
        }
        return majority;
    }

    /**
     * 5. Sorting
     * https://leetcode.com/problems/majority-element/discuss/51854/My-two-line-java-solution
     */
}
