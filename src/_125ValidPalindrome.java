

public class _125ValidPalindrome {
    /**
     * 1. Two Pointers
     *
     * https://leetcode.com/problems/valid-palindrome/discuss/40029/Accepted-pretty-Java-solution(271ms)
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 3 ms, faster than 95.82% of Java online submissions for Valid Palindrome.
     * Memory Usage: 38 MB, less than 89.29% of Java online submissions for Valid Palindrome.
     *
     * @param s
     * @return
     */
    public boolean isPalindrome(String s) {
        int head = 0;
        // String.length()
        int tail = s.length() - 1;

        char cHead;
        char cTail;

        while (head < tail) {
            cHead = s.charAt(head);
            cTail = s.charAt(tail);

            if (!Character.isLetterOrDigit(cHead)) {
                head++;
            } else if (!Character.isLetterOrDigit(cTail)) {
                tail--;
            } else {
                // don't forget to toLowerCase
                if (Character.toLowerCase(cHead) != Character.toLowerCase(cTail)) {
                    return false;
                }
                head++;
                tail--;
            }
        }

        return true;
    }
}
