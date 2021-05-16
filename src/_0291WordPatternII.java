import java.util.HashMap;

public class _0291WordPatternII {
    /**
     * 1. Backtracking
     *
     * use a character in the pattern to match different length of substrings in the input string,
     * keep trying till we go through the input string and the pattern.
     *
     * Variants/Optimization:
     * 1. not trying all combinations, only try possible ones considering existing mapping and length constraint
     * https://leetcode.com/problems/word-pattern-ii/discuss/73675/*Java*-HashSet-%2B-backtracking-(2ms-beats-100)
     *
     * Time: Probably C(pattern len, s len)
     * Opinion 1: O(s len ^ pattern len)
     * Opinion 2: (s len) * C(pattern len, s len)
     * partition str into len(pattern) groups, and check validity
     *
     * Space:
     * recursion call stack: O(MIN(pattern len, s len))
     * HashMap: O(26) -> O(1)
     */
    public boolean wordPatternMatch(String pattern, String s) {
        HashMap<Character, String> charToStr = new HashMap<>();
        HashMap<String, Character> strToChar = new HashMap<>();

        return backtrackMatch(pattern, 0, s, 0, charToStr, strToChar);
    }

    private boolean backtrackMatch(String pattern, int i, String s, int j,
                                   HashMap<Character, String> charToStr,
                                   HashMap<String, Character> strToChar) {
        if (i == pattern.length() && j == s.length()) {
            return true;
        }

        if (i == pattern.length() || j == s.length()) {
            return false;
        }

//        if (i == pattern.length() && j < s.length()) {
//            return false;
//        }
//
//        if (i < pattern.length() && j == s.length()) {
//            return false;
//        }

        char c = pattern.charAt(i);

        if (charToStr.containsKey(c)) {
            String str = charToStr.get(c);

            if (!str.startsWith(s, j)) {
                return false;
            }
//            int endIdx = j + str.length();
//            if (endIdx > s.length()) {
//                // Don't forget
//                return false;
//            }
//
//            for (int idx = j; idx < endIdx; idx++) {
//                if (s.charAt(idx) != str.charAt(idx - j)) {
//                    return false;
//                }
//            }

            return backtrackMatch(pattern, i + 1, s, j + str.length(), charToStr, strToChar);
        } else {
            for (int idx = j + 1; idx <= s.length(); idx++) {
                String str = s.substring(j, idx);
                if (strToChar.containsKey(str)) {
                    // Don't forget
                    continue;
                }
                charToStr.put(c, str);
                strToChar.put(str, c);

                if (backtrackMatch(pattern, i + 1, s, idx, charToStr, strToChar)) {
                    return true;
                }

                charToStr.remove(c);
                strToChar.remove(str);
            }

            return false;
        }
    }
}
