public class _1328BreakAPalindrome {
    /**
     * 1. Iteration
     *
     * Palindrome: check half of it.
     *
     * Time: O(n)
     * Space: O(n)
     *
     * @param palindrome
     * @return
     */
    public String breakPalindrome(String palindrome) {
        if (palindrome == null || palindrome.length() <= 1) {
            return "";
        }

        char[] arr = palindrome.toCharArray();
        int len = palindrome.length();
        for (int i = 0; i < len / 2; i++) {
            if (arr[i] != 'a') {
                arr[i] = 'a';
                return String.valueOf(arr);
            }
        }

        arr[len-1] = 'b';
        return String.valueOf(arr);
    }
}
