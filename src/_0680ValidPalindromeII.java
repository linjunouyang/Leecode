/**
 * https://leetcode.com/problems/valid-palindrome-ii/discuss/391809/Java-Solutions-to-Valid-Palindrome-I-and-II-with-Explanation-(SubPalindrome-Iteration-and-Recursion)
 */
public class _0680ValidPalindromeII {
    /**
     * 1. Two Pointers
     *
     * Time: O(n)
     * Space: O(1)
     */
    public boolean validPalindrome(String s) {
        if (s == null) {
            return true;
        }

        int left = 0;
        int right = s.length() - 1;

        while (left < right) {
            char c1 = s.charAt(left);
            char c2 = s.charAt(right);
            if (c1 == c2) {
                left++;
                right--;
            } else {
                return (palindromeHelper(s, left + 1, right) || palindromeHelper(s, left, right - 1));
            }
        }

        return true;
    }

    private boolean palindromeHelper(String s, int start, int end) {
        int left = start;
        int right = end;

        while (left < right) {
            if (s.charAt(left) == s.charAt(right)) {
                left++;
                right--;
            } else {
                return false;
            }
        }

        return true;
    }

    /**
     * 2. Recursion
     *
     * https://leetcode.com/problems/valid-palindrome-ii/discuss/107717/C%2B%2BJava-Clean-Code-2-liner-Generic-for-%22you-may-delete-at-most-N-character%22
     */
}
