import java.util.Arrays;

public class _0242ValidAnagram {

    /**
     * 0. Sorting
     *
     * Time complexity: O(nlgn)
     * Space complexity:
     *
     * if heapsort is use, sorting costs O(1)
     * toCharArray() costs O(n)
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram1(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }
        char[] str1 = s.toCharArray();
        char[] str2 = t.toCharArray();
        Arrays.sort(str1);
        Arrays.sort(str2);
        return Arrays.equals(str1, str2);
    }

    /**
     * 1. lower letter array iteration
     *
     * Follow up:
     * What if the inputs contain unicode characters? How would you adapt your solution to such case?
     * Unicode contains 65,536 characters -> HashMap
     * (Notice map.entrySet() instead of map.entryset())
     *
     * Time complexity: O(s + t)
     * Space complexity: O(1)
     *
     * Runtime: 3 ms, faster than 93.88% of Java online submissions for Valid Anagram.
     * Memory Usage: 36.9 MB, less than 97.42% of Java online submissions for Valid Anagram.
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isAnagram2(String s, String t) {
        if (s == null || t == null) {
            return false;
        }

        int[] count = new int[26];

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
        }

        for (int i = 0; i < t.length(); i++) {
            count[t.charAt(i) - 'a']--;
        }

        for (int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                return false;
            }
        }

        return true;
    }
}
