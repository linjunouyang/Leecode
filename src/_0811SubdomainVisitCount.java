import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Initially, I wrote a lot of repetitive code for finding ' ' and all '.' and update count
 * This could be avoided by before starting to code, talking out loud clearly the solving process
 *
 * Typical mistake:
 * String str = "987"
 * int count;
 * count = count + str.charAt(0); // count will be 57 instead of 9
 *
 *
 */
public class _0811SubdomainVisitCount {
    /**
     * 1. HashMap
     *
     * Time complexity: O(string num * string length)
     * Remember that string hash is not O(1), but in this question,
     * it doesn't change the overall time complexity
     *
     * When you know that there will be exactly one occurrence of " ",
     * you can skip a lot of the associated work that split() does.
     *
     * Space complexity: O(number of (sub)domains)
     */
    public List<String> subdomainVisits(String[] cpdomains) {
        if (cpdomains == null) {
            return new ArrayList<>();
        }

        Map<String, Integer> map = new HashMap<>();
        for (String cpdomain : cpdomains) {
            int count = 0;
            for (int p = 0; p < cpdomain.length(); p++) {
                char c = cpdomain.charAt(p);
                if (c == ' ') {
                    count = Integer.valueOf(cpdomain.substring(0, p));
                }

                if (c == ' ' || c == '.') {
                    String url = cpdomain.substring(p + 1);
                    int oldCount = map.getOrDefault(url, 0);
                    map.put(url, oldCount + count);
                }
            }
        }

        List<String> res = new ArrayList<>();
        for (Map.Entry<String, Integer> e : map.entrySet()) {
            // StringBuilder is faster than: res.add(e.getValue() + " " + e.getKey());
            // 14ms -> 7ms
            StringBuilder sb = new StringBuilder();
            sb.append(e.getValue()).append(" ").append(e.getKey());
            res.add(sb.toString());
        }

        return res;
    }
}
