/**
 * Similar questions and template: 0076
 *
 */
public class _0159LongestSubstringWithAtMostTwoDistinctCharacters {

    /**
     * 1. Two pointers / Sliding Windows
     *
     * Be careful about where to update result:
     * One thing needs to be mentioned is that when asked to find maximum substring,
     * we should update maximum after the inner while loop to guarantee that the substring is valid.
     * On the other hand, when asked to find minimum substring, we should update minimum inside the inner while loop.
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     */
    public int lengthOfLongestSubstringTwoDistinct(String s) {
        if (s == null) {
            return 0;
        }

        int start = 0;
        int end = 0;
        int counter = 0; // number of distinct chars
        int maxLength = 0;

        // Or we define it as the last position of every character
        // and we initialize every entry to -1 (not showing)
        //
        int[] map = new int[128]; // char -> occurence

        while (end < s.length()) {
            char c = s.charAt(end);
            if (map[c] == 0) {
                counter++;
            }
            map[c]++;

            while (counter > 2) {
                char c1 = s.charAt(start);
                if (map[c1] == 1) {
                    counter--;
                }
                map[c1]--;
                start++;
            }

            maxLength = Math.max(maxLength, end - start + 1);
            end++;
        }

        return maxLength;
    }
}
