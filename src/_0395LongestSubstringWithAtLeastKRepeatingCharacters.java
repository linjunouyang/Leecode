public class _0395LongestSubstringWithAtLeastKRepeatingCharacters {
    /**
     * 1. Sliding Window
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 10 ms, faster than 32.78% of Java online submissions for Longest Substring with At Least K Repeating Characters.
     * Memory Usage: 41.4 MB, less than 10.00% of Java online submissions for Longest Substring with At Least K Repeating Characters.
     *
     * @param s
     * @param k
     * @return
     */
    public int longestSubstring(String s, int k) {
        int d = 0;

        for (int numUniqueTarget = 1; numUniqueTarget <= 26; numUniqueTarget++)
            d = Math.max(d, longestSubstringWithNUniqueChars(s, k, numUniqueTarget));

        return d;
    }

    /**
     * 1.
     * We want to find a window (i to j) which has "numUniqueTarget" unique chars and if all "numUniqueTarget" occur at least K times, we have a candidate solution
     *
     * numUnique == numUniqueTarget
     * numUnique == numNoLessThanK
     *
     * 2.
     * [Rules for window Expansion]
     * so expand (j++) as long as the num of unique characters between 'i' to 'j' are "numUniqueTarget" or less
     *
     * numUnique <= numUniqueTarget
     *
     * 3.
     * during expansion, also track chars that are "noLessThanK" (which occur at least k)
     *
     * 4.
     * end of the loop update max (max len of valid window which have h unique chars and all h have at least k occurences)
     *
     * 5.
     * once we see -- unique = h + 1 -- , we just expanded our window with (h+1)th unique char, so we should start to shrink now.
     * [Rules to window Shrink window] shrink as long as we have unique chars > h (update counts, unique, NoLessThanK along the way)
     *
     * @param s
     * @param k
     * @param numUniqueTarget
     * @return
     */
    private int longestSubstringWithNUniqueChars(String s, int k, int numUniqueTarget) {
        int[] map = new int[128]; // letter occurrence
        int numUnique = 0; // counter 1
        int numNoLessThanK = 0; // counter 2
        int begin = 0, end = 0;
        int maxLength = 0;

        while (end < s.length()) {
            // update occurrence, numUnique, numNoLessThanK, and end pointer (expand window)
            if (map[s.charAt(end)] == 0) {
                numUnique++; // increment map[c] after this statement
            }
            map[s.charAt(end)]++;
            if (map[s.charAt(end)] == k) {
                numNoLessThanK++; // inc end after this statement
            }
            end++;

            // shrink window if too many unique characters.
            // update occurrence, numUnique, numNoLessThanK, and begin pointer (shrink window)
            while (numUnique > numUniqueTarget) {
                if (map[s.charAt(begin)]-- == k) {
                    numNoLessThanK--; // decrement map[c] after this statement
                }
                map[s.charAt(begin)]--;
                if (map[s.charAt(begin)] == 0) {
                    numUnique--; // inc begin after this statement
                }
                begin++;
            }

            // if we found a string where the number of unique chars equals our target
            // and all those chars are repeated at least K times then update max
            if (numUnique == numUniqueTarget && numUnique == numNoLessThanK)
                maxLength = Math.max(end - begin, maxLength);
        }

        return maxLength;
    }

}
