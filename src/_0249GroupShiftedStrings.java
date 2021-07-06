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
     */
    public List<List<String>> groupStrings(String[] strings) {
        HashMap<String, List<String>> diffsToStrs = new HashMap<>();
        for (String string : strings) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < string.length(); i++) {
                int diff = string.charAt(i - 1) - string.charAt(i);
                if (diff < 0) {
                    // "az" -> 25 "ba" -> -1 + 26 = 2
                    diff += 26;
                }
                sb.append(diff);
            }
            String diffs = sb.toString();
            List<String> strs = diffsToStrs.getOrDefault(diffs, new ArrayList<>());
            strs.add(string);
            diffsToStrs.put(diffs, strs);
        }

        List<List<String>> res = new ArrayList<>();
        for (String diffs : diffsToStrs.keySet()) {
            List<String> strs = diffsToStrs.get(diffs);
            res.add(strs);
        }
        return res;
    }
}
