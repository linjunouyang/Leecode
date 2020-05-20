import java.util.Arrays;
import java.util.Comparator;

/**
 * Java String compareTo(): compare two strings lexicographically.
 *
 * Given: 10a + b > 10b + a, 10b + c > 10c + b
 * so: 10b + c + a > 10c + b + a, 10a + b + c > 10b + a + c
 * so: 10a + b + c > 10c + b + a
 * so: 10a + c > 10c + a
 *
 * so this comparison is transitive
 *
 * What to return?
 * We want 'big' strings to come before 'small' strings
 * so that we can append/substring easily (from left to right) later on.
 *
 * s2 + s1 > s1 + s2, compare returns positive, s1 > s2, so s2 comes earlier
 *
 */
class NumbersComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        return (s2 + s1).compareTo(s1 + s2);
    }
}

/**
 * 1. Sort with customized string comparators
 *
 * https://leetcode.com/problems/largest-number/discuss/53158/My-Java-Solution-to-share
 *
 * Time Complexity: O(knlogn)
 *
 * the length of nums is n
 * average length of strings is k

 * merge sort tree: O(logn) levels
 * each comparison takes O(k)
 * each level has O(n) comparisons
 * each level spent O(kn) on comparisons
 *
 *
 */
public class _0179LargestNumber {

    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];

        for (int i = 0; i < nums.length; i++) {
            strs[i] = Integer.toString(nums[i]);
        }

        Arrays.sort(strs, new NumbersComparator());

        if (strs[0].charAt(0) == '0') {
            return "0";
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
        }
        return sb.toString();

    }
}
