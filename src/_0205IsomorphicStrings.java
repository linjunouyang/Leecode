import java.util.HashMap;
import java.util.Map;

public class _0205IsomorphicStrings {
    /**
     * 1. Ascii Array
     *
     * The ASCII table has 128 characters.
     *
     * Store the last seen positions of current character
     *
     * If previously stored positions are different,
     * then they're occuring in the current i-th position simultaneously is a mistake.
     *
     *
     *
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     *
     * Runtime: 5 ms, faster than 80.16% of Java online submissions for Isomorphic Strings.
     * Memory Usage: 37.7 MB, less than 69.30% of Java online submissions for Isomorphic Strings.
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic(String s, String t) {
        int[] m = new int[256];
        for (int i = 0; i < s.length(); i++) {
            if (m[s.charAt(i)] != m[t.charAt(i) + 128]) {
                return false;
            }
            m[s.charAt(i)] = m[t.charAt(i) + 128] = i+1;
        }
        return true;
    }

    /**
     * 2. HashMap
     *
     * put() returns previous stored value
     *
     * You can't compare two Integer with a simple == they're objects so most of the time references won't be the same.
     *
     * There is a trick, with Integer between -128 and 127, references will be the same
     * as autoboxing uses Integer.valueOf() which caches small integers.
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 13 ms, faster than 32.31% of Java online submissions for Isomorphic Strings.
     * Memory Usage: 37.1 MB, less than 100.00% of Java online submissions for Isomorphic Strings.
     *
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic2(String s, String t) {
        Map<Character, Integer> map1 = new HashMap<>();
        Map<Character, Integer> map2 = new HashMap<>();

        for (Integer i = 0; i < s.length(); i++) {
            if (map1.put(s.charAt(i), i) != map2.put(t.charAt(i), i)) {
                return false;
            }
        }

        return true;
    }

    /**
     * 3. Two HashMaps
     *
     * Time complexity: O(n)
     * Space complexity: O(n)
     *
     * Runtime: 20 ms, faster than 14.11% of Java online submissions for Isomorphic Strings.
     * Memory Usage: 37.5 MB, less than 90.35% of Java online submissions for Isomorphic Strings.
     *
     * @param s
     * @param t
     * @return
     */
    public boolean isIsomorphic3(String s, String t) {
        if (s == null || t == null) return false;
        if (s.length() != t.length()) return false;

        Map<Character, Integer> mapS = new HashMap<Character, Integer>();
        Map<Character, Integer> mapT = new HashMap<Character, Integer>();

        for (int i = 0; i < s.length(); i++) {
            int indexS = mapS.getOrDefault(s.charAt(i), -1);
            int indexT = mapT.getOrDefault(t.charAt(i), -1);

            if (indexS != indexT) {
                return false;
            }

            mapS.put(s.charAt(i), i);
            mapT.put(t.charAt(i), i);
        }

        return true;
    }
}
