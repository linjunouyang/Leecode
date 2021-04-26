import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class _0003LongestSubstringWithoutRepeatingCharacters {
    /**
     * 1. Sliding Window
     *
     * A sliding window is an abstract concept commonly used in array/string problems.
     * A window is a range of elements in the array/string which usually defined by the start and end indices, i.e. [i, j)[i,j) (left-closed, right-open).
     * A sliding window is a window "slides" its two boundaries to the certain direction.
     * For example, if we slide [i, j) to the right by 1 element,
     * then it becomes [i+1, j+1)[i+1,j+1) (left-closed, right-open).
     *
     * We use HashSet to store the characters in current window [i, j) (j = i initially).
     * Then we slide the index j to the right.
     * If it is not in the HashSet, we slide j further.
     * Doing so until s[j] is already in the HashSet.
     * At this point, we found the maximum size of substrings without duplicate characters start with index i.
     * If we do this for all ii, we get our answer.
     *
     * Time complexity: O(2n) = O(n)
     * Worst case: each character visited twice by i and j;
     *
     * Space complexity: O(min(26, length of s)
     */
    public int lengthOfLongestSubstring(String s) {
        int n = s.length();

        Set<Character> set = new HashSet<>();

        int maxLength = 0;
        int i = 0;
        int j = 0;

        while (i < n - maxLength && j < n) {
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j++));
                maxLength = Math.max(maxLength, j - i);
            } else {
                set.remove(s.charAt(i++));
            }
        }

        return maxLength;
    }

    /**
     * 2. Sliding Window Optimized
     *
     * if input="abba"
     *
     * Why Math.max(i, ...)
     * when i = 3, s.chatAt(3) == 'a' ,
     * map: 'a' -> 0,
     * but we should not update j from 2 ('b') to 0 ('a'),
     * because here although the 'a' is in hashMap, but it appears before 'b'.
     *
     * Time complexity: O(n)
     * Space complexity: O(min(26, length of s)
     */
    public int lengthOfLongestSubstring2(String s) {
        int n = s.length();

        int maxLength = 0;
        Map<Character, Integer> map = new HashMap<>();

        for (int i = 0, j = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(i, map.get(s.charAt(j)) + 1);
            }
            map.put(s.charAt(j), j);
            maxLength = Math.max(maxLength, j - i + 1);
        }

        return maxLength;
    }

    /**
     * 3. Optimized for Letters, ASCII, and Extended ASCII
     *
     * int[26] for letters 'a' - 'z' or 'A' - 'Z'
     * int[128] for ASCII
     * int[256] for Extended ASCII
     *
     * ----
     *
     * Consider "abcabcbb"
     * when i = 0, j = 3
     * i = Math.max(0, 0) = 0 -> ans = 4
     *
     * so we should set index[s.charAt(j)] = j + 1
     *
     * ----
     *
     * We can't set i = Math.max(index[s.charAt(j)] + 1, i);
     * consider " ", output will be 0;
     *
     *
     *
     *
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring3(String s) {
        int n = s.length(), ans = 0;
        int[] index = new int[128]; // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            i = Math.max(index[s.charAt(j)], i);
            ans = Math.max(ans, j - i + 1);
            index[s.charAt(j)] = j + 1;
        }
        return ans;
    }
}
