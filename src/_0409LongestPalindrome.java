import java.util.HashMap;
import java.util.Map;

public class _0409LongestPalindrome {
    /**
     * 1. HashMap
     *
     * lower case / upper case -> HashMap instead of array
     *
     * Time: O(n)
     * Space: O(1)
     */
    public int longestPalindrome(String s) {
        HashMap<Character, Integer> charToCount = new HashMap<>();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int oldCount = charToCount.getOrDefault(c, 0);
            charToCount.put(c, oldCount + 1);
        }

        int maxLen = 0;
        for (Map.Entry<Character, Integer> entry : charToCount.entrySet()) {
            char c = entry.getKey();
            int count = entry.getValue();

            int increment = count % 2 == 0 ? count : count - 1; // 5->4 1->0
            maxLen += increment;
                // Why not update map?
                // java.util.ConcurrentModificationException
        }

        if (maxLen < s.length()) {
            maxLen++;
        }

        return maxLen;
    }

    /**
     * https://leetcode.com/problems/longest-palindrome/discuss/89604/Simple-HashSet-solution-Java
     * count number of pairs
     * HashSet stores unpaired char
     * 1) if paired, remove that char
     * 2) otherwise, add to HashSet
     */
}
