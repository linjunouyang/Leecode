public class _0844BackspaceStringCompare {
    /**
     * 1. StringBuilder
     *
     * Time: O(S len + T len)
     * Space: O(S len + T len)
     */
    public boolean backspaceCompare(String S, String T) {
        String s = processStr(S);
        String t = processStr(T);
        return s.equals(t);
    }

    private String processStr(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c == '#') {
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 2. Two Pointers
     *
     * Time: O(max(s len, t len))
     * Space: O(1)
     */
    public boolean backspaceCompare2(String S, String T) {
        int s = S.length() - 1;
        int t = T.length() - 1;

        // start to compare string from the end
        while (s >= 0 && t >= 0) {
            s = nextNonSkipChar(S, s);
            t = nextNonSkipChar(T, t);

            // in case any of two string is already exhausted
            if (s < 0 || t < 0) {
                break;
            }
            // compare two characters
            if (S.charAt(s--) != T.charAt(t--)) {
                return false;
            }
        }

        // DON'T FORGET THIS CASE
        // if any of two string is not exhausted yet,
        // check to see if it can be exhausted
        if (s >= 0) {
            s = nextNonSkipChar(S, s);
        }
        if (t >= 0) {
            t = nextNonSkipChar(T, t);
        }

        // check two see if both string has been exhausted
        return s < 0 && t < 0;
    }

    /*
    helper method to find the next unskippable character in the string and return
    its index. Return -1 if the string is exhausted.
     */
    private int nextNonSkipChar(String str, int end) {
        int skip = 0;
        while (end >= 0) {
            // if current char is #, we need to skip next character
            if (str.charAt(end) == '#') {
                skip++;
                end--;
            }
            // if current character is not #, but we still have some characters
            // need to be skipped
            else if (skip > 0) {
                skip--;
                end--;
            }
            // in case this is the character we cannot skip
            else break;
        }
        return end;
    }
}
