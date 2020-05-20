import java.util.HashMap;
import java.util.Map;

public class _0294FlipGameII {
    /**
     * 1. Backtracking with memorization (2 is betteR)
     *
     * Time complexity:
     * T(N) = (N-1) * T(N-2) = (N-1) * (N-3) * T(N-4) = (N-1) * (N-3) * (N-5) .... * T(0) = O(N!)
     * Space complexity:
     *
     * Runtime: 9 ms, faster than 81.16% of Java online submissions for Flip Game II.
     * Memory Usage: 40.5 MB, less than 90.00% of Java online submissions for Flip Game II.
     *
     * @param s
     * @return
     */
    public boolean canWin(String s) {
        if (s == null) {
            return false;
        }

        Map<String, Boolean> map = new HashMap<>();
        return canWin(s, map);
    }

    private boolean canWin(String s, Map<String, Boolean> map) {
        if (map.containsKey(s)) {
            return map.get(s);
        }

        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == '+' && s.charAt(i + 1) == '+') {
                // String concatenation is expensive
                String next = s.substring(0, i) + "--" + s.substring(i + 2);
                if (!canWin(next, map)) {
                    map.put(s, true);
                    return true;
                }
            }
        }

        map.put(s, false);
        return false;
    }

    /**
     *
     * 2. Backtracking with memorization
     *
     * No String cancatenation
     *
     * String -> char[] : s.toCharArray()
     * char[] -> String : String.valueOf(char[] data), new String(char[] value)
     *
     * Time complexity:
     * Space complexity:
     *
     * T(N) = (N-1) * T(N-2) = (N-1) * (N-3) * T(N-4) = (N-1) * (N-3) * (N-5) .... * T(0) = O(N!)
     *
     * @param s
     * @return
     */
    public boolean canWin2(String s) {
        HashMap<String, Boolean> h = new HashMap();
        return canWin2(s.toCharArray(), h);
    }

    private boolean canWin2(char[] c, HashMap<String, Boolean> h) {
        for (int i = 1; i < c.length; i++) {
            if (c[i] == '+' && c[i - 1] == '+') {
                c[i] = '-';
                c[i - 1] = '-';

                boolean t;
                String key = String.valueOf(c);

                if (!h.containsKey(key)) {
                    t = canWin2(c, h);
                    h.put(key, t);
                } else {
                    t = h.get(key);
                }   // can not directly use if (t) return true here, cuz you need to restore

                c[i] = '+';
                c[i - 1] = '+';

                if (!t) {
                    return true;
                }
            }
        }

        return false;
    }

}
