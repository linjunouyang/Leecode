import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class _0249GroupShiftedStrings {

    /**
     * 1. HashMap with customized key
     *
     * Java Map.values: O(n), here n is the number of the lists
     *
     * If we only need to read each character,
     * String.charAt(int i) is better than String.toCharArray();
     * StringBuilder is more efficient than String concatenation;
     *
     * Time complexity: O(N K)
     * N: number of words
     * K: longest length
     *
     * Space complexity: O(N)
     *
     * Runtime: 1 ms, faster than 100.00% of Java online submissions for Group Shifted Strings.
     * Memory Usage: 36.6 MB, less than 100.00% of Java online submissions for Group Shifted Strings.
     *
     * @param strings
     * @return
     */
    public List<List<String>> groupStrings(String[] strings) {
        if (strings == null) {
            return new ArrayList<>();
        }

        Map<String, List<String>> map = new HashMap<>();

        for(String s : strings) {
            String key = getKey(s);
            List<String> list = map.getOrDefault(key, new ArrayList<>());
            list.add(s);
            map.put(key, list);
        }
        return new ArrayList<>(map.values());
    }

    private String getKey(String s) {
        if (s == null) {
            return "null";
        } else if (s.isEmpty()) {
            return "zero";
        }

        StringBuilder builder = new StringBuilder();

        for(int i = 1; i < s.length(); i++) {
            int diff = s.charAt(i) - s.charAt(i-1);
            builder.append(diff < 0 ? diff + 26 : diff);
        }

        return builder.toString();
    }
}
