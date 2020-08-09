import java.util.ArrayList;
import java.util.List;

public class _0438FindAllAnagramsInAString {
    /**
     * 1. Two Pointers / Sliding Window
     *
     * Time complexity: O(n)
     * Space complexity: O(1)
     */
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();

        if (s == null || p == null || p.length() == 0) {
            return res;
        }

        int[] map = new int[128];
        for (int i = 0; i < p.length(); i++) {
            map[p.charAt(i)]++;
        }
        int counter = p.length();

        int start = 0;
        int end = 0;


        while (end < s.length()) {
            char c = s.charAt(end);
            if (map[c] > 0) {
                counter--;
            }
            map[c]--;

            while (counter == 0) {
                if (end - start + 1 == p.length()) {
                    res.add(start);
                }

                if (map[s.charAt(start)] >= 0) {
                    counter++;
                }
                map[s.charAt(start)]++;
                start++;
            }

            end++;
        }

        return res;
    }
}
