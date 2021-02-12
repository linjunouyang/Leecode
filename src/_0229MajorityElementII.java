import java.util.ArrayList;
import java.util.List;

public class _0229MajorityElementII {
    /**
     * Voting algorithm
     * https://leetcode.com/problems/majority-element-ii/discuss/63537/My-understanding-of-Boyer-Moore-Majority-Vote
     *
     * delete triplets
     * 1 2
     * (1 2 3)
     * (1 2 3)
     * (1 2 3)
     * (1 2 3)
     *
     * 3 * k <= n
     * k <= n/3
     * by definition ME appears more than floor(n/3) times
     * so by the end of traversal, candidate1 and candidate2 could be majority elements
     *
     * (1 2 3)
     * even counter is set to 0, but num = 1, 2
     * we need to examine again to valid candidates
     *
     * Time: O(n)
     * Space: O(1)
     */
    public List<Integer> majorityElement(int[] nums) {
        List<Integer> res = new ArrayList<>();
        if(nums.length == 0)
            return res;

        int num1 = nums[0];
        int num2 = nums[0];
        int count1 = 0;
        int count2 = 0;

        for (int val : nums) {
            if (val == num1) {
                count1++;
            } else if (val == num2) {
                count2++;
            } else if (count1 == 0) {
                num1 = val;
                count1++;
            } else if (count2 == 0) {
                num2 = val;
                count2++;
            } else {
                count1--;
                count2--;
            }
        }

        count1 = 0;
        count2 = 0;
        for (int val : nums) {
            if (val == num1) {
                count1++;
            } else if (val == num2) {
                count2++;
            }
        }
        if (count1 > nums.length/3) {
            res.add(num1);
        }
        if (count2 > nums.length/3) {
            res.add(num2);
        }
        return res;
    }
}
