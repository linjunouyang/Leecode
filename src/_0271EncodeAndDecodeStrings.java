import java.util.ArrayList;
import java.util.List;

/**
 *
 * More reading about streams:
 *
 * https://leetcode.com/problems/encode-and-decode-strings/discuss/70451/Java-solution-pretty-straight-forward
 *
 *
 */
public class _0271EncodeAndDecodeStrings {
    /**
     * Encodes a list of strings to a single string
     *
     * It does not matter if there is any / in the string, or any combinations of number with slash.
     * we can use anything to replace the /, as long as it is not a number.
     * Just try to replace it with 'a'.
     * the slash here is just used as a separator, between the length and the actually string.
     * It is act as a boundary to show where the length string ends, when the length has multiple digits.
     * If we know all substring has less than 10 length, we in fact don't have to use any separator like /.
     *
     * Time complexity: O(total length of strings)
     * Space complexity: O(total length of strings)
     *
     * @param strs
     * @return
     */
    public String encode(List<String> strs) {
        if (strs == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        for (String str : strs) {
            sb.append(str.length()).append('/').append(str);
        }

        return sb.toString();
    }

    /**
     * Decodes a single string to a list of strings
     *
     * Java String methods:
     * indexOf(int ch)
     * indexOf(int ch, int fromIndex)
     * indexOf(String str)
     * indexOf(String str, int fromIndex)
     * -> indexOf('/') and indexOf("/") both will work
     *
     * indexOf() time complexity:
     * Worst Case O(m * n) m: length of the pattern, n: length of the search string
     * Typical is O(n). O(m*n) ignores the short-circuiting of matching pattern to current position,
     * which will stop as soon a pattern doesn't match.
     * That would rarely progress past the 3rd character of the pattern.
     * indexOf() even has specific optimization to look for first character before even entering the pattern matching logic.
     * Amortized, it's probably something like O(1.1 * n), so O(n)
     *
     *
     * String.substring() returns a string,
     * to turn it into an Integer,
     * Integer.valueOf()
     *
     * Time complexity: O(length of s)
     * Space complexity: O(number of strings)
     *
     * @param s
     * @return
     */
    public List<String> decode(String s) {
        List<String> strs = new ArrayList<>();

        int i = 0;

        while (i < s.length()) {
            int slash = s.indexOf('/', i);
            int length = Integer.valueOf(s.substring(i, slash));
            i = slash + length + 1;
            strs.add(s.substring(slash + 1, i));
        }

        return strs;


    }
}
