package Lintcode;


public class _49SortLettersBycase {
    /**
     * 1. Two Pointers
     *
     * 'a' = 97
     * 'A' = 65
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * @param chars
     */
    public void sortLetters(char[] chars) {
        if (chars == null) {
            return;
        }

        int lastLowerIndex = -1;

        for (int i = 0; i < chars.length; i++) {
            // chars[i] is a lower case letter
            if (chars[i] - 'A' >= 26) {
                lastLowerIndex++;

                char temp = chars[lastLowerIndex];
                chars[lastLowerIndex] = chars[i];
                chars[i] = temp;
            }
        }

        return;
    }

    // pos = 0, neg 1
    // 0   1  2 3 4 5 6
    // -1 -2 -3 1 2 3 4

    // pos = 0, neg = 3
    // 1 -2 -3 -1 2 3 4

    // pos = 2, neg = 5
    // 1 -2 3 -1 2 -3 4

    // pos = 0, neg = 1
    // 1 2 3 4 -1 -2 -3

    // pos = 4, neg = 1
    // 1 -1 3 4 2 -2 -3

    // pos = 6, neg = 3
    // 1 -1 3 -3 2 -2 3



}
