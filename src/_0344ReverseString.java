/**
 * https://leetcode.com/problems/reverse-string/discuss/80937/JAVA-Simple-and-Clean-with-Explanations-6-Solutions
 *
 *
 */
public class _0344ReverseString {
    /**
     * 1. Iterative two pointers
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public void reverseString(char[] s) {
        if (s == null) {
            return;
        }

        int left = 0;
        int right = s.length - 1;

        char temp;

        while (left < right) {
            temp = s[left];
            s[left] = s[right];
            s[right] = temp;

            left++;
            right--;
        }
    }

    /**
     * 2. Recursive two pointers
     *
     * Time: O(n)
     * Space: O(n)
     */
}
