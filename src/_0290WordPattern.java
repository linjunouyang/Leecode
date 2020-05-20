import java.util.HashMap;
import java.util.Map;

public class _0290WordPattern {
    /**
     * 1. HashMap
     *
     * How to avoid unnecessary map.containsKey() calls?
     * Map.put(K key, V value) returns the previous value or null if no mapping.
     *
     * The map distinguishes keys with equals, and Character and String objects aaren't equals.
     *
     * Time complexity: O(n)
     * Space compleity: O(n)
     *
     * Runtime: 1 ms, faster than 98.54% of Java online submissions for Word Pattern.
     * Memory Usage: 33.9 MB, less than 97.30% of Java online submissions for Word Pattern.
     *
     * @param pattern
     * @param str
     * @return
     */
    public boolean wordPattern(String pattern, String str) {
        String[] words = str.split(" ");

        if (words.length != pattern.length()) {
            return false;
        }

        Map map = new HashMap<>();

        for (Integer i = 0; i < words.length; i++) {
            if (map.put(pattern.charAt(i), i) != map.put(words[i], i)) {
                return false;
            }
        }

        return true;
    }
}
